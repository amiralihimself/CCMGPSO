/* An implementation of the WFG test suite
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.WFG;

import problems.problem;

import java.util.Random;

public abstract class WFG extends problem {
    private final double epsilon = (double) 1e-7;

    protected int k;
    protected int m;
    protected int l;
    protected int[] a;
    protected int[] s;
    protected int d = 1;
    protected Random random = new Random();
    public WFG(Integer k, Integer l, Integer M) {
        this.k = k;
        this.l = l;
        this.m = M;

    }
    public double[] calculateX(double[] t) {
        double[] x = new double[m];

        for (int i = 0; i < m - 1; i++) {
            x[i] = Math.max(t[m - 1], a[i]) * (t[i] - (double) 0.5) + (double) 0.5;
        }

        x[m - 1] = t[m - 1];

        return x;
    }
    /**
     * Normalize a vector
     */
    public double[] normalise(double[] z) {
        double[] result = new double[z.length];

        for (int i = 0; i < z.length; i++) {
            double bound = (double) 2.0 * (i + 1);
            result[i] = z[i] / bound;
            result[i] = correctTo01(result[i]);
        }

        return result;
    }

    public double correctTo01(double a) {
        double min = (double) 0.0;
        double max = (double) 1.0;

        double minEpsilon = min - epsilon;
        double maxEpsilon = max + epsilon;

        if ((a <= min && a >= minEpsilon) || (a >= min && a <= minEpsilon)) {
            return min;
        } else if ((a >= max && a <= maxEpsilon) || (a <= max && a >= maxEpsilon)) {
            return max;
        } else {
            return a;
        }
    }

    public double[] subVector(double[] z, int head, int tail) {
        int size = tail - head + 1;
        double[] result = new double[size];

        System.arraycopy(z, head, result, head - head, tail + 1 - head);

        return result;
    }
    abstract public double[] evaluate(double[] variables);
    protected double getLowerBoundForDimension(int index){
        return 0;
    }
    protected double getUpperBoundForDimension(int index){
        return 2*((double)index+1);

    }

}

