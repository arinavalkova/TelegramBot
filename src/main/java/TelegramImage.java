import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mortennobel.imagescaling.ResampleOp;
import image.ImageResult;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

class TelegramImage
{
    private String botToken;

    TelegramImage(String botToken)
    {
        this.botToken = botToken;
    }

    BufferedImage getPhoto(Message message)
    {
        putPhotoToFile(message);

        Image photo = null;
        try
        {
            photo = ImageIO.read(new File(Consts.PHOTO_PATH));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        BufferedImage image = null;
        try
        {
            image = toBufferedImage(photo);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        ResampleOp resamOp = new ResampleOp(Consts.DEST_WIDTH, Consts.DEST_HEIGHT);
        BufferedImage newImage = resamOp.filter(image, null);

        return  newImage;
    }

    private void putPhotoToFile(Message message)
    {
        List<PhotoSize> image = message.getPhoto();
        String photoID = image.get(0).getFileId();

        URL url = null;
        URL url2 = null;
        try
        {
            url = new URL(Consts.FIRST_PART_OF_JSON_URL + botToken + Consts.SECOND_PART_OF_JSON_URL + photoID);

            Scanner in1 = new Scanner((InputStream) url.getContent());
            String result = Consts.EMPTY_LINE;
            while (in1.hasNext())
            {
                result += in1.nextLine();
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ImageResult imageResult = gson.fromJson(result, ImageResult.class);

            String path = imageResult.getResult().getFilePath();

            url2 = new URL(Consts.FIRST_PART_OF_IMG_URL + botToken + Consts.SECOND_PART_OF_IMG_URL + path);

            putPhotoFromUrlToPath(url2, Consts.PHOTO_PATH);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void putPhotoFromUrlToPath(URL url2, String photoPath) throws IOException
    {
        HttpURLConnection urlconn;
        urlconn = (HttpURLConnection) url2.openConnection();
        urlconn.setRequestMethod(Consts.GET_METHOD);
        urlconn.connect();
        InputStream in = null;
        in = urlconn.getInputStream();
        OutputStream writer = new FileOutputStream(photoPath);
        byte[] buffer = new byte[Consts.BUFFER_SIZE_IMG];
        int c = in.read(buffer);
        while (c > 0)
        {
            writer.write(buffer, 0, c);
            c = in.read(buffer);
        }
        writer.flush();
        writer.close();
        in.close();
    }

    private static BufferedImage toBufferedImage(Image img) throws IOException
    {

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, Consts.X_COORD, Consts.Y_COORD, null);
        bGr.dispose();

        return bimage;
    }
}
