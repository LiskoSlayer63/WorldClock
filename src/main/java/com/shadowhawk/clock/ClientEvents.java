package com.shadowhawk.clock;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
	public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		ForgeModWorldClock worldclock = ForgeModWorldClock.instance;
		
		worldclock.render(minecraft);
	}
}
