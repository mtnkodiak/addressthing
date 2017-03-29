/**
 * 
 */

$(document).ready(function() {
	populateAddressTable();
});

$.ajax({
    type: 'get', // it's easier to read GET request parameters
    url: 'AddressesServletNew',
    dataType: 'JSON',
    data: { 
      action: JSON.stringify("listAddresses") // look here!
    },
    success: function(data) {
    	alert(data);
    	populateAddressTable(data);
    },
    error: function(data) {
        alert('fail');
    }
});


/**
 * Using the AddressesServletNew, obtain all existing entries, and populate table
 * @returns
 */
function populateAddressTable(data) {

	//var servletUrl = "jdbc:postgresql://localhost:5432/addresses";
	
	var displayHTML = "";
	var i;

	// get the data from database
	//var storedDatabase = localStorage.getItem("customerDatabase");
	
//    $.get('AddressesServletNew', function(data) {
//        alert(data);
//    });

//    $.getJSON('AddressesServletNew', function(data) {
//        var table = $('<table>').appendTo($('#address_table'));
//        $.each(data, function(i, product) {
//            var row = $('<tr>').appendTo(table);
//            $('<td>').text(product.id).appendTo(row);
//            $('<td>').text(product.name).appendTo(row);
//            $('<td>').text(product.description).appendTo(row);
//        });
//    });
	var userObject;
	
	// parse the data from string to JSON object
	//var parsedObject = JSON.parse(data);
	var parsedObject = data;

	// access the users key of the JSON object
	//var userObject = parseObject.users;

	// get the length of the object (how many entries the database has)
	var contactsLength = parsedObject.length;

	for (i = 0; i < contactsLength; i++) {
		var trElement = '<tr id="address' + (i + 1) + '">';
		var tdId = '<td id="id' + (i + 1) + '">' + parsedObject[i].id + '</td>';
		var tdName = '<td id="' + (i + 1) + 'name">' + parsedObject[i].name
				+ '</td>';
		var tdEmail = '<td id="' + (i + 1) + 'email">' + parsedObject[i].email
				+ '</td>';
		var tdButton = '<td><button isEditButton="true" id="'
				+ parsedObject[i].id + 'editButton">Edit</button> | <button id="'
				+ parsedObject[i].id + 'deleteButton">Delete</button></td>';

		displayHTML += trElement + tdId + tdName + tdEmail + tdButton + '</tr>';
	}

	displayHTML = "<tr><td><strong>ID</strong></td><td><strong>Name</strong></td><td><strong>Email</strong></td><td><strong>Actions</strong></td></tr>"
			+ displayHTML;

	$('#address_table').html(displayHTML);

}
