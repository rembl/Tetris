import java.awt.*;

public class GameLogic implements Runnable {

    Color[][] board;
    private KeyBoard myKeyBoard;
    private Interface myInterface;
    private Solver mySolver;
    boolean gameStart = false;
    boolean solveStart = false;
    boolean gameStop = true;
    boolean solverDown;
    Point currentCoordinates;
    int currentShape;
    Color currentColor;
    int rotation;
    int score = 0;

    public void setMyKeyBoard(KeyBoard myKeyBoard) {
        this.myKeyBoard = myKeyBoard;
    }
    public void setMyInterface(Interface myInterface) {
        this.myInterface = myInterface;
    }
    public void setMySolver(Solver mySolver) {
        this.mySolver = mySolver;
    }


    @Override
    public void run() {
        while(true) {
            if (gameStart) {
                if (!gameStop) {
                    down();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (solveStart) {
                try {
                    mySolver.solve();
                    down();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //запускает thread
    public void start() throws InterruptedException {
        Thread thread = new Thread(this, "thread");
        thread.start();
    }

    //при нажатии на кнопку запускает игру для игрока
    public void doStartButton() {
        score = 0;
        myKeyBoard.activeKeyBoard = true;
        gameStart = true;
        solveStart = false;
        gameStop = false;
        try {
            start();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    //при нажатии на кнопку открывает стартовое меню - можно будет включить либо игру, либо солвер
    public void doRestartButton() {
        myKeyBoard.activeKeyBoard = false;
        gameStart = false;
        solveStart = false;
        gameStop = true;
        setBackground();
        setTetromino();
    }

    //при нажатии на кнопку запускает солвер
    public void doSolveButton() {
        score = 0;
        myKeyBoard.activeKeyBoard = false;
        gameStart = false;
        solveStart = true;
        gameStop = true;
        try {
            start();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    //заполняет массив фона начальными цветами
    public void setBackground() {
        board = new Color[14][24];
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 24; j++) {
                if ( i == 0 || i == 13 || j == 23) board[i][j] = Color.DARK_GRAY;
                else board[i][j] = Color.BLACK;
            }
        }
        myInterface.frame.repaint();
    }

    //создает сверху новую фигуру или заканчивает игру, если это невозможно
    public void setTetromino() {
        solverDown = true;
        currentCoordinates = new Point(6, 1);
        rotation = (int) (Math.random() * 4);
        currentShape = (int) (Math.random() * 7);
        currentColor = Tetromino.Colors[(int) (Math.random() * 8)];
        if (!collides(6, 1)) myInterface.frame.repaint();
        else {
            myInterface.start.setEnabled(true);
            myInterface.solve.setEnabled(true);
            myInterface.restart.setEnabled(false);
            doRestartButton();
        }
    }

    //создает "тень" в игре и используется для проверки лучшего положения в солвере
    public int setShadow() {
        int minShadow = 100;
        int currShadow;
        for (Point point : Tetromino.Shapes[currentShape][rotation]) {
            currShadow = 0;
            for (int i = currentCoordinates.y + point.y + 1; i < 23; i++) {
                if(board[currentCoordinates.x + point.x][i] == Color.BLACK) currShadow++;
                else break;
            }
            if (currShadow < minShadow) minShadow = currShadow;
        }
        return minShadow;
    }

    //поворачивает фигуру
    public void rotate() {
        int prevRotation = rotation;
        if (rotation == 3) rotation = 0;
        else rotation++;
        if (collides(currentCoordinates.x, currentCoordinates.y)) rotation = prevRotation;
        myInterface.frame.repaint();
    }

    //опускает фигуру
    public void down() {
        if (!collides(currentCoordinates.x, currentCoordinates.y + 1)) currentCoordinates.y++;
        else setShape();
        myInterface.frame.repaint();
    }

    //перемещает фигуру влево
    public void left() {
        if (!collides(currentCoordinates.x - 1, currentCoordinates.y)
                && !collides(currentCoordinates.x, currentCoordinates.y + 1)) currentCoordinates.x--;
        myInterface.frame.repaint();
    }

    //перемещает фигуру вправо
    public void right() {
        if (!collides(currentCoordinates.x + 1, currentCoordinates.y)
                && !collides(currentCoordinates.x, currentCoordinates.y + 1)) currentCoordinates.x++;
        myInterface.frame.repaint();
    }

    //устанавливает фигуру на доске, проверяет на удаляемые ряды и вызывает создание новой фигуры
    public void setShape() {
        for (Point point : Tetromino.Shapes[currentShape][rotation])
            board[currentCoordinates.x + point.x][currentCoordinates.y + point.y] = currentColor;
        deleteRow();
        setTetromino();
        myInterface.frame.repaint();
    }

    //проверяет, можно ли подвигать фигуру
    public boolean collides(int x, int y) {
        for (Point point : Tetromino.Shapes[currentShape][rotation])
            if (board[point.x + x][point.y + y] != Color.BLACK) return true;
        return false;
    }

    //удаляет полные ряды
    public void deleteRow() {
        for (int j = 22; j > 1; j--) {
            boolean clear = true;
            for (int i = 1; i < 13; i++)
                if (board[i][j] == Color.BLACK) {
                    clear = false;
                    break;
                }
            if (clear) {
                singleDelete(j);
                score++;
                j = j + 1;
            }
            myInterface.frame.repaint();
        }
    }

    //вспомогательная функция для удаления
    public void singleDelete(int y) {
        for (int j = y; j > 1; j--)
            for (int i = 1; i < 13; i++)
                board[i][j] = board[i][j - 1];
    }
}
