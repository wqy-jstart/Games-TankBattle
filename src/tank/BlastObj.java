package tank;

import java.awt.*;

//爆炸效果
public class BlastObj extends GameObject {
    //爆炸图集
    private static Image[] imgs = new Image[8];
    private int explodeCount = 0;//切换图片
    //爆炸图片路径
    static {
        for (int i=0;i<8;i++){
            imgs[i] = Toolkit.getDefaultToolkit().getImage("images/blast/blast" +(i + 1)+".png");
        }
        }
    public BlastObj() {
        super();
    }
    //爆炸的坐标
    public BlastObj(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 游戏的爆炸构造方法
     *  @param x
     * @param y
     */


    /**
     * 重写超类的方法
     *
     * @param g 画笔
     */
    public void painSelf(Graphics g) {
        //绘制点击爆炸效果(连续绘制)
        if (explodeCount < 8 && explodeCount>=0){
            g.drawImage(imgs[explodeCount],x,y,null);
            explodeCount++;
        }
    }

    public Rectangle getRec() {
        return null;
    }
}
