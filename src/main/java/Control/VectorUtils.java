package Control;

public class VectorUtils {
    public static double[] add(double[] a, double[] b) {
        return new double[]{a[0] + b[0], a[1] + b[1]};
    }
    public static double[] sub(double[] a, double[] b) {
        return new double[]{a[0] - b[0], a[1] - b[1]};
    }
    public static double[] scalarMult(double[] vect, double multiplier) {
        return new double[]{vect[0] * multiplier, vect[1] * multiplier};
    }
    public static double getDotProduct(double[] a, double[] b) {
        return (a[0] * b[0] + a[1] * b[1]);
    }
    public static double calcNorm(double[] a) {
        return Math.sqrt(a[0] * a[0] + a[1] * a[1]);
    }
    public static double[] getNormalizedVector(double[] a) {
        return new double[]{a[0] / calcNorm(a), a[1] / calcNorm(a)};
    }
    public static double[] getPerpVector(double[] a) {
        return new double[]{-getNormalizedVector(a)[1], getNormalizedVector(a)[0]};
    }
    public static double[] getPerpImage(double[] a, double[] b) {
        return new double[]{(getDotProduct(a, b)/Math.pow(calcNorm(b), 2)) * b[0], (getDotProduct(a, b) / Math.pow(calcNorm(b), 2)) * b[1]};
    }
}
