package Lab5;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {// ����������� �� ���������� ��������, �.�. ���������� ������� ������ ���� ���������

    public static final int MAX_ITERATIONS = 2000; // ������������ ��������, ��� ��� ������� ����� �������������� ����������

    @Override
    public void getInitialRange(Rectangle2D.Double range) { // ������������� �������� ���������,

        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    @Override
    public int numIterations(double x, double y) {
        // ���������� ���������� �� ��������
        int iteration = 0;
        // ������ ������� � �������� �����
        double zreal = 0;
        double zimaginary = 0;

        /**
         * ���������� Zn = Zn-1^2 + ��� �������� �������� ������������ �������
         * ��������������� ��� zreal and zimaginary, Z0=0, � ����� �� ��������,
         * ������� ����� ���������� (����� � ���� X � Y).  ���� ����� �������������
         * �� Z^2 > 4 (�������� �� ������ Z ������ ��� 2) ��� �� ��������� ��������
         * ������� ��� ��������� �����.
         */
        // ���� �������� �� �������� ��������� ����� ��������� ������ ������� � ��������� �������� ������ 4
        while (iteration < MAX_ITERATIONS && zreal * zreal + zimaginary * zimaginary < 4)
        {
            // ������� ����� �������� ��������� �������� �������� x  � ������� ��������� �������� z � �������� � ������ ������� � ��������
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            // ������� ����� �������� ������ ������� �� ������� 2 * �������� ����� * ������ ������� + y
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated; // ��������� ����� �������� � ���������� ���������� �� �������� ��������
            zimaginary = zimaginaryUpdated; // ��������� ����� ������ ������� � ���������� ���������� �� ������ �������
            iteration += 1; // �������� ������� �������� �� 1
        }

        if (iteration == MAX_ITERATIONS) // ���� �������� ������� ��������
        {
            return -1; // ������ -1
        }

        return iteration; // ���� �� ������ � �������, �� ������ ��������
    }
    public String toString() {
        return "Mondelbort";
    }
}
