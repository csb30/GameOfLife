import javax.swing.*;
import java.awt.*;

public class MainFrame {

    public MainFrame(){
        JFrame f=new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(500,500);
        f.setTitle("GameOfLife");
        f.setResizable(false);

        JPanel main_panel=new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        f.add(main_panel);

        JPanel panel1=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.gray);
        panel1.add(new JLabel("Conway's Game Of Life"));
        main_panel.add(panel1);

        JPanel panel2=new JPanel();
        panel2.setBackground(Color.gray);
        panel2.setLayout(new FlowLayout());
        main_panel.add(panel2);

        JButton new_game=new JButton("New Game");
        JButton load_game=new JButton("Load Game");
        JButton exit=new JButton("Exit");
        new_game.setPreferredSize(new Dimension(100, 100));
        load_game.setPreferredSize(new Dimension(100, 100));
        exit.setPreferredSize(new Dimension(100, 100));
        panel2.add(new_game);
        panel2.add(load_game);
        panel2.add(exit);


        f.setVisible(true);
    }
}
