package com.shadowhawk.clock;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Ignore;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = ForgeModWorldClock.MOD_ID)
public class ClockConfig 
{
	@Ignore
	public static float clockSize = 64;
	
	@Comment({
		  "Toggles the clock visibility."
	})
	@Name("Clock Visible")
	public static boolean clockVisible = true;
	
	@Comment({
		  "Displays current game time."
	})
	@Name("World Clock")
	public static boolean worldClock = true;
	
	@Comment({
		  "Displays current system time."
	})
	@Name("System Clock")
	public static boolean systemClock = false;
	
	@Comment({
		  "Use a digital clock instead of an analog."
	})
	@Name("Digital Mode")
	public static boolean digitalMode = false;
	
	@Comment({
		  "Use the AM/PM indicator."
	})
	@Name("AM/PM Indicator")
	public static boolean useIndicator = false;

	
	@Comment({
	  "Enable debugging mode.",
	  "(for development use only)"
	})
	@Name("Enable Debug")
	@RequiresMcRestart
	public static boolean DEBUG = true;
	
	
	public static void sync()
	{
		ConfigManager.sync(ForgeModWorldClock.MOD_ID, Config.Type.INSTANCE);
		
		Logger.debug("Configuration changed!");
	}
}