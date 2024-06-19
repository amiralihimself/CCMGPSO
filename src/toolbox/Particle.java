/*
This is the particle class,
and keeps track of the personal best for each particle,
as well as the velocity and position vectors, and the Omega parameter (as described in the paper)
 */
package toolbox;
import java.util.*;
import problems.Problem;
public class Particle  {
    private int numDimensions;
    private int numObjectives;
    private double lambda;
    private double omega;
    public double crowdingDistance;
    private double[] position;
    private double [] objectives;
    private double[] velocity;
    private double pBest;
    private double[] pBestPosition;
    private double[] pBestObjectives;
    //this field stores the crwoding distance of a particle if put in the toolbox.archive
    public Particle(int num_dimensions, int num_objectives, double omega,  Problem optimizationProblem, int [] dimensionGroupIndices){
        this.pBestObjectives=new double[num_objectives];
        Arrays.fill(pBestObjectives, Double.MAX_VALUE);
        this.numDimensions=num_dimensions;
        this.numObjectives=num_objectives;
        Random random= new Random();
        this.lambda= random.nextDouble();
        this.omega=omega;
        position=new double[this.numDimensions];
        velocity= new double[this.numDimensions];
        objectives= new double[this.numObjectives];
        //for zdt functions, all dimensions are in the range [0,1]
        //for wfg dimension i is in the range [0,2i] so it's a function of the dimension index
        //this is the reasoning behind lowerIndex and upperIndex
        Random random1= new Random();
        for(int i=0; i<dimensionGroupIndices.length; i++){
            velocity[i]=0.0;
            double lowerBound=optimizationProblem.getLowerBoundForDimension(dimensionGroupIndices[i]);
            double upperBound=optimizationProblem.getUpperBoundForDimension(dimensionGroupIndices[i]);
            position[i]=lowerBound+ (upperBound-lowerBound)*random1.nextDouble();

        }
        this.crowdingDistance=0.0;
        this.pBest=Double.MAX_VALUE;
        this.pBestPosition=position.clone();


    }

    public Particle(double [] objectives, double [] position){
        this.objectives=objectives;
        this.velocity=new double[objectives.length];
        this.position=position;
        this.crowdingDistance=0;
        this.pBestPosition=position.clone();
    }

    public Particle(int num_dimensions, int num_objectives,   Problem optimizationProblem){
        this.numDimensions=num_dimensions;
        this.numObjectives=num_objectives;
        this.crowdingDistance=0.0;
        Random random= new Random();
        this.lambda= random.nextDouble();
        position=new double[this.numDimensions];
        velocity= new double[this.numDimensions];
        objectives= new double[this.numObjectives];
        Random random1= new Random();

        for(int i=0; i<num_dimensions; i++){
            velocity[i]=0.0;
            double lowerBound=optimizationProblem.getLowerBoundForDimension(i);
            double upperBound=optimizationProblem.getUpperBoundForDimension(i);
            position[i]=lowerBound+ (upperBound-lowerBound)*random1.nextDouble();

        }
        this.pBest=Double.MAX_VALUE;
        this.pBestPosition=position.clone();


    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity.clone();
    }

    public double[] getVelocity() {
        return velocity.clone();
    }

    public double getLambda(){
        return this.lambda;
    }
    public double [] getPosition(){
        return this.position.clone();
    }

    public double [] getPbestPosition(){
        return this.pBestPosition.clone();
    }
    public double getPbest(){
        return pBest;
    }

    public void setPbest(double pBest){
        this.pBest = pBest;
    }

    public void setPbestPosition(double[] pBestPosition){
        this.pBestPosition = pBestPosition.clone();
    }

    public double[] getObjectives(){
        return objectives;
    }

    public void setObjectives(double[] objectives){
        this.objectives = objectives.clone();
    }

    public void setPosition(double[] position){
        this.position = position.clone();
    }


}
