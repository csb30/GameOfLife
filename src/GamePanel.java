import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ComponentListener, MouseListener, MouseMotionListener {
    boolean[][] map;
    int gridsize;
    int gap=1;
    int padding=20;
    int size;
    int windowsize=800;
    int gameareasize;
    boolean[][] drag_array;

    public GamePanel(int gridsize){
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        map=new boolean[gridsize][gridsize];
        this.gridsize=gridsize;

        size=(800-padding*2-gridsize*gap)/ gridsize;
        gameareasize=gridsize*(size+gap);
    }

    public GamePanel(boolean[][] map){
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.gridsize=map.length;

        size=(800-padding*2-gridsize*gap)/ gridsize;
        gameareasize=gridsize*(size+gap);

        step(map);
    }

    public void paint(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(0,0,windowsize,windowsize);

        g.setColor(Color.WHITE);

        int x=padding;
        int y=padding;
        for(int i = 0; i< gridsize; i++){
            x=padding;
            for(int j = 0; j< gridsize; j++){
                if(map[j][i]){
                    g.setColor(Color.BLACK);
                } else{
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x,y,size,size);
                x+=size+gap;
            }
            y+=size+gap;
        }
    }

    public void flipCell(int x, int y){
        map[x][y]=!map[x][y];
        repaint();
    }

    public int getNeighbourCount(int x, int y){
        int count=0;

        //row above
        if(x-1>=0 && y-1>=0 && map[x-1][y-1]) count++;
        if(y-1>=0 && map[x][y-1]) count++;
        if(x+1<gridsize && y-1>=0 && map[x+1][y-1]) count++;

        //two sides
        if(x-1>=0 && map[x-1][y]) count++;
        if(x+1<gridsize && map[x+1][y]) count++;

        //row below
        if(x-1>=0 && y+1<gridsize && map[x-1][y+1]) count++;
        if(y+1<gridsize && map[x][y+1]) count++;
        if(x+1<gridsize && y+1<gridsize && map[x+1][y+1]) count++;

        return count;
    }

    public boolean[][] getMap() {
        return map;
    }

    public void step(boolean[][] map){
        this.map=copyMap(map);
        repaint();
    }

    public boolean[][] copyMap(boolean[][] map){
        boolean[][] ret=new boolean[map.length][map[0].length];
        for(int y=0; y<map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                ret[x][y]=map[x][y];
            }
        }

        return ret;
    }

    public int pos_to_coord(int i){
        return (i-padding)/(size+gap);
    }

    public void clear(){
        map=new boolean[gridsize][gridsize];
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x=e.getPoint().x;
        int y=e.getPoint().y;
        if(x>padding && x<padding+gameareasize
        && y>padding && y<padding+gameareasize){
            flipCell(pos_to_coord(x),pos_to_coord(y));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        drag_array=new boolean[gridsize][gridsize];
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drag_array=null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x=e.getPoint().x;
        int y=e.getPoint().y;
        if(x>padding && x<padding+gameareasize
        && y>padding && y<padding+gameareasize
        && !drag_array[pos_to_coord(x)][pos_to_coord(y)]){
            drag_array[pos_to_coord(x)][pos_to_coord(y)]=true;
            mouseClicked(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
