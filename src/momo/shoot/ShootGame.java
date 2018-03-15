package momo.shoot;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
