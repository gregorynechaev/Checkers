import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class Checkers extends JPanel {

    public static void main(String[] args) {
        JFrame window = new JFrame("Checkers");
        Checkers cont = new Checkers();
        window.setContentPane(cont);
        window.pack();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((size.width - window.getWidth()) / 2, (size.height - window.getHeight()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    public static JButton play;
    public static JButton finish;
    public static JLabel label;


    public Checkers() {
        setLayout(null);
        setPreferredSize(new Dimension(700, 500));
        setBackground(new Color(222, 184, 135));


        Board board = new Board();
        add(board);
        add(play);
        add(finish);
        add(label);
        board.setBounds(40, 40, 328, 328);
        play.setBounds(420, 120, 240, 60);
        finish.setBounds(420, 240, 240, 60);
        label.setBounds(50, 404, 600, 40);

    }









}
