package org.robotframework.examplesut.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class TodoListPanel extends JPanel implements TodoListView {

	private JList todoList = new JList() {{setName("todolist");}};
	private JButton submitButton = new JButton("Add todo item") {{setName("submit");}};
	private JTextField descField = new JTextField() {{setName("description");}};
	private JButton deleteButton = new JButton("Delete item") {{setName("delete");}};
	
	public TodoListPanel() {
		setName("Main Panel");
		setLayout(new BorderLayout());
		JPanel todoPanel = new JPanel();
		todoPanel.setName("todopanel");
		todoPanel.setLayout(new BorderLayout());
		todoPanel.add(new JLabel("Todo items in system:"), BorderLayout.NORTH);
		todoPanel.add(todoList, BorderLayout.CENTER);
		JPanel deleteButtonPane = new JPanel();
		deleteButtonPane.setLayout(new BorderLayout());
		deleteButtonPane.add(todoPanel, BorderLayout.EAST);
		todoPanel.add(deleteButton, BorderLayout.SOUTH);
		add(todoPanel, BorderLayout.CENTER);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(new JLabel("Description:  "));
		topPanel.add(descField);
		topPanel.add(submitButton);
		add(topPanel, BorderLayout.NORTH);
	}

	public void setTodoListItems(Object[] todoItems) {
		todoList.setListData(todoItems);
	}
	
	public String getDescText() {
		return descField.getText();
	}
	
	public void setDescText(String text) {
		descField.setText(text);
	}
	
    public void addAddButtonListener(final  ButtonListener listener) {
    	bind(submitButton, listener);
    }
    
    public void addDeleteButtonListener(final ButtonListener listener) {
    	bind(deleteButton, listener);
    }

    private void bind(JButton button, final ButtonListener listener) {
    	button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				listener.onButtonPush();
			}
		});
    }
    
	public Object getSelectedValue() {
		return todoList.getSelectedValue();
	}

}
