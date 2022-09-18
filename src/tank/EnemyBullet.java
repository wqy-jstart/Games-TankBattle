package tank;

import java.awt.*;
import java.util.ArrayList;

//敌方子弹-----实现敌方坦克射击功能
public class EnemyBullet extends Bullet {
    //添加构造器
    public EnemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }
    //检测敌方子弹与我方坦克碰撞
    public void hitPlayer(){
        ArrayList<Tank> players = this.getGamePanel().playerList;
        for(Tank player : players ){
            if (this.getRec().intersects(player.getRec())){
                this.getGamePanel().playerList.remove(player);
                this.getGamePanel().removeList.add(this);
                break;
            }
        }
    }

    //重写超类两个抽象方法
    //画笔方法,传入坐标和图片
    @Override
    public void painSelf(Graphics g) {
        g.drawImage(getImg(),getX(),getY(),null);
        this.go();//调用go方法来移动子弹
        this.hitPlayer();
    }
    //返回矩形方法,传入坐标和大小
    public Rectangle getRec(){
        return new Rectangle(getX(),getY(),width,height);
    }
}
