package de.confuse.openGL.gui;

import de.confuse.openGL.Window;
import de.confuse.openGL.font.FontUtils;
import de.confuse.openGL.gui.window.CFrame;

public abstract class GuiScreen extends Gui
{
	/*
	 * TODO: Window System for serializing the window clicks and rendering.
	 * 
	 */

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

	public abstract void update(float dt, int mouseX, int mouseY);

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

	public void initGui()
	{}

	public void onGuiClosed()
	{}

	// --------------------
	// --- Window Logic ---
	// --------------------

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
		return ((mouseX * Window.scaleX) >= x && (mouseY * Window.scaleY) >= y && (mouseX * Window.scaleX) < x + width && (mouseY * Window.scaleY) < y + height);
	}

	/**
	 * @return the parentScreen
	 */
	public GuiScreen getParentScreen()
	{ return parentScreen; }

}
