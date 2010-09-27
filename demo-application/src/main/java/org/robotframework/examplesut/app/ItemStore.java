package org.robotframework.examplesut.app;

public interface ItemStore {
	public abstract void addTodoItem(final String desc);
	public abstract void removeTodoItemWithDesc(final String desc);
	public abstract Object[] allTodoItems();
	public abstract void createTables();

}