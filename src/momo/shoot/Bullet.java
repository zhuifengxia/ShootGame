package momo.shoot;

/**
 * 封装子弹属性和功能的类
 * 
 * @author liudanfeng
 *
 */
public class Bullet extends Flyer {
	private int speed = 3;// 子弹每次上升3个单位长度

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}
}
