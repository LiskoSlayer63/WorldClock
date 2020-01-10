package com.shadowhawk.clock;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod.EventBusSubscriber(modid = ForgeModWorldClock.MOD_ID)
public class ClientEvents 
{
	@SubscribeEvent
	public static void onConfigChanged(final OnConfigChangedEvent event) 
	{
		if (event.getModID().equals(ForgeModWorldClock.MOD_ID))
			ClockConfig.sync();
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		if (event.isCanceled() || !event.getType().equals(ElementType.ALL)) return;
		
		Minecraft minecraft = Minecraft.getMinecraft();
		ForgeModWorldClock instance = ForgeModWorldClock.instance;
			
		if (ClockConfig.clockVisible)
		{
			if(!ClockConfig.digitalMode)
				instance.clock.render(minecraft);
			else
				instance.clock2.render(minecraft);
		}
	}
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		ForgeModWorldClock instance = ForgeModWorldClock.instance;
		
		if (event.isCanceled() || minecraft.isGamePaused() || !Minecraft.isGuiEnabled() || !ForgeModWorldClock.clockKeyBinding.isPressed()) return;
			
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			ClockConfig.clockSize = Math.max(32, ((int)ClockConfig.clockSize << 1) & 0x1FF);
			instance.clock.setSize(ClockConfig.clockSize);
			instance.clock2.setSize(ClockConfig.clockSize);
		}
		else
			ClockConfig.clockVisible = !ClockConfig.clockVisible;
		
		ClockConfig.sync();
	}
}
