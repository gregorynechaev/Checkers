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
        window.setLocation((size.width - window.getWidth())/2, (size.height - window.getHeight())/2 );
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    private JButton play;
    private JButton finish;
    private JLabel label;




    public Checkers() {
        setLayout(null);
        setPreferredSize(new Dimension(700, 420));
        setBackground(new Color(222, 184, 135));


        Board board = new Board();
        add(board);
        add(play);
        add(finish);
        add(label);

        board.setBounds(40,40,328,328);
        play.setBounds(400, 40, 280, 30);
        finish.setBounds(400, 100, 280, 30);
        label.setBounds(400, 200, 280, 200);

    }

    private static class move {
        int fromRow;
        int toRow;
        int fromColumn;
        int toColumn;

        move(int r1, int c1, int r2, int c2) {
            fromRow = r1;
            toRow = r2;
            fromColumn = c1;
            toColumn = c2;
        }
    }

    private class Board extends JPanel implements ActionListener, MouseListener {
        CheckersInfo board;
        boolean gameOn;
        int current;

        move[] available;


         Board() {
             addMouseListener(this);
             setBackground(Color.BLACK);
             play = new JButton("Start a game");
             finish = new JButton("Finish a game");
             label = new JLabel("WELCOME!", JLabel.CENTER);
             label.setForeground(new Color(47, 79, 79));
             board = new CheckersInfo();

         }

        public void actionPerformed(ActionEvent evt) {
            Object src = evt.getSource();
            if (src == play)
                label.setText("The game was started");
            else if (src == finish)
                label.setText("Your game was finished");
        }

        void start() {
             if(gameOn){
                 label.setText("Firstly, finish your game!");
                 return;
             }

             board.ready();
             current = CheckersInfo.LIGHT;
             label.setText("White - It's your turn!");
            gameOn = true;
            play.setEnabled(false);
            finish.setEnabled(true);

        }

        void finish(){
             if(current == CheckersInfo.LIGHT) label.setText("Game over. RED wins");
             else label.setText("Game over. WHITE wins");
        }



        public void mousePressed(MouseEvent evt) {
            if (gameOn == false)
                label.setText("Press \"Start a game\"");
            else {
                int column = (evt.getX() - 4) / 40;
                int row = (evt.getY() - 4) / 40;
                if (column >= 0 && column < 8 && row >= 0 && row < 16)
                    label.setText("aaaaaaa");
            }
        }

        public void mouseReleased(MouseEvent evt) { }
        public void mouseClicked(MouseEvent evt) { }
        public void mouseEntered(MouseEvent evt) { }
        public void mouseExited(MouseEvent evt) { }




        public void paintComponent(Graphics g) {


            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (row % 2 == column % 2)
                        g.setColor(new Color(210, 180, 140));
                    else
                        g.setColor(new Color(139, 69, 19));
                    g.fillRect(4 + column * 40, 4 + row * 40, 40, 40);

                    switch (board.pieceAt(row,column)) {
                        case CheckersInfo.LIGHT:
                            g.setColor(Color.WHITE);
                            g.fillOval(8 + column * 40, 8 + row * 40, 30, 30);
                            break;
                        case CheckersInfo.DARK:
                            g.setColor(new Color(128, 0, 0));
                            g.fillOval(8 + column * 40, 8 + row * 40, 30, 30);
                            break;
                    }
                }
            }

            g.setColor(Color.black);
            g.drawRect(0,0,getSize().width-1,getSize().height-1);
            g.drawRect(1,1,getSize().width-3,getSize().height-3);
        }

    }





    private static class CheckersInfo {
        static final int
                EMPTY = 0,
                LIGHT = 1,
                LIGHT_KING = 2,
                DARK = 3,
                DARK_KING = 4;

        int[][] board;

        CheckersInfo() {
            board = new int[8][8];
            ready();
        }

        void ready() {
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if ( row % 2 == column % 2 ) {
                        if (row < 3)
                            board[row][column] = DARK;
                        else if (row > 4)
                            board[row][column] = LIGHT;
                        else
                            board[row][column] = EMPTY;
                    }
                    else {
                        board[row][column] = EMPTY;
                    }
                }
            }
        }


        int pieceAt(int row, int column) {
            return board[row][column];
        }
    }





}
