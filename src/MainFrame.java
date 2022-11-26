import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    public MainFrame() {
        //Initial settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,800);
        setTitle("GameOfLife");
        setResizable(false);

        Container menu_container=intitMenu();
        add(menu_container);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    
    private void setButtonStyle(JButton button, Dimension dimension, Color color, Font font){
        button.setPreferredSize(dimension);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setFont(font);
    }

    private Container intitMenu(){
        Container menu_container=new Container();
        menu_container.setLayout(new BoxLayout(menu_container, BoxLayout.Y_AXIS));

        //Title panel
        JPanel panel1=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.gray);
        JLabel label=new JLabel("Conway's Game of Life");
        label.setFont(new Font("Impact", Font.PLAIN, 50));
        panel1.add(label);
        menu_container.add(panel1);

        //Buttons panel
        JPanel panel2=new JPanel();
        panel2.setBackground(Color.gray);
        panel2.setLayout(new FlowLayout());
        menu_container.add(panel2);

        //Buttons
        JButton new_game = new JButton("New Game");
        JButton load_game = new JButton("Load Game");
        JButton exit = new JButton("Exit");

        //Button events
        new_game.addActionListener(ae -> new GameFrame());
        load_game.addActionListener(ae -> setTitle(ae.getActionCommand()));
        exit.addActionListener(e -> System.exit(0));

        //Button formatting
        Font basic_font=new Font("Impact", Font.PLAIN, 15);
        Dimension button_dimension=new Dimension(150, 100);
        setButtonStyle(new_game, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(load_game, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(exit, button_dimension, Color.WHITE, basic_font);

        //Adding buttons to panel
        panel2.add(new_game);
        panel2.add(load_game);
        panel2.add(exit);

        return menu_container;
    }
}
