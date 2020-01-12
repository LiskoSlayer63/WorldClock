package com.shadowhawk.clock;

public class Color 
{
	private float red = 0;
	private float green = 0;
	private float blue = 0;
	private float alpha = 1;
	
	public Color()
	{
		
	}
	
	public Color(java.awt.Color color)
	{
		this.red = color.getRed() / 255F;
		this.green = color.getGreen() / 255F;
		this.blue = color.getBlue() / 255F;
		this.alpha = color.getAlpha() / 255F;
	}

	public Color(float red, float green, float blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public Color(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public Color(int red, int green, int blue)
	{
		this.red = red / 255F;
		this.green = green / 255F;
		this.blue = blue / 255F;
	}
	
	public Color(int red, int green, int blue, int alpha)
	{
		this.red = red / 255F;
		this.green = green / 255F;
		this.blue = blue / 255F;
		this.alpha = alpha / 255F;
	}
	
	public int getRed()
	{
		return Math.round(red * 255);
	}
	
	public int getGreen()
	{
		return Math.round(green * 255);
	}
	
	public int getBlue()
	{
		return Math.round(blue * 255);
	}
	
	public int getAlpha()
	{
		return Math.round(alpha * 255);
	}
	
	public float getRedF()
	{
		return red;
	}
	
	public float getGreenF()
	{
		return green;
	}
	
	public float getBlueF()
	{
		return red;
	}
	
	public float getAlphaF()
	{
		return alpha;
	}
	
	public static Color fromColor(java.awt.Color color)
	{
		return new Color(color);
	}
}
