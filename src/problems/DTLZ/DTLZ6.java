/* An implementation of the DTLZ6 test problem
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.dtlz;

public class DTLZ6 extends DTLZ {

    public DTLZ6(int numberOfVariables, int numberOfObjectives){
        super( numberOfVariables,  numberOfObjectives);
        setProblemName("DTLZ6");

    }

    public double[] evaluate(double[] variables) {
        int numberOfVariables = getNumberOfVariables();
        int numberOfObjectives = getNumberOfObjectives() ;
        double[] theta = new double[numberOfObjectives - 1];

        double[] f = new double[numberOfObjectives];
        double[] x = new double[numberOfVariables] ;

        int k = getNumberOfVariables() - getNumberOfObjectives() + 1;

        for (int i = 0; i < numberOfVariables; i++) {
            x[i] = variables[i] ;
        }

        double g = 0.0;
        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g += java.lang.Math.pow(x[i], 0.1);
        }

        double t = java.lang.Math.PI / (4.0 * (1.0 + g));
        theta[0] = x[0] * java.lang.Math.PI / 2;
        for (int i = 1; i < (numberOfObjectives - 1); i++) {
            theta[i] = t * (1.0 + 2.0 * g * x[i]);
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            f[i] = 1.0 + g;
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            for (int j = 0; j < numberOfObjectives - (i + 1); j++) {
                f[i] *= java.lang.Math.cos(theta[j]);
            }
            if (i != 0) {
                int aux = numberOfObjectives - (i + 1);
                f[i] *= java.lang.Math.sin(theta[aux]);
            }
        }

        return f;
    }
}
