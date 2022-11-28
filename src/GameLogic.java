import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameLogic implements Runnable {
    GamePanel panel;
    boolean[][] temp_map;
    boolean StopFlag;

    int speed;

    public GameLogic(GamePanel panel){
        this.panel=panel;
        temp_map=panel.copyMap(panel.getMap());
        setStopFlag(true);
        speed=100;
    }

    @Override
    public synchronized void run() {
        while(!StopFlag) {
            temp_map = panel.copyMap(panel.getMap());
            for (int y = 0; y < temp_map.length; y++) {
                for (int x = 0; x < temp_map.length; x++) {
                    int neighbours = panel.getNeighbourCount(x, y);
                    if (neighbours < 2 || neighbours > 3) killCell(x, y);
                    else if (neighbours == 3) spawnCell(x, y);
                }
            }
            panel.step(temp_map);
            try {
                wait(10000/speed);
            } catch (InterruptedException e) {
                setStopFlag(true);
            }
        }
    }

    public void setStopFlag(boolean state){
        StopFlag=state;
    }

    public boolean getStopFlag(){
        return StopFlag;
    }

    public void killCell(int x, int y){
        if(temp_map[x][y]) temp_map[x][y]=false;
    }

    public void spawnCell(int x, int y){
        if(!temp_map[x][y]) temp_map[x][y]=true;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void save(){
        boolean[][] map=panel.getMap();
        JSONObject output = new JSONObject();
        output.put("shape", "square");
        output.put("size", map.length);

        for (int y = 0; y < map.length; y++) {
            JSONArray row=new JSONArray();
            for (int x = 0; x < map.length; x++) {
                if(map[x][y]){
                    row.add(1);
                } else{
                    row.add(0);
                }
            }
            output.put(y, row);
        }

        try {
            Files.write(Paths.get("test.json"), output.toJSONString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
