package test.gui;

import org.lwjgl.input.Keyboard;

import de.confuse.openGL.font.FontUtils;
import de.confuse.openGL.gui.GuiScreen;
import de.confuse.openGL.gui.frame.elements.SwitchElement;
import de.confuse.openGL.gui.frame.elements.TestElement;

public class GuiMainMenu extends GuiScreen
{
	private final SwitchElement switchElement = new SwitchElement("TestSwitch", 10, 10, FontUtils.ARIAL_HALF, true, 0xFF000000, 0xFFBD025A);
	private final SwitchElement switchElement2 = new SwitchElement("TestSwitch2", 10, 100, FontUtils.ARIAL_HALF, true, 0xFF000000, 0xFFBD025A);	

	public GuiMainMenu(GuiScreen parent)
	{
		super(parent);
		addElementToFrame(new TestElement("Test", 10, 10, 40, 60));
//		addElementToFrame(switchElement);
//		addElementToFrame(switchElement2);
		
		System.out.println("Main Menu");
	}

	@Override
	public void update(float dt, int mouseX, int mouseY)
	{
		fillBackground(0xFF303033);

//		arial.drawString("Hi my name is yeff", 100, 100, 0xFFFF0000);

		frame.drawElementsToScreen(dt, mouseX, mouseY);
	}

	@Override
	public void keyPressed(int key, int state)
	{
		System.out.println(Keyboard.getKeyName(key) + " | " + state);
	}

	@Override
	public void keyHeld(int key, int state)
	{}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button, int state)
	{
		System.out.println("Click! " + button + " | " + state);
		frame.mouseClicked(mouseX, mouseY, button, state);
	}

	@Override
	public void screenRefocused()
	{

	}

	@Override
	public void mouseDragging(int mouseX, int mouseY, int button)
	{
	}

}
