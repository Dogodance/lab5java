package Lab5;

import java.awt.geom.Rectangle2D;

/**
 * класс для фрактала Горящий корабль
 * фрактал расчитывается итерационно следующей функций
 * z(n+1) = (|Re(z(n) - i|Im(Z(n)|)^2 + c
 * По сути:
 * действительная часть расcчитывается как абсолютное значение разницы
 * квадратов реальной части мнимой + координата x
 * мнимая часть рассчитывается как абсолтное значение удвоенного произведения
 * реальной части на мнимую + y
 * Отличительной разницой от Мандельброта является приведение мнимой и реальной части
 * к абсолютным значением
 */
public class BurningShip extends FractalGenerator {
    // Ограничение итераций
    public static final int MAX_ITERATIONS = 1000;
    // Конструктор по умолчанию
    public BurningShip() { }
    // Устанавливаем диапазон отрисовки,
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }

    /**
     * Метод обходит каждую итерацию для подсчета значения для координат по следующей формуле
     * Вычисление Zn+1=(|Re(Zn)|-i|Im(Zn)|)2 + C, Z0=0 + где значения являются комплексными числами
     * представленными как zreal and zimaginary, Z0=0, и точки во фрактале,
     * которые будут отображены (данны в виде X и Y).  цикл будет итерироваться
     * до Z^2 > 4 (значение по модулю Z больше чем 2) или до максимума итераций
     * который был определен ранее.
     *
    */
    public int numIterations(double x, double y) {
        // Итерация
        int iteration = 0;
        // Реальная часть
        double realPart = 0;
        // Мнимая часть
        double imaginaryPart = 0;
        // Пока итерация не достигла максимума сумма квадратов мнимой единицы и реального значения меньше 4
        while ((iteration < MAX_ITERATIONS) && ((realPart * realPart + imaginaryPart * imaginaryPart) < 4)) {
            // расчет реальной часть
            double rp = Math.abs(realPart * realPart - imaginaryPart * imaginaryPart + x);
            // расчет мнимой часть
            double ip = Math.abs(2 * realPart * imaginaryPart + y);
            // запись расчетов на место реаельной части
            realPart = rp;
            // запись расчетов на место мнимой части
            imaginaryPart = ip;
            // увеличение итерации
            iteration += 1;
        }

        // Выход по достижению предела итераций
        if (iteration == MAX_ITERATIONS)
            return -1;
        else
            return iteration;
    }
    // Метод выводит строку, для пунктов в comboBox
    @Override
    public String toString() {
        return "BurningShip";
    }
}