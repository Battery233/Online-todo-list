package db.mysql;

import java.sql.DriverManager;
//import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

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
			sql = "CREATE TABLE items ("
					+ "item_id INT NOT NULL AUTO_INCREMENT,"
					+ "user_id VARCHAR(255) NOT NULL,"
//					+ "name VARCHAR(255),"
					+ "content VARCHAR(255),"
//					+ "time VARCHAR(255),"
//					+ "checked TinyInt(1) NOT NULL,"
					+ "PRIMARY KEY (item_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "first_name VARCHAR(255),"
					+ "last_name VARCHAR(255),"
					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);
			
//			// Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
//			sql = "INSERT INTO users VALUES('1234', '1111', 'Eat lunch', 'Remember to eat lunch', '2020-01-16 07:08', '0')";
//			statement.executeUpdate(sql);
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
