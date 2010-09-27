package org.robotframework.examplesut.storefactory;

import org.robotframework.examplesut.app.ItemStore;
import org.robotframework.examplesut.store.TodoItemStore;

public class DBStoreFactory implements StoreFactory {
	public ItemStore getStore() {
		return new TodoItemStore();
	}
}
