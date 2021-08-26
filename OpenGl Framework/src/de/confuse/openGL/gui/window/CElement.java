package de.confuse.openGL.gui.window;

import de.confuse.openGL.gui.Gui;

public class CElement extends Gui
{
	/** The Window name */
	private final String name;
	private final boolean automaticResize;

	/** True when the window is being relocated */
	private boolean dragging;
	/**
	 */
	private boolean expanded;

	/* --- Window size --- */
	private int x, y;
	private int width, height;

	/* --- Dragging offsets --- */
	private int draggingXOff, draggingYOff;

	public CElement(final String name, int x, int y, int width, int height)
	{
		this.name = name;
		this.automaticResize = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public CElement(final String name, int x, int y)
	{
		this.name = name;
		this.automaticResize = true;
		this.x = x;
		this.y = y;
	}

	/**
	 * Does nothing if {@link #automaticResize} is not true!
	 */
	public void updateWindowSize()
	{
		if (!automaticResize)
			return;

	}

}
