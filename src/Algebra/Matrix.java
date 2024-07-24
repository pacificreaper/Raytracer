package Algebra;

public class Matrix {
    private double[][] entries;

    public Matrix(double[][] values) {
        this.entries = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.entries[i][j] = values[i][j];
            }
        }
    }

    public Vector transformPoint(Vector v) {
        double x = entries[0][0] * v.getX() + entries[0][1] * v.getY() + entries[0][2] * v.getZ() + entries[0][3];
        double y = entries[1][0] * v.getX() + entries[1][1] * v.getY() + entries[1][2] * v.getZ() + entries[1][3];
        double z = entries[2][0] * v.getX() + entries[2][1] * v.getY() + entries[2][2] * v.getZ() + entries[2][3];
        return new Vector(x, y, z);
    }

    public Vector transformVector(Vector v) {
        double x = entries[0][0] * v.getX() + entries[0][1] * v.getY() + entries[0][2] * v.getZ();
        double y = entries[1][0] * v.getX() + entries[1][1] * v.getY() + entries[1][2] * v.getZ();
        double z = entries[2][0] * v.getX() + entries[2][1] * v.getY() + entries[2][2] * v.getZ();
        return new Vector(x, y, z);
    }

    public Matrix multiply(Matrix other) {
        double[][] result = new double[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int k = 0; k < 4; k++) {
                    result[row][col] += this.entries[row][k] * other.entries[k][col];
                }
            }
        }
        return new Matrix(result);
    }
}
