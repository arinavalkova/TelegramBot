package neuralNetwork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static neuralNetwork.NeuralNetworkConsts.*;

public class MNISTtraining
{
    public static void main(String[] args)
    {
        try
        {
            FileReader file = new FileReader(FILE_NAME);
            BufferedReader reader = new BufferedReader(file);
            String line = reader.readLine();

            while (line != null)
            {

                int[] splitNums = Arrays.stream(line.split(REGEX)).mapToInt(Integer::parseInt).toArray();
                int[] numsWithOutFirst = new int[splitNums.length - 1];

                int expected = splitNums[0];
                System.out.println(expected);

                for (int i = 0; i < splitNums.length - 1; i++)
                    numsWithOutFirst[i] = splitNums[i + 1];
                BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_BYTE_GRAY);
                WritableRaster raster = image.getRaster();
                raster.setPixels(IMAGE_CORNER_X, IMAGE_CORNER_Y, IMAGE_WIDTH, IMAGE_HEIGHT, numsWithOutFirst);

                try
                {
                    File outputfile = new File(PATH_NAME);
                    ImageIO.write(image, FORMAT_NAME, outputfile);
                    System.out.println(SAVED_ANSWER);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                String nameOfFile = INPUT_FILE;

                TrainingNeuralNetwork neuralNetwork = new TrainingNeuralNetwork(expected, image, INPUT_NODES, HIDDEN_NODES, OUTPUT_NODES, LEARN_COUNT, nameOfFile);
                neuralNetwork.train();

                System.out.println(SUC_TRAINED_ANSWER);
                line = reader.readLine();
            }

            file.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
