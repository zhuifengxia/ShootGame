package momo.shoot;
/**
 * 封装所有飞行物公共属性的功能和父类
 * @author liudanfeng
 *
 */

import java.awt.image.BufferedImage;

public abstract class Flyer {
	protected int x; // 飞行物左上角x坐标
	protected int y; // 飞行物左上角的y坐标
	protected int width; // 飞行物的宽度
	protected int height; // 飞行物的高度
	// java中保存图片使用BufferedImage类型
	protected BufferedImage image; // 飞行物使用的图片

	/**
	 * 要求所有飞行物都必须能移动 但移动方式由子类实现
	 */
	public abstract void step();

	/**
	 * 检查越界的方法
	 * 
	 * @return 是否越界
	 */
	public abstract boolean outOfBounds();
}
