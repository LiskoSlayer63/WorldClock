package com.shadowhawk.clock.indicator;

import java.util.Calendar;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.ReadableColor;

import com.shadowhawk.clock.ClockData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.*;

public class Indicator extends ClockData{

	public Indicator(Minecraft minecraft, float xPos, float yPos, float scale, ReadableColor color, boolean isMinecraft)
	{
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		this.scale = scale;
		this.minecraft = minecraft;
		this.isMinecraft = isMinecraft;
	}
	
	public void render()
	{
		if((isMinecraft && isDay(Minecraft.getMinecraft()))|| (!isMinecraft && isDay(Calendar.getInstance())))
		{
			drawFilledCircle(xPos + 0.1F * scale, yPos + 0.07F * scale, 0.03F * scale, color, 1.0F);
		}
	}
	
	public void drawFilledCircle(float x, float y, float radius, ReadableColor color, float transparency){
		int triangleAmount = 20; //# of triangles used to draw circle
		float processedTransparency = Math.min(1.0f, Math.max(0.0f, transparency));
		GlStateManager.disableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), processedTransparency);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexBuffer = tessellator.getBuffer();
		float twicePi = (float) (2.0f * Math.PI);
		
		vertexBuffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
			for(int i = 0; i <= triangleAmount;i++) { 
				float xBuffer = (float) (x + (radius * Math.cos(i *  twicePi / triangleAmount)));
				float yBuffer = (float) (y + (radius * Math.sin(i * twicePi / triangleAmount)));
				vertexBuffer.pos(xBuffer, yBuffer, 0).endVertex();//.tex(u  * texMapScale, v  * texMapScale).endVertex();
			}
		tessellator.draw();
		
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
	}
}