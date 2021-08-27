package de.confuse.openGL.gui.frame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import de.confuse.openGL.gui.Gui;
import de.confuse.openGL.gui.GuiScreen;

public class CFrame extends Gui
{
	protected final GuiScreen guiScreen;
	private final List<CElement> panes = new ArrayList<CElement>();

	public CFrame(GuiScreen guiScreen, List<CElement> panes)
	{
		this.guiScreen = guiScreen;
		if (panes != null)
			this.panes.addAll(panes);
	}

	public CFrame(GuiScreen guiScreen, CElement... cElements)
	{
		this(guiScreen, Arrays.asList(cElements));
	}

	// -------------------
	// --- Frame Logic ---
	// -------------------

	public boolean drawElementsToScreen(float dt, int mouseX, int mouseY)
	{
		for (CElement element : panes)
			element.update(dt, mouseX, mouseY);

		return true;
	}

	// - Interface to forward Input to the Elements -

	/**
	 * Fired every time a key is pressed/released. With the given key int you can
	 * check LWJGLs {@link Keyboard} class if it matches your desired key.
	 * 
	 * @param key   The pressed keys key code
	 * @param state The state of the key
	 * @see GuiScreen#STATE_PRESSED
	 * @see #STATE_RELEASE
	 */
	public void keyPressed(int key, int state)
	{
		for (CElement element : panes)
			element.keyPressed(key, state);
	}

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
	 * @see #STATE_PRESSED
	 * @see #STATE_RELEASE
	 */
	public void keyHeld(int key, int state)
	{
		for (CElement element : panes)
			element.keyHeld(key, state);
	}

	/**
	 * Fired when, and only when, a mouse button was pressed or released.<br>
	 * See {@link #mouseDragging(int, int, int)} in case you need a sort of repeat
	 * event for the mouse.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param button The event button
	 * @param state  The current state of the button
	 * @see #BUTTON_LMB
	 * @see #BUTTON_RMB
	 * @see #BUTTON_MMB
	 * @see #STATE_PRESSED
	 * @see #STATE_RELEASE
	 */
	public void mouseClicked(int mouseX, int mouseY, int button, int state)
	{
		for (CElement element : panes)
			if (element.isOverElement(mouseX, mouseY))
				element.mouseClicked(mouseX, mouseY, button, state);
	}

	/**
	 * Fired when either {@link #BUTTON_LMB} or {@link #BUTTON_RMB} is pressed and
	 * the mouse is moving (The delta X or Y, compared to last frame, is greater
	 * than 0).
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 * @param button The event button
	 * @see #BUTTON_LMB
	 * @see #BUTTON_RMB
	 * @see #BUTTON_MMB
	 * @see #STATE_PRESSED
	 * @see #STATE_RELEASE
	 */
	public void mouseDragging(int mouseX, int mouseY, int button)
	{
		for (CElement element : panes)
			if (element.isOverElement(mouseX, mouseY))
				element.mouseDragging(mouseX, mouseY, button);
	}

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
	{
		for (CElement element : panes)
			element.mouseScrollIncreased(mouseX, mouseY, change);
	}

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
	{
		for (CElement element : panes)
			element.mouseScrollIncreased(mouseX, mouseY, change);
	}

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
	{
		for (CElement element : panes)
			element.mouseScrollDecreased(mouseX, mouseY, change);
	}

	public void updateSizes()
	{
		for (CElement element : panes)
			element.updateWindowSize();
	}

	public void addElement(CElement element)
	{
		this.panes.add(element);
	}

}
