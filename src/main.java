import ccmgpso.CCMGPSO;
import problems.*;
import problems.dtlz.*;
import problems.wfg.*;
import problems.zdt.*;

public class main {
    public static void main(String[] args) {
        String problemName="dtlz1"; // Specify the problem name here. Currently, wfg1-9, dtlz1-7, zdt1-4 and zdt6 are supported
        int numDimensions=2000; // Specify the number of decision variables you want on this problem
        int numObjectives=2; /* Specify the number of objectives you want on this problem. You may skip this part
                                if you have picked any of the zdt problems. */

        int numDimensionGroups=30; // Specify the number of groups you want the decision variables to be partitioned into
        int numContextVectors=2; //Specify the number of context vectors
        int iterationsPerObjective=10; //Specify the lambda parameter (as described in the CCMGPSO paper)
        int archiveSize=50; //Specify the archive size
        int tournamentSize=3; // Specify the tournament size for tournament selection in the archive guide retrieval process.
        int numIterations=1; // Specify the total number of iterations
        int numParticles=100;
        int numberOfRuns=1; // specify how many times do you want to run this problem instance

        new runner( numDimensions,  numObjectives,  numDimensionGroups,  numContextVectors,
                    iterationsPerObjective,  archiveSize,  tournamentSize,  numIterations,
                    numParticles,  numberOfRuns,  problemName);
    }

}