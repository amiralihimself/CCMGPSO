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
}