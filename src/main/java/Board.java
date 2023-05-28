import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel implements ActionListener, MouseListener {
    CheckersInfo board;
    boolean gameOn;
    int current;

    Move[] available;
    int selectRow, selectColumn;


    Board() {
        addMouseListener(this);
        setBackground(Color.BLACK);
        Checkers.play = new JButton("Start a game");
        Checkers.finish = new JButton("Finish a game");
        Checkers.label = new JLabel("WELCOME!", JLabel.CENTER);
        Checkers.label.setForeground(new Color(47, 79, 79));
        board = new CheckersInfo();

    }

    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == Checkers.play)
            Checkers.label.setText("The game was started");
        else if (src == Checkers.finish)
            Checkers.label.setText("Your game was finished");
    }

    void start() {
        if (gameOn) {
            Checkers.label.setText("Firstly, finish your game!");
            return;
        }
        selectRow = -1;
        board.ready();
        current = CheckersInfo.LIGHT;
        Checkers.label.setText("White - It's your turn!");
        gameOn = true;
        Checkers.play.setEnabled(false);
        Checkers.finish.setEnabled(true);

        repaint();

    }

    void finish() {
        if (current == CheckersInfo.LIGHT) Checkers.label.setText("Game over. RED wins");
        else Checkers.label.setText("Game over. WHITE wins");
    }


    void cellClick(int row, int column) {
        for (int i = 0; i < available.length; i++) {
            if (available[i].fromRow == row && available[i].fromColumn == column) {
                selectColumn = column;
                selectRow = row;
                if (current == CheckersInfo.LIGHT) Checkers.label.setText("WHITE, it's your turn");
                else Checkers.label.setText("RED, it's your turn");
                repaint();
                return;
            }
        }

        for (int i = 0; i < available.length; i++)
            if (available[i].fromRow == selectRow && available[i].fromColumn == selectColumn
                    && available[i].toRow == row && available[i].toColumn == column) {
                doMakeMove(available[i]);
                return;
            }


    }

    void doMakeMove(Move move) {
        board.makeMove(move);


        if (current == CheckersInfo.LIGHT) {
            current = CheckersInfo.DARK;
            available = board.getAvailableMoves(current);
            if (available == null) Checkers.label.setText("WHITE wins!");
            else Checkers.label.setText("RED, it's your turn!");
        } else {
            current = CheckersInfo.LIGHT;
            available = board.getAvailableMoves(current);
            if (available == null) Checkers.label.setText("RED wins!");
            else Checkers.label.setText("WHITE, it's your turn!");
        }

        selectRow = -1;


        if (available != null) {
            boolean sameStart = true;
            for (int i = 1; i < available.length; i++)
                if (available[i].fromRow != available[0].fromRow ||
                        available[i].fromColumn != available[0].fromColumn) {
                    sameStart = false;
                    break;
                }
            if (sameStart) {
                selectRow = available[0].fromRow;
                selectColumn = available[0].fromColumn;
            }
        }
        repaint();
    }


    public void mousePressed(MouseEvent evt) {
        if (gameOn == false)
            Checkers.label.setText("Press \"Start a game\"");
        else {
            int column = (evt.getX() - 4) / 40;
            int row = (evt.getY() - 4) / 40;
            if (column >= 0 && column < 8 && row >= 0 && row < 16)
                cellClick(row, column);
        }
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }


    public void paintComponent(Graphics g) {


        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (row % 2 == column % 2)
                    g.setColor(new Color(210, 180, 140));
                else
                    g.setColor(new Color(139, 69, 19));
                g.fillRect(4 + column * 40, 4 + row * 40, 40, 40);

                switch (board.pieceAt(row, column)) {
                    case CheckersInfo.LIGHT:
                        g.setColor(Color.WHITE);
                        g.fillOval(8 + column * 40, 8 + row * 40, 30, 30);
                        break;
                    case CheckersInfo.DARK:
                        g.setColor(new Color(128, 0, 0));
                        g.fillOval(8 + column * 40, 8 + row * 40, 30, 30);
                        break;
                    case CheckersInfo.LIGHT_QUEEN:
                        g.setColor(Color.WHITE);
                        g.fillOval(8 + column * 40, 8 + row * 40, 30, 30);
                        g.setColor(Color.WHITE);
                        g.drawString("K", 14 + column * 40, 32 + row * 40);
                        break;
                    case CheckersInfo.DARK_QUEEN:
                        g.setColor(Color.BLACK);
                        g.fillOval(8 + column * 40, 8 + row * 40, 30, 30);
                        g.setColor(Color.WHITE);
                        g.drawString("K", 14 + column * 40, 32 + row * 40);
                        break;
                }
            }
        }

        if (gameOn) {
            g.setColor(Color.cyan);
            for (int i = 0; i < available.length; i++) {
                g.drawRect(4 + available[i].fromColumn * 40, 4 + available[i].fromRow * 40, 38, 38);
                g.drawRect(6 + available[i].fromColumn * 40, 6 + available[i].fromRow * 40, 34, 34);
            }
        }


        if (selectRow >= 0) {
            g.setColor(Color.white);
            g.drawRect(4 + selectColumn*40, 4 + selectRow*40, 38, 38);
            g.drawRect(6 + selectColumn*40, 6 + selectRow*40, 34, 34);
            g.setColor(Color.green);
            for (int i = 0; i < available.length; i++) {
                if (available[i].fromColumn == selectColumn && available[i].fromRow == selectRow) {
                    g.drawRect(4 + available[i].toColumn*40, 4 + available[i].toRow*40, 38, 38);
                    g.drawRect(6 + available[i].toColumn*40, 6 + available[i].toRow*40, 34, 34);
                }
            }
        }


        g.setColor(Color.black);
        g.drawRect(0, 0,

                getSize().width - 1,

                getSize().height - 1);
        g.drawRect(1, 1,

                getSize().width - 3,

                getSize().height - 3);


    }

}