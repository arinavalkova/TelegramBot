import com.vdurmont.emoji.EmojiParser;
import neuralNetwork.EnquireNeuralNetwork;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Bot extends TelegramLongPollingBot
{
    private static String location;

    public interface Command
    {
        void executeCommand(List<String> args, Message message);
    }

    private Map<String, Command> commands = new HashMap<>();

    public static void main(String[] args)
    {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try
        {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e)
        {
            e.printStackTrace();
        }
    }

    void sendindMessageToBot(Message message, String text)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText(text);
        try
        {
            setButtons(sendMessage);
            execute(sendMessage);

        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }

    private void setButtons(SendMessage sendMessage)
    {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(EmojiParser.parseToUnicode(Consts.HELP_BUTTON)));
        keyboardFirstRow.add(new KeyboardButton(EmojiParser.parseToUnicode(Consts.TIME_BUTTON)));
        keyboardFirstRow.add(new KeyboardButton(EmojiParser.parseToUnicode(Consts.DATE_BUTTON)));
        keyboardFirstRow.add(new KeyboardButton(EmojiParser.parseToUnicode(Consts.WEATHER_BUTTON)));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    @Override
    public void onUpdateReceived(Update update)
    {
        commandsPutToMap();
        Message message = update.getMessage();
        if (message != null && message.hasPhoto())
        {
            TelegramImage telegramImage = new TelegramImage(getBotToken());
            BufferedImage image = telegramImage.getPhoto(message);

            String nameOfFile = Consts.INPUT_FILE;

            EnquireNeuralNetwork neuralNetwork = new EnquireNeuralNetwork(image, Consts.INPUT_NODES, Consts.HIDDEN_NODES, Consts.OUTPUT_NODES, Consts.LEARN_COUNT, nameOfFile);

            int neuralAnswer = neuralNetwork.enquire();

            sendindMessageToBot(message, Integer.toString(neuralAnswer));
        } else if (message != null && message.hasText())
        {
            String[] cmdLine = message.getText().split(Consts.SPACE);
            List<String> cmdArgs = null;
            String cmd = cmdLine[0];
            if (cmdLine.length == 1 && location != null)
            {
                String[] locs = location.split(Consts.SLASH);
                cmdArgs = Arrays.asList(Arrays.copyOfRange(locs, 1, locs.length));
            } else
            {
                cmd = cmdLine[0];
                cmdArgs = Arrays.asList(Arrays.copyOfRange(cmdLine, 1, cmdLine.length));
            }
            if (commands.containsKey(cmd))
            {
                commands.get(cmd).executeCommand(cmdArgs, message);
            } else
            {
                sendindMessageToBot(message, Consts.HELP);
            }
        }
    }

    private void commandsPutToMap()
    {
        commands.put(Consts.WEATHER_COMMAND, new WeatherCommand());
        commands.put(Consts.TRANSLATE_COMMAND, new TranslateCommand());
        commands.put(Consts.DATE_COMMAND, new DateCommand());
        commands.put(Consts.TIME_COMMAND, new TimeCommand());
        commands.put(Consts.LOCATION_COMMAND, new LocationCommand());
        commands.put(Consts.LIST_OF_LANGS, new ListOfLangCommand());
    }


    public String getBotUsername()
    {
        return Consts.BOT_USER_NAME;
    }

    public String getBotToken()
    {
        return Consts.BOT_TOKEN;
    }

    private class TranslateCommand implements Command
    {
        @Override
        public void executeCommand(List<String> args, Message message)
        {
            String text = args.get(0);
            if (args.size() > Consts.MIN_TRANSLATE_LINE_SIZE)
            {
                for (int i = 1; i < args.size() - 1; i++)
                    text += Consts.SPACE_REPLASETMENT + args.get(i);
            }
            YandexTranslate yandexTranslate = new YandexTranslate(text, args.get(args.size() - 1));
            sendindMessageToBot(message, yandexTranslate.getTranslate());
        }
    }

    private class TimeCommand implements Command
    {
        @Override
        public void executeCommand(List<String> args, Message message)
        {
            DateAndTime dateAndTime = new DateAndTime(location);
            sendindMessageToBot(message, dateAndTime.getCurrentTime());
            if (dateAndTime.getLocateServerFlag())
            {
                sendindMessageToBot(message, Consts.SERVER_TIME);
            }
        }
    }

    public class WeatherCommand implements Bot.Command
    {
        @Override
        public void executeCommand(List<String> args, Message message)
        {
            WeatherModel weatherModel = new WeatherModel(location);
            try
            {
                if(args.isEmpty())
                    sendindMessageToBot(message, Weather.getWeather(null, weatherModel));
                else
                 sendindMessageToBot(message, Weather.getWeather(args.get(0), weatherModel));
                if (Weather.getLocationServerFlag())
                {
                    sendindMessageToBot(message, Consts.SERVER_WEATHER);
                }
            } catch (IOException e)
            {
                sendindMessageToBot(message, Consts.UNKNOWN_CITY);
            }
        }
    }

    public class DateCommand implements Bot.Command
    {
        @Override
        public void executeCommand(List<String> args, Message message)
        {
            DateAndTime dateAndTime = new DateAndTime(location);
            sendindMessageToBot(message, dateAndTime.getCurrentDate());
            if (dateAndTime.getLocateServerFlag())
            {
                sendindMessageToBot(message, Consts.SERVER_DATE);
            }
        }
    }

    private class LocationCommand implements Command
    {
        @Override
        public void executeCommand(List<String> args, Message message)
        {
            location = args.get(0);
            sendindMessageToBot(message, Consts.SUCCES_SET_LOCATION);
        }
    }

    private class ListOfLangCommand implements Command
    {
        @Override
        public void executeCommand(List<String> args, Message message)
        {
            sendindMessageToBot(message, Consts.LANGS);
        }
    }
}
