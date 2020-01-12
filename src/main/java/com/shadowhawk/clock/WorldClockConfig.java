package com.shadowhawk.clock;

import javax.annotation.Nonnull;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

final public class WorldClockConfig 
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec spec = BUILDER.build();
    
    private static ModConfig CONFIG;
    private static General GENERAL;
    
    public static float clockSize = 64;
    
    public static boolean clockVisible = true;
    public static boolean worldClock = true;
    public static boolean systemClock = false;
    public static boolean digitalMode = false;
    public static boolean useIndicator = false;

	public static boolean DEBUG = false;
	

	static
	{
        GENERAL = new General();
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener((ModConfig.ModConfigEvent event) -> {
            CONFIG = event.getConfig();
            
            load();
        });
	}
	
    final public static class General
    {
    	@Nonnull
    	final private ForgeConfigSpec.BooleanValue clockVisible;
        @Nonnull
        final private ForgeConfigSpec.BooleanValue worldClock;
        @Nonnull
        final private ForgeConfigSpec.BooleanValue systemClock;
        @Nonnull
        final private ForgeConfigSpec.BooleanValue digitalMode;
        @Nonnull
        final private ForgeConfigSpec.BooleanValue useIndicator;
        
        public General() {
        	BUILDER.push("general");
    		
            clockVisible = BUILDER
            		.translation("gui.clock.config.clock_visible")
            		.define("clockVisible", true);
            worldClock = BUILDER
            		.translation("gui.clock.config.world_clock")
            		.define("worldClock", true);
            systemClock = BUILDER
            		.translation("gui.clock.config.system_clock")
            		.define("systemClock", false);
            digitalMode = BUILDER
            		.translation("gui.clock.config.digital_mode")
            		.define("digitalMode", false);
            useIndicator = BUILDER
            		.translation("gui.clock.config.use_indicator")
            		.define("useIndicator", false);

            BUILDER.pop();
        }
    }
	
	public static void save()
	{
		if (CONFIG == null) return;

		CONFIG.getConfigData().set("general.clockVisible", clockVisible);
		CONFIG.getConfigData().set("general.worldClock", worldClock);
		CONFIG.getConfigData().set("general.systemClock", systemClock);
		CONFIG.getConfigData().set("general.digitalMode", digitalMode);
		CONFIG.getConfigData().set("general.useIndicator", useIndicator);
    	
		CONFIG.save();
	}
	
	public static void load()
	{
		if (CONFIG == null || GENERAL == null)  return;
		
		clockVisible = GENERAL.clockVisible.get();
		worldClock = GENERAL.clockVisible.get();
		systemClock = GENERAL.clockVisible.get();
		digitalMode = GENERAL.clockVisible.get();
		digitalMode = GENERAL.clockVisible.get();
	}
}
