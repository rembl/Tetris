import java.awt.*;

public class Solver {

    private final GameLogic myLogic;

    public Solver(GameLogic myLogic) {
        this.myLogic = myLogic;
        myLogic.setMySolver(this);
    }

    //присваивает нужные значения ротации и x, вызывая bestPosition()
    public void solve() throws InterruptedException {
        int[] best = bestPosition();
        myLogic.currentCoordinates.x = best[0];
        myLogic.rotation = best[1];
        //myLogic.currentCoordinates.y += myLogic.setShadow();
        myLogic.solverDown = false;
    }

    //для данной фигуры проходит по каждой ротации и каждой x, высчитывает для каждого положения setScore()
    public int[] bestPosition() {
        int[] result = new int[2];
        double currScore;
        double bestScore = -1000;
        int bestRotation = 0;
        int bestX = 0;
        myLogic.rotation = -1;
        for (int rot = 0; rot < 4; rot++) {
            myLogic.rotation++;
            for (int x = 1; x < 13; x++) {
                if (myLogic.collides(x, myLogic.currentCoordinates.y)) continue;
                myLogic.currentCoordinates.x = x;
                int additionalY = myLogic.setShadow();
                for (Point point : Tetromino.Shapes[myLogic.currentShape][myLogic.rotation])
                    myLogic.board[myLogic.currentCoordinates.x + point.x][myLogic.currentCoordinates.y + additionalY + point.y] = Color.WHITE;
                currScore = setScore();
                if (currScore > bestScore) {
                    bestScore = currScore;
                    bestRotation = rot;
                    bestX = x;
                }
                for (int i = 0; i < 14; i++)
                    for (int j = 0; j < 24; j++)
                        if (myLogic.board[i][j] == Color.WHITE) myLogic.board[i][j] = Color.BLACK;
            }
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
        for (int x = 1; x < 13; x++)
            for (int y = 0; y < 23; y++)
                if (myLogic.board[x][y] != Color.BLACK) {
                    result += 23 - y;
                    break;
                }
        return result;
    }

    //количество удаленный линий
    public int completeLines() {
        int result = 0;
        for (int y = 0; y < 23; y++){
            int row = 0;
            for (int x = 1; x < 13; x++) if (myLogic.board[x][y] != Color.BLACK) row++;
            if (row == 12) result++;
        }
        return result;
    }

    //количество дырок - пустые квадраты, у которых сверху есть фигура
    public int holes() {
        int result = 0;
        for (int x = 1; x < 13; x++)
            for (int y = 1; y < 23; y++)
                if (myLogic.board[x][y] == Color.BLACK && myLogic.board[x][y - 1] != Color.BLACK) result++;
        return result;
    }

    //суммарная разница между высотой столбцов
    public int bumpiness() {
        int result = 0;
        int column1 = 0;
        for (int y = 0; y < 23; y++)
            if (myLogic.board[1][y] != Color.BLACK) {
            column1 = y;
            break;
        }

        for (int x = 2; x < 13; x++) {
            int column2 = 0;
            for (int y = 0; y < 23; y++)
                if (myLogic.board[x][y] != Color.BLACK) {
                column2 = y;
                break;
            } else column2 = 23;
            result += Math.abs(column1 - column2);
            column1 = column2;
        }
        return result;
    }
}
