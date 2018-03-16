package momo.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	// 游戏界面大小固定：400*700---定义常量
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;

	/**
	 * 游戏启动第一件事，从硬盘中加载所有需要用的图片到内存中，只在启动时加载一次，就使用静态块 缓存在程序中的所有图片都会反复使用，仅保存一份静态变量
	 * 
	 */
	public static BufferedImage backcground; // 背景图片
	public static BufferedImage start;// 开始图片
	public static BufferedImage airplane;// 敌机图片
	public static BufferedImage bee;// 蜜蜂图片
	public static BufferedImage bullet;// 子弹图片
	public static BufferedImage hero0;// 英雄机图片0
	public static BufferedImage hero1;// 英雄机图片1
	public static BufferedImage pause;// 暂停图片
	public static BufferedImage gameover;// 结束图片
	static {// 静态块，仅在类首次加载到方法区时执行一次，专门加载静态资源
		try {
			backcground = ImageIO.read(ShootGame.class.getResource("background.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为游戏中的角色对象定义数据结构 ;
	 * 
	 */
	public Hero hero = new Hero();// 英雄对象
	public Flyer[] flyers = {};// 存储所有敌人的对象数组，敌机和蜜蜂
	public Bullet[] bullets = {};// 存储子弹对象数组

	public static void main(String[] args) {
		JFrame frame = new JFrame("飞机大战");// 创建jframe对象--窗框
		frame.setSize(WIDTH, HEIGHT);// 设置窗体宽和高
		frame.setAlwaysOnTop(true);// 设置窗体总在最上，不被其他窗体挡住
		// 设置窗体关闭同时，退出程序
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);// 设置窗体的初始位置，null表示居中
		// 在窗体中嵌入背景面板对象--jpanel
		ShootGame game = new ShootGame();// 创建背景面板对象
		frame.add(game);// 将背景面板对象嵌入到窗体对象中
		// 窗体默认不可见，设置可见才能看到窗体
		frame.setVisible(true);// 自动调用窗体的paint方法
		game.action();
	}

	/**
	 * 游戏启动需要做的事情
	 */
	public void action() {
		// 创建定时器对象
		Timer timer = new Timer();
		// 调用定时方法做计划
		timer.schedule(new TimerTask() {

			// 定义计时器变量记录run执行次数
			private int runTimes = 0;

			@Override
			public void run() {
				runTimes++;// 记录执行的run方法次数
				if (runTimes % 50 == 0) {// 这样可以降低生成新对象频率
					// 自动随机创建对象
					nextOne();
				}
				// 遍历对象，调用step方法，使对象移动位置
				for (int i = 0; i < flyers.length; i++) {
					flyers[i].step();
				}

				// 每三百毫秒创建子弹
				if (runTimes % 30 == 0) {
					shoot();// 创建一次子弹
					// 移动子弹位置
					for (int i = 0; i < bullets.length; i++) {
						bullets[i].step();
					}
				}
				// 界面发生改变必须使用repaint重绘界面
				repaint();

			}
		}, 10, 10);// 10毫秒变化一次
	}

	@Override
	public void paint(Graphics g) {
		// 绘制背景图片0,0左上角绘制
		g.drawImage(backcground, 0, 0, null);
		// 绘制英雄机
		paintHero(g);
		// Airplane airplane = new Airplane();
		// airplane.y += 100;
		// Bee bee = new Bee();
		// bee.y += 50;
		// flyers = new Flyer[2];
		// flyers[0] = airplane;
		// flyers[1] = bee;
		// 批量绘制敌人
		paintFlyers(g);
		// 批量绘制子弹
		paintBullets(g);
	}

	/**
	 * 绘制英雄机对象方法
	 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}

	/**
	 * 绘制所有敌人对象方法
	 * 
	 * @param g
	 */
	public void paintFlyers(Graphics g) {
		for (int i = 0; i < flyers.length; i++) {
			g.drawImage(flyers[i].image, flyers[i].x, flyers[i].y, null);
		}
	}

	/**
	 * 绘制所有子弹的方法
	 * 
	 * @param g
	 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
		}
	}

	/**
	 * 随机生成敌人对象
	 */
	public void nextOne() {
		Random r = new Random();
		Flyer f = null;
		if (r.nextInt(20) == 0) {// 设置蜜蜂生成概率
			f = new Bee();
		} else {
			f = new Airplane();
		}

		flyers = Arrays.copyOf(flyers, flyers.length + 1);
		// 新创建的放到数组末尾
		flyers[flyers.length - 1] = f;
	}

	/**
	 * 获得英雄机发射的子弹对象，将新的子弹对象保存数组中统一管理
	 */
	public void shoot() {
		// 获得英雄机新的子弹数组
		Bullet[] newBullets = hero.shoot();
		// 根据返回新的子弹扩容子弹数组
		bullets = Arrays.copyOf(bullets, bullets.length + newBullets.length);
		// 从newbullets数组中所有数组到bullets数组末尾
		System.arraycopy(newBullets, 0, bullets, bullets.length - newBullets.length, newBullets.length);
	}
}
