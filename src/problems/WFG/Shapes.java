package problems.wfg;


/**
 * An implementation of the shapes used in the WFG test suite
 */
/*
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */
public class Shapes {

    /**
     * Calculate a linear shape
     */
    public double linear(double[] x, int m) {
        double result = (double) 1.0;
        int M = x.length;

        for (int i = 1; i <= M - m; i++) {
            result *= x[i - 1];
        }

        if (m != 1) {
            result *= (1 - x[M - m]);
        }

        return result;
    }

    /**
     * Calculate a convex shape
     */
    public double convex(double[] x, int m) {
        double result = (double) 1.0;
        int M = x.length;

        for (int i = 1; i <= M - m; i++) {
            result *= (1 - Math.cos(x[i - 1] * Math.PI * 0.5));
        }

        if (m != 1) {
            result *= (1 - Math.sin(x[M - m] * Math.PI * 0.5));
        }

        return result;
    }

    /**
     * Calculate a concave shape
     */
    public double concave(double[] x, int m) {
        double result = (double) 1.0;
        int M = x.length;

        for (int i = 1; i <= M - m; i++) {
            result *= Math.sin(x[i - 1] * Math.PI * 0.5);
        }

        if (m != 1) {
            result *= Math.cos(x[M - m] * Math.PI * 0.5);
        }

        return result;
    }

    /**
     * Calculate a mixed shape
     */
    public double mixed(double[] x, int A, double alpha) {
        double tmp;
        tmp =
                (double) Math.cos((double) 2.0 * A * (double) Math.PI * x[0] + (double) Math.PI * (double) 0.5);
        tmp /= (2.0 * (double) A * Math.PI);

        return (double) Math.pow(((double) 1.0 - x[0] - tmp), alpha);
    }

    /**
     * Calculate a disc shape
     */
    public double disc(double[] x, int A, double alpha, double beta) {
        double tmp;
        tmp = (double) Math.cos((double) A * Math.pow(x[0], beta) * Math.PI);

        return (double) 1.0 - (double) Math.pow(x[0], alpha) * (double) Math.pow(tmp, 2.0);
    }
}