package org.bakesale.addresses.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bakesale.addresses.implementation.AddressDatabase;
import org.bakesale.addresses.implementation.AddressEntry;

import com.google.gson.Gson;

/**
 * Servlet implementation class AddressesServletNew
 */
@WebServlet("/AddressesServletNew")
public class AddressesServletNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CREATE_ADDRESS = "\"createAddress\"";

	private static final String LIST_ADDRESSES = "\"listAddresses\""; //TODO: why the extra quotes?

	private static final String UPDATE_ADDRESS = "\"updateAddress\"";

	private static final String DELETE_ADDRESS = "\"deleteAddress\"";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddressesServletNew() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request.getParameter("action") != null)
		{
			
		}
		
		
		 //response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, Exception {

		response.setContentType("application/json");

		if (request.getParameter("action") != null) {
			String requestType = request.getParameter("action");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String jsonRequest = "";
	        if(br != null){
	            jsonRequest = br.readLine();
	        }

			switch (requestType) {
			case CREATE_ADDRESS:
				createAddress(request, response);
				break;
			case LIST_ADDRESSES:
				listAddresses(request, response);
				break;
			case UPDATE_ADDRESS:
				updateAddress(request, response);
				break;
			case DELETE_ADDRESS:
				deleteAddress(request, response);
				break;
			}
		}
		
//		String jsonTest = new Gson().toJson("Hello friend!");
//		response.getWriter().append(jsonTest);

	}

	private void getAddress(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {
		AddressEntry address;
		String addressInJson;

		String id = request.getParameter("id");
		
		// get address from database, and write the jsonized string to the response
		AddressDatabase addressDatabase;
		try {
			addressDatabase = AddressDatabase.getInstance();
			address = addressDatabase.getAddress(Integer.parseInt(id));
			addressInJson = new Gson().toJson(address);
			response.getWriter().write(addressInJson);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private void deleteAddress(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {

		// get database.
		AddressDatabase addressDatabase;
		String id = request.getParameter("id");
		try {
			addressDatabase = AddressDatabase.getInstance();
			addressDatabase.deleteAddress(Integer.parseInt(id));
			response.getWriter().write("{success:true}");			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{success:false}");
		}
	}

	private void updateAddress(HttpServletRequest request, HttpServletResponse response) {

		//update an existing record with new values.
		// get database.
		AddressDatabase addressDatabase;
		String id = new Gson().fromJson(request.getParameter("id"), String.class);
		String newName = new Gson().fromJson(request.getParameter("newName"), String.class);
		String newEmail = new Gson().fromJson(request.getParameter("newEmail"), String.class);
		String newTele = new Gson().fromJson(request.getParameter("newTele"), String.class);
		String newStreet = new Gson().fromJson(request.getParameter("newStreet"), String.class);
		String newCity = new Gson().fromJson(request.getParameter("newCity"), String.class);
		String newState = new Gson().fromJson(request.getParameter("newState"), String.class);
		String newZip = new Gson().fromJson(request.getParameter("newZip"), String.class);
		
		AddressEntry entry = new AddressEntry(Integer.parseInt(id), newName, newEmail, newTele, newStreet, newCity, newState, newZip);
		
		try {
			addressDatabase = AddressDatabase.getInstance();
			addressDatabase.updateAddress(Integer.parseInt(id), entry);
			response.getWriter().write("{success:true}");			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void listAddresses(HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<AddressEntry> addresses = new ArrayList<>();
		String addressesInJson;

		// get addresses from database.
		AddressDatabase addressDatabase;
		try {
			addressDatabase = AddressDatabase.getInstance();
			addresses = addressDatabase.getAllAddresses();
		} catch (Exception e) {
			e.printStackTrace();
		}

		addressesInJson = new Gson().toJson(addresses);
		response.getWriter().write(addressesInJson);
	}

	private void createAddress(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, IOException {
		boolean success = true;

		AddressDatabase db = AddressDatabase.getInstance();
		// TODO: finish this
		response.getWriter().write(new Gson().toJson(success));

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
