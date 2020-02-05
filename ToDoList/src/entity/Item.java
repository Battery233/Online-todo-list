package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	// Three fields for each record
	private String itemId;
	private String userId;
	private String content;
	
	/**
	 * This is a builder pattern in Java.
	 */
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.userId = builder.userId;
		this.content = builder.content;
	}
	
	//getters for the values
	public String getItemId() {
		return itemId;
	}

	public String getUserId() {
		return userId;
	}

	public String getContent() {
		return content;
	}
	
	// Create a json object using the values
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId);
			obj.put("user_id", userId);
			obj.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	// a builder class for create the item objects and init values 
	public static class ItemBuilder {
		private String itemId;
		private String userId;
		private String content;
		
		//setter items
		public ItemBuilder setItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}
		public ItemBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}
		public ItemBuilder setContent(String content) {
			this.content = content;
			return this;
		}
		
		//build the item
		public Item build() {
			return new Item(this);
		}
	}
}
