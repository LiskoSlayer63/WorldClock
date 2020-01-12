package com.shadowhawk.clock;

import com.shadowhawk.clock.config.WorldClockConfig;

import net.minecraft.client.Minecraft;

public class Clock {

	public Clock(float x, float y)
	{
		this.setPosition(x, y);
	}
	
	protected float xPos, yPos;
	protected float size;
	
	protected Clock nextClock = null;
	public float[] nextClockCoords = {10, 10};
	
	/**
	 * Get the current size
	 */
	public float getSize()
	{
		return this.size;
	}
	
	/**
	 * Get whether the clock is currently visible
	 */
	public boolean isVisible()
	{
		return WorldClockConfig.clockVisible;
	}
	
	/**
	 * Set the size of the clock
	 * 
	 * @param size new size (min is 32)
	 */
	public void setSize(float size)
	{
		this.size  = Math.max(32, size);
	}
	
	/**
	 * Set the location of the top left corner of the clock
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public void setPosition(float xPos, float yPos)
	{
		this.xPos = Math.max(10,  xPos);
		this.yPos = Math.max(10,  yPos);
	}
	
	/**
	 * Placeholder method, must overwrite
	 * @param minecraft
	 */
	public void render(Minecraft minecraft)
	{
		
	}
	
	public void updateNextClockCoords()
	{
		if(nextClock != null)
		{
			nextClock.xPos = nextClockCoords[0];
			nextClock.yPos = nextClockCoords[1];
		}
	}
}