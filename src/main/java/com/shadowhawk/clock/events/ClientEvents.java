package com.shadowhawk.clock.events;

import com.shadowhawk.clock.WorldClock;
import com.shadowhawk.clock.config.ConfigHelper;
import com.shadowhawk.clock.config.WorldClockConfig;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@EventBusSubscriber(modid = WorldClock.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class ClientEvents 
{
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
		
		if (event.isCanceled() || minecraft == null || !event.phase.equals(Phase.END) || minecraft.isGamePaused() || !Minecraft.isGuiEnabled() || !minecraft.isGameFocused()) return;

		WorldClock instance = WorldClock.instance;

		boolean sync = false;
		
		if (WorldClock.clockKeyBinding.isPressed() && !WorldClock.sizeKeyBinding.isPressed())
		{
			WorldClockConfig.clockVisible = !WorldClockConfig.clockVisible;
			sync = true;
		}
		
		if (WorldClock.sizeKeyBinding.isPressed())
		{
			WorldClockConfig.clockSize = Math.max(32, ((int)WorldClockConfig.clockSize << 1) & 0x1FF);
			instance.clock.setSize(WorldClockConfig.clockSize);
			instance.clock2.setSize(WorldClockConfig.clockSize);
			sync = true;
		}
		
		if (sync)
			ConfigHelper.save();
	}
}
