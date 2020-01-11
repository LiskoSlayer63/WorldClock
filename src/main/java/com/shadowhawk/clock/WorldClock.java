package com.shadowhawk.clock;

import org.lwjgl.input.Keyboard;

import com.shadowhawk.clock.analog.AnalogClock;
import com.shadowhawk.clock.digital.DigitalClock;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This is a very simple example LiteMod, it draws an analogue clock on the minecraft HUD using
 * a traditional onTick hook supplied by LiteLoader's "Tickable" interface.
 *
 * @author Zachary Cook
 */

@Mod(modid = WorldClock.MOD_ID, name = WorldClock.MOD_NAME, version = WorldClock.MOD_VERSION, acceptedMinecraftVersions = "[1.12.2]", clientSideOnly = true)
@Mod.EventBusSubscriber(modid = WorldClock.MOD_ID)
public class WorldClock
{
	@Mod.Instance(WorldClock.MOD_ID)
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
		} else {
			instance = this;
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		Logger.init(event.getModLog());
		Logger.enableDebug(WorldClockConfig.DEBUG);
		Logger.debug("========= P R E  I N I T =========");

		clockKeyBinding = new KeyBinding("key.clock.toggle", Keyboard.KEY_F12, MOD_NAME);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{
		Logger.debug("========= I N I T =========");

		clock.setSize(WorldClockConfig.clockSize);
		clock2.setSize(WorldClockConfig.clockSize);
		
		ClientRegistry.registerKeyBinding(clockKeyBinding);
	}
}