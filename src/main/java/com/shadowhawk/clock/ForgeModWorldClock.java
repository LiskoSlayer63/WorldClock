package com.shadowhawk.clock;

import org.lwjgl.input.Keyboard;

import com.shadowhawk.clock.analog.AnalogClock;
import com.shadowhawk.clock.digital.DigitalClock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This is a very simple example LiteMod, it draws an analogue clock on the minecraft HUD using
 * a traditional onTick hook supplied by LiteLoader's "Tickable" interface.
 *
 * @author Zachary Cook
 */

@Mod(modid = ForgeModWorldClock.MOD_ID, name = ForgeModWorldClock.MOD_NAME, version = ForgeModWorldClock.MOD_VERSION, acceptedMinecraftVersions = "[1.12.2]")
@Mod.EventBusSubscriber(modid = ForgeModWorldClock.MOD_ID)
public class ForgeModWorldClock
{
	@Mod.Instance(ForgeModWorldClock.MOD_ID)
	public static ForgeModWorldClock instance;
	
	public static final String MOD_ID = "worldclock";
	public static final String MOD_NAME = "World Clock";
	public static final String MOD_VERSION = "1.2.1 Beta";

	
	  /**
	 * This is a keybinding that we will register with the game and use to toggle the clock
	 * 
	 * Notice that we specify the key name as an *unlocalised* string. The localisation is provided from the included resource file
	 */

	protected KeyBinding clockKeyBinding = new KeyBinding("key.clock.toggle", Keyboard.KEY_F12, "key.categories.worldclock");
	
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
	public ForgeModWorldClock()
	{
		if (instance != null) {
			Logger.error("Attempted to instantiate " + MOD_NAME + " twice.");
		    throw new RuntimeException("Double instantiation of " + MOD_NAME);
		} else {
		    instance = this;
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		Logger.init(event.getModLog());
		Logger.enableDebug(ClockConfig.DEBUG);
		Logger.debug("========= P R E  I N I T =========");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{
		this.clock.setSize(ClockConfig.clockSize);
		this.clock2.setSize(ClockConfig.clockSize);
		this.clock.setVisible(ClockConfig.clockVisible);
		this.clock2.setVisible(ClockConfig.clockVisible);
		
		Logger.debug("========= I N I T =========");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		Logger.debug("========= P O S T  I N I T =========");
	}
	
	public void render(Minecraft minecraft)
	{
		if (!minecraft.isGamePaused() && minecraft.currentScreen == null && Minecraft.isGuiEnabled())
		{
			if (clockKeyBinding.isPressed())
			{
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
				{
					ClockConfig.clockSize = ((int)ClockConfig.clockSize << 1) & 0x1FF;
					this.clock.setSize(ClockConfig.clockSize);
					this.clock2.setSize(ClockConfig.clockSize);
					ClockConfig.clockSize = this.clock.getSize();
				}
				else
					ClockConfig.clockVisible = !ClockConfig.clockVisible;
				
				ClockConfig.sync();
			}
			
			// Render the clock			
			if (ClockConfig.clockVisible)
			{
				if(!ClockConfig.digitalMode)
					this.clock.render(minecraft);
				else
					this.clock2.render(minecraft);
			}
		}
	}
}