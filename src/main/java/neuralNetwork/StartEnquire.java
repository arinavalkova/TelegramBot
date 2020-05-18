package neuralNetwork;

import javax.swing.*;

public class StartEnquire
{
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new FrameForEnquire(args[0]);
            }
        });
    }
}
