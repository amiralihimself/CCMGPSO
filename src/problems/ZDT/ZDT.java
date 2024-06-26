/* An implementation of the ZDT test suite
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.zdt;

import problems.Problem;

public abstract class ZDT extends Problem {
    private int numberOfVariables;
    private int numberOfObjectives;
    ZDT(int numberOfVariables){
        this.numberOfVariables=numberOfVariables;
        this.numberOfObjectives=2;
    }
    protected int getNumberOfVariables(){
        return  numberOfVariables;
    }
    protected int getNumberOfObjectives(){
        return numberOfObjectives;
    }
    abstract public double[] evaluate(double[] variables);
    public abstract double getLowerBoundForDimension(int index);
    public abstract double getUpperBoundForDimension(int index);


}