package com.shadowhawk.clock;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

import com.shadowhawk.clock.analog.AnalogClock;
import com.shadowhawk.clock.config.ConfigHelper;
import com.shadowhawk.clock.config.WorldClockConfig;
import com.shadowhawk.clock.digital.DigitalClock;
import com.shadowhawk.clock.utils.Logger;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is a very simple example LiteMod, it draws an analogue clock on the minecraft HUD using
 * a traditional onTick hook supplied by LiteLoader's "Tickable" interface.
 *
 * @author Zachary Cook
 */

@Mod(WorldClock.MOD_ID)
@Mod.EventBusSubscriber(modid = WorldClock.MOD_ID)
public class WorldClock
{

	public static WorldClock instance;
	
	public static final String MOD_ID = "worldclock";
	public static final String MOD_NAME = "World Clock";
	public static final String MOD_VERSION = "1.2.1 Beta";

	/**
	* This is a keybinding that we will register with the game and use to toggle the clock
	* 
	* Notice that we specify the key name as an *unlocalised* string. The localisation is provided from the included resource file
	*/
	public static KeyBinding clockKeyBinding;
	public static KeyBinding sizeKeyBinding;

	/**
	 * This is our instance of Clock which we will draw every tick
	 */
	public AnalogClock clock = new AnalogClock(10, 10);
	public DigitalClock clock2 = new DigitalClock(10, 10);
	
	/**
	 * Default constructor. All LiteMods must have a default constructor. In general you should do very little
	 * in the mod constructor EXCEPT for initialising any non-game-interfacing components or performing
	 * sanity checking prior to initialisation
	 */
	public WorldClock()
	{
		if (instance != null)
			throw new RuntimeException("Double instantiation of " + MOD_NAME);
		instance = this;
			
		Logger.init(LogManager.getLogger(MOD_ID));
		Logger.enableDebug(WorldClockConfig.DEBUG);
	    
	    ModLoadingContext.get().registerConfig(Type.COMMON, ConfigHelper.SPEC);
	    
	    DistExecutor.runWhenOn(Dist.CLIENT, ()->()-> {
	    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
	    });
	}

	public void init(FMLClientSetupEvent event) 
	{
		Logger.debug("========= I N I T =========");

		clock.setSize(WorldClockConfig.clockSize);
		clock2.setSize(WorldClockConfig.clockSize);
		
		clockKeyBinding = new KeyBinding(I18n.format("key.clock.toggle"), GLFW.GLFW_KEY_F12, MOD_NAME);
		sizeKeyBinding = new KeyBinding(I18n.format("key.clock.size"), GLFW.GLFW_KEY_UNKNOWN, MOD_NAME);

		ClientRegistry.registerKeyBinding(sizeKeyBinding);
		ClientRegistry.registerKeyBinding(clockKeyBinding);
	}
}