import java.util.*;

import java.text.SimpleDateFormat;
import java.sql.Time;

public class ControllerOutput implements Runnable {
    private Thread thread;

    ControllerOutput() {
        thread = new Thread(this::run);
        thread.start();
    }

    @Override
    public void run() {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");

        MapDater mapDater = new MapDater();
        mapDater.convertParsedDataToMap();
        List<Map> newMapList = mapDater.getMap();

        for (int i = 0; i < newMapList.size(); i++) {
            try {
                thread.sleep(((long) newMapList.get(i).get("time") -
                        ((long) newMapList.get(i - 1).get("time"))));

                String time = convertMillisecondsToDate(newMapList, i);
                Date date = format.parse(time);

                System.out.println("Event: " + newMapList.get(i).get("eventName"));
                System.out.println("Time: " + new Time(date.getTime()));
             } catch (IndexOutOfBoundsException e) {
                try {
                    thread.sleep((long) newMapList.get(i).get("time"));

                    String time = convertMillisecondsToDate(newMapList, i);
                    Date date = format.parse(time);

                    System.out.println("Event: " + newMapList.get(i).get("eventName"));
                    System.out.println("Time: " + new Time(date.getTime()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String convertMillisecondsToDate(List<Map> maps, int i) {
        return (((long) maps.get(i).get("time")
                / (1000 * 60) % 60) + ":" + (((long) maps.get(i).get("time")
                / 1000) % 60));
    }
}
