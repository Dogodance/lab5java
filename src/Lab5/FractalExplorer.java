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
    // ������ ������
    private int width;
    // ������ ������
    private int heigth;
    private JImageDisplay image;
    // ����������� ���������������� ���������
    private Rectangle2D.Double range;
    // ����� ��� ���� ���������
    private JFrame frame;
    // ������ ���������� ����������
    private JButton buttonRestart;
    // ������ c��������� ����������
    private JButton buttonSave;
    // ��������� ��� ������ ���������
    private JComboBox comboBox;
    // ������ ��� ����������
    private JPanel panelUp;
    // ������ ��� ������ �����
    private JPanel panelDown;
    // ������� � ����
    private JLabel label;

    // ���� ��������� ������ ���������
    private ArrayList<FractalGenerator> fractals;

    // ����������� ���� ���� ����� ���� �������� - ��� ����� ����������
    public FractalExplorer(int size) {
        this(size, size);
    }

    // ����������� ����, ��� �������� �� ������ � ������
    public FractalExplorer(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        // ������������� ������
        this.range = new Rectangle2D.Double();
        // ������������� ��������� � ���������� �� � ������
        fractals = new ArrayList<>();
        fractals.add(new Mandelbrot());
        fractals.add(new Tricorn());
        fractals.add(new BurningShip());
    }
    //��������� ���������� (���������� ��������� ���������)
    private class comboBoxListener implements  ActionListener{
        // ���� ������� ����� �������
        public void actionPerformed(ActionEvent event){
            int index = comboBox.getSelectedIndex();// �������� ������ ���������� ��������
            fractals.get(index).getInitialRange(range); // ���������������� ��� �������� ������ �������� ���������
            FractalExplorer.this.drawFractal(index); // ��������� �������� �� ����������� �������
        }
    }
    // ��������� ������ ���������
    private class saveButtonListener implements  ActionListener{
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser(); // ������� ���������� ���� ��� ���������� �����������
            // ��������� ������� ��� ���������� ����������� png
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
            file.setFileFilter(filter); // ���������� ������ � ����������� ����
            file.setAcceptAllFileFilterUsed(false); // �� ������������ ������ *, �� ���� ��� ��������� �����
            file.setCurrentDirectory(new File("C:\\")); // ���������� ���������� ������� ����������� ��� ���� C
            int option = file.showSaveDialog(frame); // ��������� �������� ������� ������ ������ ���� ����������
            if (option != JFileChooser.APPROVE_OPTION) // ���� ������ ������ �� "��", ����� ������� ����������
                return;
            File path = new File(file.getSelectedFile().getPath().replace(".png", "") +
                    ".png"); // �������� ���� �� ���� ���������� �� ����������� ���� � ����������� png
            if (path.exists()) // ���� ���� �� ������ ���� ����������
                /*
                    ������� ������ � ������� ������������� � ���������� ���� �� ����� ���, �� ����������� ����������
                 */
                if (JOptionPane.showConfirmDialog(FractalExplorer.this.frame,
                        "This file already exists. Do you want to overwrite it?",
                        "Overwrite File?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
                    return;
            // ���� ����� �� ���, ������ ����� ��, ����� ���������� ��������� ����
            try {
                // ������� ����� ������ BufferedImage ������ �����
                ImageIO.write(image.getBuffImage(), "png", path);
                JOptionPane.showMessageDialog(FractalExplorer.this.frame, "File was saved", "File save",
                        JOptionPane.INFORMATION_MESSAGE); // ������� ���������, � ��� ��� ���� ���������� �������
            } catch (IOException exception){ // ���� ��������� ���������� ��� ������
                exception.printStackTrace(); // ������� ������ � �������
                JOptionPane.showMessageDialog(FractalExplorer.this.frame, "Error: " + exception.getMessage(),
                        "Cannot save image", JOptionPane.WARNING_MESSAGE);
                // ������� ������ �����, �������, ��� �� ����� ��������� �����������
            }

        }
    }

    // ��������� ������ ��������
    private class resetButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = comboBox.getSelectedIndex(); // ������� ������ ����������
            fractals.get(index).getInitialRange(range); // �� ������ ����� ������� ������� ����������� ��� ����������� ��������
            FractalExplorer.this.drawFractal(index); // �������� ������� � "�������" ����
        }
    }

    // ��������� ������� �� ����� �� ������� ����
    private class mouseClickListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int index = comboBox.getSelectedIndex(); // �������� ������ �������� � ������
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
     *  ��������� � ������������ ���������, ������������ "���������" ������� �� ������ � ������� ��� ������� ����
     *  ��������� �������� �� ��������� ��� �������� ����
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
     * ��������� �������� �� �������. �������� ��� ������� � ������������� ���������� �������� �� �������.
     * ���� ��������� �� -1, �� ������ ������� �� ����������� �������������� �������, � ���� ����������� �� �������
     * � ������� �������� ����� � HSB �� RGN
     */
    private void drawFractal(int index) {
        // ������� �� ������
        for (int x = 0; x < width; x++) {
            // ������� �� ������
            for (int y = 0; y < heigth; y++) {
                double xCoord = FractalGenerator.getCoord(range.x,
                        range.x + range.width, width, x); // ����������� ���������� x � ����� � ��������� �������
                double yCoord = FractalGenerator.getCoord(range.y,
                        range.y + range.height, heigth, y);// ����������� ���������� y � ����� � ��������� �������
                int iteration = fractals.get(index).numIterations(xCoord, yCoord);// �������� ��������, ��� ��������� �������� ��������
                if (iteration == -1) { // ���� ������� �� ����� �� ������� ����������� �� 2000 ��������
                    image.drawPixel(x, y, new Color(0)); // ���������� ����� ���� ��� �������
                }
                else{
                    float hue = 0.7f + (float) iteration / 200f; // ����� ��������� �������� ���(hue) ��� ����� �������� 0.7 + �������� �������� / 200
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f); // ����������� �������� ��� � ����������� RGB ����
                    image.drawPixel(x, y, new Color(rgbColor)); // �������� ������
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