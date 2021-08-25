package de.confuse.openGL.gui;

import de.confuse.openGL.Window;
import de.confuse.openGL.font.FontUtils;

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

	// ------------------------------
	// --- General Render Methods ---
	// ------------------------------

	protected void fillBackground(int color)
	{
		Gui.drawRect(0, 0, window.getWidth(), window.getHeight(), color);
	}

	/**
	 * @return the parentScreen
	 */
	public GuiScreen getParentScreen()
	{ return parentScreen; }

}
