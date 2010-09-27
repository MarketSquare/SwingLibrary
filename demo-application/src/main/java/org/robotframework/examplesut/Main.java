package org.robotframework.examplesut;

import org.robotframework.examplesut.app.App;
import org.robotframework.examplesut.app.ItemStore;
import org.robotframework.examplesut.app.TodoListApplication;
import org.robotframework.examplesut.guifactory.GuiFactory;
import org.robotframework.examplesut.guifactory.SwingGuiFactory;
import org.robotframework.examplesut.storefactory.DBStoreFactory;

public class Main {

	public static void main(String[] args) {
		boolean introduceBugs = Boolean.valueOf(System.getProperty("buggy", "true"));
		DBStoreFactory storeFactory = new DBStoreFactory();
		ItemStore store = storeFactory.getStore();
		tryToCreateDbIfNeeded(store);
		App app = new TodoListApplication(store, introduceBugs);
		GuiFactory guiFactory = new SwingGuiFactory(app, introduceBugs);
		new TodoListApplication(store, introduceBugs).displayWith(guiFactory.getGui());
	}
	
	private static void tryToCreateDbIfNeeded(ItemStore store) {
		try { store.createTables(); } catch (Exception ignored) {}
	}
	
}
