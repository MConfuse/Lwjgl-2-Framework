package de.confuse.openGL.util;

import org.lwjgl.input.Mouse;

import de.confuse.openGL.Window;

public class MouseUtil
{

	/**
	 * Used retrieve the correctly aligned Y-Axis of the {@link Mouse#getY()} which
	 * is usually flipped around.
	 * 
	 * @return inverted Y-Position
	 */
	public static int getInvertedMouseY()
	{
		return Window.get().getHeight() - Mouse.getY();
	}

}
