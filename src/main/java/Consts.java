import com.vdurmont.emoji.EmojiParser;

class Consts
{
    static final String LIST_OF_LANGS = "ListOfLang";
    static final String LANGS = "албанский sq,\nанглийский en,\nарабский ar,\nармянский hy,\nазербайджанский az,\nафрикаанс af,\nбаскский eu,\nбелорусский be,\nболгарский bg,\nбоснийский bs,\nваллийский cy,\nвьетнамский vi,\nвенгерский hu,\nгаитянский(креольский) ht,\nгалисийский gl,\nголландский nl,\nгреческий el,\nгрузинский  ka,\nдатский da,\nиврит he,\nиндонезийский id,\nирландский  ga,\nитальянский it,\nиспанский es,\nказахский  kk,\nкаталанский ca,\nкиргизский  ky,\nкитайский zh,\nкорейский ko,\nлатынь  la,\nлатышский   lv,\nлитовский   lt,\nмалагасийский   mg,\nмалайский   ms,\nмальтийский mt,\nмакедонский mk,\nмонгольский mn,\nнемецкий de,\nнорвежский  no,\nперсидский  fa,\nпольский  pl,\nпортугальский pt,\nрумынский ro,\nрусский ru,\nсербский sr,\nсловацкий sk,\nсловенский  sl,\nсуахили sw,\nтаджикский  tg,\nтайский th,\nтагальский  tl,\nтатарский   tt,\nтурецкий tr,\nузбекский   uz,\nукраинский  uk,\nфинский fi,\nфранцузский fr,\nхорватский  hr,\nчешский cs,\nшведский sv,\nэстонский   et,\nяпонский ja";
    static final String SLASH = "/";
    static final String LOCATION_COMMAND = "Location";
    static final String SERVER_TIME = "(server time)";
    static final String SERVER_WEATHER = "(server weather)";
    static final String SERVER_DATE = "(server date)";
    static final String SUCCES_SET_LOCATION = "Successfully set location";
    static final int MIN_TRANSLATE_LINE_SIZE = 3;
    static final String HELP_BUTTON = "Help :grapes:";
    static final String TIME_BUTTON = "Time :hourglass:";
    static final String DATE_BUTTON = "Date :calendar:";
    static final String WEATHER_BUTTON = "Weather";
    static final String BOT_USER_NAME = "VeryVeryVeryGodBot";
    static final String BOT_TOKEN = "1046723400:AAHHisrS-IU-eX7HeSKde_mpxccx2savH_Y";
    static final String SPACE_REPLASETMENT = "%20";
    static final String UNKNOWN_CITY = "Unknown city";
    static final String WEATHER_COMMAND = "Weather";
    static final String TRANSLATE_COMMAND = "Translate";
    static final String DATE_COMMAND = "Date";
    static final String TIME_COMMAND = "Time";
    static final String INPUT_FILE = "input.txt";
    static final int INPUT_NODES = 28 * 28;
    static final int HIDDEN_NODES = 100;
    static final int OUTPUT_NODES = 10;
    static final double LEARN_COUNT = 0.1;

    static final String SPACE = " ";
    static final String PHOTO_PATH = "src\\main\\resources\\photo.png";
    static final int DEST_WIDTH = 28;
    static final int DEST_HEIGHT = 28;
    static final String FIRST_PART_OF_JSON_URL = "https://api.telegram.org/bot";
    static final String SECOND_PART_OF_JSON_URL = "/getFile?file_id=";
    static final String EMPTY_LINE = "";
    static final int BUFFER_SIZE_IMG = 1000000;
    static final String FIRST_PART_OF_IMG_URL = "https://api.telegram.org/file/bot";
    static final String SECOND_PART_OF_IMG_URL = "/";
    static final String RESULT_LINE = "result";
    static final String FILE_PATH_LINE = "file_path";
    static final String GET_METHOD = "GET";
    static final int X_COORD = 0;
    static final int Y_COORD = 0;

    static final String FORMAT_FOR_DATE = "dd.MM.yyyy";
    static final String FORMAT_FOR_TIME = "HH:mm:ss zzzz";

    static final String FIRST_PART_OF_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    static final String SECOND_PART_OF_WEATHER_URL = "&units=metric&appid=cffbe4b961ec581d11adb3dbc92cdde0";
    static final String NEXT_LINE = "\n";
    static final String NAME_OF_CITY = "Name of the city: ";
    static final String TEMPERATURE = "Temperature: ";
    static final String NUMIDUTY = "Humidity: ";
    static final String DESCRIPTION = "Description: ";

    static final String FIRST_PART_OF_TRANSLATOR_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200407T052301Z.6a647c360646028e.d0f0b711521d4cd20a6d60e6937307e2053d138a&text=";
    static final String SECOND_PART_OF_TRANSLATOR_URL = "&lang=";

    static final String HELP = EmojiParser.parseToUnicode("*Hello! Here is a list of what I can do ::grapes:*\n" +
            "\n" +
            "*Date* - I will say today's date :calendar:\n\n" +
            "*Time* - I’ll say what time it is :hourglass:\n\n" +
            "*Weather <city>* - I will talk about the weather in any city :thermometer::cloud::rainbow:\n\n" +
            "*<picture>* - Guess the number in your picture:camera:\n\n" +
            "*Location <ZONE/CITY>* - To set your location\n\n" +
            "*Translate <text> <en-ru/ru-en>* - I will translate your text into Eng or Rus :books: \n\n" +
            "*ListOfLang* - list of available languages for translator");
             }
