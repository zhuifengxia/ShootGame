package momo.shoot;

/**
 * 封装子弹属性和功能的类
 * 
 * @author liudanfeng
 *
 */
public class Bullet extends Flyer {
	private int speed = 3;// 子弹每次上升3个单位长度

	/**
	 * 子弹类的带参构造方法 子弹位置需要根据英雄机的位置来决定
	 * 
	 * @param x
	 *            英雄机指定的子弹x坐标
	 * @param y
	 *            英雄机制定的子弹y坐标
	 */
	public Bullet(int x, int y) {
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}

	@Override
	public void step() {
		// 每次向上移动一个speed
		y -= speed;

	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}
}
