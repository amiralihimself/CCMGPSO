import ccmgpso.CCMGPSO;
import problems.*;
import problems.dtlz.*;
import problems.wfg.*;
import problems.zdt.*;

public class main {
    public static void main(String[] args) {
        int numDimensions=2000;
        int numObjectives=2;
        int numDimensionGroups=30;
        int numContextVectors=2;
        int iterationsPerObjective=10; //this is the lambda parameter
        int archiveSize=50;
        int tournamentSize=3;
        int numIterations=2000;
        int numParticles=100;
        int runNumber=1; // this is a parameter for keeping track of the number of times wwe have run a specific instance
        Problem optimizationProblem= new DTLZ1(numDimensions, numObjectives);
        new CCMGPSO(numDimensions, numObjectives, numDimensionGroups, numContextVectors, iterationsPerObjective, optimizationProblem, archiveSize, tournamentSize, numIterations, numParticles,  runNumber).algorithm();
    }

}