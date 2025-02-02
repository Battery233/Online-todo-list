package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import entity.Item.ItemBuilder;

/**
 * Servlet implementation class SearchItem
 * 
 * The class for displaying all the todo list items 
 * to the todo list. Will read the items from HTTP connections
 * or make post requests for item editing and removing.
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
    // To resolve get http requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		String userId = session.getAttribute("user_id").toString(); 
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			//Connect db and make requests
			List<Item> items = connection.getItems(userId);
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
	//handle post request from the front end
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
	  		 JSONObject input = RpcHelper.readJSONObject(request);
	  		 if (input.has("item_id")) { //search exist id, if exist, modify item		  		 
		  		 ItemBuilder builder = new ItemBuilder();
		  		 builder.setItemId(input.getString("item_id"));
				 builder.setUserId(input.getString("user_id"));
				 builder.setContent(input.getString("content"));
				 Item item = builder.build();
				 connection.updateItems(item);
	  		 } else {
	  			 //if do not exist, create new item
	  			 ItemBuilder builder = new ItemBuilder();
	  			 builder.setUserId(input.getString("user_id"));
				 builder.setContent(input.getString("content"));
				 Item item = builder.build();
				 connection.addItems(item);
	  		 }
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
	// resolve remove request
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
	  		 JSONObject input = RpcHelper.readJSONObject(request);
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
