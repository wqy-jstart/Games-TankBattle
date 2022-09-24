package tank;

import java.awt.*;

//墙
public class Wall extends GameObject {
    //尺寸
    public int width = 60;
    public int height = 60;

    /**
     * 游戏墙的构造方法
     *
     * @param img
     * @param x
     * @param y
     * @param gamePanel
     */
    public Wall(Image img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    /**
     * 重写超类的方法
     *
     * @param g 画笔
     */
    public void painSelf(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    /**
     * 获取当前游戏元素的矩形,是为碰撞检测而写（位置）
     * @return
     */
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
