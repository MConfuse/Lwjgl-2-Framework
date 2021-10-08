package test;

import de.confuse.openGL.Window;
import test.gui.GuiMainMenu;

public class Main
{

	public Main() throws Exception
	{
		Window.default_Screen = GuiMainMenu.class;
		Window.get("Test", 800, 600, false, true);
	}

	public static void main(String[] args)
	{
		try
		{
			new Main();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}

