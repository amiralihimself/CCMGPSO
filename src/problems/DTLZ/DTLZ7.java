/* An implementation of the DTLZ7 test problem
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.dtlz;

public class DTLZ7 extends DTLZ1 {

    public DTLZ7(int numberOfVariables, int numberOfObjectives){
        super(numberOfVariables, numberOfObjectives);
        setProblemName("DTLZ7");
    }

    public double[] evaluate(double[] variables) {
        int numberOfVariables = getNumberOfVariables();
        int numberOfObjectives = getNumberOfObjectives() ;

        double[] f = new double[numberOfObjectives];
        double[] x = new double[numberOfVariables] ;

        int k = getNumberOfVariables() - getNumberOfObjectives() + 1;

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = variables[i] ;
        }

        double g = 0.0;
        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g += x[i];
        }

        g = 1 + (9.0 * g) / k;

        System.arraycopy(x, 0, f, 0, numberOfObjectives - 1);

        double h = 0.0;
        for (int i = 0; i < numberOfObjectives - 1; i++) {
            h += (f[i] / (1.0 + g)) * (1 + Math.sin(3.0 * Math.PI * f[i]));
        }

        h = numberOfObjectives - h;

        f[numberOfObjectives - 1] = (1 + g) * h;

        return f;
    }
}
