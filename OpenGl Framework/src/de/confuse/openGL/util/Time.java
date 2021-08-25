package de.confuse.openGL.util;

import org.lwjgl.Sys;

public class Time
{
//	public static float timeStarted = System.nanoTime();
	public static float timeStarted = (float) Sys.getTime();

	public static float getTime()
	{
//		return (float) ((float) (System.nanoTime() - timeStarted) * 1E-9);
		return (float) ((float) (Sys.getTime() - timeStarted) * 1E-9);
	}

}
