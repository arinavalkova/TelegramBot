import Translate.TranslateResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class YandexTranslate
{
    private String text;
    private String lang;

    YandexTranslate(String text, String lang)
    {
        this.lang = lang;
        this.text = text.replace(Consts.SPACE, Consts.SPACE_REPLASETMENT);
    }

    String getTranslate()
    {
        String result = Consts.EMPTY_LINE;
        Scanner scanner = null;
        URL url = null;
        try
        {
            url = new URL(Consts.FIRST_PART_OF_TRANSLATOR_URL + text + Consts.SECOND_PART_OF_TRANSLATOR_URL + lang);
            scanner = new Scanner((InputStream) url.getContent());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        while (scanner.hasNext())
        {
            result += scanner.nextLine();
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        TranslateResult translateResult = gson.fromJson(result, TranslateResult.class);

        return translateResult.getText().get(0);
    }
}
