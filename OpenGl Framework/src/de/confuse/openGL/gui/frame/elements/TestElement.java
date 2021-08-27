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
		drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFFFF0000);
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
