import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {

    private final GameLogic myLogic;
    boolean activeKeyBoard = false;

    public KeyBoard(GameLogic myLogic) {
        this.myLogic = myLogic;
        myLogic.setMyKeyBoard(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (activeKeyBoard) {

            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP) {
                myLogic.rotate();
            }

            if (keyCode == KeyEvent.VK_DOWN) {
                myLogic.down();
            }

            if (keyCode == KeyEvent.VK_LEFT) {
                myLogic.left();
            }

            if (keyCode == KeyEvent.VK_RIGHT) {
                myLogic.right();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
