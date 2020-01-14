package com.shadowhawk.clock.config;

import com.shadowhawk.clock.utils.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import static com.shadowhawk.clock.WorldClock.MOD_ID;

/**
 * For configuration settings that change the behaviour of code on the LOGICAL CLIENT.
 * This can be moved to an inner class of ExampleModConfig, but is separate because of personal preference and to keep the code organised
 *
 * @author Cadiboo
 */
final class Config 
{
	final protected ForgeConfigSpec.BooleanValue clockVisible;
    final protected ForgeConfigSpec.BooleanValue worldClock;
    final protected ForgeConfigSpec.BooleanValue systemClock;
    final protected ForgeConfigSpec.BooleanValue digitalMode;
    final protected ForgeConfigSpec.BooleanValue useIndicator;

	Config(final ForgeConfigSpec.Builder builder)
	{
		builder.push("general");
		
		clockVisible = builder
				.comment("Toggles the clock visibility")
        		.translation(MOD_ID + ".config.clock_visible")
        		.define("clockVisible", true);
        worldClock = builder
				.comment("Displays current game time")
        		.translation(MOD_ID + ".config.world_clock")
        		.define("worldClock", true);
        systemClock = builder
				.comment("Displays current system time")
        		.translation(MOD_ID + ".config.system_clock")
        		.define("systemClock", false);
        digitalMode = builder
				.comment("Use a digital clock instead of an analog")
        		.translation(MOD_ID + ".config.digital_mode")
        		.define("digitalMode", false);
        useIndicator = builder
				.comment("Use the AM/PM indicator")
        		.translation(MOD_ID + ".config.use_indicator")
        		.define("useIndicator", false);
        
		builder.pop();
	}
	
	protected void save(ModConfig config)
	{
		if (config == null) return;

		config.getConfigData().set("general.clockVisible", WorldClockConfig.clockVisible);
		config.getConfigData().set("general.worldClock", WorldClockConfig.worldClock);
		config.getConfigData().set("general.systemClock", WorldClockConfig.systemClock);
		config.getConfigData().set("general.digitalMode", WorldClockConfig.digitalMode);
		config.getConfigData().set("general.useIndicator", WorldClockConfig.useIndicator);
    	
		config.save();
		
		Logger.debug("Configuration saved!");
	}
	
	protected void load()
	{
		WorldClockConfig.clockVisible = clockVisible.get();
		WorldClockConfig.worldClock = worldClock.get();
		WorldClockConfig.systemClock = systemClock.get();
		WorldClockConfig.digitalMode = digitalMode.get();
		WorldClockConfig.useIndicator = useIndicator.get();

		Logger.debug("Configuration loaded!");
	}
}
