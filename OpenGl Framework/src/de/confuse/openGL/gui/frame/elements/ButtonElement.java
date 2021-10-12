package de.confuse.openGL.gui.frame.elements;

import static de.confuse.openGL.gui.GuiScreen.BUTTON_LMB;

import org.lwjgl.input.Mouse;

import de.confuse.openGL.font.FontUtils;
import de.confuse.openGL.gui.GuiScreen;
import de.confuse.openGL.gui.frame.CElement;

public class ButtonElement extends CElement
{
	// - General -
	private int textColor = 0xaaaaaa;

	// - Indicator -
	private int clickedColor = 0xFF99034B;
	private int hoveredColor = 0xFFBD025A;
	private int borderColor = 0xFF000000;

	/** True if the text should be drawn with shadows */
	private boolean shadows;

	public ButtonElement(String name, int x, int y, int width, int height, FontUtils font, boolean shadows)
	{
		super(name, x, y, width, height);
		this.font = font;
		this.shadows = shadows;
	}

	public ButtonElement(String name, int x, int y, FontUtils font, boolean shadows)
	{
		this(name, x, y, (int) font.getWidth(name) + 10, (int) font.getHeight(), font, shadows);
	}

	@Override
	public void update(float dt, int mouseX, int mouseY)
	{
		drawHoveredBox(mouseX, mouseY);

		if (shadows)
			font.drawCenteredStringWithShadow(getName(), x + width / 2, y, textColor);
		else
			font.drawCenteredString(getName(), x + width / 2, y, textColor);
	}

	@Override
	public void keyPressed(int key, int state)
	{}

	@Override
	public void keyHeld(int key, int state)
	{}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button, int state)
	{
		if (isOverElement(mouseX, mouseY) && state == GuiScreen.STATE_PRESSED)
			onPress(mouseX, mouseY);
		else if (isOverElement(mouseX, mouseY) && state == GuiScreen.STATE_RELEASE)
			onRelease(mouseX, mouseY);
	}

	@Override
	public int getMaxWidth()
	{ return (int) (font.getWidth(name) + 10); }

	@Override
	public int getMaxHeight()
	{ return (int) font.getHeight(); }

	/**
	 * Draws the box behind the actual text and state indicator. Override this to
	 * change the style of this render element.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 */
	public void drawHoveredBox(int mouseX, int mouseY)
	{
		if (isOverElement(mouseX, mouseY) && Mouse.isButtonDown(BUTTON_LMB))
		{
			fillBackground(clickedColor);
			drawRect(x, y, x + width, y + 2, borderColor); // top
			drawRect(x, y, x + 2, y + height, borderColor); // left
			drawRect(x + width - 2, y, x + width, y + height, borderColor); // right
			drawRect(x, y + height - 2, x + width, y + height, borderColor); // bottom
			this.shadows = true;
		}
		else if (isOverElement(mouseX, mouseY))
		{
			fillBackground(hoveredColor);
			drawRect(x, y, x + width, y + 2, borderColor); // top
			drawRect(x, y, x + 2, y + height, borderColor); // left
			drawRect(x + width - 2, y, x + width, y + height, borderColor); // right
			drawRect(x, y + height - 2, x + width, y + height, borderColor); // bottom
			this.shadows = true;
		}
		else
			this.shadows = false;
	}

	public void applyColors(int textColor, int clickedColor, int hoveredColor, int borderColor)
	{
		this.clickedColor = clickedColor;
		this.hoveredColor = hoveredColor;
		this.borderColor = borderColor;
	}

	/**
	 * Override this for the click update.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 */
	public void onPress(int mouseX, int mouseY)
	{}

	/**
	 * Override this for the release update.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 */
	public void onRelease(int mouseX, int mouseY)
	{}

}
