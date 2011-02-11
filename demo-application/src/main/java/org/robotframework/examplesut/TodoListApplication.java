package org.robotframework.examplesut;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TodoListApplication {
    private JPanel panel;
    private JFrame frame;
    private JList todoItemsList;
    private boolean introduceBugs;
    private List<String> todoItems = new ArrayList<String>();
    
    public TodoListApplication(boolean introduceBugs) {
        this.introduceBugs = introduceBugs;
        createFrame();
        createMainPanel();
        addComponentsToMainPanel();
        addMainPanelToFrame();
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private void createFrame() {
        frame = new JFrame("Todo List");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        createUsersList();
    }

    private void createUsernameInput() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(new JLabel("Description:  "));
        final JTextField textField = usernameField();
        topPanel.add(textField);
        topPanel.add(submitButton(textField));
        panel.add(topPanel, BorderLayout.NORTH);
    }

    private JButton submitButton(final JTextField textField) {
        @SuppressWarnings("serial")
        final JButton submitButton = new JButton("Add Todo item") {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    todoItems.add(textField.getText());
                    updateTodoItems();
                }
            });
        }};
        submitButton.setName("add");
        return submitButton;
    }

    private void updateTodoItems() {
        todoItemsList.setListData(todoItems.toArray());
    }

    private JTextField usernameField() {
        final JTextField textField = new JTextField();
        textField.setName("description");
        return textField;
    }

    private void createUsersList() {
        JPanel usersPanel = usersPanel();
        usersPanel.add(usersList(), BorderLayout.CENTER);
        usersPanel.add(deleteButton(), BorderLayout.SOUTH);
        panel.add(usersPanel, BorderLayout.CENTER);
    }

    private JList usersList() {
        todoItemsList = new JList();
        todoItemsList.setName("todolist");
        updateTodoItems();
        return todoItemsList;
    }

    private JPanel usersPanel() {
        JPanel listPane = new JPanel();
        listPane.setLayout(new BorderLayout());
        listPane.add(new JLabel("Todo items:"), BorderLayout.NORTH);
        return listPane;
    }

    private JPanel deleteButton() {
        JPanel deleteButtonPane = new JPanel();
        deleteButtonPane.setLayout(new BorderLayout());
        @SuppressWarnings("serial")
        final JButton deleteButton = new JButton("Delete") {{
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
        Object possibleSelection = todoItemsList.getSelectedValue();
        if (introduceBugs || possibleSelection != null) {
            todoItems.remove(possibleSelection.toString());
            updateTodoItems();
        }
    }
    
    public static void main(String[] args) {
        new TodoListApplication(false).display();
    }
}