package Lab5;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Дисплей для отрисовки изображений взятый из 4 лабы
 * Дополнененный мутаторами <code>setImageSize(), getBuffImage()</code>
 * Так как фракталы нужно сохранять как изображение при нажаьтии на кнопку,
 * было решено использовать BufferedImage как объект для перевода пикселей с дисплея
 * в файл с изображением
 */
public class JImageDisplay extends javax.swing.JComponent {
    private BufferedImage buffImage;
    private Graphics g;


    public JImageDisplay(int width, int height){
        buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g=buffImage.getGraphics();
        setPreferredSize(new Dimension(width,height));
    }
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage(buffImage, 0,0, buffImage.getWidth(), buffImage.getHeight(), null);

    }

    public void clearImage(){
        g.clearRect(0,0,buffImage.getWidth(), buffImage.getHeight());
        this.repaint();
    }
    public void setImageSize(){
        clearImage();
        if(g instanceof Graphics2D)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.white);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawString("Clear Image",buffImage.getWidth() / 2,buffImage.getHeight() / 2);
        }
        this.repaint();
    }
    public void drawPixel(int x, int y, Color rgbColor){
        g.setColor(rgbColor);
        g.fillRect(x, y, 1, 1);
        this.repaint();
    }
    public BufferedImage getBuffImage(){
        return buffImage;
    }
}