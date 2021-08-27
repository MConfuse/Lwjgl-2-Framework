package de.confuse.openGL.gui.frame;

import org.lwjgl.input.Keyboard;

import de.confuse.openGL.Window;
import de.confuse.openGL.gui.Gui;
import de.confuse.openGL.gui.GuiScreen;

public abstract class CElement extends Gui
{
	protected final Window window = Window.get();

	/** The Window name */
	protected final String name;
	protected final boolean automaticResize;

	/* --- Window size --- */
	protected int x, y;
	protected int width, height;

	public CElement(final String name, int x, int y, int width, int height)
	{
		this.name = name;
		this.automaticResize = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		initElement();
	}

	public CElement(final String name, int x, int y)
	{
		this.name = name;
		this.automaticResize = true;
		this.x = x;
		this.y = y;

		initElement();
	}

	/**
	 * Called by the render loop whenever this {@link GuiScreen} is the
	 * {@link Window}s current {@link GuiScreen}.
	 * 
	 * @param dt     The time between the last and this frame
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @see Window#displayGuiScreen(GuiScreen)
	 */
	public abstract void update(float dt, int mouseX, int mouseY);

	/**
	 * Fired every time a key is pressed/released. With the given key int you can
	 * check LWJGLs {@link Keyboard} class if it matches your desired key.
	 * 
	 * @param key   The pressed keys key code
	 * @param state The state of the key
	 * @see GuiScreen#STATE_PRESSED
	 * @see GuiScreen#STATE_RELEASE
	 */
	public abstract void keyPressed(int key, int state);

	/**
	 * If repeat events are enabled this method will be fired everytime a key was
	 * held long enough to trigger the repeat events.<br>
	 * <br>
	 * <strong>Note:</strong> This method will be fired each render cycle until the
	 * User releases the key so be careful using this method as it can cause
	 * performance issues.
	 * 
	 * @param key   The pressed keys key code
	 * @param state The state of the key, 0 = released, 1 = pressed
	 * @see Keyboard#areRepeatEventsEnabled()
	 * @see Keyboard#enableRepeatEvents(boolean)
	 * @see GuiScreen#STATE_PRESSED
	 * @see GuiScreen#STATE_RELEASE
	 */
	public abstract void keyHeld(int key, int state);

	/**
	 * Fired when, and only when, a mouse button was pressed or released.<br>
	 * See {@link #mouseDragging(int, int, int)} in case you need a sort of repeat
	 * event for the mouse.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param button The event button
	 * @param state  The current state of the button
	 * @see GuiScreen#BUTTON_LMB
	 * @see GuiScreen#BUTTON_RMB
	 * @see GuiScreen#BUTTON_MMB
	 * @see GuiScreen#STATE_PRESSED
	 * @see GuiScreen#STATE_RELEASE
	 */
	public abstract void mouseClicked(int mouseX, int mouseY, int button, int state);

	/**
	 * Gets called when the element has been created and the
	 * {@link #automaticResize} is enabled.
	 * 
	 * @return the {@link CElement}s width
	 * @see #updateWindowSize()
	 */
	protected abstract int getMaxWidth();

	/**
	 * Gets called when the element has been created and the
	 * {@link #automaticResize} is enabled.
	 * 
	 * @return the {@link CElement}s height
	 * @see #updateWindowSize()
	 */
	protected abstract int getMaxHeight();

	/**
	 * Fired when either {@link #BUTTON_LMB} or {@link #BUTTON_RMB} is pressed and
	 * the mouse is moving (The delta X or Y, compared to last frame, is greater
	 * than 0).
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param button The event button
	 * @see GuiScreen#BUTTON_LMB
	 * @see GuiScreen#BUTTON_RMB
	 * @see GuiScreen#BUTTON_MMB
	 * @see GuiScreen#STATE_PRESSED
	 * @see GuiScreen#STATE_RELEASE
	 */
	public void mouseDragging(int mouseX, int mouseY, int button)
	{}

	/**
	 * This method combines both the {@link #mouseScrollIncreased(int, int, int)}
	 * and the {@link #mouseScrollDecreased(int, int, int)}. See those methods for a
	 * detailed explanation on the <code>change</code> value.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param change The changed Y-Offset of the mouse wheel
	 * @see #mouseScrollIncreased(int, int, int)
	 * @see #mouseScrollDecreased(int, int, int)
	 */
	public void mouseScrollChange(int mouseX, int mouseY, int change)
	{}

	/**
	 * Fired when the mouse wheel has been used. This method will always give out
	 * positive values.<br>
	 * Basically, for a website this method would always then be fired, when the
	 * user scrolls from the top to the bottom of the website, increasing the scroll
	 * offset.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param change The changed Y-Offset of the mouse wheel
	 * @see #mouseScrollChange(int, int, int)
	 */
	public void mouseScrollIncreased(int mouseX, int mouseY, int change)
	{}

	/**
	 * Fired when the mouse wheel has been used. This method will always give out
	 * negative values.<br>
	 * Basically, for a website this method would always then be fired, when the
	 * user scrolls from the bottom to the top of the website, decreasing the scroll
	 * offset.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param change The changed Y-Offset of the mouse wheel
	 * @see #mouseScrollChange(int, int, int)
	 */
	public void mouseScrollDecreased(int mouseX, int mouseY, int change)
	{}

	/**
	 * Called by the {@link CElement} constructor. You can use this method to
	 * create objects using enormous nested loops and hide it somewhere at the
	 * bottom of the Class where no one has to see it ever again :P.
	 */
	public void initElement()
	{}

	public boolean isOverElement(int mouseX, int mouseY)
	{
		return (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height);
	}

	/**
	 * <strong>NOTE:</strong> Does nothing if {@link #automaticResize} is not
	 * true!<br>
	 * <br>
	 * Calls both the {@link #getMaxWidth()} and {@link #getMaxHeight()} methods to
	 * automatically set the width and height of the Element.
	 */
	public void updateWindowSize()
	{
//		int padding = 10;

		if (!automaticResize)
			return;

		this.width = getMaxWidth();
		this.height = getMaxHeight();
	}

	// ------------------------------
	// --- General Render Methods ---
	// ------------------------------

	protected void fillBackground(int color)
	{
		Gui.drawRect(0, 0, window.getInputWidth(), window.getInputHeight(), color);
	}

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

	public String getName()
	{ return name; }

	public boolean isAutomaticResize()
	{ return automaticResize; }

}
