package db;

import java.util.List;

import entity.Item;

/**
 * The tool interface for establishing connections with databases.
 *
 */
public interface DBConnection {
	/**
	 * Close the connection.
	 */
	public void close();	

	/**
	 * Insert a item for a user.
	 * 
	 * @param item
	 */
	public void addItems(Item item);
	
	/**
	 * Update a item for a user.
	 * 
	 * @param item
	 */
	public void updateItems(Item item);
	
	/**
	 * Delete a item for a user.
	 * 
	 * @param item
	 */
	public void deleteItems(String itemId);
	
//	/**
//	 * Check if the item exists under the user
//	 * 
//	 * @param userId
//	 * @param itemId
//	 * @return true or false
//	 */
//	public boolean containsItem(String userId, String itemId);
	
	/**
	 * Gets item id based on user id
	 * 
	 * @param userId
	 * @return set of item id
	 */
	public List<Integer> getItemIds(String userId);
	
	/**
	 * Gets item set based on item id
	 * 
	 * @param itemId
	 * @return set of item
	 */
	public List<Item> getItems(String userId);

	/**
	 * Get full name of a user. (This is not needed for main course, just for demo
	 * and extension).
	 * 
	 * @param userId
	 * @return full name of the user
	 */
	public String getFullname(String userId);

	/**
	 * Return whether the credential is correct. (This is not needed for main
	 * course, just for demo and extension)
	 * 
	 * @param userId
	 * @param password
	 * @return boolean
	 */
	public boolean verifyLogin(String userId, String password);
	
	/**
	 * Register one user
	 * 
	 * @param userId
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @return boolean
	 */
	public boolean registerUser(String userId, String password, String firstname, String lastname);

}
