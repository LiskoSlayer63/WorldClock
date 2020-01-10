package com.shadowhawk.clock.analog;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import com.shadowhawk.clock.ClockData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class ClockHands extends ClockData{	
	
	/**
	 * Draw an opaque rectangle
	 */
	private static void glDrawRect(float x1, float y1, float x2, float y2, ReadableColor colour)
    {
		// Set GL modes
		GlStateManager.disableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(colour.getRed(), colour.getGreen(), colour.getBlue(), 1.0F);
        
        // Draw the quad
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x1, y2, 0).endVertex();
        worldRenderer.pos(x2, y2, 0).endVertex();
        worldRenderer.pos(x2, y1, 0).endVertex();
        worldRenderer.pos(x1, y1, 0).endVertex();
        tessellator.draw();
        
        // Restore GL modes
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
    }
	
	/**
	 * Angles for the hands
	 */
	private float smallHandAngle, largeHandAngle, secondHandAngle;
	
	/**
	 * Sizes for each of the hands
	 */
	private float smallHandSize, largeHandSize, secondHandSize;
	
	/**
	 * Width of the hands 
	 */
	private float handWidth = 1.0F;
	
	/**
	 * Colours for each of the hands
	 */
	private ReadableColor smallHandColour, largeHandColour, secondHandColour;
	
	

	private boolean hasSeconds;
	
	public ClockHands(Minecraft minecraft, float xPos, float yPos, float size, ReadableColor smallHandColor, ReadableColor largeHandColor, boolean isMinecraft)
	{
		this.largeHandColour  = largeHandColor;   
		this.smallHandColour  = smallHandColor;
		this.xPos = xPos;
		this.yPos = yPos;
		this.scale = size;
		this.minecraft = minecraft;
		this.isMinecraft = isMinecraft;
		
		
		setSize();
	}
	
	public ClockHands(Minecraft minecraft, float xPos, float yPos, float size, ReadableColor smallHandColor, ReadableColor largeHandColor, ReadableColor secondHandColor, boolean isMinecraft)
	{
		this(minecraft, xPos, yPos, size, smallHandColor, largeHandColor, isMinecraft);
		this.secondHandColour = secondHandColor;
		hasSeconds = true;
		
		
		setSize();
	}
	void calculateAngles()
	{
	    super.updateTimes();
		
		this.smallHandAngle  = 360.0F * (0.0833F * hour + 0.00138F * minute);
		this.largeHandAngle  = 360.0F * (0.0166F * minute);
		this.secondHandAngle  = 360.0F * (0.0166F * second);
	}
	
	void calculateAngles(Minecraft minecraft)
	{
	    super.updateTimes(minecraft);
		
		this.smallHandAngle  = 360.0F * (0.0833F * hour + 0.00138F * minute);
		this.largeHandAngle  = 360.0F * (0.0166F * minute);
		this.secondHandAngle  = 360.0F * (0.0166F * second);
	}
	
	public void render(/*Minecraft minecraft*/)
	{
		if(isMinecraft)
		{
			calculateAngles(minecraft);
		}
		else
		{
			calculateAngles();
		}
		
		this.renderClockHand(this.largeHandAngle,  this.largeHandSize,  this.handWidth * 1.2F, this.largeHandColour);
		this.renderClockHand(this.smallHandAngle,  this.smallHandSize,  this.handWidth * 2.0F, this.smallHandColour);
		if(hasSeconds)
		{
			this.renderClockHand(this.secondHandAngle, this.secondHandSize, this.handWidth * 1.2F, this.secondHandColour);
		}
	}
	
	/**
	 * Render one of the hands 
	 */
	private void renderClockHand(float angle, float length, float width, ReadableColor colour)
	{
		// Push the current transform onto the stack
		GlStateManager.pushMatrix();
		
		// Transform to the mid point of the clock
		GlStateManager.translate(this.xPos + (this.scale / 2), this.yPos + (this.scale / 2), 0);
		
		// and rotate by the hand angle
		GlStateManager.rotate(angle, 0.0F, 0.0F, 1.0F);
		
		// then draw the hand (straight up of course)
		glDrawRect(width * -0.5F, length * 0.2F, width * 0.5F, -length, colour);
		
		// and finally restore the current transform
		GlStateManager.popMatrix();
	}

	
	public void setSize()
	{
		this.smallHandSize  = this.scale * 0.25F;
		this.largeHandSize  = this.scale * 0.38F;
		this.secondHandSize = this.scale * 0.35F;
		this.handWidth = this.scale / 64.0F;
	}
}