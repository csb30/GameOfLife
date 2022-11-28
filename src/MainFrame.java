import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;

public class MainFrame extends JFrame implements ActionListener {
    JPanel title_panel;
    JPanel button_panel;
    JPanel new_game_panel;
    JPanel errors;
    Container menu_container;

    public MainFrame() {
        //Initial settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,400);
        setTitle("GameOfLife");
        setResizable(false);

        menu_container=new Container();
        menu_container.setLayout(new BoxLayout(menu_container, BoxLayout.Y_AXIS));
        menu_container.setBackground(Color.GRAY);

        errors=new JPanel();
        errors.setLayout(new FlowLayout());
        errors.setBackground(Color.GRAY);

        intitMenu();
        initNewGame();

        menu_container.add(button_panel);

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

    private void initNewGame(){
        new_game_panel=new JPanel();
        new_game_panel.setBackground(Color.GRAY);
        new_game_panel.setLayout(new FlowLayout());

        //Components
        Font basic_font=new Font("Impact", Font.PLAIN, 15);
        //Map size
        JLabel label1=new JLabel("Grid size:");
        label1.setFont(basic_font);
        JTextField mapsize=new JTextField(3);
        //Grid
        JLabel label2=new JLabel("Grid Type:");
        label2.setFont(basic_font);
        String[] gridtypes= {"Square", "Hexagon"};
        JComboBox gridtype=new JComboBox(gridtypes);
        //Buttons
        Dimension button_dimension=new Dimension(100, 30);
        JButton ok=new JButton("OK");
        setButtonStyle(ok,button_dimension,Color.WHITE,basic_font);
        JButton back=new JButton("Back");
        setButtonStyle(back,button_dimension,Color.LIGHT_GRAY,basic_font);

        //Button events

        ok.addActionListener(e -> {
            try{
                int size = Integer.parseInt(mapsize.getText());
                if (size<2 || size>100){
                    throw(new Exception("Not in range"));
                }
                ok(size, (String) gridtype.getSelectedItem());
            } catch (Exception exception){
                menu_container.add(errors);
                errors.add(new JLabel("Error: Grid size must be between 2-100"));
                repaint();
                revalidate();
            }
        });
        back.addActionListener(ae -> back());

        //Adding Components
        new_game_panel.add(label1);
        new_game_panel.add(mapsize);
        new_game_panel.add(label2);
        new_game_panel.add(gridtype);
        new_game_panel.add(ok);
        new_game_panel.add(back);
    }

    private void intitMenu(){
        //Title panel
        title_panel=new JPanel();
        title_panel.setLayout(new FlowLayout());
        title_panel.setBackground(Color.gray);
        JLabel label=new JLabel("Conway's Game of Life");
        label.setFont(new Font("Impact", Font.PLAIN, 50));
        title_panel.add(label);
        menu_container.add(title_panel);

        //Buttons panel
        button_panel=new JPanel();
        button_panel.setBackground(Color.gray);
        button_panel.setLayout(new FlowLayout());
        menu_container.add(button_panel);

        //Buttons
        JButton new_game = new JButton("New Game");
        JButton load_game = new JButton("Load Game");
        JButton exit = new JButton("Exit");

        //Button events
        new_game.addActionListener(ae -> newGame());
        load_game.addActionListener(ae -> loadGame("test.json"));
        exit.addActionListener(ae -> System.exit(0));

        //Button formatting
        Font basic_font=new Font("Impact", Font.PLAIN, 15);
        Dimension button_dimension=new Dimension(150, 100);
        setButtonStyle(new_game, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(load_game, button_dimension, Color.WHITE, basic_font);
        setButtonStyle(exit, button_dimension, Color.WHITE, basic_font);

        //Adding buttons to panel
        button_panel.add(new_game);
        button_panel.add(load_game);
        button_panel.add(exit);

        add(menu_container);
    }

    private void newGame(){
        menu_container.remove(button_panel);
        errors.removeAll();
        menu_container.remove(errors);
        menu_container.add(new_game_panel);
        repaint();
        revalidate();
    }

    private void loadGame(String filename){
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) readJson(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int size=Integer.parseInt(jsonObject.get("size").toString());

        String gridtype= jsonObject.get("shape").toString();
        boolean[][] map=new boolean[size][size];
        for (int y = 0; y < size; y++) {
            JSONArray row=(JSONArray) jsonObject.get(Integer.toString(y));
            for (int x = 0; x < size; x++) {
                map[x][y]= Integer.parseInt(row.get(x).toString()) == 1;
            }
        }

        setVisible(false);
        GameFrame gf=new GameFrame(this, size, gridtype, map);
    }

    private Object readJson(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    private void back(){
        menu_container.remove(new_game_panel);
        errors.removeAll();
        menu_container.remove(errors);
        menu_container.add(button_panel);
        repaint();
        revalidate();
    }

    private void ok(int size, String gridtype){
        setVisible(false);
        GameFrame gf=new GameFrame(this, size, gridtype, null);
    }
}
