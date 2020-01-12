package com.shadowhawk.clock;

import java.util.Arrays;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input 
{
    // The input callbacks 
    public static GLFWKeyCallback keyCallback;
    
    // Stores the states of the keys in the current and last frames
    public static boolean[] keys = new boolean[512];
    public static boolean[] lastKeys = new boolean[512];
    
    static void init()
    {
    // Setup the keyboard callback
	keyCallback = new GLFWKeyCallback() {

	    @Override
	    public void invoke(long window, int keycode, int scancode, int action, int mods) {
		// If the key was pressed
		if(action == GLFW.GLFW_PRESS)
			keys[keycode] = true;
		// If the key was released
		else if (action == GLFW.GLFW_RELEASE)
			keys[keycode] = false;
	    }
	    
	};
	
	// Fill the arrays with default info
	Arrays.fill(keys, false);
	Arrays.fill(lastKeys, false);
    }
    
    /**
     * Updates the input key states
     */
    public static void update() {
	lastKeys = keys;
    }
    
    /**
     * Releases memory used by GLFW Callbacks
     */
    public static void dispose() {
	keyCallback.free();
    }
    
    /**
     * Returns whether or not the specified key is currently down
     * @param keycode The key to check
     * @return true if the key is down, false if the key is up
     */
    public static boolean isKeyPressed(int keycode) {    
	return keys[keycode];
    }
    
    /**
     * Returns whether or not the specified key was pressed between the last and current frame
     * @param keycode The key to check
     * @return true if the key was just pressed, false otherwise
     */
    public static boolean isKeyJustPressed(int keycode) {
	return keys[keycode] && !lastKeys[keycode];
    }
    
    /**
     * Returns whether or not the specified key was released between the last and current frame
     * @param keycode The key to check
     * @return true if the key was just released, false otherwise
     */
    public static boolean isKeyJustReleased(int keycode) {
	return !keys[keycode] && lastKeys[keycode];
    }
}