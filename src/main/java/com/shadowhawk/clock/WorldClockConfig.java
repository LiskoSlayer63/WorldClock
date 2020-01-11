package com.shadowhawk.clock;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Ignore;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid = WorldClock.MOD_ID)
public class WorldClockConfig 
{
	@Ignore
	public static float clockSize = 64;
	
	@LangKey("gui.clock.config.clock_visible")
	public static boolean clockVisible = true;

	@LangKey("gui.clock.config.world_clock")
	public static boolean worldClock = true;

	@LangKey("gui.clock.config.system_clock")
	public static boolean systemClock = false;

	@LangKey("gui.clock.config.digital_mode")
	public static boolean digitalMode = false;

	@LangKey("gui.clock.config.use_indicator")
	public static boolean useIndicator = false;

	
	@Comment({
	  "Enable debugging mode.",
	  "(for development use only)"
	})
	@Name("Enable Debug")
	@RequiresMcRestart
	public static boolean DEBUG = false;
	
	
	public static void sync()
	{
		ConfigManager.sync(WorldClock.MOD_ID, Config.Type.INSTANCE);
		
		Logger.debug("Configuration changed!");
	}
}