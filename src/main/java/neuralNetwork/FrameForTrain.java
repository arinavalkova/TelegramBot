package neuralNetwork;

import com.mortennobel.imagescaling.ResampleOp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static neuralNetwork.NeuralNetworkConsts.*;

class FrameForTrain implements ActionListener
{
    private JPanel panel;
    private JButton button1, button2;
    private JTextField field;
    private JLabel label1, label2;
    private JFrame frame;

    private String nameOfFile;

    FrameForTrain(String nameOfFile)
    {
        this.nameOfFile = nameOfFile;

        frame = new JFrame(TITLE_OF_TRAIN_FRAME);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        frame.setVisible(true);

        panel = new DrawingClass();

        frame.add(panel);

        label1 = new JLabel(NUMBER_LABEL);
        frame.add(label1);

        field = new JTextField(COLUMNS);
        field.addActionListener(this);
        frame.add(field);


        button1 = new JButton(TRAIN_BUTTON);
        button1.addActionListener(this);
        frame.add(button1, BorderLayout.SOUTH);

        label2 = new JLabel(SPACE);
        frame.add(label2);

        button2 = new JButton(CLEAN_BUTTON);
        button2.addActionListener(this);
        frame.add(button2, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {

        if (ae.getActionCommand().equals(TRAIN_BUTTON))
        {
            BufferedImage image = getScreenShot(panel);
            TrainingNeuralNetwork neuralNetwork = new TrainingNeuralNetwork(Integer.parseInt(field.getText()), image, INPUT_NODES, HIDDEN_NODES, OUTPUT_NODES, LEARN_COUNT, nameOfFile);
            neuralNetwork.train();

            label2.setText(SUC_TRAINED_ANSWER);
        } else if (ae.getActionCommand().equals(CLEAN_BUTTON))
        {
            frame.remove(panel);
            frame.remove(label2);
            frame.remove(label1);
            frame.remove(field);
            frame.remove(button2);
            frame.remove(button1);

            panel = new DrawingClass();
            frame.add(panel);
            frame.add(label1);
            frame.add(field);
            field.setText(EMPTY_LABEL);
            frame.add(button1, BorderLayout.SOUTH);
            label2.setText(EMPTY_LABEL);
            frame.add(label2);
            frame.add(button2, BorderLayout.SOUTH);

            panel.revalidate();
            panel.repaint();

        }
    }

    private static BufferedImage getScreenShot(JPanel panel)
    {
        BufferedImage bi = new BufferedImage(
                panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        panel.paint(bi.getGraphics());

        ResampleOp resamOp = new ResampleOp(DEST_WIDTH, DEST_HEIGHT);
        BufferedImage newBi = resamOp.filter(bi, null);

        try
        {
            File outputfile = new File(PATH_NAME);
            ImageIO.write(newBi, FORMAT_NAME, outputfile);
            System.out.println(SAVED_ANSWER);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return newBi;
    }
}