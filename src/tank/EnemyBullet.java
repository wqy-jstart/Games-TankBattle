package tank;

import java.awt.*;
import java.util.List;

//敌方子弹
public class EnemyBullet extends Bullet {


    /**
     * 子弹的构造方法
     *
     * @param img
     * @param x
     * @param y
     * @param gamePanel
     * @param direction
     */
    public EnemyBullet(Image img, int x, int y, GamePanel gamePanel, Direction direction) {
        super(img, x, y, gamePanel, direction);
    }
    /*子弹与玩家1碰撞检测*/
    public void hitTank(){
        List<Tank> tanks = this.gamePanel.tankList;

        //子弹和bot
        for(Tank tank : tanks){
            //敌方子弹与我方坦克碰撞检测
            if (this.getRec().intersects(tank.getRec())){
                this.gamePanel.blastList.add(new BlastObj(tank.x-34, tank.y-14));
                this.gamePanel.tankList.remove(tank);
                this.gamePanel.removeList.add(this);
                tank.alive = false;
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
        g.drawImage(img,x,y,null);
        go();
        hitTank();
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
