package com.shadowhawk.clock;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod.EventBusSubscriber(modid = WorldClock.MOD_ID)
public class ClientEvents 
{
	@SubscribeEvent
	public static void onConfigChanged(final OnConfigChangedEvent event) 
	{
		if (event.getModID().equals(WorldClock.MOD_ID))
			WorldClockConfig.sync();
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		
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
	public static void onKeyInput(KeyInputEvent event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		
		if (event.isCanceled() || minecraft.isGamePaused() || !Minecraft.isGuiEnabled() || !minecraft.inGameHasFocus || !WorldClock.clockKeyBinding.isPressed()) return;
		
		WorldClock instance = WorldClock.instance;
			
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			WorldClockConfig.clockSize = Math.max(32, ((int)WorldClockConfig.clockSize << 1) & 0x1FF);
			instance.clock.setSize(WorldClockConfig.clockSize);
			instance.clock2.setSize(WorldClockConfig.clockSize);
		}
		else
			WorldClockConfig.clockVisible = !WorldClockConfig.clockVisible;
		
		WorldClockConfig.sync();
	}
}
