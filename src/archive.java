import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class archive {
    private int archiveSize;
    private LinkedList<particle> archiveParticles = new LinkedList<>();
    private int num_dimensions;
    private int num_objectives;

    public archive(int archiveSize, int num_dimensions, int num_objectives){
        this.archiveSize=archiveSize;
        new LinkedList<>();
        this.num_dimensions=num_dimensions;
        this.num_objectives=num_objectives;
    }
    public LinkedList<particle> getArchiveParticles() {
        return archiveParticles;
    }

    public void removeTheWorstSolution(){
        updateCrowdingDistances();
        int indexToBeRemoved=0;
        double minimumCrowdingDistance=0;
        for (int particleIndex=0; particleIndex<archiveParticles.size();particleIndex++){
            if(particleIndex==0){
                indexToBeRemoved=0;
                minimumCrowdingDistance=archiveParticles.get(0).crowdingDistance;
            }
            else{
                if(archiveParticles.get(particleIndex).crowdingDistance<minimumCrowdingDistance){
                    minimumCrowdingDistance=archiveParticles.get(particleIndex).crowdingDistance;
                    indexToBeRemoved=particleIndex;

                }
            }
        }

        archiveParticles.remove(indexToBeRemoved);

    }

    public void maintainArchive(){
        double[] maximumPerObjective=new vectorOperators().getMaximumOfEachObjective(archiveParticles.toArray(new particle[archiveParticles.size()]), num_objectives);
        double[]  minimumPerObjective=new vectorOperators().getMinimumOfEachObjective(archiveParticles.toArray(new particle[archiveParticles.size()]), num_objectives);
        double[] maxMinusMin= new vectorOperators().elementWiseSubtraction(maximumPerObjective, minimumPerObjective);
        ArrayList<double[]> normalizedArchive= new ArrayList<>();
        for (int i=0; i< archiveParticles.size(); i++){
            double [] objectives= archiveParticles.get(i).getObjectives().clone();
            double [] part1=new vectorOperators().elementWiseSubtraction(objectives, minimumPerObjective);
            double [] normalizedObjectives= new vectorOperators().elementWiseDivision(part1, maxMinusMin);
            for(int j=0; j< normalizedObjectives.length; j++){
                if(maxMinusMin[j]==0){
                    normalizedObjectives[j]=0.25;
                    //normalizedObjectives[j]= ((double)1)/ (double) normalizedArchive.size();
                }
            }
            normalizedArchive.add(normalizedObjectives);
        }
        applyDominanceResistanceError(normalizedArchive);
        removeDominatedSolutions(normalizedArchive);
    }

    private void applyDominanceResistanceError(ArrayList<double[]> normalizedArchive){
        for (int i=0; i< normalizedArchive.size(); i++){
            double [] normalizedObjectives= normalizedArchive.get(i);
            for(int j=0; j< num_objectives; j++){
                if (normalizedObjectives[j]< 3e-6){
                    normalizedObjectives[j]=0;
                }
            }
        }
    }

    private void removeDominatedSolutions(ArrayList<double[]> normalizedArchive){

        int [] dominationCounter= new int[normalizedArchive.size()];
        ArrayList<Integer> indicesToRemove=new ArrayList<>();
        for (int solution1=0; solution1< normalizedArchive.size(); solution1++){
            double[] objectives1= normalizedArchive.get(solution1);
            for (int solution2=0; solution2< normalizedArchive.size(); solution2++){
                double[] objectives2= normalizedArchive.get(solution2);
                if(solution1!= solution2){

                    if(checkIfDominates(objectives2, objectives1)){

                        dominationCounter[solution1]++;
                    }
                }
            }

            if (dominationCounter[solution1]>0){
                indicesToRemove.add(solution1);
            }

        }
        Collections.reverse(indicesToRemove);
        for (int i=0; i< indicesToRemove.size();i++){

            int indexToRemove= indicesToRemove.get(i);
            normalizedArchive.remove(indexToRemove);
            archiveParticles.remove(indexToRemove);
        }

    }
    private void removeDominatedSolutions(){

        int [] dominationCounter= new int[archiveParticles.size()];
        ArrayList<Integer> indicesToRemove=new ArrayList<>();
        for (int solution1=0; solution1< archiveParticles.size(); solution1++){
            double[] objectives1= archiveParticles.get(solution1).getObjectives();
            for (int solution2=0; solution2< archiveParticles.size(); solution2++){
                double[] objectives2= archiveParticles.get(solution2).getObjectives();
                if(solution1!= solution2){

                    if(checkIfDominates(objectives2, objectives1)){

                        dominationCounter[solution1]++;
                    }
                }
            }

            if (dominationCounter[solution1]>0){
                indicesToRemove.add(solution1);
            }

        }
        Collections.reverse(indicesToRemove);
        for (int i=0; i< indicesToRemove.size();i++){

            int indexToRemove= indicesToRemove.get(i);
            archiveParticles.remove(indexToRemove);
        }

    }

    public void addToArchive(double [] objectives, double [] positions){
        double [] prospectiveParticle=objectives.clone();
        double [] position= positions.clone();
        // first, let's check if the prospective particle is dominated by any other particles already in the archive
        boolean newParticleIsDominated= false;
        for (int particleIndex=0; particleIndex<archiveParticles.size(); particleIndex++){
            double [] archiveParticleValues=archiveParticles.get(particleIndex).getObjectives();
            if(checkIfDominates(archiveParticleValues, prospectiveParticle) || Arrays.equals(archiveParticleValues, prospectiveParticle)){
                newParticleIsDominated=true;
                break;
            }


        }

        if(!newParticleIsDominated){

            //now that we are here, ne particle is not dominated by any of the particles in the archive
            // let's remove particles that are dominated by this new particle
            LinkedList<particle> archiveParticlesTemp= new LinkedList<>();
            for (int index=0; index< archiveParticles.size(); index++){
                double [] archiveParticleValues= archiveParticles.get(index).getObjectives();
                if(!checkIfDominates(prospectiveParticle, archiveParticleValues)){
                    archiveParticlesTemp.add((archiveParticles.get(index)));
                }
            }
            particle newParticle =new particle(prospectiveParticle, position);
            archiveParticlesTemp.add(newParticle);
            archiveParticles= (LinkedList) archiveParticlesTemp.clone();
            // if the number of the particles in the archive exceeds the maximum, remove the worst (the lowest crowding distance)
            if (archiveParticles.size()>archiveSize){
                removeTheWorstSolution();
            }
            updateCrowdingDistances();
        }

    }
    public void updateCrowdingDistances(){
        for (int i=0; i<archiveParticles.size();i++){
            archiveParticles.get(i).crowdingDistance=0;
        }
        // now for each objective I have to sort the archive based on its (the objective's) value
        for(int objective=0; objective<num_objectives;objective++){
            final int objectiveIndex=objective;
            Collections.sort(archiveParticles, new Comparator<particle>() {
                @Override
                public int compare(particle o1, particle o2) {
                    return Double.compare(o1.getObjectives()[objectiveIndex],o2.getObjectives()[objectiveIndex]);
                }
            });

            archiveParticles.get(0).crowdingDistance=Double.MAX_VALUE;
            archiveParticles.getLast().crowdingDistance=Double.MAX_VALUE;
            // so that the boundary point always get selected
            for (int particleIndex=1; particleIndex<archiveParticles.size()-1; particleIndex++){
                if(archiveParticles.get(particleIndex).crowdingDistance!=Double.MAX_VALUE){
                    //to avoid overflow
                    double minimumValue=archiveParticles.get(0).getObjectives()[objectiveIndex];
                    double maximumValue=archiveParticles.getLast().getObjectives()[objectiveIndex];

                    double valueOnTheLeft=archiveParticles.get(particleIndex-1).getObjectives()[objectiveIndex];
                    double valueOnTheRight=archiveParticles.get(particleIndex+1).getObjectives()[objectiveIndex];
                    archiveParticles.get(particleIndex).crowdingDistance += ((valueOnTheRight-valueOnTheLeft));


                }
            }


        }

    }
    public boolean checkIfDominates(double [] solution1, double[] solution2){
        // this returns true if solution1 dominates solution2
        boolean dominated=false;
        int numberOfBetterObjectives=0;
        int objective=0;
        for (; objective<num_objectives;objective++){
            if (solution1[objective]>solution2[objective]){
                break;
            }
            else{
                if(solution1[objective]<solution2[objective]){
                    numberOfBetterObjectives=numberOfBetterObjectives+1;
                }
            }
        }
        if(objective==num_objectives&& numberOfBetterObjectives>0){
            dominated=true;
        }
        return dominated ;
    }

}

