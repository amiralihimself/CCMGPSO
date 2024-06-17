/*
This is the particle class,
and keeps track of the personal best for each particle,
as well as the velocity and position vectors, and the Omega parameter (as described in the paper)
 */
package toolbox;
import java.util.*;
import problems.problem;
public class particle  {
    private int num_dimensions;
    private int num_objectives;
    private double lambda;
    private double omega;
    public double crowdingDistance;
    private double[] position;
    private double [] objectives;
    private double[] velocity;
    private double pbest;
    private double[] localBestPosition;
    private double[] pbest_position;
    private double[] pBestObjectives;
    //this field stores the crwoding distance of a particle if put in the toolbox.archive
    public particle(int num_dimensions, int num_objectives, double omega,  problem optimizationProblem, int [] dimensionGroupIndices){
        this.pBestObjectives=new double[num_objectives];
        Arrays.fill(pBestObjectives, Double.MAX_VALUE);
        this.num_dimensions=num_dimensions;
        this.num_objectives=num_objectives;
        localBestPosition=new double[num_dimensions];
        Random random= new Random();
        this.lambda= random.nextDouble();
        this.omega=omega;
        position=new double[this.num_dimensions];
        velocity= new double[this.num_dimensions];
        objectives= new double[this.num_objectives];
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
        this.pbest=Double.MAX_VALUE;
        this.pbest_position=position.clone();


    }

    public particle(double [] objectives, double [] position){
        this.objectives=objectives;
        this.velocity=new double[objectives.length];
        this.position=position;
        this.crowdingDistance=0;
        this.pbest_position=position.clone();
    }

    public particle(int num_dimensions, int num_objectives,   problem optimizationProblem){
        this.num_dimensions=num_dimensions;
        this.num_objectives=num_objectives;
        this.crowdingDistance=0.0;
        Random random= new Random();
        this.lambda= random.nextDouble();
        position=new double[this.num_dimensions];
        velocity= new double[this.num_dimensions];
        objectives= new double[this.num_objectives];
        Random random1= new Random();

        for(int i=0; i<num_dimensions; i++){
            velocity[i]=0.0;
            double lowerBound=optimizationProblem.getLowerBoundForDimension(i);
            double upperBound=optimizationProblem.getUpperBoundForDimension(i);
            position[i]=lowerBound+ (upperBound-lowerBound)*random1.nextDouble();

        }
        this.pbest=Double.MAX_VALUE;
        this.pbest_position=position.clone();


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

    public double [] getPbest_position(){
        return this.pbest_position.clone();
    }
    public double getPbest(){
        return pbest;
    }

    public void setPbest(double pbest){
        this.pbest = pbest;
    }

    public void setPbest_position(double[] pbest_position){
        this.pbest_position = pbest_position.clone();
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

    public void setCrowdingDistance(double crowdingDistance){
        this.crowdingDistance = crowdingDistance;
    }

    public double getCrowdingDistance(){
        return crowdingDistance;
    }

    public void setNum_dimensions(int num_dimensions){
        this.num_dimensions = num_dimensions;
    }

    public void setNum_objectives(int num_objectives){
        this.num_objectives = num_objectives;
    }

    public void setLambda(double lambda){
        this.lambda = lambda;
    }

    public void setLocalBestPosition(double[] localBestPosition){
        this.localBestPosition=localBestPosition.clone();
    }

    public double[] getLocalBestPosition(){
        return localBestPosition.clone();
    }


    public double[] getpBestObjectives(){
        return pBestObjectives.clone();
    }

    public void setpBestObjectives(double[] pBestObjectives){
        this.pBestObjectives = pBestObjectives.clone();
    }
}
