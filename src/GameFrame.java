import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    Thread t;
    GamePanel gamePanel;
    MainFrame mf;

    public GameFrame(MainFrame mf, int size, String gridtype, boolean[][] map){
        this.mf=mf;

        //Initial settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameBoard");

        Container game_container=intitGameBoard(size, map);
        add(game_container);
        pack();

        setResizable(false);
        setVisible(true);
    }

    public Container intitGameBoard(int size, boolean[][] map) {
        Container game_container=new Container();
        game_container.setLayout(new BoxLayout(game_container, BoxLayout.Y_AXIS));

        if(map==null){
            gamePanel=new GamePanel(size);
        } else{
            gamePanel=new GamePanel(map);
        }
        gamePanel.setPreferredSize(new Dimension(800,800));
        game_container.add(gamePanel);

        JPanel panel1=new JPanel();
        panel1.setPreferredSize(new Dimension(800,70));
        panel1.setBackground(Color.GRAY);
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));

        //Buttons
        JButton play_pause = new JButton("PLAY");
        JButton plus=new JButton("+");
        JButton minus=new JButton("-");
        JButton save=new JButton("Save");
        JButton clear=new JButton("Clear");
        JButton menu=new JButton("Menu");

        GameLogic logic=new GameLogic(gamePanel);
        t = new Thread(logic);

        //Speed label
        JLabel speed_label=new JLabel(Integer.toString(logic.getSpeed()));

        //Button events
        play_pause.addActionListener(e -> {
            if(play_pause.getText().equals("PLAY")) play_pause.setText("PAUSE");
            else play_pause.setText("PLAY");

            play_pause(logic);
        });
        plus.addActionListener(e -> {
            if(logic.getSpeed()+10<=500) {
                logic.setSpeed(logic.getSpeed() + 10);
                speed_label.setText(Integer.toString(logic.getSpeed()));
            }
        });
        minus.addActionListener(e -> {
            if(logic.getSpeed()-10>0) {
                logic.setSpeed(logic.getSpeed() - 10);
                speed_label.setText(Integer.toString(logic.getSpeed()));
            }
        });
        save.addActionListener(ae -> logic.save());
        clear.addActionListener(ae-> gamePanel.clear());
        menu.addActionListener(e -> {
            mf.setVisible(true);
            dispose();
        });

        //Button formatting
        Font basic_font=new Font("Impact", Font.PLAIN, 15);
        Dimension button_dimension=new Dimension(150, 50);
        Dimension small_button_dimension=new Dimension(50, 50);
        setButtonStyle(play_pause, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(plus, small_button_dimension, Color.WHITE, basic_font);
        setButtonStyle(minus, small_button_dimension, Color.WHITE, basic_font);
        setButtonStyle(save, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(clear, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(menu, button_dimension, Color.WHITE, basic_font);

        //Adding buttons to panel
        panel1.add(play_pause);
        panel1.add(minus);
        panel1.add(speed_label);
        panel1.add(plus);
        panel1.add(save);
        panel1.add(clear);
        panel1.add(menu);

        game_container.add(panel1);

        return game_container;
    }

    private void setButtonStyle(JButton button, Dimension dimension, Color color, Font font){
        button.setPreferredSize(dimension);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setFont(font);
    }

    public void play_pause(GameLogic logic) {
       if(logic.getStopFlag()){
           logic.setStopFlag(false);
           t.start();
        } else{
           t.interrupt();
           t = new Thread(logic);
       }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
