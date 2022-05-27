import java.awt.*;

public class Solver {

    private final GameLogic myLogic;
    private final int gridWidth = 14;
    private final int gridHeight = 24;
    private Color[][] helpBoard = new Color[gridWidth][gridHeight];

    public Solver(GameLogic myLogic) {
        this.myLogic = myLogic;
        myLogic.setMySolver(this);
    }

    //присваивает нужные значения ротации и x, вызывая bestPosition()
    public void solve(Color[][] board) throws InterruptedException {
        for (int i = 0; i < gridWidth; i++) {
            System.arraycopy(board[i], 0, helpBoard[i], 0, gridHeight);
        }
        int[] best = bestPosition();
        myLogic.setBestX(best[0]);
        myLogic.setBestRotation(best[1]);
    }

    //для данной фигуры проходит по каждой ротации и каждой x, высчитывает для каждого положения setScore()
    public int[] bestPosition() {
        int[] result = new int[2];
        double currScore;
        double bestScore = -1000;
        int bestRotation = 0;
        int bestX = 0;
        myLogic.rotation = 0;
        for (int rot = 0; rot < 4; rot++) {
            for (int x = 1; x < gridWidth - 1; x++) {
                if (myLogic.collides(x, myLogic.currentCoordinates.y)) continue;
                myLogic.currentCoordinates.x = x;
                int additionalY = myLogic.setShadow();
                for (Point point : Tetromino.Shapes[myLogic.currentShape][myLogic.rotation])
                    helpBoard[myLogic.currentCoordinates.x + point.x][myLogic.currentCoordinates.y + additionalY + point.y] = Color.WHITE;
                currScore = setScore();
                if (currScore > bestScore) {
                    bestScore = currScore;
                    bestRotation = rot;
                    bestX = x;
                }
                for (int i = 0; i < gridWidth; i++)
                    for (int j = 0; j < gridHeight; j++)
                        if (helpBoard[i][j] == Color.WHITE) helpBoard[i][j] = Color.BLACK;
            }
            if (myLogic.rotation < 3) myLogic.rotation++;
        }
        result[0] = bestX;
        result[1] = bestRotation;
        return result;
    }

    //функция для определения "хорошести" положения фигуры
    public double setScore() {
        return -0.510066 * aggregateHeight() + 0.760666 * completeLines() + -0.35663 * holes() + -0.184483 * bumpiness();
    }

    //суммарная высота фигур
    public int aggregateHeight(){
        int result = 0;
        for (int x = 1; x < gridWidth - 1; x++)
            for (int y = 0; y < gridHeight - 1; y++)
                if (helpBoard[x][y] != Color.BLACK) {
                    result += 23 - y;
                    break;
                }
        return result;
    }

    //количество удаленный линий
    public int completeLines() {
        int result = 0;
        for (int y = 0; y < gridHeight - 1; y++){
            int row = 0;
            for (int x = 1; x < gridWidth - 1; x++) if (helpBoard[x][y] != Color.BLACK) row++;
            if (row == 12) result++;
        }
        return result;
    }

    //количество дырок - пустые квадраты, у которых сверху есть фигура
    public int holes() {
        int result = 0;
        for (int x = 1; x < gridWidth - 1; x++)
            for (int y = 1; y < gridHeight - 1; y++)
                if (helpBoard[x][y] == Color.BLACK && helpBoard[x][y - 1] != Color.BLACK) result++;
        return result;
    }

    //суммарная разница между высотой столбцов
    public int bumpiness() {
        int result = 0;
        int column1 = 0;
        for (int y = 0; y < gridHeight - 1; y++)
            if (helpBoard[1][y] != Color.BLACK) {
            column1 = y;
            break;
        } else column1 = gridHeight - 1;

        for (int x = 2; x < gridWidth - 1; x++) {
            int column2 = 0;
            for (int y = 0; y < gridHeight - 1; y++)
                if (helpBoard[x][y] != Color.BLACK) {
                column2 = y;
                break;
                } else column2 = gridHeight - 1;
            result += Math.abs(column1 - column2);
            column1 = column2;
        }
        return result;
    }
}
