package org.robotframework.examplesut.app;


public interface App {
	public void addTodoItem(String text);
	public Object[] allTodoItems();
	public void removeTodoItemWithDesc(String string);
}
