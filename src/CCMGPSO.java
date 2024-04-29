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


}