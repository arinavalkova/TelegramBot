import java.util.TimeZone;

class WeatherModel
{
    private String nameOfSity;
    private Double temperature;
    private String description;
    private Integer humidity;
    private String location;

    WeatherModel(String location)
    {
        if(location == null)
        {
            TimeZone tz = TimeZone.getDefault();
            location = tz.getID();
        }
        String[] loc = location.split(Consts.SLASH);
        this.location = loc[1];
    }

    Double getTemperature()
    {
        return temperature;
    }

    void setTemperature(Double temperature)
    {
        this.temperature = temperature;
    }

    String getNameOfSity()
    {
        return nameOfSity;
    }

    void setNameOfSity(String nameOfSity)
    {
        this.nameOfSity = nameOfSity;
    }

    void setDescription(String description)
    {
        this.description = description;
    }

    String getDescription()
    {
        return description;
    }

    Integer getHumidity()
    {
        return humidity;
    }

    void setHumidity(Integer humidity)
    {
        this.humidity = humidity;
    }

    String getLocation()
    {
        return location;
    }
}
