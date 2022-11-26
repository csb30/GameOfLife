import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

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

        gamePanel.flipCell(1,1);

        return game_container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
