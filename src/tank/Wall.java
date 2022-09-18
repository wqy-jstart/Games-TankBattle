package tank;

import java.awt.*;

//围墙类
public class Wall extends GameObject {
    //定义大小
    int length = 50;
    int height = 50;
    //添加构造器
    public Wall(String img, int x, int y, GamePanel gamePanel) {
        super(img, x, y, gamePanel);
    }

    @Override
    public void painSelf(Graphics g) {
        g.drawImage(getImg(),getX(),getY(),getGamePanel());
    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(getX(),getY(),length,height);
    }
}
