package Lab5;

import java.awt.geom.Rectangle2D;

/**
 *  ������� "���������" �������� �� ���� ������� ������� ��������� ������������
 *  ������� �� ���� ����� �������� ������ � ������������ ������ �������
 */
public class Tricorn extends FractalGenerator {
    // ����������� ��������
    public static final int MAX_ITERATIONS = 1000;
    // ����������� �� ���������
    public Tricorn() {

    }
    // ������������� �������� ���������,
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }
    // ��������
    public int numIterations(double x, double y) {
        int iteration = 0;
        double realPart = 0;
        double imaginaryPart = 0;

        while ((iteration < MAX_ITERATIONS) && ((realPart * realPart + imaginaryPart * imaginaryPart) < 4)) {
            double rp = realPart * realPart - imaginaryPart * imaginaryPart + x;
            double ip = -2 * realPart * imaginaryPart + y;
            realPart = rp;
            imaginaryPart = ip;
            iteration += 1;
        }

        // ����� �� ������ �� ���������� ��������
        if (iteration == MAX_ITERATIONS)
            return -1;
        else
            return iteration;
    }
    // ����� ������� ������, ��� ������� � comboBox
    public String toString() {
        return "Tricorn";
    }
}