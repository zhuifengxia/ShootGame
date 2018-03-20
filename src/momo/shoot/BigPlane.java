package momo.shoot;

import java.util.Random;

/**
 * 大型敌机的相关封装
 * 
 * @author liudanfeng
 *
 */
public class BigPlane extends Flyer implements EnemyScore {

	private int speed = 1;// 敌机每次下落1个单位长度
	private int score = 50;// 敌机包含的奖励分数

	/**
	 * 大型敌机类的无参构造方法
	 */
	public BigPlane() {
		image = ShootGame.bigplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random r = new Random();
		x = r.nextInt(ShootGame.WIDTH - width);
	}

	@Override
	public void step() {
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
