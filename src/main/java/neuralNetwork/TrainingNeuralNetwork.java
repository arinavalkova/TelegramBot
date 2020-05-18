package neuralNetwork;

import org.apache.commons.math3.linear.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static neuralNetwork.NeuralNetworkConsts.*;

class TrainingNeuralNetwork
{
    private double learnCount;
    private int expectedNumber;
    private double[] imageColors;
    private RealMatrix wWIH;
    private RealMatrix wHWO;
    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;

    String nameOfFile;

    TrainingNeuralNetwork(int expectedNumber, BufferedImage image, int inputNodes, int hiddenNodes, int outputNodes, double learnCount, String nameOfFile)
    {
        this.hiddenNodes = hiddenNodes;
        this.inputNodes = inputNodes;
        this.outputNodes = outputNodes;

        this.nameOfFile = nameOfFile;
        this.learnCount = learnCount;
        this.expectedNumber = expectedNumber;

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
                imageColors[i] = (col.getBlue() / 255.0 * 0.99) + 0.01;
                i++;
            }
        }

        File file = new File(nameOfFile);
        BufferedReader br = null;
        try
        {
            if (!file.exists())
                file.createNewFile();

            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), StandardCharsets.UTF_8));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        String line;
        try
        {
            if ((line = br.readLine()) == null)
            {

                for (i = 0; i < hiddenNodes; i++)
                    for (int j = 0; j < inputNodes; j++)
                    {
                        weightsInputWithHidden[i][j] = Math.random() * Math.pow(inputNodes, -0.5);
                    }
                for (i = 0; i < outputNodes; i++)
                    for (int j = 0; j < hiddenNodes; j++)
                        weightsHiddenWithOutput[i][j] = Math.random() * Math.pow(hiddenNodes, -0.5);
            } else
            {

                boolean flag = true;
                for (i = 0; i < hiddenNodes; i++)
                {
                    if (!flag)
                        line = br.readLine();
                    flag = false;
                    String[] mas = line.split(SPACE);

                    for (int j = 0; j < inputNodes; j++)
                    {
                        weightsInputWithHidden[i][j] = Double.parseDouble(mas[j]);
                    }
                }

                for (i = 0; i < outputNodes; i++)
                {
                    line = br.readLine();
                    String[] mas = line.split(SPACE);

                    for (int j = 0; j < hiddenNodes; j++)
                        weightsHiddenWithOutput[i][j] = Double.parseDouble(mas[j]);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        wWIH = new Array2DRowRealMatrix(weightsInputWithHidden);
        wHWO = new Array2DRowRealMatrix(weightsHiddenWithOutput);

    }

    void train()
    {
        RealMatrix inputs = new Array2DRowRealMatrix(imageColors);
        RealMatrix targets = new Array2DRowRealMatrix(generateTargets(expectedNumber));

        RealMatrix hiddenInputs = wWIH.multiply(inputs);

        RealMatrix hiddenOutputs = activate(hiddenInputs);

        RealMatrix finalInputs = wHWO.multiply(hiddenOutputs);

        RealMatrix finalOutputs = activate(finalInputs);

        RealMatrix outputErrors = targets.subtract(finalOutputs);

        RealMatrix hiddenErrors = wHWO.transpose().multiply(outputErrors);

        wWIH = wWIH.add(getErrorMatrix(hiddenErrors, hiddenOutputs, inputs));
        wHWO = wHWO.add(getErrorMatrix(outputErrors, finalOutputs, hiddenOutputs));

        writeWeights(wWIH, wHWO, nameOfFile);

        System.out.println(findAnswerNeuralNetwork(finalOutputs));

    }

    private int findAnswerNeuralNetwork(RealMatrix finalOutputs)
    {
        double[] array = MatrixUtils.createRealVector(finalOutputs.getColumn(0)).toArray();
        double max = 0;
        int maxIndex = 0;

        for (int i = 0; i < array.length; i++)
            if (array[i] > max)
            {
                max = array[i];
                maxIndex = i;
            }
        return maxIndex;
    }

    private void writeWeights(RealMatrix wWIH, RealMatrix wHWO, String nameOfFile)
    {
        File writer = new File(nameOfFile);

        try
        {
            double[] array;
            FileWriter fooWriter = new FileWriter(writer, false);

            for (int i = 0; i < hiddenNodes; i++)
            {
                array = MatrixUtils.createRealVector(wWIH.getRow(i)).toArray();
                for (double v : array)
                {
                    fooWriter.write(String.valueOf(v));
                    fooWriter.write(SPACE);
                }
                fooWriter.write(GO_TO_NEXT_LINE);
            }

            for (int i = 0; i < outputNodes; i++)
            {
                array = MatrixUtils.createRealVector(wHWO.getRow(i)).toArray();

                for (double v : array)
                {
                    fooWriter.write(String.valueOf(v));
                    fooWriter.write(SPACE);
                }
                fooWriter.write(GO_TO_NEXT_LINE);
            }

            fooWriter.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private RealMatrix getErrorMatrix(RealMatrix errors, RealMatrix outputs, RealMatrix something)
    {
        double[] array1 = MatrixUtils.createRealVector(errors.getColumn(0)).toArray();
        double[] array2 = MatrixUtils.createRealVector(outputs.getColumn(0)).toArray();

        double[] array = multipleArrays(array1, array2);

        double[] temp1 = OneSub(array2);

        double[] temp2 = multipleArrays(array, temp1);

        RealMatrix first = new Array2DRowRealMatrix(temp2);

        something = something.transpose();

        RealMatrix answer = first.multiply(something);

        answer = answer.scalarMultiply(learnCount);

        return answer;
    }

    private double[] OneSub(double[] array)
    {
        for (int i = 0; i < array.length; i++)
            array[i] = 1.0 - array[i];

        return array;
    }

    private double[] multipleArrays(double[] array1, double[] array2)
    {
        for (int i = 0; i < array1.length; i++)
            array1[i] *= array2[i];
        return array1;
    }

    private RealMatrix activate(RealMatrix inputs)
    {
        double[] array;

        for (int i = 0; i < inputs.getRowDimension(); i++)
        {
            array = inputs.getRowVector(i).toArray();
            for (int j = 0; j < array.length; j++)
                array[j] = 1.0 / (1 + Math.exp(-array[j]));

            inputs.setRowVector(i, MatrixUtils.createRealVector(array));
        }
        return inputs;
    }

    private double[] generateTargets(int expectedNumber)
    {
        double[] mas = new double[outputNodes];
        for (int i = 0; i < outputNodes; i++)
            mas[i] = 0.01;
        mas[expectedNumber] = 0.99;

        return mas;
    }
}
