package problems;

/* This is the abstract class for optimization problems.
Specific optimization problems (such as the DTLZ suite, WFG test suite, etc are
inheritors of this class
 */
public abstract class problem {
    protected String problemName;

    abstract protected double[] evaluate(double[] variables);
    public abstract double getLowerBoundForDimension(int index);
    public abstract double getUpperBoundForDimension(int index);
    protected void setProblemName(String problemName){
        this.problemName=problemName;
    }
    public String getProblemName(){
        return problemName; }

}
