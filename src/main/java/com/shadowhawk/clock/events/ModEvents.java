package com.shadowhawk.clock.events;

import com.shadowhawk.clock.WorldClock;
import com.shadowhawk.clock.config.ConfigHelper;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid = WorldClock.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents 
{
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event)
	{
		ModConfig config = event.getConfig();
		
		if (config.getSpec() == ConfigHelper.SPEC)
			ConfigHelper.load(config);
	}
}