package Lab5;

import java.awt.geom.Rectangle2D;

/**
 * ����� ��� �������� ������� �������
 * ������� ������������� ����������� ��������� �������
 * z(n+1) = (|Re(z(n) - i|Im(Z(n)|)^2 + c
 * �� ����:
 * �������������� ����� ���c���������� ��� ���������� �������� �������
 * ��������� �������� ����� ������ + ���������� x
 * ������ ����� �������������� ��� ��������� �������� ���������� ������������
 * �������� ����� �� ������ + y
 * ������������� �������� �� ������������ �������� ���������� ������ � �������� �����
 * � ���������� ���������
 */
public class BurningShip extends FractalGenerator {
    // ����������� ��������
    public static final int MAX_ITERATIONS = 1000;
    // ����������� �� ���������
    public BurningShip() { }
    // ������������� �������� ���������,
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    /**
     * ����� ������� ������ �������� ��� �������� �������� ��� ��������� �� ��������� �������
     * ���������� Zn+1=(|Re(Zn)|-i|Im(Zn)|)2 + C, Z0=0 + ��� �������� �������� ������������ �������
     * ��������������� ��� zreal and zimaginary, Z0=0, � ����� �� ��������,
     * ������� ����� ���������� (����� � ���� X � Y).  ���� ����� �������������
     * �� Z^2 > 4 (�������� �� ������ Z ������ ��� 2) ��� �� ��������� ��������
     * ������� ��� ��������� �����.
     *
    */
    public int numIterations(double x, double y) {
        // ��������
        int iteration = 0;
        // �������� �����
        double realPart = 0;
        // ������ �����
        double imaginaryPart = 0;
        // ���� �������� �� �������� ��������� ����� ��������� ������ ������� � ��������� �������� ������ 4
        while ((iteration < MAX_ITERATIONS) && ((realPart * realPart + imaginaryPart * imaginaryPart) < 4)) {
            // ������ �������� �����
            double rp = Math.abs(realPart * realPart - imaginaryPart * imaginaryPart + x);
            // ������ ������ �����
            double ip = Math.abs(2 * realPart * imaginaryPart + y);
            // ������ �������� �� ����� ��������� �����
            realPart = rp;
            // ������ �������� �� ����� ������ �����
            imaginaryPart = ip;
            // ���������� ��������
            iteration += 1;
        }

        // ����� �� ���������� ������� ��������
        if (iteration == MAX_ITERATIONS)
            return -1;
        else
            return iteration;
    }
    // ����� ������� ������, ��� ������� � comboBox
    @Override
    public String toString() {
        return "BurningShip";
    }
}