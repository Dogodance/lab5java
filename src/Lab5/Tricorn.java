package Lab5;

import java.awt.geom.Rectangle2D;

/**
 *  Фрактал "Треуголка" является по сути частным случаем Множества Мандельброта
 *  Который по сути имеет различие только в коэффициенте мнимой единицы
 */
public class Tricorn extends FractalGenerator {
    // Ограничение итераций
    public static final int MAX_ITERATIONS = 1000;
    // Конструктор по умолчанию
    public Tricorn() {

    }
    // Устанавливаем диапазон отрисовки,
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }
    // Итерации
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

        // Выход из метода по достижению итераций
        if (iteration == MAX_ITERATIONS)
            return -1;
        else
            return iteration;
    }
    // Метод выводит строку, для пунктов в comboBox
    public String toString() {
        return "Tricorn";
    }
}