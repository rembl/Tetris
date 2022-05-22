import javax.swing.*;
import java.awt.*;

public class Interface {

    JFrame frame;
    Button start;
    Button solve;
    Button restart;
    private final KeyBoard myKeyBoard;
    private final GameLogic myLogic;
    private final int width = 26*15;
    private final int height = 26*25;

    public Interface(GameLogic myLogic, KeyBoard myKeyBoard) {
        this.myLogic = myLogic;
        this.myKeyBoard = myKeyBoard;
        myLogic.setMyInterface(this);
        init();
    }

    //создание фрейма с его заполнением
    public void init() {
        frame = new JFrame("\uD83D\uDC96 Tetris \uD83D\uDC96");
        frame.setFocusable(true);
        frame.addKeyListener(myKeyBoard);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.toFront();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Component());
        frame.setBounds(10, 10, width + 66, height);

        myLogic.setBackground();
        myLogic.setTetromino();

        JPanel side = new JPanel();
        side.setVisible(true);
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        start = new Button("Start!");
        solve = new Button("Solve!");
        restart = new Button("Restart!");
        start.setFocusable(false);
        solve.setFocusable(false);
        restart.setFocusable(false);
        start.setMaximumSize(new Dimension(60, 210));
        solve.setMaximumSize(new Dimension(60, 210));
        restart.setMaximumSize(new Dimension(60, 210));
        restart.setEnabled(false);
        start.addActionListener(e -> {
            start.setEnabled(false);
            solve.setEnabled(false);
            restart.setEnabled(true);
            myLogic.doStartButton();
        });
        solve.addActionListener(e -> {
            start.setEnabled(false);
            solve.setEnabled(false);
            restart.setEnabled(true);
            myLogic.doSolveButton();
        });
        restart.addActionListener(e -> {
            start.setEnabled(true);
            solve.setEnabled(true);
            restart.setEnabled(false);
            myLogic.doRestartButton();
        });
        side.add(start);
        side.add(solve);
        side.add(restart);

        frame.add(side, BorderLayout.EAST);
    }

    //отрисовка поля
    private class Component extends JComponent {

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 24; j++) {
                    g.setColor(myLogic.board[i][j]);
                    g.fillRect(26*i, 26*j, 25, 25);
                }
            }

            g.setColor(Color.GRAY);
            g.drawString("cleared lines: " + myLogic.score, 26*8, 17);

            if (myLogic.gameStart) {
                g.setColor(Color.GRAY);
                int additionalY = myLogic.setShadow();
                for (Point point : Tetromino.Shapes[myLogic.currentShape][myLogic.rotation])
                    g.fillRect((myLogic.currentCoordinates.x + point.x) * 26,
                            (myLogic.currentCoordinates.y + additionalY + point.y) * 26,
                            25, 25);
            }

            g.setColor(myLogic.currentColor);
            for (Point point : Tetromino.Shapes[myLogic.currentShape][myLogic.rotation])
                g.fillRect((myLogic.currentCoordinates.x + point.x) * 26,
                        (myLogic.currentCoordinates.y + point.y) * 26,
                        25, 25);
        }
    }
}
