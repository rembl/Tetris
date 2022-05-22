public class Tetris {

    private final Interface myInterface;
    private final GameLogic myLogic;
    private final KeyBoard myKeyBoard;
    private final Solver mySolver;

    public Tetris() {
        myLogic = new GameLogic();
        myKeyBoard = new KeyBoard(myLogic);
        myInterface = new Interface(myLogic, myKeyBoard);
        mySolver = new Solver(myLogic);
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
    }
}
