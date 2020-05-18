import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import weather.WeatherResult;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

class Weather
{
    private static Boolean locateServerFlag;

    static String getWeather(String message, WeatherModel weatherModel) throws IOException
    {
        String result = Consts.EMPTY_LINE;
        List<String> cmdArgs = null;
        locateServerFlag = false;

        if(message == null)
        {
            message = weatherModel.getLocation();
            locateServerFlag = true;
        }

        URL url = new URL(Consts.FIRST_PART_OF_WEATHER_URL + message + Consts.SECOND_PART_OF_WEATHER_URL);

        Scanner scanner = new Scanner((InputStream) url.getContent());
        while (scanner.hasNext())
        {
            result += scanner.nextLine();
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        WeatherResult weatherResult = gson.fromJson(result, WeatherResult.class);

        weatherModel.setNameOfSity(weatherResult.getName());
        weatherModel.setTemperature(weatherResult.getMain().getTemp());
        weatherModel.setHumidity(weatherResult.getMain().getHumidity());
        weatherModel.setDescription(weatherResult.getWeather().get(0).getDescription());

        return  Consts.NAME_OF_CITY + weatherModel.getNameOfSity() + Consts.NEXT_LINE +
                Consts.TEMPERATURE + weatherModel.getTemperature() + Consts.NEXT_LINE +
                Consts.NUMIDUTY + weatherModel.getHumidity() + Consts.NEXT_LINE +
                Consts.DESCRIPTION + weatherModel.getDescription() + Consts.NEXT_LINE;
    }

    static boolean getLocationServerFlag()
    {
        return locateServerFlag;
    }
}
