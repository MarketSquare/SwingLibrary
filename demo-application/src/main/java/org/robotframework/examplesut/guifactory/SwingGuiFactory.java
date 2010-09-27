package org.robotframework.examplesut.guifactory;

import org.robotframework.examplesut.app.App;
import org.robotframework.examplesut.app.Gui;
import org.robotframework.examplesut.gui.TodoListApplicationUI;

public class SwingGuiFactory implements GuiFactory {

	private App app;
	private boolean introduceBugs;
	
	public SwingGuiFactory(App app, boolean introduceBugs) {
		this.app = app;
		this.introduceBugs = introduceBugs;
	}
	
	public Gui getGui() {
		return new TodoListApplicationUI(app, introduceBugs);
	}

}
