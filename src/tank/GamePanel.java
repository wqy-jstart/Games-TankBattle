package tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
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
    //游戏模式 0 游戏未开始 1:单人模式 2:双人模式 3:游戏暂停 4：游戏失败 5:游戏胜利
    private int state = 0;
    //重绘，控制敌方坦克
    int count = 0;
    //已生成敌人数量
    static int enemyCount;

    //物体集合
    public List<Bullet> bulletList = new ArrayList<>();
    public List<Bot> botList = new ArrayList<>();
    public List<Tank> tankList = new ArrayList<>();
    public List<Wall> wallList = new ArrayList<>();
    public List<Bullet> removeList = new ArrayList<>();
    public List<Base> baseList = new ArrayList<>();
    public List<BlastObj> blastList = new ArrayList<>();
    //PlayerOne，玩家1，图片路径
    private PlayerOne playerOne = new PlayerOne(Toolkit.getDefaultToolkit().getImage("images/player1/p1tankU.gif"),
            125, this.height - 100,
            Toolkit.getDefaultToolkit().getImage("images/player1/p1tankU.gif"), Toolkit.getDefaultToolkit().getImage("images/player1/p1tankD.gif"),
            Toolkit.getDefaultToolkit().getImage("images/player1/p1tankL.gif"), Toolkit.getDefaultToolkit().getImage("images/player1/p1tankR.gif"), this);
    ////PlayerTwo，玩家2，图片路径
    private PlayerTwo playerTwo = new PlayerTwo(Toolkit.getDefaultToolkit().getImage("images/player2/p2tankU.gif"),
            615, this.height - 100,
            Toolkit.getDefaultToolkit().getImage("images/player2/p2tankU.gif"), Toolkit.getDefaultToolkit().getImage("images/player2/p2tankD.gif"),
            Toolkit.getDefaultToolkit().getImage("images/player2/p2tankL.gif"), Toolkit.getDefaultToolkit().getImage("images/player2/p2tankR.gif"), this);
    //敌方坦克，图片路径
    Bot bot = new Bot(Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1U.gif"),
            500, 110,
            Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1U.gif"), Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1D.gif"),
            Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1L.gif"), Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1R.gif"), this);
    //基地
    private Base base = new Base(Toolkit.getDefaultToolkit().getImage("images/star.gif")
            , 375, 570, this);

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
        //调用添加墙体的方法
        AddWalls();
        //添加基地
        baseList.add(base);
        //重绘
        while (true) {
            //游戏胜利判定
            if (botList.size() == 0 && enemyCount == 10) {
                state = 5;
            }
            //游戏失败判定
            if (tankList.size() == 0 && (state == 1 || state == 2) || baseList.size() == 0) {
                state = 4;
            }
            //添加敌方坦克,在游戏开始的时候
            if (count % 100 == 1 && enemyCount < 10 && (state == 1 || state == 2)) {
                Random r = new Random();
                int rnum = r.nextInt(700);
                botList.add(new Bot(Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1U.gif"), rnum, 80,
                        Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1U.gif"), Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1D.gif"),
                        Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1L.gif"), Toolkit.getDefaultToolkit().getImage("images/enemy/enemy1R.gif"), this));
                //坦克生成的数量
                enemyCount++;
            }
            repaint();
            try {
                Thread.sleep(30);//间隔25ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //paint()方法
    public void paint(Graphics g) {
        //创建和容器一样大小的Image图片
        if (offScreenImage == null) {
            offScreenImage = this.createImage(width, height);
        }
        //获得该图片的画笔
        Graphics gImage = offScreenImage.getGraphics();
        //设置画笔颜色
        gImage.setColor(Color.BLACK);
        //绘制实心矩形
        gImage.fillRect(0, 0, width, height);
        //挂变画笔颜色
        gImage.setColor(Color.WHITE);
        //改变文字大小
        gImage.setFont(new Font("华文彩云", Font.BOLD, 50));
        //添加文字,游戏未开始
        if (state == 0) {
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/model.png"),0,20,this);
            gImage.drawString("1  PLAYER", 250, 300);
            gImage.drawString("2  PLAYERS", 250, 400);
            gImage.drawString("按1，2选择模式", 220, 500);
            gImage.drawString("按回车开始游戏",220,570);
            //绘制指针(坦克标志)
            gImage.drawImage(select, 180, y+110, null);
        } else if (state == 1 || state == 2) {
            gImage.setFont(new Font("仿宋", Font.BOLD, 30));
            gImage.setColor(Color.red);
            gImage.drawString("剩余敌人:" + botList.size(), 0, 60);
            //添加游戏元素
            for (Tank tank : tankList) {
                tank.painSelf(gImage);
            }
            //重绘子弹元素
            for (Bullet bullet : bulletList) {
                bullet.painSelf(gImage);
            }
            //清除子弹
            bulletList.removeAll(removeList);
            //重绘敌方坦克
            for (Bot bot : botList) {
                bot.painSelf(gImage);
            }
            //重绘墙
            for (Wall wall : wallList) {
                wall.painSelf(gImage);
            }
            //重绘基地
            for (Base base : baseList) {
                base.painSelf(gImage);
            }
            //重绘爆炸图片
            for (BlastObj blastObj : blastList) {
                blastObj.painSelf(gImage);
            }

            //重绘次数
            count++;
        }
        if (state == 5) {
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/win.jpeg"),0,0,this);
        }
        if (state == 4) {
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/gameover.jpeg"),0,0,this);
        }
        if (state == 3) {
            gImage.drawImage(Toolkit.getDefaultToolkit().getImage("images/pause.jpeg"),0,0,this);
        }

        //将缓存区绘制好的图形整个绘制到容器的画布中
        g.drawImage(offScreenImage, 0, 0, null);
    }
    private void AddWalls(){
        //添加围墙 60*60
        for (int i = 0; i < 60; i++) {//竖排围墙
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), i * 15, 170, this));
        }
        for (int i = 0; i < 5; i++) {
            //围着基地的围墙
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 0, 400, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 60, 400, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 680, 400, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 740, 400, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 620, 340, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 560, 280, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 365, 230, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 120, 340, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 180, 280, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 305, 560, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 305, 500, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 365, 500, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 365, 22, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 425, 500, this));
            wallList.add(new Wall(Toolkit.getDefaultToolkit().getImage("images/walls.gif"), 425, 560, this));
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
                    if (state != 4) {
                        state = a;
                        if (state == 1) {
                            tankList.add(playerOne);//添加玩家坦克
                            playerOne.alive = true;
                        } else {
                            tankList.add(playerOne);//添加玩家坦克
                            tankList.add(playerTwo);
                            playerOne.alive = true;
                            playerTwo.alive = true;
                        }
                    }
                    break;
                case KeyEvent.VK_P:
                    if (state != 3) {
                        a = state;
                        state = 3;
                    } else {
                        state = a;
                        if (a == 0) {
                            a = 1;
                        }
                    }
                default:
                    playerOne.KeyPressed(e);
                    playerTwo.KeyPressed(e);
                    break;
            }
        }

        //松开键盘
        public void keyReleased(KeyEvent e) {
            playerOne.KeyReleased(e);
            playerTwo.KeyReleased(e);
        }

    }

    public static void main(String[] args) {
        GamePanel g = new GamePanel();
        g.launch();//启动
    }
}
