package DZ_2.server;

import java.io.FileReader;
import java.io.FileWriter;

public class LogStorage implements iStorage<String> {
    private static final String LOG_PATH = "src/DZ_2/log.txt";

    public void saveLog(String text){//метод сохраниения логов
        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String loadLog(){//метод загркзки логов
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}