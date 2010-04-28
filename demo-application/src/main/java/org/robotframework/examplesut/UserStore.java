package org.robotframework.examplesut;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserStore {

    public void addUser(final String name) {
    	new DbCmd().execute(new Sql() {
			public Object execute(Statement stmt) throws SQLException {
				return stmt.execute("insert into users values (NULL, '" + name + "')");
		    }
	    });
    }

    public void removeUserWithName(final String name) {
    	new DbCmd().execute(new Sql() {
			public Object execute(Statement stmt) throws SQLException {
				return stmt.execute("delete from users where name='" + name + "'");
		    }
	    });
    }

	public Object[] allUsers() {
		List<?> usersFromDB = (List<?>) new DbCmd().execute(new Sql() {
			public Object execute(Statement stmt) throws SQLException {
				return parseUsersFrom(stmt.executeQuery("select * from users"));
			}
		});
		return usersFromDB.toArray();
	}
		
    private Object parseUsersFrom(ResultSet rs) throws SQLException {
        List<String> users = new ArrayList<String>();
        while (rs.next()) users.add(rs.getString("name"));
        return users;
    }

	public void createTables() {
    	new DbCmd().execute(new Sql() {
			public Object execute(Statement stmt)  throws SQLException {
				return stmt.execute("create table users(id identity, name varchar(40))");
		    }
	    });
	}
}

interface Sql{
	public Object execute(Statement stmt) throws SQLException;
} 

class DbCmd {
    {
        try {
            Class.forName("org.hsqldb.jdbcDriver" );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }  

	public Object execute(Sql sql) {
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
		return DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "SA", "");
	}
}