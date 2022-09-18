package tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JFrame {
    //定义双缓存图片
    private Image offScreenImage = null;
    //窗口大小
    private int width = 800;
    private int height = 610;
    //指针图片
    private Image select = Toolkit.getDefaultToolkit().getImage("images/selecttank.gif");
    //指针初始坐标
    private int y = 150;
    //临时变量
    private int a = 0;
    //游戏模式 0 游戏未开始 1:单人模式 2:双人模式
    private int state = 0;
    //重绘制次数
    int count = 0;
    //生成的敌人数量
    int enemyCount = 0;
    //添加子弹列表(使用集合更方便增删)
    ArrayList<Bullet> bullets = new ArrayList<>();
    //添加敌方坦克列表
    ArrayList<Bot> bots = new ArrayList<>();
    //添加我方子弹列表
    ArrayList<Bullet> removeList = new ArrayList<>();
    //添加玩家一列表
    ArrayList<Tank> playerList = new ArrayList<>();
    //添加围墙列表
    ArrayList<Wall> wallsList = new ArrayList<>();
    //添加基地列表
    ArrayList<Base> baseList = new ArrayList<>();
    //PlayerOne，玩家1图片
     PlayerOne playerOne = new PlayerOne("images/player1/p1tankU.gif",125, 510,
            "images/player1/p1tankU.gif","images/player1/p1tankD.gif",
            "images/player1/p1tankL.gif","images/player1/p1tankR.gif", this);
     //Base
    Base base = new Base("images/star.gif",375,565,this);
    //窗口启动方法
    public void launch() {
        //标题
        setTitle("坦克大战");
        //窗口初始大小
        setSize(width, height);
        //是屏幕居中
        setLocationRelativeTo(null);
        //添加关闭事件
        setDefaultCloseOperation(3);
        //使用户不能调整大小
        setResizable(false);
        //使窗口可见
        setVisible(true);
        //添加键盘监听器
        this.addKeyListener(new keyMonitor());

        //添加围墙
        this.AddWall();
        baseList.add(base);
        //重绘
        while (true) {
            if ((count % 50) == 1 && enemyCount <10){
                Random random = new Random();
                int run = random.nextInt(800);//随机在横坐标为0~799之间生成
                bots.add(new Bot("images/enemy/enemy1D.gif",run,110,
                        "images/enemy/enemy1U.gif","images/enemy/enemy1D.gif",
                        "images/enemy/enemy1L.gif","images/enemy/enemy1R.gif",this));
                enemyCount ++;//每生成一辆坦克后自增
            }
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //paint()方法
    public void paint(Graphics g) {
        System.out.println(bullets.size());
        //创建和容器一样大小的Image图片
        if (offScreenImage==null){
            offScreenImage = this.createImage(width,height);
        }
        //获得该图片的画笔
        Graphics gImage = offScreenImage.getGraphics();
        //设置画笔颜色
        gImage.setColor(Color.gray);
        //绘制实心矩形
        gImage.fillRect(0, 0, width, height);
        //挂变画笔颜色
        gImage.setColor(Color.blue);
        //改变文字大小
        gImage.setFont(new Font("宋体", Font.BOLD, 50));
        //添加文字,游戏未开始
        if (state == 0) {
            gImage.drawString("选择游戏模式", 220, 100);
            gImage.drawString("选择单人模式", 220, 200);
            gImage.drawString("选择双人模式", 220, 300);
            gImage.drawString("按1，2选择模式，按回车开始游戏",0,400);
            //绘制指针(坦克标志)
            gImage.drawImage(select, 160, y, null);
        } else if (state == 1 || state == 2) {
            g.drawString("游戏开始了", 220, 100);
            if (state == 1) {
                gImage.drawString("选择单人模式", 220, 200);
            } else {
                gImage.drawString("选择双人模式", 220, 300);
            }
            //添加游戏元素
            for (Tank player : playerList){
                player.painSelf(gImage);
            }
            //遍历子弹集合进行绘制
            for (Bullet bullet : bullets){
                bullet.painSelf(gImage);
            }
            bullets.removeAll(removeList);//删除removeList中要删除的子弹
            for (Bot bot : bots){
                bot.painSelf(gImage);
            }
            for (Wall wall : wallsList){
                wall.painSelf(gImage);
            }
            for (Base base : baseList){
                base.painSelf(gImage);
            }
            //重绘
            count++;
        }

        //将缓存区绘制好的图形整个绘制到容器的画布中
        g.drawImage(offScreenImage,0,0,null);

    }

    //添加围墙的方法
    private  void AddWall(){
        for (int i = 0; i < 30; i++) {
            wallsList.add(new Wall("images/walls.gif",10 +i*25,170 , this));
        }
        for (int i = 0; i < 3; i++) {
            wallsList.add(new Wall("images/walls.gif",305,560 , this));
            wallsList.add(new Wall("images/walls.gif",305,500 , this));
            wallsList.add(new Wall("images/walls.gif",365,500 , this));
            wallsList.add(new Wall("images/walls.gif",425,500 , this));
            wallsList.add(new Wall("images/walls.gif",425,560 , this));
        }
    }

    //键盘监听器
    private class keyMonitor extends KeyAdapter {
        //按下键盘
        public void keyPressed(KeyEvent e) {
            //返回键
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_1:
                    a = 1;
                    y = 150;
                    break;
                case KeyEvent.VK_2:
                    a = 2;
                    y = 250;
                    break;
                case KeyEvent.VK_ENTER:
                    state = a;
                    playerList.add(playerOne);
                    break;
                default:
                   playerOne.KeyPressed(e);
                   break;
            }
        }
        //松开键盘
        public void keyReleased(KeyEvent e){
            playerOne.KeyReleased(e);
        }

    }

    public static void main(String[] args) {
        GamePanel g = new GamePanel();
        g.launch();//启动
    }
}
