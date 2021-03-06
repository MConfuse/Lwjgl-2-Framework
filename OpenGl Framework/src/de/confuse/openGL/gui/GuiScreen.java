package de.confuse.openGL.gui;

import org.lwjgl.input.Keyboard;

import de.confuse.openGL.Window;
import de.confuse.openGL.font.FontUtils;
import de.confuse.openGL.gui.frame.CElement;
import de.confuse.openGL.gui.frame.CFrame;

public abstract class GuiScreen extends Gui
{
	/** Integer value for when the mouse button is released */
	public static final int STATE_RELEASE = 0;
	/** Integer value for when the mouse button is clicked */
	public static final int STATE_PRESSED = 1;

	/** Integer value equal to the left mouse button */
	public static final int BUTTON_LMB = 0;
	/** Integer value equal to the right mouse button */
	public static final int BUTTON_RMB = 1;
	/** Integer value equal to the middle mouse button */
	public static final int BUTTON_MMB = 2;

	// --- Pane framework ---
	protected final CFrame frame = new CFrame(this);

	protected final FontUtils arial = FontUtils.ARIAL;
	protected final Window window = Window.get();
	protected final GuiScreen parentScreen;

	public GuiScreen(GuiScreen parent)
	{
		this.parentScreen = parent;

		initGui();
	}

	/**
	 * Used to trick Java to get the {@link Window#default_Screen} to work :P.
	 * 
	 * @return null
	 */
	public static GuiScreen getEmptyGuiScreen()
	{ return null; }

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
	 * Called any time this screen element is rendered again. This is only truly
	 * reliable when using the {@link Window#displayGuiScreen(GuiScreen)} method as
	 * you might forget to call the method in case of a custom system.<br>
	 * <br>
	 * I personally (currently) don't know how this might be useful but I
	 * implemented it anyway just in case :).
	 */
	public abstract void screenRefocused();

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
	 * Called by the {@link GuiScreen} constructor. You can use this method to
	 * create objects using enormous nested loops and hide it somewhere at the
	 * bottom of the Class where no one has to see it ever again :P.
	 */
	public void initGui()
	{}

	/**
	 * Called by {@link Window#displayGuiScreen(GuiScreen)} when closing a non null
	 * {@link GuiScreen}. Basically, use this method to save content to disk or
	 * finalizing executions. After this method has been called the
	 * {@link GuiScreen} will no longer get any updated or calls to the overridden
	 * methods.<br>
	 * <strong>Note:</strong> The {@link GuiScreen} instance can still receive
	 * updates through the new {@link GuiScreen} put into the
	 * {@link Window#displayGuiScreen(GuiScreen)} method, this can, for example, be
	 * used to keep a background rendered.<br>
	 * This can be done by setting the {@link #parentScreen} of the new
	 * {@link GuiScreen} put into the {@link Window#displayGuiScreen(GuiScreen)} to
	 * this instance, and then calling the wanted functions through, for example,
	 * {@link #parentScreen#update(float, int, int)}.
	 */
	public void onGuiClosed()
	{}

	// --------------------
	// --- Screen Logic ---
	// --------------------

	public void addElementToFrame(CElement element)
	{
		this.frame.addElement(element);
	}

	// ------------------------------
	// --- General Render Methods ---
	// ------------------------------

	protected void fillBackground(int color)
	{
		Gui.drawRect(0, 0, window.getInputWidth(), window.getInputHeight(), color);
	}

	// ----------------------
	// --- Static Helpers ---
	// ----------------------

	/**
	 * Easy method for streamlining the question if the mouse is inside the bounds
	 * of your Object.
	 * 
	 * @param mouseX X-Position of the mouse
	 * @param mouseY Y-Position of the mouse
	 * @param x      Left most X-Position of the Object
	 * @param width  The width of this Object (x + width = total width)
	 * @param y      Top most Y-Position of the Object
	 * @param height The height of this Object (y + height = total height)
	 * @return true if the mouse is inside the specified bounds, false if it's not
	 * @see #mouseOver(int, int, float, float, float, float)
	 */
	public static boolean mouseOver(int mouseX, int mouseY, int x, int width, int y, int height)
	{
		return mouseOver(mouseX, mouseY, (float) x, (float) width, (float) y, (float) height);
	}

	/**
	 * Easy method for streamlining the question if the mouse is inside the bounds
	 * of your Object.
	 * 
	 * @param mouseX X-Position of the mouse
	 * @param mouseY Y-Position of the mouse
	 * @param x      Left most X-Position of the Object
	 * @param width  The width of this Object (x + width = total width)
	 * @param y      Top most Y-Position of the Object
	 * @param height The height of this Object (y + height = total height)
	 * @return true if the mouse is inside the specified bounds, false if it's not
	 */
	public static boolean mouseOver(int mouseX, int mouseY, float x, float width, float y, float height)
	{
		return ((mouseX * Window.scaleX) >= x && (mouseY * Window.scaleY) >= y && (mouseX * Window.scaleX) < x + width
				&& (mouseY * Window.scaleY) < y + height);
	}

	/**
	 * @return the parentScreen
	 */
	public GuiScreen getParentScreen()
	{ return parentScreen; }

}
