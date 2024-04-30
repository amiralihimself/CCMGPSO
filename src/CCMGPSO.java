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


}