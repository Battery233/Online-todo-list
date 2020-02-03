package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import db.DBConnection;
import entity.Item;
import entity.Item.ItemBuilder;

public class MySQLConnection implements DBConnection {
	
	private Connection conn;
	
	public MySQLConnection() {
	  	 try {
	  		 Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
	  		 conn = DriverManager.getConnection(MySQLDBUtil.URL);
	  		
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 }
	}

	@Override
	public void close() {
		if (conn != null) {
	  		 try {
	  			 conn.close();
	  		 } catch (Exception e) {
	  			 e.printStackTrace();
	  		 }
	  	 }
	}

	@Override
	public void addItems(Item item) {
		if (conn == null) {
			System.err.println("DB connection failed");
	  		return;
		}
		try {
	  		 String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?, ?)";
	  		 PreparedStatement ps = conn.prepareStatement(sql);
	  		 ps.setString(1, item.getItemId());
	  		 ps.setString(2, item.getUserId());
	  		 //ps.setString(3, item.getName());
	  		 ps.setString(3, item.getContent());
	  		 //ps.setString(5, item.getTime());
	  		 ps.setInt(4, item.isChecked());
	  		 ps.execute();
	  		
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 }
	}
	
	@Override
	public void updateItems(Item item) {
		if (conn == null) {
			System.err.println("DB connection failed");
	  		return;
		}
		try {
	  		 String sql = "UPDATE items SET content = ?, checked = ?"
	  		 		+ " WHERE user_id = ? AND item_id = ?";
	  		 PreparedStatement ps = conn.prepareStatement(sql);
	  		 //ps.setString(1, item.getName());
	  		 ps.setString(1, item.getContent());
	  		 //ps.setString(3, item.getTime());
	  		 ps.setInt(2, item.isChecked());
	  		 ps.setString(3, item.getUserId());
	  		 ps.setString(4, item.getItemId());
	  		 ps.executeUpdate();
	  		
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 }
	}

	@Override
	public void deleteItems(String itemId) {
		if (conn == null) {
			System.err.println("DB connection failed");
	  		return;
	  	}
		try {
			String sql = "DELETE FROM items WHERE item_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, itemId);
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public boolean containsItem(String userId, String itemId) {
//		if (conn == null) {
//			return false;
//		}
//		try {
//			String sql = "SELECT item_id FROM items WHERE user_id = ? AND item_id = ?";
//			PreparedStatement stmt = conn.prepareStatement(sql);
//			stmt.setString(1, userId);
//			stmt.setString(2, itemId);
//			
//			ResultSet rs = stmt.executeQuery();
//			
//			if (rs != null) {
//				return true;
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	@Override
	public Set<String> getItemIds(String userId) {
		if (conn == null) {
			return new HashSet<>();
		}
		Set<String> Items = new HashSet<>();
		try {
			String sql = "SELECT item_id FROM items WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String itemId = rs.getString("item_id");
				Items.add(itemId);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Items;
	}
	
	@Override
	public Set<Item> getItems(String userId) {
		if (conn == null) {
			return new HashSet<>();
		}
		
		Set<Item> Items = new HashSet<>();
		Set<String> itemIds = getItemIds(userId);
		
		try {
			String sql = "SELECT * FROM items WHERE item_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (String itemId : itemIds) {
				stmt.setString(1, itemId);
				
				ResultSet rs = stmt.executeQuery();
				
				ItemBuilder builder = new ItemBuilder();
				
				while (rs.next()) {
					builder.setItemId(rs.getString("item_id"));
					builder.setUserId(rs.getString("user_id"));
					//builder.setName(rs.getString("name"));
					builder.setContent(rs.getString("content"));
					//builder.setTime(rs.getString("time"));
					builder.setChecked(rs.getInt("checked"));
					
					Items.add(builder.build());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Items;
	}

	@Override
	public String getFullname(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyLogin(String userId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
