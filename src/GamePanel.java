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

    public GamePanel(int gridsize){
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        map=new boolean[gridsize][gridsize];
        this.gridsize=gridsize;

        size=(800-padding*2-gridsize*gap)/ gridsize;
        gameareasize=gridsize*(size+gap);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        int x=e.getPoint().x;
        int y=e.getPoint().y;
        if(x>padding && x<padding+gameareasize
        && y>padding && y<padding+gameareasize){
            flipCell((x-padding)/(size+gap),(y-padding)/(size+gap));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseClicked(e);
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
