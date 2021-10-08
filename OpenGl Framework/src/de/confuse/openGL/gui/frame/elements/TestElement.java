package de.confuse.openGL.gui.frame.elements;

import de.confuse.openGL.gui.frame.CElement;

public class TestElement extends CElement
{

	public TestElement(String name, int x, int y, int width, int height)
	{
		super(name, x, y, width, height);
	}

	@Override
	public void update(float dt, int mouseX, int mouseY)
	{
//		drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFFFF0000);
		float width = 266, rectAX = 0, rectBX = 266, rectCX = 522;

		float y1 = 0, y2 = 10;

		drawRect(rectAX, y2, rectBX, y1, 0xFFFF0000);
		drawRect(rectBX, y2, rectCX, y1, 0xFF00FF00);
		drawRect(rectCX, y2, window.getWidth(), y1, 0xFF0000FF);
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
		System.out.println("Element Clicked!");
	}

	@Override
	protected int getMaxWidth()
	{ return 0; }

	@Override
	protected int getMaxHeight()
	{ return 0; }

}
