package com.shadowhawk.clock.analog;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.shadowhawk.clock.Clock;
import com.shadowhawk.clock.config.WorldClockConfig;
import com.shadowhawk.clock.indicator.IndicatorArray;
import com.shadowhawk.clock.utils.Color;
import com.shadowhawk.clock.utils.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;


/**
 * Analog clock to show the time in minecraft or IRL based on the example mod by Adam Mummery-Smith
 *
 * @author Shadow_Hawk
 */
public class AnalogClock extends Clock
{
	/**
	 * This is the clock face resource, you need to create a resource location for any assets that you
	 * wish to use. It is best to make these static to avoid new instances being created for every instance
	 * of the referencing object, this also means they will only be garbage collected when the class is
	 * garbage collected or when no instances of the class are left.
	 * 
	 * The first parameter for the resource location is the "domain" and this should normally be your mod's
	 * name. The domain MUST be lower case! The second is the resource "path" and represents the path to the
	 * resource within the domain. It is convention that the path always start with the resource type, such
	 * as "textures" in this case.
	 * 
	 * Resources are always stored in a path of the form "assets/{domain}/{path}" which makes the appropriate
	 * path to the CLOCKFACE resource: "/assets/example/textures/clock/face.png"
	 */
	private static final ResourceLocation CLOCKFACE = new ResourceLocation("worldclock", "textures/clock/face.png");
	
	/**
	 * Draw a rectangle using the currently bound texture
	 */
	static void glDrawTexturedRect(float x, float y, float width, float height, int u, int v, int u2, int v2)
	{
		// Set the appropriate OpenGL modes
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01F);
		GlStateManager.enableTexture();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		float texMapScale = 0.001953125F; // 512px
		
		// We use the tessellator rather than drawing individual quads because it uses vertex arrays to
		// draw the quads more efficiently.
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(x + 0,	 y + height, 0).tex(u  * texMapScale, v2 * texMapScale).endVertex();
		worldRenderer.pos(x + width, y + height, 0).tex(u2 * texMapScale, v2 * texMapScale).endVertex();
		worldRenderer.pos(x + width, y + 0,	  0).tex(u2 * texMapScale, v  * texMapScale).endVertex();
		worldRenderer.pos(x + 0,	 y + 0,	  0).tex(u  * texMapScale, v  * texMapScale).endVertex();
		tessellator.draw();
	}
	
	
	private ClockHands mcHands, sysHands;
	
	/**
	 * @param xPos X position for the clock
	 * @param yPos Y position for the clock
	 */
	public AnalogClock(int xPos, int yPos)
	{
		super(xPos, yPos);
		
		this.mcHands = new ClockHands(Minecraft.getInstance(), xPos, yPos, WorldClockConfig.clockSize, Color.fromColor(java.awt.Color.WHITE), Color.fromColor(java.awt.Color.WHITE), true);
		this.sysHands = new ClockHands(Minecraft.getInstance(), xPos, yPos, WorldClockConfig.clockSize, Color.fromColor(java.awt.Color.MAGENTA), Color.fromColor(java.awt.Color.MAGENTA), Color.fromColor(java.awt.Color.MAGENTA), false);
		this.nextClock = new IndicatorArray(nextClockCoords[0], nextClockCoords[1]);
		
		Logger.debug(CLOCKFACE.getPath());
	}
	
	/**
	 * Render the clock at its current position, unless hidden
	 * 
	 * @param minecraft Minecraft game instance
	 */
	public void render(Minecraft minecraft)
	{		
		if (this.isVisible())
		{
			// First, update the hand angles
			sysHands.calculateAngles();
			mcHands.calculateAngles(minecraft);
			
			// Then render the actual clock
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
	 * Renders the clock
	 * 
	 * @param minecraft Minecraft game instance
	 */
	private void renderClock(Minecraft minecraft)
	{
		// Render the face
		this.renderClockFace(minecraft);
		
		// Render each of the hands
		if(WorldClockConfig.systemClock)
		{
			sysHands.render();
		}
		if(WorldClockConfig.worldClock)
		{
			mcHands.render();
		}
	}

	/**
	 * Renders the clock face texture using the texture resource
	 * 
	 * @param minecraft Minecraft game instance
	 */
	private void renderClockFace(Minecraft minecraft)
	{
		// Bind the texture resource
		minecraft.getTextureManager().bindTexture(CLOCKFACE);
		
		// Draw a rectangle using the currently bound texture
		glDrawTexturedRect(this.xPos, this.yPos, this.size, this.size, 1, 1, 511, 511);
	}

	
	@Override
	public void setSize(float scale)
	{
		super.setSize(scale);
		this.nextClockCoords[0] = xPos + 0.90F * this.size;
		this.nextClockCoords[1] = yPos + 0.16F * this.size;
		updateNextClockCoords();
		if(nextClock != null)
		{
			nextClock.setSize(scale * 1.7F);
		}
		this.mcHands = new ClockHands(Minecraft.getInstance(), xPos, yPos, this.size, Color.fromColor(java.awt.Color.WHITE), Color.fromColor(java.awt.Color.WHITE), true);
		this.sysHands = new ClockHands(Minecraft.getInstance(), xPos, yPos, this.size, Color.fromColor(java.awt.Color.MAGENTA), Color.fromColor(java.awt.Color.MAGENTA), Color.fromColor(java.awt.Color.MAGENTA), false);
	}
}