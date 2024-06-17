/* An implementation of the ZDT6 test problem
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.zdt;

public class ZDT6 extends ZDT {

    public ZDT6(int numberOfVariables) {
        super(numberOfVariables);
        setProblemName("ZDT6");
    }

    public double getLowerBoundForDimension(int index) {
        return 0;
    }

    public double getUpperBoundForDimension(int index) {
        return 1;
    }
    public double[] evaluate(double[] solution) {
        double[] f = new double[getNumberOfObjectives()];

        double x1;
        x1 = solution[0];
        f[0] = 1 - Math.exp(-4 * x1) * Math.pow(Math.sin(6 * Math.PI * x1), 6);
        double g = this.evalG(solution);
        double h = this.evalH(f[0], g);
        f[1] = h * g;

        return f;
    }


    protected double evalG(double[] solution) {
        double g = 0.0;
        for (int var = 1; var < getNumberOfVariables(); var++) {
            g += solution[var];
        }
        g = g / (getNumberOfVariables() - 1);
        g = Math.pow(g, 0.25);
        g = 9.0 * g;
        g = 1.0 + g;
        return g;
    }


    protected double evalH(double f, double g) {
        return 1.0 - Math.pow((f / g), 2.0);
    }
}
