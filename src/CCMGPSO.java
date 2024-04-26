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

}