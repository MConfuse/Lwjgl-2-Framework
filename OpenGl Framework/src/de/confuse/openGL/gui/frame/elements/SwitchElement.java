package de.confuse.openGL.gui.frame.elements;

import org.lwjgl.input.Mouse;

import de.confuse.openGL.font.FontUtils;
import de.confuse.openGL.gui.GuiScreen;
import de.confuse.openGL.gui.frame.CElement;
import static de.confuse.openGL.gui.GuiScreen.*;

public class SwitchElement extends CElement
{
	private final int offset = 8;

	private boolean enabled;

	// - General -
	private int textColor = 0xaaaaaa;
	private int backgroundColor = 0xFF353535;

	// - Indicator -
	private int clickedColor = 0xFF99034B;
	private int hoveredColor = 0xFFBD025A;
	private int indicatorBorderColor = 0xFF000000;
	private int indicatorBackgroundColor = backgroundColor;
	private int indicatorColor = 0xFFBD025A;

	/** True if the text should be drawn with shadows */
	private boolean shadows;

	// - Values -
	private double radius = 0;

	public SwitchElement(String name, int x, int y, int width, int height, FontUtils font, boolean shadows)
	{
		super(name, x, y, width, height);
		this.font = font;
		this.radius = (this.height - 6) / 2;
		this.width = (int) (this.width + this.radius * 2 + this.offset + 4);
	}

	public SwitchElement(String name, int x, int y, FontUtils font, boolean shadows)
	{
		this(name, x, y, (int) font.getWidth(name) + 2, (int) font.getHeight(), font, shadows);
	}

	@Override
	public void update(float dt, int mouseX, int mouseY)
	{
		drawHoveredBox(mouseX, mouseY);
		drawIndicator(mouseX, mouseY);
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
		if (state == GuiScreen.STATE_RELEASE)
		{
			this.enabled = !this.enabled;

			if (this.enabled)
				onEnable();
			else
				onDisable();
		}

	}

	@Override
	public int getMaxWidth()
	{ return (int) (this.font.getWidth(name) + 2 + this.radius * 2 + this.offset + 4); }

	@Override
	public int getMaxHeight()
	{ return (int) font.getHeight(); }

	/**
	 * Override this to add code to execute for when the element has been Enabled.
	 */
	public void onEnable()
	{}

	/**
	 * Override this to add code to execute for when the element has been Disabled.
	 */
	public void onDisable()
	{}

	@Override
	public void fillBackground(int color)
	{
		super.fillBackground(color);
	}

	public void applyColors(int textColor, int backgroundColor, int clickedColor, int hoveredColor, int indicatorBorder,
			int indicatorBackroundColor, int indicatorColor)
	{
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.clickedColor = clickedColor;
		this.hoveredColor = hoveredColor;
		this.indicatorBorderColor = indicatorBorder;
		this.indicatorBackgroundColor = indicatorBackroundColor;
		this.indicatorColor = indicatorColor;
	}

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
			drawRect(x, y, x + width, y + 2, indicatorBorderColor); // top
			drawRect(x, y, x + 2, y + height, indicatorBorderColor); // left
			drawRect(x + width - 2, y, x + width, y + height, indicatorBorderColor); // right
			drawRect(x, y + height - 2, x + width, y + height, indicatorBorderColor); // bottom
			this.shadows = true;
		}
		else if (isOverElement(mouseX, mouseY))
		{
			fillBackground(hoveredColor);
			drawRect(x, y, x + width, y + 2, indicatorBorderColor); // top
			drawRect(x, y, x + 2, y + height, indicatorBorderColor); // left
			drawRect(x + width - 2, y, x + width, y + height, indicatorBorderColor); // right
			drawRect(x, y + height - 2, x + width, y + height, indicatorBorderColor); // bottom
			this.shadows = true;
		}
		else
			this.shadows = false;
	}

	/**
	 * Draws the round indicator that displays the current {@link #enabled} boolean
	 * state. Override this to change the indicator and text rendering and position.
	 * 
	 * @param mouseX The current X-Position of the mouse
	 * @param mouseY The current Y-Position of the mouse
	 */
	public void drawIndicator(int mouseX, int mouseY)
	{
		if (!enabled)
		{
			drawFullCircle(x + radius + 4, y + radius + 3.5d, radius, indicatorBorderColor);
			drawFullCircle(x + radius + 4, y + radius + 3.5d, radius - 3, indicatorBackgroundColor);
		}
		else
		{
			drawFullCircle(x + radius + 4, y + radius + 3.5d, radius, indicatorBorderColor);
			drawFullCircle(x + radius + 4, y + radius + 3.5d, radius - 3, indicatorColor);
		}

		if (shadows)
			font.drawStringWithShadow(getName(), x + offset + (float) radius * 2,
					(y + height + offset) / 2F - height / 2F, textColor);
		else
			font.drawString(getName(), x + offset + (float) radius * 2, (y + height + offset) / 2F - height / 2F,
					textColor);
	}

}
