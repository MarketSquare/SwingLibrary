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

public class RegistrationUI {
    private JPanel panel;
    private JFrame frame;
    private JList userList;
    private boolean introduceBugs;
    private UserStore users;

    public RegistrationUI(UserStore store, boolean introduceBugs) {
    	this.users = store;
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
		frame = new JFrame("Test App") {
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
        createUsersList();
    }

	private void createUsernameInput() {
		JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        final JTextField textField = usernameField(topPanel);
        topPanel.add(textField);
        topPanel.add(submitButton(textField));
        panel.add(topPanel, BorderLayout.NORTH);
	}

	private JButton submitButton(final JTextField textField) {
		@SuppressWarnings("serial")
		final JButton submitButton = new JButton("Add user") {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                	users.addUser(textField.getText());
                    updateUsers();
                }
            });
        }};
        submitButton.setName("submit");
		return submitButton;
	}
    
    private void updateUsers() {
		userList.setListData(users.allUsers());
	}

	private JTextField usernameField(JPanel topPanel) {
		topPanel.add(new JLabel("Username:  "));
        final JTextField textField = new JTextField();
        textField.setName("username");
        topPanel.add(textField);
		return textField;
	}
    
    private void createUsersList() {
        JPanel usersPanel = usersPanel();
        usersPanel.add(usersList(), BorderLayout.CENTER);
        usersPanel.add(deleteButton(), BorderLayout.SOUTH);
        panel.add(usersPanel, BorderLayout.CENTER);
    }

	private JList usersList() {
		userList = new JList();
        userList.setName("users");
        updateUsers();
        return userList;
	}

	private JPanel usersPanel() {
		JPanel listPane = new JPanel();
        listPane.setLayout(new BorderLayout());
        listPane.add(new JLabel("Users in system:"), BorderLayout.NORTH);
		return listPane;
	}

	private JPanel deleteButton() {
		JPanel deleteButtonPane = new JPanel();
		deleteButtonPane.setLayout(new BorderLayout());
		@SuppressWarnings("serial")
		final JButton deleteButton = new JButton("Delete user") {{
        	addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					removeUser();
				}
			});
        }};
        deleteButton.setName("delete");
        deleteButtonPane.add(deleteButton, BorderLayout.EAST);
		return deleteButtonPane;
	}

	private void removeUser() {
		Object possibleSelection = userList.getSelectedValue();
		if (introduceBugs || possibleSelection != null) {
			users.removeUserWithName(possibleSelection.toString());
			updateUsers();
		}
	}
}
