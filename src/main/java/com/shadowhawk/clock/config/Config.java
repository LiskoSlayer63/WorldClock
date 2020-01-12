package com.shadowhawk.clock.config;

import com.shadowhawk.clock.utils.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

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
				.comment(I18n.format("gui.clock.config.clock_visible.tooltip"))
        		.translation("gui.clock.config.clock_visible")
        		.define("clockVisible", true);
        worldClock = builder
				.comment(I18n.format("gui.clock.config.world_clock.tooltip"))
        		.translation("gui.clock.config.world_clock")
        		.define("worldClock", true);
        systemClock = builder
				.comment(I18n.format("gui.clock.config.system_clock.tooltip"))
        		.translation("gui.clock.config.system_clock")
        		.define("systemClock", false);
        digitalMode = builder
				.comment(I18n.format("gui.clock.config.digital_mode.tooltip"))
        		.translation("gui.clock.config.digital_mode")
        		.define("digitalMode", false);
        useIndicator = builder
				.comment(I18n.format("gui.clock.config.use_indicator.tooltip"))
        		.translation("gui.clock.config.use_indicator")
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
