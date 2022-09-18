package tank;

import java.awt.*;
import java.util.ArrayList;

//玩家坦克
public class Tank extends GameObject {
    private String upImage; //向上移动时的图片
    private String downImage;//向下移动时的图片
    private String rightImage;//向右移动时的图片
    private String leftImage;//向左移动时的图片
    //尺寸
    public int width = 40;
    public int height = 50;
    //速度
    int speed = 8;
    //方向，枚举
     Direction direction = Direction.UP;

     //攻击冷却状态
    private boolean attackDown = true;
    //攻击冷却时间间隔
    private int attackDownTime = 1000;

    //坦克坐标，方向，图片，方向，面板
    public Tank(String img, int x, int y, String upImage, String downImage, String leftImage, String rightImage, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
        this.upImage = upImage;
        this.downImage = downImage;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
    }

    /**
     * 坦克向左移动
     */
    public void leftward(){
        direction = Direction.LEFT;
        setImg(leftImage);
        if ( ! hitWall(getX()-getSpeed(),getY()) && ! moveToBorder(getX()-getSpeed(),getY())){
            this.setX(getX()-getSpeed());
        }
    }

    /**
     * 坦克向上移动
     */
    public void upward(){
        direction = Direction.UP;
        setImg(upImage);
        if ( ! hitWall(getX(),getY()-getSpeed()) && ! moveToBorder(getX(),getY()-getSpeed())){
            this.setY(getY()-getSpeed());
        }
    }

    /**
     * 坦克向右移动
     */
    public void rightward(){
        direction = Direction.RIGHT;
        setImg(rightImage);
        if ( ! hitWall(getX()+getSpeed(),getY()) && ! moveToBorder(getX()+getSpeed(),getY())){
            this.setX(getX()+getSpeed());
        }
    }

    /**
     * 坦克向下移动
     */
    public void downward(){
        direction = Direction.DOWN;
        setImg(downImage);
        if ( ! hitWall(getX(),getY()+getSpeed()) && ! moveToBorder(getX(),getY()+getSpeed())){
            this.setY(getY()+getSpeed());
        }
    }

    //实现坦克攻击(发射子弹的方法)
    public void attack(){
        if (attackDown){
            Point p = this.getHeadPoint();//接收返回的坦克头部坐标
            //用坦克头部坐标来初始化子弹
            Bullet bullet = new Bullet("images/bullet/bulletGreen.gif",p.x,p.y,this.getGamePanel(),this.direction);
            this.getGamePanel().bullets.add(bullet);//调用父类的gamePanel属性间接访问GamePanel类来添加子弹至集合中
            //启动线程
            new AttackCD().start();
        }
    }
    //当坦克移动时返回头部坐标,引入Point坐标类来接收返回的坐标
    public Point getHeadPoint(){
        switch (direction){
            case LEFT:
                return new Point(getX(),getY()+height/2);
            case RIGHT:
                return new Point(getX()+width,getY()+height/2);
            case UP:
                return new Point(getX()+width/2,getY());
            case DOWN:
                return new Point(getX()+width/2,getY()+height);
            default:
                return null;
        }
    }
    //围墙碰撞检测
    public boolean hitWall(int x,int y){//传入坦克下一步坐标
        //添加围墙列表
        ArrayList<Wall> walls = this.getGamePanel().wallsList;
        Rectangle nextRec = new Rectangle(x,y,width,height);
        for (Wall wall : walls){
            if (nextRec.intersects(wall.getRec())){
                //如果发生碰撞,返回true
                return true;
            }
        }
        return false;
    }
    //添加线程
    class AttackCD extends Thread{
        @Override
        public void run() {
            //将攻击设为冷却状态
            attackDown = false;
            //线程休眠1秒
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //解除冷却
            attackDown = true;
            this.interrupt();//中断线程阻塞
        }
    }

    //判断坦克是否出界
    public boolean moveToBorder(int x,int y){
        if (x<0){//左侧
            return true;
        }else if ( x+ this.width > this.getGamePanel().getWidth()){//右侧
            return true;
        }else if (y<0){//上边缘
            return true;
        }else if (y + this.height  >this.getGamePanel().getHeight()){//下边缘
            return true;
        }else {
            return false;
        }
    }



    /**
     * 重写超类的方法
     *
     * @param g 画笔
     */
    public void painSelf(Graphics g) {
        g.drawImage(getImg(),getX(),getY(),null);
    }

    public Rectangle getRec() {
        return new Rectangle(getX(), getY(), width, height);
    }


}
