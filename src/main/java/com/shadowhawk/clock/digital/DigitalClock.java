package com.shadowhawk.clock.digital;

import com.shadowhawk.clock.Clock;
import com.shadowhawk.clock.Color;
import com.shadowhawk.clock.WorldClockConfig;
import com.shadowhawk.clock.Logger;
import com.shadowhawk.clock.indicator.IndicatorArray;

import net.minecraft.client.Minecraft;

public class DigitalClock extends Clock{
	private DigitalClockPanel mcPanel, sysPanel;
	
	/**
	 * An object containing up to two digital clocks
	 * @param xPos X-coordinate for the top left of the group of clocks
	 * @param yPos Y-coordinate for the top left of the group of clocks
	 */
	public DigitalClock(int xPos, int yPos)
	{
		super(xPos, yPos);
		this.setSize(64);
		
		this.mcPanel = new DigitalClockPanel(Minecraft.getInstance(), this.xPos, this.yPos + 10, size, Color.fromColor(java.awt.Color.GRAY), true);
		this.sysPanel = new DigitalClockPanel(Minecraft.getInstance(), this.xPos, this.yPos + 10 + 0.32F * size, size, Color.fromColor(java.awt.Color.MAGENTA), true);
		this.nextClock = new IndicatorArray(nextClockCoords[0], nextClockCoords[1]);
		Logger.debug("Digital Clock Instantiation finished: (" + xPos + ", " + yPos + ") ");
	}
	
	/**
	 * Update the clocks and check if they're visible, and if so, attempt to render them
	 * @param minecraft
	 */
	public void render(Minecraft minecraft)
	{
		if(this.isVisible())
		{
			Logger.debug("Render the digital cock (ln 38): (" + xPos + ", " + yPos + ") ");
			sysPanel.updateTimes();
			//mcPanel.updateTimes(Minecraft.getMinecraft());
			mcPanel.updateTimes(minecraft); //TODO
			if(WorldClockConfig.systemClock || WorldClockConfig.worldClock)
			{
				this.renderClock(minecraft);
				if(WorldClockConfig.useIndicator && nextClock != null)
				{
					nextClock.render(minecraft);
				}
			}
		}
	}
	
	/**
	 * Renders each clock if possible
	 * @param minecraft
	 */
	private void renderClock(Minecraft minecraft)
	{
		if(WorldClockConfig.systemClock)
		{
			sysPanel.render();
		}
		if(WorldClockConfig.worldClock)
		{
			mcPanel.render();
		}
	}
	
	/**
	 * Resizes the clocks
	 * @param scale 
	 */
	@Override
	public void setSize(float scale)
	{
		super.setSize(scale);
		this.nextClockCoords[0] = xPos + 0.78F * size;
		this.nextClockCoords[1] = yPos;
		updateNextClockCoords();
		if(nextClock != null)
		{
			nextClock.setSize(scale);
		}
		this.mcPanel = new DigitalClockPanel(Minecraft.getInstance(), xPos, yPos, this.size, Color.fromColor(java.awt.Color.GRAY), true);
		this.sysPanel = new DigitalClockPanel(Minecraft.getInstance(), xPos, yPos + 0.32F * size, this.size, Color.fromColor(java.awt.Color.MAGENTA), false);
	}
}