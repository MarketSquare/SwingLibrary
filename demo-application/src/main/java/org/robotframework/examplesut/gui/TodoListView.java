package org.robotframework.examplesut.gui;


public interface TodoListView {
    public void addAddButtonListener(ButtonListener listener);
    public void addDeleteButtonListener(ButtonListener listener);
    public void setTodoListItems(Object[] todoItems);
    public String getDescText();
    public void setDescText(String text);
	public Object getSelectedValue();
}
