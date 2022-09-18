package tank;

import java.awt.*;
import java.util.Random;

//随机出现
public class Bot extends Tank {
    int moveTime = 0;
    //添加构造器
    public Bot(String img, int x, int y, String upImage, String downImage, String leftImage, String rightImage, GamePanel gamePanel) {
        super(img, x, y, upImage, downImage, leftImage, rightImage, gamePanel);
    }

    public Direction getRandDirection(){
        Random random = new Random();//创建一个随机数来随机决定敌方坦克的方向
        int run = random.nextInt(4);
        switch (run){ //--------利用枚举类型中switch经典调用常量
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

    public void go(){
        attack();
        if (moveTime>20){
            direction = getRandDirection();
            moveTime=0;
        }else {
            moveTime++;
        }
        switch (direction){
            case LEFT:
                leftward();
                break;
            case RIGHT:
               rightward();
                break;
            case UP:
                upward();
                break;
            case DOWN:
                downward();
                break;
        }
    }
    //该方法处理敌方坦克射击功能
    public void attack(){
        Point p = getHeadPoint();
        Random random = new Random();
        int run = random.nextInt(400);//随机执行射击功能
        if (run<10){
            this.getGamePanel().bullets.add(new EnemyBullet("images/bullet/bulletYellow.gif",p.x,p.y,this.getGamePanel(),this.direction));
        }
    }

    @Override
    public void painSelf(Graphics g) {
        g.drawImage(getImg(),getX(),getY(),null);
        go();
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(getX(),getY(),width,height);
    }
}
