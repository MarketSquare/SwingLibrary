package org.robotframework.examplesut;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoItemStore {

    public void addTodoItem(final String desc) {
    	new WithConnection().execute(new DbCommand() {
			public Object execute(Statement stmt) throws SQLException {
				return stmt.execute("insert into todo_items values (NULL, '" + desc + "')");
		    }
	    });
    }

    public void removeTodoItemWithDesc(final String desc) {
    	new WithConnection().execute(new DbCommand() {
			public Object execute(Statement stmt) throws SQLException {
				return stmt.execute("delete from todo_items where desc='" + desc + "'");
		    }
	    });
    }

	public Object[] allTodoItems() {
		List<?> todoItemsFromDB = (List<?>) new WithConnection().execute(new DbCommand() {
			public Object execute(Statement stmt) throws SQLException {
				return parseTodoItemsFrom(stmt.executeQuery("select * from todo_items"));
			}
		});
		return todoItemsFromDB.toArray();
	}
		
    private Object parseTodoItemsFrom(ResultSet rs) throws SQLException {
        List<String> todoItems = new ArrayList<String>();
        while (rs.next()) todoItems.add(rs.getString("desc"));
        return todoItems;
    }

	public void createTables() {
    	new WithConnection().execute(new DbCommand() {
			public Object execute(Statement stmt)  throws SQLException {
				return stmt.execute("create table todo_items(id identity, desc varchar(80))");
		    }
	    });
	}
}

interface DbCommand{
	public Object execute(Statement stmt) throws SQLException;
} 

class WithConnection {
    {
        try {
            Class.forName("org.hsqldb.jdbcDriver" );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }  

	public Object execute(DbCommand sql) {
		Connection c = null;
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			return sql.execute(stmt);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		} finally {
			try { stmt.close(); } catch (Exception ignored) {}
			try { c.close(); } catch (Exception ignored) {}
		}
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:file:tododb", "SA", "");
	}
}