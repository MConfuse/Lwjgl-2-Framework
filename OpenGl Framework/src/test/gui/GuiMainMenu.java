package test.gui;

import org.lwjgl.input.Keyboard;

import de.confuse.openGL.gui.Gui;
import de.confuse.openGL.gui.GuiScreen;

public class GuiMainMenu extends GuiScreen
{

	public GuiMainMenu(GuiScreen parent)
	{
		super(parent);
		System.out.println("Main Menu");
	}

	@Override
	public void update(float dt, int mouseX, int mouseY)
	{
		fillBackground(0xFF303033);
		Gui.enableGL2D();
		arial.drawString("Hi my name is yeff", 100, 100, 0xFFFF0000);
		Gui.disableGL2D();
	}

	@Override
	public void keyPressed(int key, int state)
	{
		System.out.println(Keyboard.getKeyName(key) + " | " + state);
	}

	@Override
	public void keyHeld(int key, int state)
	{
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button, int state)
	{

	}

}
