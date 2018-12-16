import java.io.*;

public class Parser {
    public static String readJsonFile(String fileName) {
        String jsongData = "";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String readLine;

            while ((readLine = bufferedReader.readLine()) != null){
                jsongData += readLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsongData;
    }
}
