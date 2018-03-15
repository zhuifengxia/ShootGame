package momo.shoot;

import java.util.Random;

/**
 * 封装敌机属性和功能的类
 * 
 * @author liudanfeng
 *
 */
public class Airplane extends Flyer {
	private int speed = 2;// 敌机每次下落2个单位长度
	private int score = 5;// 敌机包含的奖励分数

	/**
	 * 敌机类的无参构造方法
	 */
	public Airplane() {
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random r = new Random();
		x = r.nextInt(ShootGame.WIDTH - width);
	}

	/**
	 * 外部读取敌机奖励分数方法
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
}
