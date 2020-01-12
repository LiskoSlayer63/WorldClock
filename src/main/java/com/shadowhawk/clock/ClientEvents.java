package com.shadowhawk.clock;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = WorldClock.MOD_ID)
public class ClientEvents 
{
	@SubscribeEvent
	public static void onConfigChanged(final OnConfigChangedEvent event) 
	{
		if (event.getModID().equals(WorldClock.MOD_ID))
			WorldClockConfig.save();
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		
		if (event.isCanceled() || !Minecraft.isGuiEnabled() || !event.getType().equals(ElementType.ALL)) return;

		WorldClock instance = WorldClock.instance;

		if (WorldClockConfig.clockVisible)
		{
			if(!WorldClockConfig.digitalMode)
				instance.clock.render(minecraft);
			else
				instance.clock2.render(minecraft);
		}
	}
	
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		Minecraft minecraft = Minecraft.getInstance();
		
		if (event.isCanceled() || minecraft == null || !event.phase.equals(Phase.END) || minecraft.isGamePaused() || !Minecraft.isGuiEnabled() || !minecraft.isGameFocused() || !WorldClock.clockKeyBinding.isPressed()) return;

		WorldClock instance = WorldClock.instance;
			
		if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT) || Input.isKeyPressed(GLFW.GLFW_KEY_RIGHT_SHIFT))
		{
			WorldClockConfig.clockSize = Math.max(32, ((int)WorldClockConfig.clockSize << 1) & 0x1FF);
			instance.clock.setSize(WorldClockConfig.clockSize);
			instance.clock2.setSize(WorldClockConfig.clockSize);
		}
		else
			WorldClockConfig.clockVisible = !WorldClockConfig.clockVisible;
		
		WorldClockConfig.save();
	}
}
