/* An implementation of the WFG1 test problem
Parts of this implementation were inspired from the jMetal framework:
https://github.com/jMetal/jMetal?tab=MIT-1-ov-file with the following license:
                Copyright <2017> <Antonio J. Nebro, Juan J. Durillo>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package problems.wfg;
public class WFG1 extends WFG {
    public WFG1(Integer k, Integer l, Integer m) {
        super(k, l, m);
        setProblemName("wfg1");

        s = new int[m];
        for (int i = 0; i < m; i++) {
            s[i] = 2 * (i + 1);
        }

        a = new int[m - 1];
        for (int i = 0; i < m - 1; i++) {
            a[i] = 1;
        }
    }

    public double[] evaluate(double[] z) {
        double[] y;

        y = normalise(z);
        y = t1(y, k);

        y = t2(y, k);

        y = t3(y);

        y = t4(y, k, m);


        double[] result = new double[m];
        double[] x = calculateX(y);
        for (int m = 1; m <= this.m - 1; m++) {
            result[m - 1] = d * x[this.m - 1] + s[m - 1] * (new Shapes()).convex(x, m);
        }

        result[m - 1] = d * x[m - 1] + s[m - 1] * (new Shapes()).mixed(x, 5, (double) 1.0);

        return result;
    }


    public double[] t1(double[] z, int k) {
        double[] result = new double[z.length];

        System.arraycopy(z, 0, result, 0, k);

        for (int i = k; i < z.length; i++) {
            result[i] = (new Transformations()).sLinear(z[i], (double) 0.35);
        }

        return result;
    }


    public double[] t2(double[] z, int k) {
        double[] result = new double[z.length];

        System.arraycopy(z, 0, result, 0, k);

        for (int i = k; i < z.length; i++) {
            result[i] = (new Transformations()).bFlat(z[i], (double) 0.8, (double) 0.75, (double) 0.85);
        }

        return result;
    }


    public double[] t3(double[] z){
        double[] result = new double[z.length];

        for (int i = 0; i < z.length; i++) {
            result[i] = (new Transformations()).bPoly(z[i], (double) 0.02);
        }

        return result;
    }


    public double[] t4(double[] z, int k, int M) {
        double[] result = new double[M];
        double[] w = new double[z.length];

        for (int i = 0; i < z.length; i++) {
            w[i] = (double) 2.0 * (i + 1);
        }

        for (int i = 1; i <= M - 1; i++) {
            int head = (i - 1) * k / (M - 1) + 1;
            int tail = i * k / (M - 1);
            double[] subZ = subVector(z, head - 1, tail - 1);
            double[] subW = subVector(w, head - 1, tail - 1);

            result[i - 1] = (new Transformations()).rSum(subZ, subW);
        }

        int head = k + 1 - 1;
        int tail = z.length - 1;
        double[] subZ = subVector(z, head, tail);
        double[] subW = subVector(w, head, tail);
        result[M - 1] = (new Transformations()).rSum(subZ, subW);

        return result;
    }

}
