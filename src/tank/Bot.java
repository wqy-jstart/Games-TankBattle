package tank;

import java.awt.*;
import java.util.Random;

//随机出现敌方坦克
public class Bot extends Tank {
    //移动的时间
    private int moveTime = 0;
    /**
     * 敌方坦克的构造方法
     * @param img
     * @param x
     * @param y
     * @param upImage
     * @param downImage
     * @param leftImage
     * @param rightImage
     * @param gamePanel
     */
    public Bot(Image img, int x, int y, Image upImage, Image downImage, Image leftImage, Image rightImage, GamePanel gamePanel) {
        super(img, x, y, upImage, downImage, leftImage, rightImage, gamePanel);
    }
    //敌方坦克移动
    public void go(){
        attack();
        if(moveTime>=20) {
            //当大于等于20时坦克按照随机数random移动
            direction = getRandomDirection();
            moveTime=0;
        }else {
            moveTime+=1;
        }
        switch (direction) {
            case UP :
                upward();
                break;
            case DOWN :
                downward();
                break;
            case RIGHT :
                rightward();
                break;
            case LEFT :
                leftward();
                break;
        }
    }

    /**
     * 随机生成敌方坦克移动的方向
     * @return 获取敌方坦克的方向
     */
    public Direction getRandomDirection(){
        Random random = new Random();
        int rnum = random.nextInt(4);
        switch (rnum){
            case 0:
                return Direction.LEFT;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.DOWN;
            default:
                return null;
        }
    }

    /**
     * 敌方坦克发射子弹
     */
    public void attack(){
        Point p = getHeadPoint();
        Random random = new Random();
        int rnum = random.nextInt(200);
        if (rnum<4){
            //获取默认的敌方子弹图片路径
            EnemyBullet enemyBullet = new EnemyBullet(Toolkit.getDefaultToolkit().getImage("images/bullet/bulletYellow.gif"),p.x,p.y, gamePanel, direction);
            this.gamePanel.bulletList.add(enemyBullet);
        }
    }



    /**
     * 重写超类的方法
     *
     * @param g 画笔
     */
    public void painSelf(Graphics g) {
        g.drawImage(img,x,y,null);
        this.go();
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, width, height);
    }
}
