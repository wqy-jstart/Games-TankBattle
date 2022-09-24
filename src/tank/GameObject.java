package tank;

import java.awt.*;

public abstract class GameObject {
    //图片
     public Image img;
    //坐标
     public int x;
     public int y;
     //速度
    public int speed;
    //界面
    public GamePanel gamePanel;

    /**
     * 游戏的父类构造方法
     * @param img
     * @param x
     * @param y
     * @param gamePanel
     */
    public GameObject(Image img, int x, int y, GamePanel gamePanel) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }
    public GameObject(){}

    /**
     * 继承绘制元素自己的方法
     * @param g 画笔
     */
    public abstract void painSelf(Graphics g);

    /**
     * 获取当前游戏元素的矩形，是为碰撞检测而写(位置)
     * @return 返回游戏的矩形
     */
    public abstract Rectangle getRec();

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
