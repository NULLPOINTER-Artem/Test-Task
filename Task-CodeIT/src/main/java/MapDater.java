import org.json.*;
import java.util.*;

import java.time.Duration;
import java.time.LocalTime;

public class MapDater {
    private List<Map> maps = new ArrayList<>();

    public void convertParsedDataToMap() {
        String jsongData = Parser.readJsonFile("src/main/java/events.json");

        try {
            JSONArray jsonArray = new JSONArray(jsongData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                maps.add(object.toMap());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < maps.size(); i++) {
            maps.get(i).replace("time", maps.get(i).get("time"),
                    Duration.between(
                            LocalTime.MIN,
                            LocalTime.parse( (String) maps.get(i).get("time") )
                    ).toMillis() / 60);
        }

        maps = sortMapByTime();
    }
    private List<Map> sortMapByTime() {
        List sortedKeys = new ArrayList();
        for (int i = 0; i < maps.size(); i++) {
            sortedKeys.add(maps.get(i).get("time"));
        }

        Collections.sort(sortedKeys);

        List<Map> newMapList = new ArrayList<>();
        for (int i = 0; i < sortedKeys.size(); i++) {
            for (int j = 0; j < maps.size(); j++) {
                if (maps.get(j).get("time") == sortedKeys.get(i)) {
                    newMapList.add(maps.get(j));
                    break;
                }
            }
        }

        return newMapList;
    }
    public List<Map> getMap() {
        List<Map> newList = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++) {
            newList.add(maps.get(i));
        }

        return newList;
    }
}
