package tank;

import java.awt.*;
import java.util.ArrayList;

//子弹------实现我方子弹射击功能
public class Bullet extends GameObject {
    //大小
    int width = 10;
    int height = 10;
    //方向
    Direction direction;
    //速度
    int speed = 15;
    //构造器
    public Bullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel);
        this.direction = direction;
    }

    //四个方向移动无返回值
    public void leftMove(){
        setX(getX()-this.speed);
    }
    public void rightMove(){
        setX(getX()+this.speed);
    }
    public void upMove(){
        setY(getY()-this.speed);
    }
    public void downMove(){
        setY(getY()+this.speed);
    }

    /**
     * 创建go方法使子弹移动
     * 该方法利用枚举类常用的switch分支方式,来根据坦克所对的方向来决定子弹移动的方向
     */
    public void go(){
        switch (direction){
            case LEFT:
                leftMove();
                break;
            case RIGHT:
                rightMove();
                break;
            case UP:
                upMove();
                break;
            case DOWN:
                downMove();
                break;
        }
        this.hitWall();
        this.MoveBorder();
        this.hitBase();
    }
    //子弹与敌方坦克检测碰撞碰撞
    public void hitBot(){
        ArrayList<Bot> bot = this.getGamePanel().bots;
        for(Bot b : bot){
            if (this.getRec().intersects(b.getRec())){
                this.getGamePanel().bots.remove(b);
                this.getGamePanel().removeList.add(this);
                break;
            }
        }
    }

    //子弹与围墙碰撞检测
    public void hitWall(){
        ArrayList<Wall> walls = this.getGamePanel().wallsList;
        for (Wall wall : walls){
            if (this.getRec().intersects(wall.getRec())){
                this.getGamePanel().wallsList.remove(wall);
                this.getGamePanel().removeList.add(this);
                break;
            }
        }
    }

    //子弹与基地碰撞检测
    public void hitBase(){
        ArrayList<Base> bases = this.getGamePanel().baseList;
        for(Base base : bases){
            if (this.getRec().intersects(base.getRec())){
                this.getGamePanel().baseList.remove(base);
                this.getGamePanel().removeList.add(this);
                break;
            }
        }
    }


    public void MoveBorder(){
        if (this.getX()<0 || this.getX()+this.width > this.getGamePanel().getWidth()){
            this.getGamePanel().removeList.add(this);
        }
        if (this.getY()<0 || this.getY()+this.height > this.getGamePanel().getHeight()){
            this.getGamePanel().removeList.add(this);
        }
    }

    //重写超类两个抽象方法
    //画笔方法,传入坐标和图片
    @Override
    public void painSelf(Graphics g) {
        g.drawImage(getImg(),getX(),getY(),null);
        this.go();//调用go方法来移动子弹
        this.hitBot();
    }
    //返回矩形方法,传入坐标和大小
    public Rectangle getRec(){
        return new Rectangle(getX(),getY(),width,height);
    }
}
