package com.shadowhawk.clock.indicator;

import com.shadowhawk.clock.Clock;
import com.shadowhawk.clock.utils.Color;

import net.minecraft.client.Minecraft;

public class IndicatorArray extends Clock{
	
	Indicator mcInd, sysInd;
	
	public IndicatorArray(float xPos, float yPos)
	{
		super(xPos, yPos);
		mcInd = new Indicator(Minecraft.getInstance(), xPos, yPos, size, Color.fromColor(java.awt.Color.GRAY), true);
		sysInd = new Indicator(Minecraft.getInstance(), xPos, yPos + 0.32F * size, size, Color.fromColor(java.awt.Color.MAGENTA), false);
	}
	
	public void render(Minecraft minecraft)
	{
		mcInd.render();
		sysInd.render();
	}
	
	public void setSize(float scale)
	{
		super.setSize(scale);
		mcInd = new Indicator(Minecraft.getInstance(), xPos, yPos, size, Color.fromColor(java.awt.Color.GRAY), true);
		sysInd = new Indicator(Minecraft.getInstance(), xPos, yPos + 0.32F * size, size, Color.fromColor(java.awt.Color.MAGENTA), false);
	}
}