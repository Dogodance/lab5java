package Lab5;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FractalExplorer {
    // Ширина экрана
    private int width;
    // Высота экрана
    private int heigth;
    private JImageDisplay image;
    // Ограничение масштабируемости отрисовки
    private Rectangle2D.Double range;
    // Фрейм для всех элементов
    private JFrame frame;
    // Кнопка перегрузки факториала
    private JButton buttonRestart;
    // Кнопка cохранения факториала
    private JButton buttonSave;
    // Комбобокс для выбора фракталов
    private JComboBox comboBox;
    // Панель для комбобокса
    private JPanel panelUp;
    // Панель для кнопок снизу
    private JPanel panelDown;
    // Подпись в окне
    private JLabel label;

    // Одно связанный список фракталов
    private ArrayList<FractalGenerator> fractals;

    // Конструктор окна если задан один параметр - оно будет квадратным
    public FractalExplorer(int size) {
        this(size, size);
    }

    // Конструктор окна, для создания по ширине и высоте
    public FractalExplorer(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        // инициализация границ
        this.range = new Rectangle2D.Double();
        // инициализация фракталов и добавление их в список
        fractals = new ArrayList<>();
        fractals.add(new Mandelbrot());
        fractals.add(new Tricorn());
        fractals.add(new BurningShip());
    }
    //Слушатель комбобокса (обработчик изменений состояний)
    private class comboBoxListener implements  ActionListener{
        // Если выбрали новый элемент
        public void actionPerformed(ActionEvent event){
            int index = comboBox.getSelectedIndex();// Получить индекс выбранного значения
            fractals.get(index).getInitialRange(range); // Инициализировать для фрактала нужное значение диапозона
            FractalExplorer.this.drawFractal(index); // Отрисовка фрактала по полученному индексу
        }
    }
    // Слушатель кнопки сохранить
    private class saveButtonListener implements  ActionListener{
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser(); // Создать диалоговое окно для сохранения изображения
            // Поставить фильтра для сохранения изображений png
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
            file.setFileFilter(filter); // Установить фильтр к диалоговому окну
            file.setAcceptAllFileFilterUsed(false); // Не использовать фильтр *, то есть все возможные файлы
            file.setCurrentDirectory(new File("C:\\")); // Установить деректорию которая открывается как диск C
            int option = file.showSaveDialog(frame); // Получение значения нажатой кнопки внутри меню сохранения
            if (option != JFileChooser.APPROVE_OPTION) // Если кнопка нажата не "Ок", тогда прервем сохранение
                return;
            File path = new File(file.getSelectedFile().getPath().replace(".png", "") +
                    ".png"); // Создадим файл по пути полученном из диалогового окна с расширением png
            if (path.exists()) // если файл по такому пути существует
                /*
                    Вызовем панель и спросим пользователся о перезаписи Еслм он нажал нет, то заканчиваем сохранение
                 */
                if (JOptionPane.showConfirmDialog(FractalExplorer.this.frame,
                        "This file already exists. Do you want to overwrite it?",
                        "Overwrite File?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
                    return;
            // Если нажал не нет, значит нажал да, тогда попытаемся сохранить файл
            try {
                // Запищем набор байтов BufferedImage внутрь файла
                ImageIO.write(image.getBuffImage(), "png", path);
                JOptionPane.showMessageDialog(FractalExplorer.this.frame, "File was saved", "File save",
                        JOptionPane.INFORMATION_MESSAGE); // Выведем сообщение, о том что файл сохранился успешно
            } catch (IOException exception){ // Если произошло исключение при записи
                exception.printStackTrace(); // Выведем ошибку в консоль
                JOptionPane.showMessageDialog(FractalExplorer.this.frame, "Error: " + exception.getMessage(),
                        "Cannot save image", JOptionPane.WARNING_MESSAGE);
                // Выведем ошибку юзеру, сказзав, что не можем сохранить изображение
            }

        }
    }

    // Слушатель кнопки очистить
    private class resetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = comboBox.getSelectedIndex(); // Получим индекс комбобокса
            fractals.get(index).getInitialRange(range); // На основе этого индекса возьмем ограничения для конкретного фрактала
            FractalExplorer.this.drawFractal(index); // Отрисуем фрактал в "базовом" виде
        }
    }

    // Обработка нажатия на мышку из прошлой лабы
    private class mouseClickListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int index = comboBox.getSelectedIndex(); // Получаем индекс фрактала в списке
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, image.getWidth(), x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, image.getHeight(), y);

            if (e.getButton() == MouseEvent.BUTTON1) {
                fractals.get(index).recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            }
            FractalExplorer.this.drawFractal(index);
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }
    }
    /**
     *  Установка и расположение элементов, прикрепление "слушателя" событий на кнопку и полотно при нажатии мыши
     *  Установка операции по умолчанию при закрытии окна
     */
    public void createAndShowGUI() {
        this.frame = new JFrame("Fractal Generator");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(this.width, this.heigth);
        this.panelUp = new JPanel();
        this.panelDown = new JPanel();

        this.image = new JImageDisplay(heigth, width);
        frame.getContentPane().add(BorderLayout.CENTER, this.image);
        image.addMouseListener(new mouseClickListener());

        this.buttonRestart = new JButton("Reset Display");
        buttonRestart.setPreferredSize(new Dimension(150, 30));
        buttonRestart.addActionListener(new resetButtonListener());

        this.buttonSave = new JButton("Save Image");
        buttonSave.setPreferredSize(new Dimension(150, 30));
        buttonSave.addActionListener(new saveButtonListener());

        this.comboBox = new JComboBox();
        for (int i = 0; i < fractals.size(); i++) {
            comboBox.addItem(fractals.get(i).toString());
        }
        comboBox.addActionListener(new comboBoxListener());
        comboBox.setSelectedIndex(fractals.size()-1);
        this.label = new JLabel("Selected Fractal: ");

        panelDown.add(this.buttonRestart);
        panelDown.add(this.buttonSave);

        panelUp.add(label);
        panelUp.add(this.comboBox);

        frame.getContentPane().add(BorderLayout.NORTH, this.panelUp);
        frame.getContentPane().add(BorderLayout.SOUTH, this.panelDown);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    /**
     * Отрисовка фрактала на полотне. Проходим все пиксели и подсичитываем количество итераций по формуле.
     * Если результат не -1, то рисуем пиксель по координатам расчитываемого пикселя, а цвет расчитываем по формуле
     * И поменяв цветовую схему с HSB на RGN
     */
    private void drawFractal(int index) {
        // обходим по ширине
        for (int x = 0; x < width; x++) {
            // обходим по высоте
            for (int y = 0; y < heigth; y++) {
                double xCoord = FractalGenerator.getCoord(range.x,
                        range.x + range.width, width, x); // Преобразуем координату x в число с плавающей запятой
                double yCoord = FractalGenerator.getCoord(range.y,
                        range.y + range.height, heigth, y);// Преобразуем координату y в число с плавающей запятой
                int iteration = fractals.get(index).numIterations(xCoord, yCoord);// Проводим итерации, для получения значения фрактала
                if (iteration == -1) { // Если фрактал не вышел за пределы ограничения за 2000 итераций
                    image.drawPixel(x, y, new Color(0)); // установить белый цвет для пикселя
                }
                else{
                    float hue = 0.7f + (float) iteration / 200f; // Иначе определим цветовой тон(hue) как сумму литерала 0.7 + значение итерации / 200
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f); // Преобразуем цветовой тон в стандартный RGB цвет
                    image.drawPixel(x, y, new Color(rgbColor)); // Отрисуем символ
                }
                int rgbColor;
                if (iteration != -1) {
                    float hue = 0.7f + (float) iteration / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                } else {
                    rgbColor = Color.HSBtoRGB(0, 0, 0);
                }
                image.drawPixel(x, y, new Color(rgbColor));
            }
        }
    }

    public static void main(String[] args) {
        FractalExplorer fractal = new FractalExplorer(900);
        fractal.createAndShowGUI();
        fractal.drawFractal(2);
    }
}