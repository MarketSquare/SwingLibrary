package org.robotframework.examplesut;

public class TodoListApplication {
	public static void main(String[] args) {
		boolean introduceBugs = Boolean.valueOf(System.getProperty("buggy", "true"));
		TodoItemStore store = new TodoItemStore();
		tryToCreateDbIfNeeded(store);
		new TodoListApplicationUI(store, introduceBugs).create();
	}
	
	private static void tryToCreateDbIfNeeded(TodoItemStore store) {
		try {
			store.createTables();
		} catch (Exception ignored) {}
	}
}