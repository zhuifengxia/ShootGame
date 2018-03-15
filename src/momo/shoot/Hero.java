package momo.shoot;

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
		doubleFire = 0;
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
	public void step() {
		// TODO Auto-generated method stub

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
