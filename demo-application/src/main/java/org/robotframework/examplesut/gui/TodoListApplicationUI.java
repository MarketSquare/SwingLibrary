package org.robotframework.examplesut.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.robotframework.examplesut.app.App;
import org.robotframework.examplesut.app.Gui;

public class TodoListApplicationUI implements Gui {

	private App app;
	private boolean introduceBugs;
	private JFrame frame;
	private TodoListView view;

    public TodoListApplicationUI(App app, boolean introduceBugs) {
    	this.app = app;
    	this.introduceBugs = introduceBugs;
    }

	public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createFrame();
                addMainPanelToFrame();
                showGUI();
            }
        });
	}

	private void createFrame() {
		frame = new JFrame("Todo App");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showGUI() {
        frame.pack();
        frame.setVisible(true);
    }

    private void addMainPanelToFrame() {
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(createTodoListView(), BorderLayout.CENTER);
    }

	private Component createTodoListView() {
		view = new TodoListPanel();
    	view.setTodoListItems(app.allTodoItems());
		view.addAddButtonListener(new ButtonListener() {
			public void onButtonPush() {
				String text = view.getDescText();
				app.addTodoItem(text);
				Object[] allTodoItems = app.allTodoItems();
				view.setTodoListItems(allTodoItems);
				view.setDescText("");
			}
		});
		view.addDeleteButtonListener(new ButtonListener() {
			public void onButtonPush() {
				Object possibleSelection = view.getSelectedValue();
				if (introduceBugs || possibleSelection != null) {
					app.removeTodoItemWithDesc(possibleSelection.toString());
					view.setTodoListItems(app.allTodoItems());
    			}
			}
		});
		return (Component) view;
	}
}
