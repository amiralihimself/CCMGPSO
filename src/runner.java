/**
 This is the runner class, which runs CCMGPSO with the parameters specified by the user
 in the main method. This class contains all parameters of the experiments described in
 the CCMGPSO paper.
 **/

import problems.Problem;
import ccmgpso.*;
import problems.wfg.*;
import problems.dtlz.*;
import problems.zdt.*;

public class runner {

    public runner(int numDimensions, int numObjectives, int numDimensionGroups, int numContextVectors,
                  int iterationsPerObjective, int archiveSize, int tournamentSize, int numIterations,
                  int numParticles, int numberOfRuns, String problemName){

            Problem optimizationProblem= getProblemHandle(problemName, numDimensions, numObjectives);

            for (int runNumber=1; runNumber<= numberOfRuns; runNumber++){
                System.out.println("Run number " + runNumber + " on " + optimizationProblem.getProblemName() + " with " + numDimensions + " dimensions and " + numObjectives + " objectives");
                new CCMGPSO(numDimensions, numObjectives, numDimensionGroups, numContextVectors, iterationsPerObjective, optimizationProblem, archiveSize, tournamentSize, numIterations, numParticles,  runNumber).algorithm();
            }

    }


    Problem getProblemHandle(String problemName, int numDimensions, int numObjectives){
        Problem optimzationProblem;
        int k= Math.floorDiv(numDimensions, 5);

        switch (problemName.toLowerCase()){

            case "wfg1":
                optimzationProblem=new WFG1( k,numDimensions-k, numObjectives);
                break;

            case "wfg2":
                optimzationProblem=new WFG2( k,numDimensions-k, numObjectives);
                break;

            case "wfg3":
                optimzationProblem=new WFG3( k,numDimensions-k, numObjectives);
                break;

            case "wfg4":
                optimzationProblem=new WFG4( k,numDimensions-k, numObjectives);
                break;

            case "wfg5":
                optimzationProblem=new WFG5( k,numDimensions-k, numObjectives);
                break;

            case "wfg6":
                optimzationProblem=new WFG6( k,numDimensions-k, numObjectives);
                break;

            case "wfg7":
                optimzationProblem=new WFG7( k,numDimensions-k, numObjectives);
                break;

            case "wfg8":
                optimzationProblem=new WFG8( k,numDimensions-k, numObjectives);
                break;

            case "wfg9":
                optimzationProblem=new WFG9( k,numDimensions-k, numObjectives);
                break;

            case "dtlz1":
                optimzationProblem=new DTLZ1(numDimensions, numObjectives);
                break;

            case "dtlz2":
                optimzationProblem=new DTLZ2(numDimensions, numObjectives);
                break;

            case "dtlz3":
                optimzationProblem=new DTLZ3(numDimensions, numObjectives);
             break;

            case "dtlz4":
                optimzationProblem=new DTLZ4(numDimensions, numObjectives);
                break;

            case "dtlz5":
                optimzationProblem=new DTLZ5(numDimensions, numObjectives);
                break;

            case "dtlz6":
                optimzationProblem=new DTLZ6(numDimensions, numObjectives);
                break;

            case "dtlz7":
                optimzationProblem=new DTLZ7(numDimensions, numObjectives);
                break;

            case "zdt1":
                optimzationProblem=new ZDT1(numDimensions);
                break;

            case "zdt2":
                optimzationProblem=new ZDT2(numDimensions);
                break;

            case "zdt3":
                optimzationProblem=new ZDT3(numDimensions);
                break;

            case "zdt4":
                optimzationProblem=new ZDT4(numDimensions);
                break;

            case "zdt6":
                optimzationProblem=new ZDT6(numDimensions);
                break;

            default:
                throw new IllegalArgumentException("Invalid problem name.");

        }
    return optimzationProblem;
    }

}
