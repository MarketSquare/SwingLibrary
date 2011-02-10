package org.robotframework.examplesut.app;


public class TodoListApplication implements App {
	
	private ItemStore store;
	private boolean introduceBugs;
	
	public TodoListApplication(ItemStore store, boolean introduceBugs) {
		this.store = store;
	}

	public void addTodoItem(String desc) {
		store.addTodoItem(desc);
	}

	public Object[] allTodoItems() {
		return store.allTodoItems();
	}

	public void removeTodoItemWithDesc(String desc) {
		store.removeTodoItemWithDesc(desc);
		if (introduceBugs || desc != null)
			store.removeTodoItemWithDesc(desc);
	}
}