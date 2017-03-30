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

	private void deleteAddress(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {

		// get database.
		AddressDatabase addressDatabase;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String id = request.getParameter("id");
		try {
			addressDatabase = AddressDatabase.getInstance();
			addressDatabase.deleteAddress(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateAddress(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

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

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		super.doDelete(request, response);

	}

}
