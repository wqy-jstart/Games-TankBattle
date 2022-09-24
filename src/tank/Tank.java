package tank;

import java.awt.*;
import java.util.List;

//玩家坦克
public class Tank extends GameObject {
    private boolean attackCoolDown = true;//攻击冷却时间
    private int attackCoolDownTime = 100;//攻击冷却时间为1000ms发射
    private Image upImage; //向上移动时的图片
    private Image downImage;//向下移动时的图片
    private Image rightImage;//向右移动时的图片
    private Image leftImage;//向左移动时的图片
    //生命
    public boolean alive = true;
    //尺寸
    protected int width = 60;
    protected int height = 60;
    //速度
    private int speed = 3;
    //方向，枚举
    protected Direction direction = Direction.UP;

    //坦克坐标，方向，图片，方向，面板
    public Tank(Image img, int x, int y, Image upImage, Image downImage, Image leftImage, Image rightImage, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
        this.upImage = upImage;
        this.downImage = downImage;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
    }

    /**
     * 坦克向左移动
     */
    public void leftward() {
        direction = Direction.LEFT;
        setImg(leftImage);
        //坦克下一步的坐标
        if (!hitWall(x - speed, y) && !moveToBorder(x - speed, y) && alive) {
            this.x -= speed;
        }
    }

    /**
     * 坦克向上移动
     */
    public void upward() {
        direction = Direction.UP;
        setImg(upImage);
        if (!hitWall(x, y - speed) && !moveToBorder(x, y - speed) && alive) {
            this.y -= speed;
        }
    }

    /**
     * 坦克向右移动
     */
    public void rightward() {
        direction = Direction.RIGHT;
        setImg(rightImage);
        if (!hitWall(x + speed, y) && !moveToBorder(x + speed, y) && alive) {
            this.x += speed;
        }
    }

    /**
     * 坦克向下移动
     */
    public void downward() {
        direction = Direction.DOWN;
        setImg(downImage);
        if (!hitWall(x, y + speed) && !moveToBorder(x, y + speed) && alive) {
            this.y += speed;
        }
    }

    /**
     * 坦克发射子弹
     */
    public void attack() {
        Point p = this.getHeadPoint();
        if (attackCoolDown && alive) {
            Bullet bullet = new Bullet(Toolkit.getDefaultToolkit().getImage("images/bullet/bulletGreen.gif"), p.x, p.y, this.gamePanel, direction);
            this.gamePanel.bulletList.add(bullet);
            attackCoolDown = false;
            new AttackCD().start();
        }
    }

    /**
     * 坦克发射子弹的时间
     */
    public class AttackCD extends Thread {
        public void run() {
            attackCoolDown = false;//将攻击功能设置为冷却状态
            try {
                Thread.sleep(attackCoolDownTime);//休眠1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attackCoolDown = true;//将攻击功能解除冷却状态
            this.interrupt();
        }
    }

    /**
     * 获取坦克头部的坐标
     *
     * @return 获取头部坐标
     */
    public Point getHeadPoint() {
        switch (direction) {
            case LEFT:
                return new Point(x, y + height / 2);
            case RIGHT:
                return new Point(x + width, y + height / 2);
            case UP:
                return new Point(x + width / 2, y);
            case DOWN:
                return new Point(x + width / 2, y + height);
            default:
                return null;
        }
    }

    //与围墙碰撞检测
    public boolean hitWall(int x, int y) {
        //假设玩家坦克前进，下一个位置形成的矩形
        Rectangle next = new Rectangle(x, y, width, height);
        //地图里所有的墙体
        List<Wall> walls = this.gamePanel.wallList;
        //判断两个矩形是否相交（即是否撞墙）
        for (Wall w : walls) {
            if (w.getRec().intersects(next)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 坦克坐标边界
     *
     * @param x
     * @param y
     * @return
     */
    public boolean moveToBorder(int x, int y) {
        if (x < 0) {
            return true;
        } else if (x > this.gamePanel.getWidth() - width) {
            return true;
        }
        if (y < 0) {
            return true;
        } else if (y > this.gamePanel.getHeight() - height) {
            return true;
        }
        return false;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
