package momo.shoot;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

	// 定义游戏状态,默认游戏状态为开始状态
	private int state = START;
	public static final int START = 0;// 游戏开始状态
	public static final int RUNNING = 1;// 游戏运行状态
	public static final int PAUSE = 2;// 游戏暂停状态
	public static final int GAME_OVER = 3;// 游戏结束状态

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
		// 鼠标移动事件响应
		MouseAdapter l = new MouseAdapter() {
			// 鼠标移除事件
			@Override
			public void mouseExited(MouseEvent e) {

				// 更改游戏状态
				if (state == RUNNING) {
					// 只有运行状态才能改为暂停
					state = PAUSE;
				}
			}

			// 鼠标进入状态
			@Override
			public void mouseEntered(MouseEvent e) {
				// 更改游戏状态
				if (state == PAUSE) {
					// 只有暂停状态才能改为running
					state = RUNNING;
				}
			}

			// 重写鼠标单击事件
			@Override
			public void mouseClicked(MouseEvent e) {
				// 更改游戏状态
				if (state == START) {// 只有处于start状态单击屏幕才能改成running状态
					state = RUNNING;
				} else if (state == GAME_OVER) {
					state = START;
					// 从gameover到开始状态要初始化游戏数据
					flyers = new Flyer[0];
					bullets = new Bullet[0];
					hero = new Hero();
				}
			}

			// 重写鼠标移动事件
			@Override
			public void mouseMoved(MouseEvent e) {
				// 只有在游戏运行状态下，英雄机才根据鼠标移动
				if (state == RUNNING) {
					// 移动就获得鼠标新位置
					int x = e.getX();
					int y = e.getY();
					// 将新的坐标传给英雄机对象move方法
					hero.move(x, y);
				}
			}
		};// 匿名内部类

		// 游戏开始监听鼠标事件添加，添加到程序的监听器中
		this.addMouseMotionListener(l);// 支持鼠标移动事件
		this.addMouseListener(l);// 支持鼠标单击事件

		// 创建定时器对象
		Timer timer = new Timer();
		// 调用定时方法做计划
		timer.schedule(new TimerTask() {
			// 定义计时器变量记录run执行次数
			private int runTimes = 0;

			@Override
			public void run() {
				// 除repaint方法外，其余功能只在游戏运行（running）状态下才执行
				if (state == RUNNING) {
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
					}
					// 移动子弹位置
					for (int i = 0; i < bullets.length; i++) {
						bullets[i].step();
					}

					// 添加英雄机动画效果
					hero.step();
					// 子弹击中敌人的碰撞检测
					bang();
					// 敌人和英雄机碰撞检测
					hit();
					// 添加越界销毁
					outOfBounds();
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
		// 批量绘制敌人
		paintFlyers(g);
		// 批量绘制子弹
		paintBullets(g);
		// 绘制分数和生命
		paintScore_Life(g);
		// 根据游戏状态，绘制不同的图片
		if (state == START) {
			g.drawImage(start, 0, 0, null);
		} else if (state == PAUSE) {
			g.drawImage(pause, 0, 0, null);
		} else if (state == GAME_OVER) {
			g.drawImage(gameover, 0, 0, null);
		}

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

	/**
	 * 遍历子弹数组和地人数，进行碰撞检测，一旦发生碰撞，敌人和子弹都减少一个
	 */
	public void bang() {
		for (int i = 0; i < bullets.length; i++) {// 取出每颗子弹
			for (int j = 0; j < flyers.length; j++) {// 和每个敌人做检测
				if (Flyer.bang(bullets[i], flyers[j])) {// 发生碰撞
					// 给英雄机加对应的奖励
					hero.getScore_Award(flyers[j]);
					// 从敌人数组中删除被击中的敌机
					// 使用数组最后一个元素替换当前位置被击中的敌人
					flyers[j] = flyers[flyers.length - 1];
					// 压缩数组，元素个数-1
					flyers = Arrays.copyOf(flyers, flyers.length - 1);
					// 从子弹数组中删除击中敌人的子弹
					bullets[i] = bullets[bullets.length - 1];
					bullets = Arrays.copyOf(bullets, bullets.length - 1);
					i--;// 每发现一次碰撞，子弹就要退一个元素，重新检测当前位置新子弹
					// 只要有敌人被击中，就退出遍历敌人数组的循环，不再往后继续循环
					break;
				}
			}
		}
	}

	/**
	 * 绘制分数和生命值的方法
	 * 
	 * @param g
	 */
	public void paintScore_Life(Graphics g) {
		int x = 10;// 文字左上角x坐标
		int y = 15;// 文字左上角y坐标
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		g.setFont(font);
		// 绘制第一行：分数
		g.drawString("SCORE:" + hero.getScore(), x, y);
		// 绘制第二行：y坐标下移20个单位
		y += 20;
		g.drawString("LIFE:" + hero.getLife(), x, y);
	}

	/**
	 * 检查所有飞行物是否越界
	 */
	public void outOfBounds() {
		// 检查敌人是否越界
		Flyer[] Flives = new Flyer[flyers.length];
		// 遍历敌人数组，将存活的敌人对象，保存到新的数组中
		// 标示下一个存活对象的位置；统计lives数组共有几个存活对象
		int index = 0;
		for (int i = 0; i < flyers.length; i++) {
			if (!flyers[i].outOfBounds()) {// 没有越界的对象
				Flives[index] = flyers[i];
				index++;
			}
		}
		// index中保存当前存活对象个数，lives保存的是存活对象数组
		// 对lives数组按照index个数进行压缩，压缩后替换回flyers变量中
		flyers = Arrays.copyOf(Flives, index);

		// 同上检测所有子弹是否越界
		Bullet[] Blives = new Bullet[bullets.length];
		index = 0;
		for (int i = 0; i < bullets.length; i++) {
			if (!bullets[i].outOfBounds()) {// 没有越界的对象
				Blives[index] = bullets[i];
				index++;
			}
		}
		bullets = Arrays.copyOf(Blives, index);
	}

	/**
	 * 遍历敌人对象，英雄机和敌人对象是否碰撞
	 */
	public void hit() {
		Flyer[] lives = new Flyer[flyers.length];
		// 记录存活敌人
		int index = 0;
		for (int i = 0; i < flyers.length; i++) {
			if (!hero.hit(flyers[i])) {// 未碰撞
				lives[index] = flyers[i];
				index++;
			}
		}
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
		}
		// 压缩存活敌人数组,替换敌人数组
		flyers = Arrays.copyOf(lives, index);
	}
}
