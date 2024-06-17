package ccmgpso;
import problems.problem;
import toolbox.archive;
import toolbox.particle;
import toolbox.swarm;
import toolbox.vectorOperators;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
public class CCMGPSO{
    private int numOfEvaluations;
    private swarm[] swarmsNDimensional;
    protected int[] previousGlobalBest;
    private int numParticles;
    private int iterationsPerObjective=5;
    private swarm[] miniSubSwarms;
    private double [][] allContextVectors;
    private double [][] contextVectorObjectives;
    private int runNumber;
    private int numDimensions;
    private int numObjectives;
    private int[] temp;
    private int tournamentSize;
    private int counter;
    private double c1;
    protected int numIterations;
    private String typeOfFunction;
    private double c2;
    private double c3;
    private double omega;
    private int archiveSize;
    private int numDimensionGroups;
    private problem optimizationProblem;
    private archive Archive;

    public CCMGPSO(int numDimensions, int numObjectives,
                   int numDimensionGroups, int numContextVectors,
                   int iterationsPerObjective, problem optimizationProblem,
                   int archiveSize, int tournamentSize,
                   int numIterations, int numParticles, int runNumber){


        this.numDimensionGroups=numDimensionGroups;
        this.numDimensions=numDimensions;
        this.numObjectives=numObjectives;
        this.optimizationProblem=optimizationProblem;
        this.tournamentSize=tournamentSize;
        this.archiveSize=archiveSize;
        Archive=new archive(archiveSize, numDimensions, numObjectives);
        this.numDimensionGroups=numDimensionGroups;
        this.numDimensions=numDimensions;
        this.c1=0.0;
        this.c2=0.0;
        this.c3=0.0;
        this.omega=0.0;
        this.optimizationProblem=optimizationProblem;
        this.numIterations=numIterations;
        typeOfFunction="CCMGPSO";
        numOfEvaluations=0;
        previousGlobalBest=new int[numObjectives];
        this.miniSubSwarms=new swarm[numDimensionGroups];
        swarmsNDimensional= new swarm[numObjectives];
        this.numParticles=numParticles;
        this.allContextVectors=new double[numContextVectors][numDimensions];
        this.iterationsPerObjective= iterationsPerObjective ;
        this.contextVectorObjectives=new double[numContextVectors][numObjectives];
        this.runNumber=runNumber;
        temp=new int[numContextVectors];
        counter=0;

    }
    private void initSwarms(){
        int[] dimensionIndicesShuffled=new vectorOperators().Fisher_YatesShuffle(numDimensions);
        int K_1= numDimensions % numDimensionGroups;
        miniSubSwarms=new swarm[numDimensionGroups];
        int dimensionsOfFirstGroup= (int) Math.ceil((double) numDimensions/ (double) numDimensionGroups);
        int dimensionsOfSecondGroup= (int) Math.floor((double) numDimensions/ (double) numDimensionGroups);
        int currentIndex=0;
        for(int dimensionGroup=0; dimensionGroup<numDimensionGroups; dimensionGroup++){
            swarm Swarm;
            if (dimensionGroup<K_1){
                Swarm= new swarm( numParticles, dimensionsOfFirstGroup,  numObjectives,  omega,
                        optimizationProblem,
                        Arrays.copyOfRange(dimensionIndicesShuffled, currentIndex,currentIndex+dimensionsOfFirstGroup));

                currentIndex+=dimensionsOfFirstGroup;

            }

            else {
                Swarm= new swarm( numParticles,  dimensionsOfSecondGroup,  numObjectives,  omega,
                        optimizationProblem,
                        Arrays.copyOfRange(dimensionIndicesShuffled, currentIndex,currentIndex+dimensionsOfSecondGroup));

                currentIndex+=dimensionsOfSecondGroup;


            }
            miniSubSwarms[dimensionGroup]=Swarm;

        }
    }
    private void initContextVectors(){

        for (int i=0; i<allContextVectors.length;i++){
            for(int dimension=0;dimension<numDimensions;dimension++){

                double lowerBound=optimizationProblem.getLowerBoundForDimension(dimension);
                double upperBound=optimizationProblem.getUpperBoundForDimension(dimension);
                allContextVectors[i][dimension]=lowerBound+ (upperBound-lowerBound)*new Random().nextDouble();

            }
            contextVectorObjectives[i]=optimizationProblem.evaluate(allContextVectors[i]);

        }

    }

    private double [] b(int contextVectorIndex, int[] dimensionIndices, double[] valuesToUse){
        double [] contextVector= allContextVectors[contextVectorIndex].clone();

        for (int index=0; index <dimensionIndices.length; index++){

            contextVector[dimensionIndices[index]]=valuesToUse[index];
        }

        return contextVector;
    }
    public void algorithm() {

        initSwarms();
        initContextVectors();
        for (int objective=0; objective< numObjectives; objective++){
            swarm Swarm2=  new swarm( 10,  numDimensions,  numObjectives,
                    optimizationProblem);
            swarmsNDimensional[objective]=Swarm2;}
        int objectiveToMinimize=-1;
        int contextVectorIndex=0;

        for (int iteration=0; iteration<numIterations;iteration++){
            //Archive.printArchive();
            //  System.out.println(runNumber);
            // System.out.println(iteration);
            for (int objectiveIndex=0; objectiveIndex< numObjectives; objectiveIndex++){
                swarm Swarm= swarmsNDimensional[objectiveIndex];
                int randomIndex  = ThreadLocalRandom.current().nextInt(0, Swarm.getNum_particles());
                Swarm.getParticle(randomIndex).setPosition(allContextVectors[contextVectorIndex].clone());
                double globalBest=Swarm.getGlobalBest();
                for (int particleIndex=0; particleIndex < Swarm.getNum_particles(); particleIndex++){
                    particle Particle= Swarm.getParticle(particleIndex);
                    double[] currentPosition= Particle.getPosition().clone();
                    double [] evaluationResult= optimizationProblem.evaluate(currentPosition);
                    double personalBest= Particle.getPbest();
                    numOfEvaluations++;
                    if (evaluationResult[objectiveIndex] < personalBest){
                        Particle.setPbest(evaluationResult[objectiveIndex]);
                        Particle.setPbest_position(currentPosition.clone());

                    }
                    if (evaluationResult[objectiveIndex] <globalBest){
                        Swarm.setGlobalBest(evaluationResult[objectiveIndex]);
                        Swarm.setGbestPosition(currentPosition.clone());

                    }


                    Particle.setObjectives(evaluationResult.clone());
                    Archive.addToArchive(evaluationResult.clone(), currentPosition.clone());
                }

            }
            if(iteration%(numObjectives*iterationsPerObjective)==0){
                initSwarms();
            }
            if (iteration%iterationsPerObjective==0){
                resetPersonalBests();
                contextVectorIndex  = ThreadLocalRandom.current().nextInt(0, allContextVectors.length);
                temp[contextVectorIndex]+=iterationsPerObjective;
            }
            int temp= iteration/iterationsPerObjective;
            objectiveToMinimize=temp%numObjectives;

            runMiniSubSwarms(objectiveToMinimize, contextVectorIndex);
            updateVelocity( contextVectorIndex);
            updatePosition();
            updateVelocityNDimensional();
            updatePositionNDimensional();
        }


        Archive.printArchive();
        System.out.println(numOfEvaluations);
        System.out.println(Arrays.toString(temp));
        System.out.println(counter);
    }

    private void runMiniSubSwarms(int objectiveToMinimize, int contextVectorIndex) {
        double contextFitness2 = contextVectorObjectives[contextVectorIndex][objectiveToMinimize];
        if(contextFitness2==0){
            counter++;
        }
        for (int dimensionGroup = 0; dimensionGroup < numDimensionGroups; dimensionGroup++) {
            swarm Swarm = miniSubSwarms[dimensionGroup];
            int numParticles = Swarm.getNum_particles();
            int[] dimensionIndices = Swarm.getDimensionGroupIndices();
            for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
                particle Particle = Swarm.getParticle(particleIndex);
                double[] currentPosition = Particle.getPosition().clone();
                double[] vectorToEvaluate = b(contextVectorIndex, dimensionIndices, currentPosition);
                double[] evaluationResult = optimizationProblem.evaluate(vectorToEvaluate);
                numOfEvaluations++;
                double personalBest = Particle.getPbest();

                if (evaluationResult[objectiveToMinimize] < personalBest) {
                    Particle.setPbest(evaluationResult[objectiveToMinimize]);
                    Particle.setPbest_position(currentPosition);
                }
                double contextFitness = contextVectorObjectives[contextVectorIndex][objectiveToMinimize];

                if (evaluationResult[objectiveToMinimize] < contextFitness) {
                    allContextVectors[contextVectorIndex] = vectorToEvaluate.clone();
                    contextVectorObjectives[contextVectorIndex]=evaluationResult.clone();
                }
                else if (evaluationResult[objectiveToMinimize] == contextFitness){

                    if(!new vectorOperators().checkIfDominates(contextVectorObjectives[contextVectorIndex].clone(), evaluationResult)){
                        allContextVectors[contextVectorIndex] = vectorToEvaluate.clone();
                        contextVectorObjectives[contextVectorIndex]=evaluationResult.clone();
                    }

                }


                Particle.setObjectives(evaluationResult.clone());
                Archive.addToArchive(evaluationResult, vectorToEvaluate.clone());


            }

        }
    }

    protected void updatePosition() {
        for (int dimensionGroup=0; dimensionGroup< numDimensionGroups; dimensionGroup++){
            swarm Swarm=miniSubSwarms[dimensionGroup];
            int [] dimensionIndices=Swarm.getDimensionGroupIndices();
            int numParticles= Swarm.getNum_particles();
            for(int particleIndex=0; particleIndex<numParticles; particleIndex++){
                particle Particle= Swarm.getParticle(particleIndex);
                double [] currentPosition=Particle.getPosition();
                double [] velocity= Particle.getVelocity().clone();
                double [] newPosition= new double[dimensionIndices.length];

                for (int dimension=0; dimension< dimensionIndices.length; dimension++){
                    newPosition[dimension]=currentPosition[dimension]+velocity[dimension];
                    double lowerBound=optimizationProblem.getLowerBoundForDimension(dimensionIndices[dimension]);
                    double upperBound=optimizationProblem.getUpperBoundForDimension(dimensionIndices[dimension]);

                    if(newPosition[dimension]<lowerBound){
                        newPosition[dimension]=lowerBound;
                    }
                    if(newPosition[dimension]>upperBound){
                        newPosition[dimension]=upperBound;
                    }

                }
                Particle.setPosition(newPosition.clone());
            }


        }

    }
    protected void updateVelocity() {
        for (int dimensionGroup=0; dimensionGroup< numDimensionGroups; dimensionGroup++){
            swarm Swarm= miniSubSwarms[dimensionGroup];
            int [] dimensionIndices=Swarm.getDimensionGroupIndices();
            int numParticles= Swarm.getNum_particles();
            double [] globalBest= Swarm.getGbestPosition();
            for(int particleIndex=0; particleIndex<numParticles; particleIndex++){
                particle Particle= Swarm.getParticle(particleIndex);
                double [] personalBest= Particle.getPbest_position();
                double [] currentPosition=Particle.getPosition();
                double lambda= Particle.getLambda();

                Random randomr1= new Random();
                Random randomr2= new Random();
                Random randomr3=new Random();
                double [] velocity= Particle.getVelocity();
                double [] newVelocity= new double[dimensionIndices.length];
                double [] archiveGuide= new vectorOperators().trimArray(Archive.getArchiveGuide(tournamentSize), dimensionIndices);
                for (int dimension=0; dimension< dimensionIndices.length; dimension++){
                    double cognitiveTerm;
                    double socialTerm;
                    double archiveTerm;
                    double r1=randomr1.nextDouble();
                    double r2=randomr2.nextDouble();
                    double r3=randomr3.nextDouble();

                    cognitiveTerm=r1*this.c1*(personalBest[dimension]-currentPosition[dimension]);
                    socialTerm=r2*this.c2*lambda*(globalBest[dimension]-currentPosition[dimension]);
                    archiveTerm=r3*this.c3*(1-lambda)*(archiveGuide[dimension]-currentPosition[dimension]);
                    newVelocity[dimension]=(this.omega*velocity[dimension])+cognitiveTerm+socialTerm+archiveTerm;

                }
                Particle.setVelocity(newVelocity);

            }

        }
    }
    protected void updateVelocity( int contextIndex) {
        for (int dimensionGroup=0; dimensionGroup< numDimensionGroups; dimensionGroup++){
            swarm Swarm= miniSubSwarms[dimensionGroup];
            int [] dimensionIndices=Swarm.getDimensionGroupIndices();
            int numParticles= Swarm.getNum_particles();
            double[] globalBest=new vectorOperators().trimArray(allContextVectors[contextIndex].clone(),dimensionIndices);

            for(int particleIndex=0; particleIndex<numParticles; particleIndex++){
                particle Particle= Swarm.getParticle(particleIndex);
                double [] personalBest= Particle.getPbest_position();
                double [] currentPosition=Particle.getPosition();
                double lambda= Particle.getLambda();
                Random randomr1= new Random();
                Random randomr2= new Random();
                Random randomr3=new Random();
                double [] velocity= Particle.getVelocity();

                double [] newVelocity= new double[dimensionIndices.length];
                double [] archiveGuide= new vectorOperators().trimArray(Archive.getArchiveGuide(tournamentSize), dimensionIndices);

                c1=1.5+ (2-1.5)*new Random().nextDouble();
                c2=1.5+ (2-1.5)*new Random().nextDouble();
                c3=1.5+ (2-1.5)*new Random().nextDouble();
                omega=0.1+ (0.5-0.1)*new Random().nextDouble();
                for (int dimension=0; dimension< dimensionIndices.length; dimension++){

                    double cognitiveTerm;
                    double socialTerm;
                    double archiveTerm;
                    double r1=randomr1.nextDouble();
                    double r2=randomr2.nextDouble();
                    double r3=randomr3.nextDouble();
                    cognitiveTerm=r1*this.c1*(personalBest[dimension]-currentPosition[dimension]);
                    socialTerm=r2*this.c2*lambda*(globalBest[dimension]-currentPosition[dimension]);
                    archiveTerm=r3*this.c3*(1-lambda)*(archiveGuide[dimension]-currentPosition[dimension]);
                    newVelocity[dimension]=(this.omega*velocity[dimension])+cognitiveTerm+socialTerm+archiveTerm;

                }

                Particle.setVelocity(newVelocity);

            }

        }
    }

    protected void updatePositionNDimensional() {
        for (int objective=0; objective< numObjectives; objective++){
            swarm Swarm= swarmsNDimensional[objective];
            int numParticles= Swarm.getNum_particles();

            for(int particleIndex=0; particleIndex<numParticles; particleIndex++){
                particle Particle= Swarm.getParticle(particleIndex);
                double [] currentPosition=Particle.getPosition();
                double [] velocity= Particle.getVelocity().clone();
                double [] newPosition= new double[numDimensions];
                for (int dimension=0; dimension< numDimensions; dimension++){
                    newPosition[dimension]=currentPosition[dimension]+velocity[dimension];
                    double lowerBound=optimizationProblem.getLowerBoundForDimension(dimension);
                    double upperBound=optimizationProblem.getUpperBoundForDimension(dimension);

                    if(newPosition[dimension]<lowerBound){
                        newPosition[dimension]=lowerBound;

                    }
                    if(newPosition[dimension]>upperBound){
                        newPosition[dimension]=upperBound;

                    }

                }
                ((particle) Particle).setPosition(newPosition);
            }

        }
    }

    protected void updateVelocityNDimensional() {
        for (int objective=0; objective< numObjectives; objective++){
            swarm Swarm= swarmsNDimensional[objective];
            int numParticles= Swarm.getNum_particles();
            double [] globalBest= Swarm.getGbestPosition();
            for(int particleIndex=0; particleIndex<numParticles; particleIndex++){
                particle Particle= Swarm.getParticle(particleIndex);
                double [] personalBest= Particle.getPbest_position();
                double [] currentPosition=Particle.getPosition();
                double lambda= Particle.getLambda();
                Random randomr1= new Random();
                Random randomr2= new Random();
                Random randomr3=new Random();
                double [] velocity= Particle.getVelocity();
                double [] newVelocity= new double[numDimensions];
                double [] archiveGuide= Archive.getArchiveGuide(tournamentSize);



                c1=1.5+ (2-1.5)*new Random().nextDouble();
                c2=1.5+ (2-1.5)*new Random().nextDouble();
                c3=1.5+ (2-1.5)*new Random().nextDouble();
                omega=0.1+ (0.5-0.1)*new Random().nextDouble();
                for (int dimension=0; dimension< numDimensions; dimension++){
                    double cognitiveTerm;
                    double socialTerm;
                    double archiveTerm;
                    double r1=randomr1.nextDouble();
                    double r2=randomr2.nextDouble();
                    double r3=randomr3.nextDouble();

                    cognitiveTerm=r1*this.c1*(personalBest[dimension]-currentPosition[dimension]);
                    socialTerm=r2*this.c2*lambda*(globalBest[dimension]-currentPosition[dimension]);
                    archiveTerm=r3*this.c3*(1-lambda)*(archiveGuide[dimension]-currentPosition[dimension]);

                    newVelocity[dimension]=(this.omega*velocity[dimension])+cognitiveTerm+socialTerm+archiveTerm;

                }
                Particle.setVelocity(newVelocity);

            }

        }
    }

    private void resetPersonalBests(){
        for(int dimensionGroup=0; dimensionGroup< numDimensionGroups;dimensionGroup++){
            for (int particleIndex=0; particleIndex< numParticles; particleIndex++){
                miniSubSwarms[dimensionGroup].getParticle(particleIndex).setPbest(Double.MAX_VALUE);
            }
        }

    }


}



