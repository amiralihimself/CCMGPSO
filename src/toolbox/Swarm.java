/*
This is the swarm class, which is made up of a list of particles,
and keeps track of the global best for each swarm
 */
package toolbox;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import problems.Problem;
public class Swarm {
    private LinkedList<Particle> particles;
    private double globalBest;
    private int[] dimensionGroupIndices;
    private double [] gBestPosition;
    public int numDimensions;
    private int numParticles;
    private double[] gBestObjectives;

    /*
    The following constructor is for the smaller swarms, i.e.,
    the subswarms with (n/k)-dimensional particles as defined in
    cooperative PSO.
     */
    public Swarm(int numParticles, int numDimensions, int numObjectives, double omega,  Problem optimizationProblem, int [] dimensionGroupIndices){
        this.gBestObjectives=new double[numObjectives];
        Arrays.fill(gBestObjectives, Double.MAX_VALUE);
        this.numParticles=numParticles;
        this.numDimensions=numDimensions;
        this.dimensionGroupIndices=dimensionGroupIndices;
        particles=new LinkedList<>();
        for (int particleNumber=0; particleNumber<this.numParticles; particleNumber++){
            Particle particle =new Particle(numDimensions,  numObjectives,  omega,   optimizationProblem, dimensionGroupIndices);
            particles.add(particle);
        }
        this.globalBest=Double.MAX_VALUE;
        Random random=new Random();
        if(particles.size()!=0){
            int randIndex=random.nextInt(particles.size());
            this.gBestPosition=particles.get(randIndex).getPosition().clone();
        }
    }

    /*
    The following constructor is for the n-dimensional swarms
    recall that the CCMGPSO uses both n-dimensional and (n/k)-dimensional
    swarms.
     */
    public Swarm(int numParticles, int numDimensions, int numObjectives,  Problem optimizationProblem){
        this.numParticles=numParticles;
        this.numDimensions=numDimensions;


        particles=new LinkedList<>();
        for (int particleNumber=0; particleNumber<this.numParticles; particleNumber++){
            Particle particle =new Particle(numDimensions,  numObjectives,   optimizationProblem);
            particles.add(particle);
        }
        this.globalBest=Double.MAX_VALUE;
        Random random=new Random();
        if(particles.size()!=0){
            int randIndex=random.nextInt(particles.size());
            this.gBestPosition=particles.get(randIndex).getPosition().clone();
        }
    }


    public void setGlobalBest(double globalBest) {
        this.globalBest = globalBest;
    }

    public void setGbestPosition(double[] gBestPosition) {
        this.gBestPosition = gBestPosition.clone();
    }

    public void setDimensionGroupIndices(int[] dimensionGroupIndices) {
        this.dimensionGroupIndices = dimensionGroupIndices.clone();
    }

    public Particle getParticle(int index){
        return particles.get(index);
    }
    public int getNum_particles() {
        return particles.size();
    }

    public int[] getDimensionGroupIndices() {
        return dimensionGroupIndices.clone();
    }

    public double getGlobalBest(){
        return globalBest;
    }
    public LinkedList<Particle> getParticles(){
        return (LinkedList) particles;
    }


    public double[] getGbestPosition() {
        return gBestPosition;
    }

}
