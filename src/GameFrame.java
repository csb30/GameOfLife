import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
    Thread t;

    public GameFrame(){
        //Initial settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameBoard");

        Container game_container=intitGameBoard();
        add(game_container);
        pack();

        setResizable(false);
        setVisible(true);

    }

    public Container intitGameBoard() {
        Container game_container=new Container();
        game_container.setLayout(new BoxLayout(game_container, BoxLayout.Y_AXIS));

        GamePanel gamePanel=new GamePanel(30);
        gamePanel.setPreferredSize(new Dimension(800,800));
        game_container.add(gamePanel);

        JPanel panel1=new JPanel();
        panel1.setPreferredSize(new Dimension(800,100));
        panel1.setBackground(Color.GRAY);
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));

        //Buttons
        JButton play_pause = new JButton("Play/Pause");

        //Button events
        GameLogic logic=new GameLogic(gamePanel);
        t = new Thread(logic);
        play_pause.addActionListener(ae -> play_pause(logic));

        panel1.add(play_pause);

        game_container.add(panel1);

        gamePanel.flipCell(1,1);

        return game_container;
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
