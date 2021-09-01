package de.confuse.openGL;

import static org.lwjgl.opengl.GL11.*;

import java.lang.reflect.InvocationTargetException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import de.confuse.openGL.gui.Gui;
import de.confuse.openGL.gui.GuiScreen;
import de.confuse.openGL.util.MouseUtil;
import de.confuse.openGL.util.Time;

public class Window
{
	private static Window instance;
	/**
	 * Input the class of your main menu in here, the class will be instantiated
	 * automatically using the {@link GuiScreen} constructor, inputting
	 * <code>null</code> as the parent ({@link GuiScreen#getParentScreen()}).
	 */
	public static Class<? extends GuiScreen> default_Screen = null;

	public static float scaleX, scaleY;

	/** The title of the Window */
	private String title;
	private int width, height;
	/**
	 * The input display width and height, use {@link #width} and
	 * {@link #height} for accurate sizes!
	 */
	private int inputWidth, inputHeight;
	private boolean maximized, resizeable;

	private boolean[] mouseButtons;

	/**
	 * TODO: Null safety for the input methods!
	 * 
	 * The currently displayed {@link GuiScreen}, if null a lot of stuff
	 * <strong>can</strong> break
	 */
	private GuiScreen currentScreen;

	private Window(String windowName, int width, int height, boolean fullscreen, boolean resizeable) throws Exception
	{
		instance = this;
		setupWindow(windowName, width, height, fullscreen, resizeable);
		Display.create();
		Keyboard.enableRepeatEvents(true);
		this.mouseButtons = new boolean[Mouse.getButtonCount()];

		// ----------------------------------------
		// --- Last GL Setup before render loop ---
		// ----------------------------------------

		glMatrixMode(GL_PROJECTION);
		glOrtho(0, this.width, this.height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		// -------------------
		// --- FPS counter ---
		// -------------------

		float beginTime = Time.getTime();
		float endTime;
		float deltaT = -1f;

		// ------------------------------------------------
		// --- Initializing the first GuiScreen to show ---
		// ------------------------------------------------

		if (default_Screen == null)
			throw new IllegalArgumentException(
					"The public static default_Screen Field cannot be null! Set it up according to documentation!");

		displayGuiScreen(null);
		while (!Display.isCloseRequested())
		{
			checkGlError();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			checkDisplay();

			// -------------------------------
			// --- Checking hardware input ---
			// -------------------------------

			while (Keyboard.next())
				checkKeyboard();
			while (Mouse.next())
				checkMouse();

			// -------------------
			// --- Render loop ---
			// -------------------

			if (currentScreen != null && deltaT >= 0)
				currentScreen.update(deltaT, Mouse.getX(), MouseUtil.getInvertedMouseY());

//			System.out.println(scaleX + " | " + scaleY);
			// ----------------------------------
			// --- Flushing buffers to Screen ---
			// ----------------------------------

			Display.update();
			endTime = Time.getTime();
			deltaT = endTime - beginTime;
			beginTime = endTime;
		}

		// ----------------------------------
		// --- Exiting the System cleanly ---
		// ----------------------------------

		Display.destroy();
		System.exit(0);
	}

	/**
	 * Checks the keyboard for inputs and updates the {@link #currentScreen}
	 * accordingly
	 */
	private void checkKeyboard()
	{
		if (Keyboard.isCreated())
		{
			int key = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();

			if (key != 0)
			{
				if (Keyboard.isRepeatEvent())
				{
					currentScreen.keyHeld(key, Keyboard.getEventKeyState() ? 1 : 0);
				}
				else
				{
					currentScreen.keyPressed(key, Keyboard.getEventKeyState() ? 1 : 0);
				}

			}

		}

	}

	/**
	 * Checks the Mouse inputs for changes and updates the {@link #currentScreen}
	 * accordingly
	 */
	private void checkMouse()
	{
		if (Mouse.isCreated())
		{
			int button = Mouse.getEventButton();
			if (button >= 0)
			{
				if (button == 0)
					button++;

				boolean stateOld = mouseButtons[button];
				boolean stateNew = Mouse.getEventButtonState();
				mouseButtons[button] = stateNew;
				
				if (stateOld != stateNew)
				{
					currentScreen.mouseClicked(Mouse.getX(), MouseUtil
							.getInvertedMouseY(), button, stateNew ? GuiScreen.STATE_PRESSED : GuiScreen.STATE_RELEASE);
				}
				
			}

			/* Checks if the mouse has moved */
			if (Mouse.getDX() > 0 || Mouse.getDY() > 0)
			{
				if (Mouse.isButtonDown(GuiScreen.BUTTON_LMB) || Mouse.isButtonDown(GuiScreen.BUTTON_RMB))
					currentScreen.mouseDragging(Mouse.getX(), MouseUtil.getInvertedMouseY(),
							Mouse.isButtonDown(0) ? GuiScreen.STATE_PRESSED : GuiScreen.STATE_RELEASE);
			}

			/* Checks if the Mouse has a wheel and if it has if it has been moved */
			if (Mouse.hasWheel())
			{
				int wheel = Mouse.getDWheel();

				if (wheel < 0)
					currentScreen.mouseScrollDecreased(Mouse.getX(), MouseUtil.getInvertedMouseY(), wheel);
				else
					currentScreen.mouseScrollIncreased(Mouse.getX(), MouseUtil.getInvertedMouseY(), wheel);
			}

		}

	}

	private void checkDisplay()
	{
		if (Display.wasResized())
		{
			Window.scaleX = (float) ((float) this.width / (float) this.inputWidth);
			Window.scaleY = (float) ((float) this.height / (float) this.inputHeight);

			this.width = Display.getWidth();
			this.height = Display.getHeight();

			if (Display.wasResized())
			{
//				try
				{
//					Display.setDisplayMode(new DisplayMode(this.width, this.height));
					glViewport(0, 0, this.width, this.height);
					Gui.pushAttrib();
					Gui.pushMatrix();
					glOrtho(0, this.width, this.height, 0, 1, -1);
					Gui.popAttrib();
					Gui.popMatrix();
				}
//				catch (LWJGLException e)
				{
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Checks for OpenGl Errors and prints them if there are any.
	 */
	private void checkGlError()
	{
		int code = glGetError();

		if (code != 0)
		{
			String err = GLU.gluErrorString(code);
			System.out.println("########## GL ERROR ##########");
			System.out.println(code + ": " + err);
		}

	}

	private boolean setupWindow(String windowName, int width, int height, boolean fullscreen, boolean resizeable)
	{
		try
		{
			this.maximized = fullscreen;

			if (this.maximized) // Application starts in full screen
			{
				Display.setFullscreen(true); // updating width and height
				DisplayMode disMode = Display.getDisplayMode();
				this.inputWidth = Math.max(1, disMode.getWidth());
				this.inputHeight = Math.max(1, disMode.getHeight());
			}
			else
			{
				this.inputWidth = width;
				this.inputHeight = height;
				Display.setDisplayMode(new DisplayMode(this.inputWidth, this.inputHeight));
			}

			// Actual Display width
			DisplayMode disMode = Display.getDisplayMode();
			this.width = Math.max(1, disMode.getWidth());
			this.height = Math.max(1, disMode.getHeight());

			this.title = windowName;
			this.resizeable = resizeable;
			Display.setTitle(this.title);
			Display.setResizable(this.resizeable);
			Display.setVSyncEnabled(true);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void displayGuiScreen(GuiScreen guiScreen)
	{
		if (currentScreen != null)
			currentScreen.onGuiClosed();

		if (currentScreen == null || guiScreen == null)
			try
			{ // tricking Java into thinking it's getting a non null object even though it is
				// getting null :P
				guiScreen = (GuiScreen) default_Screen.getConstructors()[0].newInstance(GuiScreen.getEmptyGuiScreen());
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | SecurityException e)
			{
				e.printStackTrace();
				System.exit(1);
			}

		this.currentScreen = guiScreen;
		this.currentScreen.screenRefocused();
	}

	public static Window get(String windowName, int width, int height, boolean fullscreen, boolean resizeable)
	{
		try
		{
			if (Window.instance == null)
				new Window(windowName, width, height, fullscreen, resizeable);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return Window.instance;
	}

	public static Window get(String windowName, int width, int height, boolean resizeable)
	{
		return get(windowName, width, height, false, resizeable);
	}

	public static Window get()
	{
		return get("No name was set!", 300, 300, false, true);
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{ return title; }

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{ this.title = title; }

	/**
	 * @return the width
	 */
	public int getWidth()
	{ return width; }

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{ this.width = width; }

	/**
	 * @return the height
	 */
	public int getHeight()
	{ return height; }

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{ this.height = height; }

	/**
	 * @return the inputWidth
	 */
	public int getInputWidth()
	{ return inputWidth; }

	/**
	 * @param inputWidth the inputWidth to set
	 */
	public void setInputWidth(int inputWidth)
	{ this.inputWidth = inputWidth; }

	/**
	 * @return the inputHeight
	 */
	public int getInputHeight()
	{ return inputHeight; }

	/**
	 * @param inputHeight the inputHeight to set
	 */
	public void setInputHeight(int inputHeight)
	{ this.inputHeight = inputHeight; }

	/**
	 * @return the maximized
	 */
	public boolean isMaximized()
	{ return maximized; }

	/**
	 * @param maximized the maximized to set
	 */
	public void setMaximized(boolean maximized)
	{ this.maximized = maximized; }

	/**
	 * @return the resizeable
	 */
	public boolean isResizeable()
	{ return resizeable; }

	/**
	 * @param resizeable the resizeable to set
	 */
	public void setResizeable(boolean resizeable)
	{ this.resizeable = resizeable; }

}
