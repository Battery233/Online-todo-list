package db.mysql;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import db.DBConnection;
import entity.Item;
import entity.Item.ItemBuilder;

/**
 * Tool class for database item adding, editing, deleting
 * and reading.
 */

public class MySQLConnection implements DBConnection {
	
	private Connection conn;
	
	// setup connection to the db
	public MySQLConnection() {
	  	 try {
	  		 Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
	  		 conn = DriverManager.getConnection(MySQLDBUtil.URL);
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 }
	}

	//close connection
	@Override
	public void close() {
		if (conn != null) { //make sure connection exists
	  		 try {
	  			 conn.close();
	  		 } catch (Exception e) {
	  			 e.printStackTrace();
	  		 }
	  	 }
	}

	//add items
	@Override
	public void addItems(Item item) {
		if (conn == null) {
			System.err.println("DB connection failed");
	  		return;
		}
		try {
			//insert user id, item id, item content to db
	  		 String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?)";
	  		 PreparedStatement ps = conn.prepareStatement(sql);
	  		 ps.setString(1, null);
	  		 ps.setString(2, item.getUserId());
	  		 ps.setString(3, item.getContent());
	  		 ps.execute();	
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 }
	}
	
	//modify todo item
	@Override
	public void updateItems(Item item) {
		if (conn == null) {
			System.err.println("DB connection failed");
	  		return;
		}
		try {
			//find and modify
	  		 String sql = "UPDATE items SET content = ?"
	  		 		+ " WHERE user_id = ? AND item_id = ?";
	  		 PreparedStatement ps = conn.prepareStatement(sql);
	  		 ps.setString(1, item.getContent());
	  		 ps.setString(2, item.getUserId());
	  		 ps.setInt(3, Integer.parseInt(item.getItemId()));
	  		 ps.executeUpdate();
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 }
	}

	//remove a single todo item
	@Override
	public void deleteItems(String itemId) {
		if (conn == null) {
			System.err.println("DB connection failed");
	  		return;
	  	}
		try {
			String sql = "DELETE FROM items WHERE item_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(itemId));
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Use the list to store itemID.
	@Override
	public List<Integer> getItemIds(String userId) {
		if (conn == null) {
			return new ArrayList<>();
		}
		List<Integer> items = new ArrayList<>();
		try {
			//add result to the data structure
			String sql = "SELECT item_id FROM items WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			//add all the items to list
			while (rs.next()) {
				Integer itemId = rs.getInt("item_id");
				items.add(itemId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(items);
		return items;
	}
	
	// get all the contents in the list
	@Override
	public List<Item> getItems(String userId) {
		if (conn == null) {
			return new ArrayList<>();
		}
		List<Item> Items = new ArrayList<>();
		List<Integer> itemIds = getItemIds(userId);
		try {
			//fetch content from the db
			String sql = "SELECT * FROM items WHERE item_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (int itemId : itemIds) {
				stmt.setInt(1, itemId);
				ResultSet rs = stmt.executeQuery();
				ItemBuilder builder = new ItemBuilder();
				// build the item use item id user id and content
				while (rs.next()) {
					builder.setItemId(rs.getString("item_id"));
					builder.setUserId(rs.getString("user_id"));
					builder.setContent(rs.getString("content"));			
					Items.add(builder.build());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Items;
	}

	//Read user name from database for displaying above the todo list
	@Override
	public String getFullname(String userId) {
		if (conn == null) {
			return "";
		}
		String name = "";
		try {
			String sql = "SELECT first_name, last_name FROM users WHERE user_id = ? ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				name = rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return name;
	}

	//interface for future use
	@Override
	public boolean verifyLogin(String userId, String password) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	//Create a new user
	@Override
	public boolean registerUser(String userId, String password, String firstname, String lastname) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			ps.setString(3, firstname);
			ps.setString(4, lastname);
			
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}

}
