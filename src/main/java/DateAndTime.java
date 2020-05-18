import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

class DateAndTime
{
    private String currentTime;
    private String currentDate;
    private Boolean locateServerFlag;

    DateAndTime(String location)
    {
        DateTime todayDateTime;
        Locale.setDefault(Locale.US);

        DateTimeFormatter formatForDateNow = DateTimeFormat.forPattern(Consts.FORMAT_FOR_DATE);
        DateTimeFormatter formatForTimeNow = DateTimeFormat.forPattern(Consts.FORMAT_FOR_TIME);

        if (location == null)
        {
            todayDateTime = new DateTime();
            locateServerFlag = true;
        } else
        {
            DateTimeZone tzUser = DateTimeZone.forID(location);
            todayDateTime = new DateTime(tzUser);

            locateServerFlag = false;
        }
        currentDate = todayDateTime.toString(formatForDateNow);
        currentTime = todayDateTime.toString(formatForTimeNow);
    }

    String getCurrentTime()
    {
        return currentTime;
    }

    String getCurrentDate()
    {
        return currentDate;
    }

    boolean getLocateServerFlag()
    {
        return locateServerFlag;
    }
}
