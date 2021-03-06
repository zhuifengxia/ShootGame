package momo.shoot;

import java.util.Random;

/**
 * 封装敌机属性和功能的类
 * 
 * @author liudanfeng
 *
 */
public class Airplane extends Flyer implements EnemyScore {
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

	@Override
	public void step() {
		// 每次向下移动向下移动1一个speed
		y += speed;

	}

	@Override
	public boolean outOfBounds() {
		// 敌机y坐标>游戏界面高度就算越界
		return y > ShootGame.HEIGHT;
	}

	/**
	 * 外部获取敌机的得分奖励
	 */
	@Override
	public int getScore() {
		return score;
	}
}
