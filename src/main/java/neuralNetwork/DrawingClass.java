package neuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static neuralNetwork.NeuralNetworkConsts.*;

public class DrawingClass extends JPanel
{
    private BufferedImage img;

    DrawingClass()
    {
        setBackground(BACK_COLOR);
        setPreferredSize(new Dimension(DEFAULT_WIDTH_OF_JAPNEL, DEFAULT_HEIGHT_OF_JPANEL));

        img = new BufferedImage(DEFAULT_WIDTH_OF_JAPNEL, DEFAULT_HEIGHT_OF_JPANEL, BufferedImage.TYPE_INT_RGB);

        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent evt)
            {
                int x = evt.getX();
                int y = evt.getY();

                Graphics g = img.getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(x, y, RECT_WIDTH, RECT_HEIGHT);
                repaint();
            }

        });
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(img, IMG_X, IMG_Y, null);
    }
}