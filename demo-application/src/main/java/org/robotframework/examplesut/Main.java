package org.robotframework.examplesut;

import org.robotframework.examplesut.app.App;
import org.robotframework.examplesut.app.ItemStore;
import org.robotframework.examplesut.app.TodoListApplication;
import org.robotframework.examplesut.guifactory.SwingGuiFactory;
import org.robotframework.examplesut.storefactory.DBStoreFactory;
import org.robotframework.examplesut.storefactory.StoreFactory;

public class Main {

	public static void main(String[] args) {
		boolean introduceBugs = Boolean.valueOf(System.getProperty("buggy", "true"));
		StoreFactory storeFactory = new DBStoreFactory();
		ItemStore store = storeFactory.getStore();
		tryToCreateDbIfNeeded(store);
		App app = new TodoListApplication(store, introduceBugs);
		new SwingGuiFactory(app, introduceBugs).createGui().display();
	}
	
	private static void tryToCreateDbIfNeeded(ItemStore store) {
		try { store.create(); } catch (Exception ignored) {}
	}
	
}
