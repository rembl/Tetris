import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

    GameLogic testLogic;
    KeyBoard testKeyBoard;
    Interface testInterface;
    private final int gridWidth = 14;

    public Test() {
        testLogic = new GameLogic();
        testKeyBoard = new KeyBoard(testLogic);
        testInterface = new Interface(testLogic, testKeyBoard);
        testLogic.setMyInterface(testInterface);
    }


    @org.junit.jupiter.api.Test
    void testInit() {
        testLogic.setBackground();
        boolean clear = true;
        for (int x = 1; x < gridWidth - 1; x++) {
            int gridHeight = 24;
            for (int y = 0; y < gridHeight - 1; y++) {
                if (testLogic.board[x][y] != Color.BLACK) {
                    clear = false;
                    break;
                }
            }
        }
        assertTrue(clear);
    }

    @org.junit.jupiter.api.Test
    void testDelete() {
        testLogic.setBackground();
        for (int x = 1; x < 6; x++) testLogic.board[x][21] = Color.ORANGE;
        for (int x = 1; x < gridWidth - 1; x++) testLogic.board[x][22] = Color.BLUE;
        testLogic.deleteRow();

        boolean clear = true;
        for (int x = 1; x < 6; x++)
            if (testLogic.board[x][22] != Color.ORANGE) {
                clear = false;
                break;
            }
        for (int x = 6; x < 13; x++)
            if (testLogic.board[x][22] != Color.BLACK) {
                clear = false;
                break;
            }
        assertTrue(clear);
    }

    @org.junit.jupiter.api.Test
    void testShadow() {
        GameLogic testLogic = new GameLogic();
        KeyBoard testKeyBoard = new KeyBoard(testLogic);
        Interface testInterface = new Interface(testLogic, testKeyBoard);
        testLogic.setMyInterface(testInterface);
        testLogic.setBackground();

        //квадрат на высоте 15
        testLogic.currentCoordinates = new Point(6, 6);
        testLogic.currentShape = 0;
        testLogic.rotation = 0;
        assertEquals(15, testLogic.setShadow());

        // вертикальная полоса на высоте 2
        testLogic.currentCoordinates = new Point(6, 17);
        testLogic.currentShape = 1;
        testLogic.rotation = 1;
        assertEquals(2, testLogic.setShadow());

        //фигура Т на высоте 10
        testLogic.currentCoordinates = new Point(6, 10);
        testLogic.currentShape = 2;
        testLogic.rotation = 2;
        assertEquals(10, testLogic.setShadow());
    }

    @org.junit.jupiter.api.Test
    void testRotate() {
        testLogic.rotation = 2;
        testLogic.rotate();
        assertEquals(3, testLogic.rotation);

        testLogic.rotation = 3;
        testLogic.rotate();
        assertEquals(0, testLogic.rotation);
    }

    @org.junit.jupiter.api.Test
    void testLeft() {
        testLogic.currentCoordinates = new Point(6, 1);
        testLogic.currentShape = 1;
        testLogic.rotation = 1;
        testLogic.left();
        assertEquals(5, testLogic.currentCoordinates.x);
        assertEquals(1, testLogic.currentCoordinates.y);
    }

    @org.junit.jupiter.api.Test
    void testRight() {
        testLogic.currentCoordinates = new Point(6, 1);
        testLogic.currentShape = 1;
        testLogic.rotation = 1;
        testLogic.right();
        assertEquals(7, testLogic.currentCoordinates.x);
        assertEquals(1, testLogic.currentCoordinates.y);
    }

    @org.junit.jupiter.api.Test
    void testDown() {
        testLogic.currentCoordinates = new Point(6, 1);
        testLogic.currentShape = 1;
        testLogic.rotation = 1;
        testLogic.down();
        assertEquals(2, testLogic.currentCoordinates.y);
        assertEquals(6, testLogic.currentCoordinates.x);
    }
}
