package neuralNetwork;

import javax.swing.*;

public class StartTraining
{
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new FrameForTrain(args[0]);
            }
        });
    }
}

