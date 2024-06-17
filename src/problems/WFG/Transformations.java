/**
 * An implementation of the transformation used in the WFG test suite
 */
/*
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

 */
package problems.WFG;

public class Transformations {

    /**
     * Stores a default epsilon value
     */
    private static final double EPSILON = (double) 1.0e-10;

    /**
     * bPoly transformation
     *

     */
    public double bPoly(double y, double alpha)  {
        if (!(alpha > 0)) {


            Class<String> cls = String.class;
            String name = cls.getName();

        }

        return correctTo01((double) StrictMath.pow(y, alpha));
    }

    /**
     * bFlat transformation
     */
    public double bFlat(double y, double A, double B, double C) {
        double tmp1 = Math.min((double) 0, (double) Math.floor(y - B)) * A * (B - y) / B;
        double tmp2 = Math.min((double) 0, (double) Math.floor(C - y)) * (1 - A) * (y - C) / (1 - C);

        return correctTo01(A + tmp1 - tmp2);
    }

    /**
     * sLinear transformation
     */
    public double sLinear(double y, double A) {
        return correctTo01(Math.abs(y - A) / (double) Math.abs(Math.floor(A - y) + A));
    }

    /**
     * sDecept transformation
     */
    public double sDecept(double y, double A, double B, double C) {
        double tmp, tmp1, tmp2;

        tmp1 = (double) Math.floor(y - A + B) * ((double) 1.0 - C + (A - B) / B) / (A - B);
        tmp2 =
                (double) Math.floor(A + B - y) * ((double) 1.0 - C + ((double) 1.0 - A - B) / B) / ((double) 1.0
                        - A - B);

        tmp = Math.abs(y - A) - B;

        return correctTo01((double) 1 + tmp * (tmp1 + tmp2 + (double) 1.0 / B));
    }

    /**
     * sMulti transformation
     */
    public double sMulti(double y, int A, int B, double C) {
        double tmp1, tmp2;

        tmp1 = ((double) 4.0 * A + (double) 2.0) *
                (double) Math.PI *
                ((double) 0.5 - Math.abs(y - C) / ((double) 2.0 * ((double) Math.floor(C - y) + C)));
        tmp2 = (double) 4.0 * B *
                (double) StrictMath.pow(Math.abs(y - C) / ((double) 2.0 * ((double) Math.floor(C - y) + C))
                        , (double) 2.0);

        return correctTo01(((double) 1.0 + (double) Math.cos(tmp1) + tmp2) / (B + (double) 2.0));
    }

    /**
     * rSum transformation
     */
    public double rSum(double[] y, double[] w) {
        double tmp1 = (double) 0.0, tmp2 = (double) 0.0;
        for (int i = 0; i < y.length; i++) {
            tmp1 += y[i] * w[i];
            tmp2 += w[i];
        }

        return correctTo01(tmp1 / tmp2);
    }

    /**
     * rNonsep transformation
     */
    public double rNonsep(double[] y, int A) {
        double tmp, denominator, numerator;

        tmp = (double) Math.ceil(A / (double) 2.0);
        denominator = y.length * tmp * ((double) 1.0 + (double) 2.0 * A - (double) 2.0 * tmp) / A;
        numerator = (double) 0.0;
        for (int j = 0; j < y.length; j++) {
            numerator += y[j];
            for (int k = 0; k <= A - 2; k++) {
                numerator += Math.abs(y[j] - y[(j + k + 1) % y.length]);
            }
        }

        return correctTo01(numerator / denominator);
    }

    /**
     * bParam transformation
     */
    public double bParam(double y, double u, double A, double B, double C) {
        double result, v, exp;

        v = A - ((double) 1.0 - (double) 2.0 * u) *
                Math.abs((double) Math.floor((double) 0.5 - u) + A);
        exp = B + (C - B) * v;
        result = (double) StrictMath.pow(y, exp);

        return correctTo01(result);
    }

    /**
     */
    double correctTo01(double a) {
        double min = (double) 0.0;
        double max = (double) 1.0;
        double min_epsilon = min - EPSILON;
        double max_epsilon = max + EPSILON;

        if ((a <= min && a >= min_epsilon) || (a >= min && a <= min_epsilon)) {
            return min;
        } else if ((a >= max && a <= max_epsilon) || (a <= max && a >= max_epsilon)) {
            return max;
        } else {
            return a;
        }
    }
}