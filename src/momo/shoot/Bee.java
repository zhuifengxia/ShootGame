package momo.shoot;

/**
 * 封装蜜蜂属性和功能的类
 * 
 * @author liudanfeng
 *
 */
public class Bee extends Flyer {
	/* 定义奖励类型的备选项的常量 */
	public static final int DOUBLE_FIRE = 0;// 奖励类型是0，说明是奖励双倍火力
	public static final int LIFE = 1;// 奖励类型是1，说明奖励类型是一次生命
	private int xspeed = 1;// 水平移动的速度，每次移动一个单位长度；
	private int yspeed = 2;// 垂直移动的速度；每次移动2个单位长度
	private int awardType;// 当前蜜蜂保存的奖励类型

	/**
	 * 对外读取蜜蜂奖励类型的方法
	 * 
	 * @return
	 */
	public int getAwardType() {
		return awardType;
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
}
