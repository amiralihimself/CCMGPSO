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
}

