package tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//子弹
public class Bullet extends GameObject {
    //尺寸
    protected int width = 10;
    protected int height = 10;
    //速度
    private int speed = 7;
    //方向
    Direction direction;

    /**
     * 子弹的构造方法
     *
     * @param img
     * @param x
     * @param y
     * @param gamePanel
     * @param direction
     */
    public Bullet(Image img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    /**
     * 判断移动方向
     */
    public void go(){
        /*判断移动方向*/
        switch (direction) {
            case UP :
                upward();
                break;
            case LEFT :
                leftward();
                break;
            case DOWN :
                downward();
                break;
            case RIGHT :
                rightward();
                break;
        }
        this.hitWall();
        this.moveToBorder();
        this.hitBase();
    }

    // 子弹移动+边界判断
    public void leftward() {
        x -= speed;

    }

    public void rightward() {
        x += speed;

    }

    public void upward() {
        y -= speed;

    }

    public void downward() {
        y += speed;

    }

    /*子弹与坦克碰撞检测*/
    public void hitBot(){
        List<Bot> bots = this.gamePanel.botList;

        //子弹和bot
        for(Bot bot: bots){
            //我方子弹与敌方坦克碰撞检测
            if (this.getRec().intersects(bot.getRec())){
                this.gamePanel.blastList.add(new BlastObj(bot.x-34, bot.y-14));
                this.gamePanel.botList.remove(bot);
                this.gamePanel.removeList.add(this);
                GamePanel.enemyCount--;
                break;
            }

        }
    }

    /**
     * 子弹与墙碰撞检测
     */
    public void hitWall(){
        List<Wall> walls = this.gamePanel.wallList;
        for(Wall w: walls) {
            //我方子弹与墙碰撞检测
            if (this.getRec().intersects(w.getRec())) {
                this.gamePanel.wallList.remove(w);
                this.gamePanel.removeList.add(this);
                break;
            }
        }
    }
    // 出界回收
    public void moveToBorder(){
        if (x < 0||x +width> this.gamePanel.getWidth()) {
            this.gamePanel.removeList.add(this);
        }
        if(y < 0||y+height > this.gamePanel.getHeight()) {
            this.gamePanel.removeList.add(this);
        }
    }
    //子弹与基地碰撞
    public void hitBase(){
        List<Base> bases = this.gamePanel.baseList;
        for (Base base : bases){
            if (this.getRec().intersects(base.getRec())){
                this.gamePanel.baseList.remove(base);
                this.gamePanel.removeList.add(this);
                break;

            }
        }
        }


    /**
     * 重写超类的方法
     *
     * @param g 画笔
     */
    public void painSelf(Graphics g) {
        g.drawImage(img, x, y, null);
        go();
        hitBot();
        hitWall();


    }
    /**
     * 获取当前游戏元素的矩形,是为碰撞检测而写（位置）
     * @return
     */
    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
