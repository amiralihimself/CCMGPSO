import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
public class CCMGPSO extends cooperativeMGPSO{
    private int numOfEvaluations;
    private swarm[] swarmsNDimensional;
    protected int[] previousGlobalBest;
    private int numParticles;
    private int iterationsPerObjective=5;
    private swarm[] miniSubSwarms;
    private double [][] allContextVectors;
    private double [][] contextVectorObjectives;
    private int runNumber;
    private int[] temp;
    int counter;
    public CCMGPSO(int numDimensions,
                   int numObjectives,
                   int numDimensionGroups,
                   int numContextVectors,
                   int iterationsPerObjective,
                   problem optimizationProblem,
                   int archiveSize,
                   int tournamentSize,
                   int numIterations,
                   int numParticles, int runNumber){
        this.numDimensionGroups=numDimensionGroups;
        this.numDimensions=numDimensions;

        this.c1=0.0;
        this.c2=0.0;
        this.c3=0.0;
        this.omega=0.0;
        this.numObjectives=numObjectives;
        this.optimizationProblem=optimizationProblem;
        this.tournamentSize=tournamentSize;
        this.archiveSize=archiveSize;
        Archive=new archive(archiveSize, numDimensions, numObjectives);
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
    protected void algorithm() {
        String fileName="";

        fileName=optimizationProblem.getProblemName().toUpperCase().concat(".").concat(Integer.toString(numObjectives)).concat("D.pf");
        qualityIndicator QualityIndicator=new qualityIndicator(fileName);
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
        QualityIndicator.calculateAndSaveMetrics((LinkedList) Archive.getArchiveParticles().clone());
        System.out.println(Archive.getArchiveSize());
        new JsonSimpleWriter().saveFinal(runNumber, optimizationProblem.getProblemName(), Archive.getArchiveParticles(), numDimensions, numObjectives, typeOfFunction, QualityIndicator.getIGD(), 0);
        System.out.println(numOfEvaluations);
        System.out.println(Arrays.toString(temp));
        System.out.println(counter);
    }

}