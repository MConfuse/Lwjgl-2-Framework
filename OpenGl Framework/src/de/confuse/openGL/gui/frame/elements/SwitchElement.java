package de.confuse.openGL.gui.frame.elements;

import de.confuse.openGL.font.FontUtils;
import de.confuse.openGL.gui.GuiScreen;
import de.confuse.openGL.gui.frame.CElement;

public class SwitchElement extends CElement
{
	// TODO: Rework this sh*t, lol

	// TODO: make final
	private int offset = 4;

	private boolean enabled;
	private final FontUtils font;

	// TODO: Make a color manager possible instead of hard coding?

	// - General -
	private final int textColor = 0xaaaaaa;
	private final int backgroundColor = 0xFF252525;
	// - Indicator -
	private final int hoveredColor = 0xFF99034B;
	private final int indicatorBorderColor = 0xFF000000;
	private final int indicatorBackgroundColor = 0xFF252525;
	private final int indicatorColor = 0xFFBD025A;

	public SwitchElement(String name, int x, int y, int width, int height, FontUtils font)
	{
		super(name, x, y, width, height);
		this.font = font;
		
		// TODO: Minimum size algorithm
	}

	public SwitchElement(String name, int x, int y, FontUtils font)
	{
		super(name, x, y);
		this.font = font;
	}

	@Override
	public void update(float dt, int mouseX, int mouseY)
	{
		drawRect(x, y, x + width, y + height, backgroundColor);
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
		if (state == GuiScreen.STATE_PRESSED)
			this.enabled = !this.enabled;
	}

	@Override
	protected int getMaxWidth()
	{ return 0; }

	@Override
	protected int getMaxHeight()
	{ return 0; }

	private void drawIndicator(int mouseX, int mouseY)
	{
//		this.enabled = false;
//		this.height = 40;
		this.offset = 8;
		double radius = 14;
		double diameter = 7D;
		this.width = (int) ((int) radius + offset * 2 + font.getWidth(getName()) + 10);
		drawFullCircle(x + offset + diameter + 0.5, (y + offset / 2 + diameter + 1) / 2D + (double) height / 2D, radius, indicatorBorderColor);
		if (!enabled)
		{
			drawFullCircle(x + offset + ((radius - 3) / 2) + 2.5, (y + offset / 2 + ((radius - 3) / 2) + 2.5) / 2D + (double) height / 2D, radius - 3, indicatorBackgroundColor);
		}
		else
		{
			drawFullCircle(x + offset + ((radius - 3) / 2) + 2.5, (y + offset / 2 + ((radius - 3) / 2) + 2.5) / 2D + (double) height / 2D, radius - 3, indicatorColor);
		}

		font.drawStringWidthShadow(getName(), x + offset * 2 + (float) radius + 5, (y + height + offset) / 2F - height / 2F, textColor);
	}

}
