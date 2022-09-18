package tank;

import java.awt.*;

//添加基地
public class Base extends GameObject {
    //定义基地大小
    int length = 50;
    int height = 50;


    public Base(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void painSelf(Graphics g) {
        g.drawImage(getImg(),getX(),getY(),null);
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(getX(),getY(),length,height);
    }
}
