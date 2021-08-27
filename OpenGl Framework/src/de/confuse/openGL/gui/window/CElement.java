package de.confuse.openGL.gui.window;

import de.confuse.openGL.gui.Gui;

public abstract class CElement extends Gui
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
	 * Fired every time the User presses/releases a key. This usually is a better
	 * implementation than checking {@link KeyListener#isKeyPressed(int)} for each
	 * key you are interested in every tick.
	 * 
	 * @param key   The pressed keys key code
	 * @param state The state of the key, 0 = released, 1 = pressed
	 */
	public abstract void keyPressed(int key, int state);

	public abstract void keyHeld(int key, int state);

	public abstract void mouseClicked(int mouseX, int mouseY, int button, int state);

	public abstract void screenRefocused();

	public void mouseDragging(int mouseX, int mouseY, int button)
	{}

	public void mouseScrollIncreased(int changeX)
	{}
	
	public void mouseScrollDecreased(int changeX)
	{}

	public void initElement()
	{}

	public boolean overElement(int mouseX, int mouseY)
	{
		return (mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + height);
	}

	/**
	 * Does nothing if {@link #automaticResize} is not true!
	 */
	public void updateWindowSize()
	{
		if (!automaticResize)
			return;

	}

	public boolean isDragging()
	{ return dragging; }

	public void setDragging(boolean dragging)
	{ this.dragging = dragging; }

	public boolean isExpanded()
	{ return expanded; }

	public void setExpanded(boolean expanded)
	{ this.expanded = expanded; }

	public int getX()
	{ return x; }

	public void setX(int x)
	{ this.x = x; }

	public int getY()
	{ return y; }

	public void setY(int y)
	{ this.y = y; }

	public int getWidth()
	{ return width; }

	public void setWidth(int width)
	{ this.width = width; }

	public int getHeight()
	{ return height; }

	public void setHeight(int height)
	{ this.height = height; }

	public int getDraggingXOff()
	{ return draggingXOff; }

	public void setDraggingXOff(int draggingXOff)
	{ this.draggingXOff = draggingXOff; }

	public int getDraggingYOff()
	{ return draggingYOff; }

	public void setDraggingYOff(int draggingYOff)
	{ this.draggingYOff = draggingYOff; }

	public String getName()
	{ return name; }

	public boolean isAutomaticResize()
	{ return automaticResize; }

}
