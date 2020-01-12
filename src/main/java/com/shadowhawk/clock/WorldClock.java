package com.shadowhawk.clock;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;

import com.shadowhawk.clock.analog.AnalogClock;
import com.shadowhawk.clock.digital.DigitalClock;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is a very simple example LiteMod, it draws an analogue clock on the minecraft HUD using
 * a traditional onTick hook supplied by LiteLoader's "Tickable" interface.
 *
 * @author Zachary Cook
 */

//@Mod(modid = WorldClock.MOD_ID, name = WorldClock.MOD_NAME, version = WorldClock.MOD_VERSION, acceptedMinecraftVersions = "[1.12.2]", clientSideOnly = true)
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
		if (instance != null) {
			throw new RuntimeException("Double instantiation of " + MOD_NAME);
		}
		instance = this;
			
		Logger.init(LogManager.getLogger());
		Logger.enableDebug(WorldClockConfig.DEBUG);
		
	    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
	    
	    DistExecutor.runWhenOn(Dist.CLIENT, ()->()-> {
	    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
	    });
	}

	public void preInit(FMLCommonSetupEvent event) 
	{
		Logger.debug("========= P R E  I N I T =========");
		
		ModLoadingContext.get().registerConfig(Type.COMMON, WorldClockConfig.spec);
	}

	public void init(FMLClientSetupEvent event) 
	{
		Logger.debug("========= I N I T =========");

		clock.setSize(WorldClockConfig.clockSize);
		clock2.setSize(WorldClockConfig.clockSize);

		clockKeyBinding = new KeyBinding("key.clock.toggle", GLFW.GLFW_KEY_F12, MOD_NAME);
		
		ClientRegistry.registerKeyBinding(clockKeyBinding);
		
		Input.init();
		
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::onConfigChanged);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::onRenderGameOverlay);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvents::onClientTick);
	}
}