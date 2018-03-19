package momo.shoot;

import java.util.Random;

/**
 * 封装英雄机属性和功能的类
 * 
 * @author liudanfeng
 *
 */
public class Hero extends Flyer {
	private int doubleFire;// 双倍火力子弹数
	private int life; // 生命值
	private int score; // 得分

	/**
	 * 英雄机构造方法
	 */
	public Hero() {
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 450;
		doubleFire = 20;
		life = 3;
		score = 0;
	}

	/**
	 * 对外提供读取生命值的方法
	 * 
	 * @return
	 */
	public int getLife() {
		return life;
	}

	/**
	 * 对外提供获取得分的方法
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	@Override
	/**
	 * 用来实现英雄机动画效果的方法，两种英雄机图片定时来回切换
	 */
	public void step() {

		Random r = new Random();
		if (r.nextInt(2) == 0) {
			image = ShootGame.hero0;
		} else {
			image = ShootGame.hero1;
		}
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 英雄机随鼠标移动的方法
	 * 
	 * @param x
	 *            鼠标位置的x坐标
	 * @param y
	 *            鼠标位置的y坐标
	 */
	public void move(int x, int y) {
		// 传入的是鼠标的位置，该方法作用是将英雄机中心点和鼠标位置一致
		this.x = x - width / 2;
		this.y = y - height / 2;
	}

	/**
	 * 英雄机获得分数和奖励的方法
	 * 
	 * @param f
	 *            父类型的变量，既可以指向敌机类型也可以指向蜜蜂类型
	 */
	public void getScore_Award(Flyer f) {

	}

	/**
	 * 英雄机发射子弹的方法
	 * 
	 * @return 新创建出的子弹对象，可能是一发或者两发子弹，所以数组类型保存返回值
	 */
	public Bullet[] shoot() {
		Bullet[] bullets = null;
		// 判断合适创建双倍火力
		if (doubleFire != 0) {
			bullets = new Bullet[2];
			Bullet b1 = new Bullet(x + width / 4, y - ShootGame.bullet.getHeight());
			Bullet b2 = new Bullet(x + width * 3 / 4, y - ShootGame.bullet.getHeight());
			bullets[0] = b1;
			bullets[1] = b2;
			// 每创建一次双倍火力
			doubleFire -= 2;
		} else {
			// 单倍火力情况，
			bullets = new Bullet[1];
			bullets[0] = new Bullet(x + width / 2, y - ShootGame.bullet.getHeight());
		}
		return bullets;
	}

	/**
	 * 英雄机自带的和敌人碰撞检测方法
	 * 
	 * @param f
	 *            可能发生碰撞的敌人；可能是蜜蜂或者敌机所以定义父类型
	 * @return false为碰撞；true碰撞
	 */
	public boolean hit(Flyer f) {
		return false;
	}
}
