package de.confuse.openGL.gui.window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

}
