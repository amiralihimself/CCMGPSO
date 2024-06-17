/*
This is the swarm class, which is made up of a list of particles,
and keeps track of the global best for each swarm
 */
package toolbox;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import problems.problem;
public class swarm {
    private LinkedList<particle> particles;
    private double globalBest;
    private int[] dimensionGroupIndices;
    private double [] gbestPosition;
    public int num_dimensions;
    private int num_particles;
    private double[] globalWorstPos;
    private double globalWorst;
    private double contribution;
    private double[] gBestObjectives;

    /*
    The following constructor is for the smaller swarms, i.e.,
    the subswarms with (n/k)-dimensional particles as defined in
    cooperative PSO.
     */
    public swarm(int num_particles, int num_dimensions, int num_objectives, double omega,  problem optimizationProblem, int [] dimensionGroupIndices){
        this.gBestObjectives=new double[num_objectives];
        Arrays.fill(gBestObjectives, Double.MAX_VALUE);
        this.contribution=0.0;
        this.num_particles=num_particles;
        this.num_dimensions=num_dimensions;
        this.dimensionGroupIndices=dimensionGroupIndices;
        particles=new LinkedList<>();
        globalWorst=-1*Double.MAX_VALUE;
        globalWorstPos=new double[num_dimensions];
        for (int particleNumber=0; particleNumber<this.num_particles; particleNumber++){
            particle Particle =new particle(num_dimensions,  num_objectives,  omega,   optimizationProblem, dimensionGroupIndices);
            particles.add(Particle);
        }
        this.globalBest=Double.MAX_VALUE;
        Random random=new Random();
        if(particles.size()!=0){
            int randIndex=random.nextInt(particles.size());
            this.gbestPosition=particles.get(randIndex).getPosition().clone();
            int randIndex2=random.nextInt(particles.size());
            this.globalWorstPos=particles.get(randIndex2).getPosition().clone();

        }
    }

    /*
    The following constructor is for the n-dimensional swarms
    recall that the CCMGPSO uses both n-dimensional and (n/k)-dimensional
    swarms.
     */
    public swarm(int num_particles, int num_dimensions, int num_objectives,  problem optimizationProblem){
        globalWorst=-1*Double.MAX_VALUE;
        globalWorstPos=new double[num_dimensions];
        this.num_particles=num_particles;
        this.num_dimensions=num_dimensions;


        particles=new LinkedList<>();
        for (int particleNumber=0; particleNumber<this.num_particles; particleNumber++){
            particle Particle =new particle(num_dimensions,  num_objectives,   optimizationProblem);
            particles.add(Particle);
        }
        this.globalBest=Double.MAX_VALUE;
        // this may cause errors
        Random random=new Random();
        if(particles.size()!=0){
            int randIndex=random.nextInt(particles.size());
            this.gbestPosition=particles.get(randIndex).getPosition().clone();
        }
        int randIndex2=random.nextInt(particles.size());
        this.globalWorstPos=particles.get(randIndex2).getPosition().clone();
    }


    public void setGlobalBest(double globalBest) {
        this.globalBest = globalBest;
    }

    public void setGbestPosition(double[] gbestPosition) {
        this.gbestPosition = gbestPosition.clone();
    }

    public void setDimensionGroupIndices(int[] dimensionGroupIndices) {
        this.dimensionGroupIndices = dimensionGroupIndices.clone();
    }

    public particle getParticle(int index){
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
    public LinkedList<particle> getParticles(){
        return (LinkedList) particles;
    }

    public void setParticles(LinkedList<particle> particles) {
        this.particles = (LinkedList) particles.clone();
    }

    public double[] getGbestPosition() {
        return gbestPosition;
    }

}
