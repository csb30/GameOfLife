import java.awt.*;
import java.awt.Graphics;

public class DisplayGraphics extends Canvas {
    private int rows;

    public void paint(Graphics g){
        setBackground(Color.GRAY);
        setForeground(Color.WHITE);
        int gap=5;
        int size=(780-25-rows*gap)/rows;
        int x=10;
        int y=10;
        for(int i=0; i<rows; i++){
            x=10;
            for(int j=0; j<rows; j++){
                g.fillRect(x,y,size,size);
                x+=size+gap;
            }
            y+=size+gap;
        }
    }

    public DisplayGraphics(int rows){
        this.rows=rows;
    }
}
