package org.robotframework.examplesut;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TodoListApplicationUI {
    private JPanel panel;
    private JFrame frame;
    private JList todoList;
    private boolean introduceBugs;
    private TodoItemStore todoItems;

    public TodoListApplicationUI(TodoItemStore store, boolean introduceBugs) {
    	this.todoItems = store;
		this.introduceBugs = introduceBugs;
	}

	public void create() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createFrame();
                createMainPanel();
                addComponentsToMainPanel();
                addMainPanelToFrame();
                showGUI();
            }
        });
	}

	@SuppressWarnings("serial")
	private void createFrame() {
		frame = new JFrame("Todo App") {
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showGUI() {
        frame.pack();
        frame.setVisible(true);
    }

    private void addMainPanelToFrame() {
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.CENTER);
    }

    private void createMainPanel() {
        panel = new JPanel();
        panel.setName("Main Panel");
        panel.setLayout(new BorderLayout());
    }

    private void addComponentsToMainPanel() {
        createUsernameInput();
        createTodoList();
    }

	private void createUsernameInput() {
		JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        final JTextField textField = descriptionField(topPanel);
        topPanel.add(textField);
        topPanel.add(submitButton(textField));
        panel.add(topPanel, BorderLayout.NORTH);
	}

	private JButton submitButton(final JTextField textField) {
		@SuppressWarnings("serial")
		final JButton submitButton = new JButton("Add todo item") {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                	todoItems.addTodoItem(textField.getText());
                    updateTodoList();
                    textField.setText("");
                }
            });
        }};
        submitButton.setName("submit");
		return submitButton;
	}
    
    private void updateTodoList() {
		todoList.setListData(todoItems.allTodoItems());
	}

	private JTextField descriptionField(JPanel topPanel) {
		topPanel.add(new JLabel("Description:  "));
        final JTextField textField = new JTextField();
        textField.setName("description");
        topPanel.add(textField);
		return textField;
	}
    
    private void createTodoList() {
        JPanel todoPanel = todoListPanel();
        todoPanel.add(todoItemList(), BorderLayout.CENTER);
        todoPanel.add(deleteButton(), BorderLayout.SOUTH);
        panel.add(todoPanel, BorderLayout.CENTER);
    }

	private JList todoItemList() {
		todoList = new JList();
        todoList.setName("todolists");
        updateTodoList();
        return todoList;
	}

	private JPanel todoListPanel() {
		JPanel listPane = new JPanel();
        listPane.setLayout(new BorderLayout());
        listPane.add(new JLabel("Todo items in system:"), BorderLayout.NORTH);
		return listPane;
	}

	private JPanel deleteButton() {
		JPanel deleteButtonPane = new JPanel();
		deleteButtonPane.setLayout(new BorderLayout());
		@SuppressWarnings("serial")
		final JButton deleteButton = new JButton("Delete item") {{
        	addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					removeTodoItem();
				}
			});
        }};
        deleteButton.setName("delete");
        deleteButtonPane.add(deleteButton, BorderLayout.EAST);
		return deleteButtonPane;
	}

	private void removeTodoItem() {
		Object possibleSelection = todoList.getSelectedValue();
		if (introduceBugs || possibleSelection != null) {
			todoItems.removeTodoItemWithDesc(possibleSelection.toString());
			updateTodoList();
		}
	}
}
