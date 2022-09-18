package tank;

import java.awt.*;

public abstract class GameObject {
    //图片
     private Image img;
    //坐标
     private int x;
     private int y;
     //速度
    public int speed=10;
    //界面
    private GamePanel gamePanel;

    /**
     * 游戏的父类构造方法
     * @param img
     * @param x
     * @param y
     * @param gamePanel
     */
    //这里图片要用String类型,坦克类在初始化子弹时传入img默认为为String类型
    public GameObject(String img, int x, int y, GamePanel gamePanel) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);//利用此API来将String统一转化为Image
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

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

    public void setImg(String img) {
        this.img = Toolkit.getDefaultToolkit().getImage(img);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
