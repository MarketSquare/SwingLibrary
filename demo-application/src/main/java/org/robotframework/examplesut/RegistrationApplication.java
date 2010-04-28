package org.robotframework.examplesut;

public class RegistrationApplication {
	public static void main(String[] args) {
		boolean introduceBugs = Boolean.valueOf(System.getProperty("buggy", "true"));
		UserStore store = new UserStore();
		store.createTables();
		new RegistrationUI(store, introduceBugs).create();
	}
}