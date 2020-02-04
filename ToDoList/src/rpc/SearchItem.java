package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import entity.Item.ItemBuilder;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/main")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = "1111";
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			Set<Item> items = connection.getItems(userId);
			
			JSONArray array = new JSONArray();
			for (Item item : items) {
				JSONObject obj = item.toJSONObject();
				array.put(obj);
			}
			RpcHelper.writeJsonArray(response, array);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
	  		 JSONObject input = RpcHelper.readJSONObject(request);

	  		 if (input.has("item_id")) {
	  			 //Integer itemId = Integer.parseInt(input.getString("item_id"));
		  		 
		  		 ItemBuilder builder = new ItemBuilder();
		  		 builder.setItemId(input.getString("item_id"));
				 builder.setUserId(input.getString("user_id"));
				 //builder.setName(input.getString("name"));
				 builder.setContent(input.getString("content"));
				 //builder.setTime(input.getString("time"));
				 //builder.setChecked(input.getInt("checked"));
				 Item item = builder.build();
				 connection.updateItems(item);
	  		 } else {
	  			 ItemBuilder builder = new ItemBuilder();
	  			 builder.setUserId(input.getString("user_id"));
				 //builder.setName(input.getString("name"));
				 builder.setContent(input.getString("content"));
				 Item item = builder.build();
				 connection.addItems(item);
	  		 }
//	  		 Integer itemId = Integer.parseInt(input.getString("item_id"));
//	  		 
//	  		 ItemBuilder builder = new ItemBuilder();
//	  		 builder.setItemId(input.getString("item_id"));
//			 builder.setUserId(input.getString("user_id"));
//			 //builder.setName(input.getString("name"));
//			 builder.setContent(input.getString("content"));
//			 //builder.setTime(input.getString("time"));
//			 //builder.setChecked(input.getInt("checked"));
			 
//			 Set<Integer> itemIds = connection.getItemIds(input.getString("user_id"));
//			 Item item = builder.build();
//			 
//			 if (itemIds.contains(itemId)) {
//				 connection.updateItems(item);
//			 } else {
//				 connection.addItems(item);
//			 }
			 
	  		 RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	  		
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 } finally {
	  		 connection.close();
	  	 }
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
	  		 JSONObject input = RpcHelper.readJSONObject(request);
	  		 //String userId = input.getString("user_id");
	  		 String itemId = input.getString("item_id");
	  		
	  		 connection.deleteItems(itemId);
	  		 RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	  		
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 } finally {
	  		 connection.close();
	  	 }
	}

}
