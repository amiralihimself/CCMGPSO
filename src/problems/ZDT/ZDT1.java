/* An implementation of the ZDT1 test problem
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.zdt;

public class ZDT1 extends ZDT {

    public ZDT1(int numberOfVariables) {
        super(numberOfVariables);
        setProblemName("ZDT1");


    }

    @Override
    protected double getLowerBoundForDimension(int index) {
        return 0;
    }

    @Override
    protected double getUpperBoundForDimension(int index) {
        return 1;
    }

    public double[] evaluate(double[] variables) {
        double[] f = new double[getNumberOfObjectives()];

        f[0] = variables[0];
        double g = this.evalG(variables);
        double h = this.evalH(f[0], g);
        f[1] = h * g;

        return f;
    }


    protected double evalG(double[] solution) {
        double g = 0.0;
        for (int i = 1; i <getNumberOfVariables(); i++) {
            g += solution[i];
        }
        double constant = 9.0 / (getNumberOfVariables() - 1);

        return constant * g + 1.0;
    }


    protected double evalH(double f, double g) {
        double h ;
        h = 1.0 - Math.sqrt(f / g);
        return h;
    }
}
