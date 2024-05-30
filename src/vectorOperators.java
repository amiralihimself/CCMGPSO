// This class contains implementations of some vector operations commonly used in Evolutionary Algorithms
// such as mutation, crossovoer, etc.
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


public class vectorOperators {

    public double[] getMaximumOfEachObjective(particle[] particles, int numObjectives){
        double[] arrayToReturn= new double[numObjectives];

        for (int objectiveIndex=0; objectiveIndex< numObjectives; objectiveIndex++){
            double maximumOfObjective=-1*Double.MAX_VALUE;
            for(int particleIndex=0; particleIndex<particles.length; particleIndex++){
                double[] objectiveVector=particles[particleIndex].getObjectives();
                if(objectiveVector[objectiveIndex] > maximumOfObjective){
                    maximumOfObjective=objectiveVector[objectiveIndex];
                }

            }
            arrayToReturn[objectiveIndex]=maximumOfObjective;

        }
        return arrayToReturn;
    }

    public double[] getMinimumOfEachObjective(particle[] particles, int numObjectives){
        double[] arrayToReturn= new double[numObjectives];

        for (int objectiveIndex=0; objectiveIndex< numObjectives; objectiveIndex++){
            double minimumOfObjective=Double.MAX_VALUE;
            for(int particleIndex=0; particleIndex<particles.length; particleIndex++){
                double[] objectiveVector=particles[particleIndex].getObjectives();
                if(objectiveVector[objectiveIndex] < minimumOfObjective){
                    minimumOfObjective=objectiveVector[objectiveIndex];
                }

            }
            arrayToReturn[objectiveIndex]=minimumOfObjective;

        }
        return arrayToReturn;
    }

    public double [] elementWiseSubtraction(double [] a, double[] b){
        double [] arrayToReturn=new double[a.length];

        for (int dimensionIndex=0; dimensionIndex< a.length; dimensionIndex++){
            arrayToReturn[dimensionIndex]=a[dimensionIndex]-b[dimensionIndex];
        }
        return arrayToReturn;
    }
    public double [] elementWiseAddition(double [] a, double[] b){
        double [] arrayToReturn=new double[a.length];

        for (int dimensionIndex=0; dimensionIndex< a.length; dimensionIndex++){
            arrayToReturn[dimensionIndex]=a[dimensionIndex]+b[dimensionIndex];
        }
        return arrayToReturn;
    }
    public double [] elementWiseMul(double [] a, double[] b){
        double [] arrayToReturn=new double[a.length];

        for (int dimensionIndex=0; dimensionIndex< a.length; dimensionIndex++){
            arrayToReturn[dimensionIndex]=a[dimensionIndex]*b[dimensionIndex];
        }
        return arrayToReturn;
    }
    public double [] elementWiseDivision(double [] a, double[] b){
        double [] arrayToReturn=new double[a.length];
        for (int dimensionIndex=0; dimensionIndex< a.length; dimensionIndex++){
            arrayToReturn[dimensionIndex]=a[dimensionIndex]/b[dimensionIndex];
        }
        return arrayToReturn;
    }

    public double magnitude(double [] vector){
        double result=0;
        for (int i=0; i<vector.length; i++){
            result=result+(double)(Math.pow(vector[i],2));
        }
        result= (double) Math.sqrt(result);

        return result;
    }

    public boolean checkIfDominates(double [] solution1, double[] solution2){
        // this returns true if solution1 dominates solution2
        boolean dominated=false;
        int numberOfBetterObjectives=0;
        int objective=0;
        for (; objective< solution1.length;objective++){
            if (solution1[objective]>solution2[objective]){
                break;
            }
            else{
                if(solution1[objective]<solution2[objective]){
                    numberOfBetterObjectives=numberOfBetterObjectives+1;
                }
            }
        }
        if(objective== solution1.length&& numberOfBetterObjectives>0){
            dominated=true;
        }
        return dominated ;
    }
}