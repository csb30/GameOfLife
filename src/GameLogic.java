public class GameLogic implements Runnable {
    GamePanel panel;
    boolean[][] temp_map;
    boolean StopFlag;

    public GameLogic(GamePanel panel){
        this.panel=panel;
        temp_map=panel.copyMap(panel.getMap());
        setStopFlag(true);
    }

    @Override
    public synchronized void run() {
        while(!StopFlag) {
            temp_map = panel.copyMap(panel.getMap());
            for (int y = 0; y < 30; y++) {
                for (int x = 0; x < 30; x++) {
                    int neighbours = panel.getNeighbourCount(x, y);
                    if (neighbours < 2 || neighbours > 3) killCell(x, y);
                    else if (neighbours == 3) spawnCell(x, y);
                }
            }
            panel.step(temp_map);
            try {
                wait(100);
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
}
