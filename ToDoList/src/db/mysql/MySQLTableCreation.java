package db.mysql;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

/**
 * The tool class for creating a table in the db
 * and setup corresponding schema.
 * Run the main method the first time when the database is
 * created and connected.
 */
public class MySQLTableCreation {
	public static void main(String[] args) {
		try {
			// Step 1 Connect to MySQL.
			System.out.println("Connecting to " + MySQLDBUtil.URL);
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);
			
			if (conn == null) {
				return;
			}
			
			// Step 2 Drop tables in case they exist.
			Statement statement = conn.createStatement();
			String sql = "DROP TABLE IF EXISTS items";
			statement.executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);
			
			// Step 3 Create new tables
			// create table item
			sql = "CREATE TABLE items ("
					+ "item_id INT NOT NULL AUTO_INCREMENT,"
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "content VARCHAR(255),"
					+ "PRIMARY KEY (item_id)"
					+ ")";
			statement.executeUpdate(sql);

			// create user table
			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "first_name VARCHAR(255),"
					+ "last_name VARCHAR(255),"
					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);
						
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
