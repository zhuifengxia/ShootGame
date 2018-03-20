package momo.shoot;

import java.util.Random;

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
	 * 蜜蜂对象的无参构造方法
	 */
	public Bee() {
		// 从主程序中获取蜜蜂图片的静态变量
		image = ShootGame.bee;
		// 用图片的尺寸设置对象的尺寸
		width = image.getWidth();
		height = image.getHeight();
		// 设置蜜蜂对象开始下落的高度为-height
		y = -height;
		// 初始蜜蜂对象开始下落的x坐标，在0~（界面宽度-蜜蜂宽度）随机
		Random r = new Random();
		x = r.nextInt(ShootGame.WIDTH - width);
		// 在0和1之间随机选取奖励类型
		awardType = r.nextInt(2);
	}

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
		// 每次x移动一个xspeed，y移动一个yspeed
		x += xspeed;
		y += yspeed;
		// 蜜蜂不能超出边界，碰到边界反向移动
		if (x < 0 || x > (ShootGame.WIDTH - width)) {
			xspeed *= -1;
		}

	}

	@Override
	public boolean outOfBounds() {
		// 蜜蜂y坐标>游戏界面高度就算越界
		return y > ShootGame.HEIGHT;
	}
}
