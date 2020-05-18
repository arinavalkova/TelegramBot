package neuralNetwork;

import org.apache.commons.math3.linear.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static neuralNetwork.NeuralNetworkConsts.*;

public class EnquireNeuralNetwork
{
    private double[] imageColors;
    private RealMatrix wWIH;
    private RealMatrix wHWO;

    public EnquireNeuralNetwork(BufferedImage image, int inputNodes, int hiddenNodes, int outputNodes, double learnCount, String nameOfFile)
    {
        int i = 0;
        imageColors = new double[inputNodes];
        double[][] weightsHiddenWithOutput = new double[outputNodes][hiddenNodes];
        double[][] weightsInputWithHidden = new double[hiddenNodes][inputNodes];

        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                int rgb = image.getRGB(x, y);
                Color col = new Color(rgb, true);
                imageColors[i++] = (col.getBlue() / 255.0 * 0.99) + 0.01;
            }
        }

        File file = new File(nameOfFile);
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        String line;

        try
        {
            line = br.readLine();
            boolean flag = true;
            for (i = 0; i < hiddenNodes; i++)
            {
                if (!flag)
                    line = br.readLine();
                flag = false;
                String[] mas = line.split(SPACE);

                for (int j = 0; j < inputNodes; j++)
                    weightsInputWithHidden[i][j] = Double.parseDouble(mas[j]);
            }

            for (i = 0; i < outputNodes; i++)
            {
                line = br.readLine();
                String[] mas = line.split(SPACE);

                for (int j = 0; j < hiddenNodes; j++)
                    weightsHiddenWithOutput[i][j] = Double.parseDouble(mas[j]);
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }

        wWIH = new Array2DRowRealMatrix(weightsInputWithHidden);
        wHWO = new Array2DRowRealMatrix(weightsHiddenWithOutput);

    }

    public int enquire()
    {
        RealMatrix inputs = new Array2DRowRealMatrix(imageColors);

        RealMatrix hiddenInputs = wWIH.multiply(inputs);

        RealMatrix hiddenOutputs = activate(hiddenInputs);

        RealMatrix finalInputs = wHWO.multiply(hiddenOutputs);

        RealMatrix finalOutputs = activate(finalInputs);

        System.out.println(finalOutputs);

        int answer = findAnswerNeuralNetwork(finalOutputs);

        System.out.println(GOT_ANSWER_OF_NEURAL_NETWORK);

        return answer;
    }

    private int findAnswerNeuralNetwork(RealMatrix finalOutputs)
    {
        double[] array = MatrixUtils.createRealVector(finalOutputs.getColumn(0)).toArray();
        double max = 0;
        int maxIndex = 0;

        System.out.println(array.length);

        for (int i = 0; i < array.length; i++)
            if (array[i] > max)
            {
                max = array[i];
                maxIndex = i;
            }
        return maxIndex;
    }

    private RealMatrix activate(RealMatrix inputs)
    {
        double[] array;

        for (int i = 0; i < inputs.getRowDimension(); i++)
        {
            array = inputs.getRowVector(i).toArray();
            for (int j = 0; j < array.length; j++)
                array[j] = 1 / (1 + Math.exp(-array[j]));

            inputs.setRowVector(i, MatrixUtils.createRealVector(array));
        }
        return inputs;
    }
}
