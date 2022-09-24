package tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URLDecoder;

//玩家一
public class PlayerOne extends Tank {
    private boolean up = false;
    private boolean left = false;
    private boolean right = false;
    private boolean down = false;


    /**
     * 玩家一的构造方法
     *
     * @param img
     * @param x
     * @param y
     * @param upImage
     * @param downImage
     * @param leftImage
     * @param rightImage
     * @param gamePanel
     */
    public PlayerOne(Image img, int x, int y, Image upImage, Image downImage, Image leftImage, Image rightImage, GamePanel gamePanel) {
        super(img, x, y, upImage, downImage, leftImage, rightImage, gamePanel);
    }

    /**
     * 键盘不按键移动
     * @param e
     */
    public void KeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            default:
                break;


        }
    }

    /**
     * 键盘按下移动
     * @param e
     */
    public void KeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_SPACE:
                attack();
            default:
                break;


        }
    }
    public void move(){ // 根据状态前进/停止
        if(left){
            leftward();
        }
        else if(right){
            rightward();
        }
        else if(up){
            upward();
        }else if(down){
            downward();
        }
    }


    /**
     * 重写超类的方法
     *
     * @param g 画笔
     */
    public void painSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        move();
    }

    /**
     * 获取当前游戏元素的矩形，是为碰撞检测而写(位置)
     *
     * @return 返回游戏的矩形
     */
    public Rectangle getRec() {
        return new Rectangle(x, y, getWidth(), getWidth());
    }
}
