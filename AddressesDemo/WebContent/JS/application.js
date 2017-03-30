/**
 * 
 */

// $(document).ready(function() {
// populateAddressTable();
// });
$.ajax({
	type : 'get',
	url : 'AddressesServletNew',
	dataType : 'JSON',
	data : {
		action : JSON.stringify("listAddresses")
	},
	success : function(data) {
		// alert(data);
		populateAddressTable(data);
	},
	error : function(data) {
		alert('fail');
	}
});

/**
 * Using the AddressesServletNew, obtain all existing entries, and populate
 * table
 * 
 * @returns
 */
function populateAddressTable(data) {

	// var servletUrl = "jdbc:postgresql://localhost:5432/addresses";

	var displayHTML = "";
	var i;

	var userObject;

	var parsedObject = data;

	// get the length of the object (how many entries the database has)
	var contactsLength = parsedObject.length;

	for (i = 0; i < contactsLength; i++) {
		var trElement = '<tr id="address' + (i + 1) + '">';
		var tdName = '<td id="' + (i + 1) + 'name">' + parsedObject[i].name
				+ '</td>';
		var tdEmail = '<td id="' + (i + 1) + 'email">' + parsedObject[i].email
				+ '</td>';
		var tdTele = '<td id="' + (i + 1) + 'telephone">'
				+ parsedObject[i].telephone + '</td>';
		var tdStreet = '<td id="' + (i + 1) + 'street">'
				+ parsedObject[i].addrStreet + '</td>';
		var tdCity = '<td id="' + (i + 1) + 'city">' + parsedObject[i].addrCity
				+ '</td>';
		var tdState = '<td id="' + (i + 1) + 'state">'
				+ parsedObject[i].addrState + '</td>';
		var tdZip = '<td id="' + (i + 1) + 'zip">' + parsedObject[i].zipcode
				+ '</td>';
		var tdId = '<td style="display:none;" id="' + (i+1) + 'id">' + parsedObject[i].id + '</td>';
		var tdButton = '<td><button isEditButton="true" id="'
				+ (i+1) + 'editButton" onclick="editEntry('
				+ (i+1) + ')">Edit</button> | <button id="'
				+ (i+1) + 'deleteButton"  onclick="deleteEntry('
				+ (i+1) + ')">Delete</button></td>';

		displayHTML += trElement + tdName + tdEmail + tdTele + tdStreet
				+ tdCity + tdState + tdZip + tdButton + tdId + '</tr>';
	}

	displayHTML = "<tr><td><strong>Name</strong></td><td><strong>Email</strong></td><td><strong>Telephone</strong></td><td><strong>Street</strong></td><td><strong>City</strong></td><td><strong>State</strong></td><td><strong>Zip</strong></td><td><strong>Actions</strong></td></tr>"
			+ displayHTML;

	$('#address_table').html(displayHTML);

}

function editEntry(id) {
	alert("editEntry called for row index " + id);

	// get current row values
	var tableRows = document.getElementById("address_table").rows;

	var curName = document.getElementById(id + "name").innerHTML;
	var curEmail = document.getElementById(id + "email").innerHTML;
	var curTele = document.getElementById(id + "telephone").innerHTML;
	var curStreet = document.getElementById(id + "street").innerHTML;
	var curCity = document.getElementById(id + "city").innerHTML;
	var curState = document.getElementById(id + "state").innerHTML;
	var curZip = document.getElementById(id + "zip").innerHTML;
	var curId = document.getElementById(id + "id").innerHTML;
	
	//now stuff em into the edit dialog as default values
	document.getElementById("edit_name").value = curName;
	document.getElementById("edit_email").value = curEmail;
	document.getElementById("edit_telephone").value = curTele;
	document.getElementById("edit_street").value = curStreet;
	document.getElementById("edit_city").value = curCity;
	document.getElementById("edit_state").value = curState;
	document.getElementById("edit_zip").value = curZip;
	document.getElementById("edit_id").value = curId;
	
	// bring up dialog here to allow user to change values
	editDialog = $("#edit-dialog-form").dialog({
		autoOpen : true,
		height : 400,
		width : 350,
		modal : true,
		buttons : {
			"Save" : updateUser,
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			form[0].reset();
			allFields.removeClass("ui-state-error");
		}
	});

	// form = dialog.find( "form" ).on( "submit", function( event ) {
	// event.preventDefault();
	// addUser();
	// });

//	$("#create-user").button().on("click", function() {
//		dialog.dialog("open");
//	});

//	// send change request to database servlet
//	$.ajax({
//		type : 'get',
//		url : 'AddressesServletNew',
//		dataType : 'JSON',
//		data : {
//			'action' : JSON.stringify("updateAddress"),
//			'id' : JSON.stringify(id),
//			'newName' : JSON.stringify(newName),
//			'newEmail' : JSON.stringify(newEmail),
//			'newTele' : JSON.stringify(newTele),
//			'newStreet' : JSON.stringify(Street),
//			'newCity' : JSON.stringify(newCity),
//			'newState' : JSON.stringify(newState),
//			'newZip' : JSON.stringify(newZip)
//		},
//		success : function(data) {
//			alert(data);
//			// populateAddressTable(data);
//		},
//		error : function(data) {
//			alert('fail');
//		}
//	});

	/**
	 * This is called after the user updates an entry's values using the popup & hits Save
	 */
	function updateUser() {
		// send update to database

		// get new values from update dialog's fields		
		var newName = document.getElementById("edit_name").value;
		var newEmail = document.getElementById("edit_email").value;
		var newTele = document.getElementById("edit_telephone").value;
		var newStreet = document.getElementById("edit_street").value;
		var newCity = document.getElementById("edit_city").value;
		var newState = document.getElementById("edit_state").value;
		var newZip = document.getElementById("edit_zip").value;
		var recordId =   document.getElementById("edit_id").value;
		
		// send change request to database servlet
		$.ajax({
			type : 'get',
			url : 'AddressesServletNew',
			dataType : 'JSON',
			data : {
				'action' : JSON.stringify("updateAddress"),
				'id' : JSON.stringify(recordId),
				'newName' : JSON.stringify(newName),
				'newEmail' : JSON.stringify(newEmail),
				'newTele' : JSON.stringify(newTele),
				'newStreet' : JSON.stringify(newStreet),
				'newCity' : JSON.stringify(newCity),
				'newState' : JSON.stringify(newState),
				'newZip' : JSON.stringify(newZip)
			},
			success : function(data) {
				alert(data);
				// populateAddressTable(data);
			},
			error : function(data) {
				alert('fail');
			}
		});

	}
	// dialog = $("#dialog-form").dialog({
	// autoOpen : false,
	// height : 400,
	// width : 350,
	// modal : true,
	// buttons : {
	// "Save" : changeEntry,
	// Cancel : function() {
	// dialog.dialog("close");
	// }
	// },
	// close : function() {
	// form[0].reset();
	// allFields.removeClass("ui-state-error");
	// }
	// });
	//
	// form = dialog.find("form").on("submit", function(event) {
	// event.preventDefault();
	// addUser();
	// });
	//
	// $("#create-user").button().on("click", function() {
	// dialog.dialog("open");
	// });
	//
}

function deleteEntry(id) {
	alert("delete entry called for index " + id);

	$.ajax({
		type : 'get',
		url : 'AddressesServletNew',
		dataType : 'JSON',
		data : {
			'action' : JSON.stringify("deleteAddress"),
			'id' : id
		},
		success : function(data) {
			alert(data);
			s
			// populateAddressTable(data);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

// function changeEntry(id) {
// alert("change entry called for index " + id);
//
// // get row values
// var tableRows = document.getElementById("address_table").rows;
//
// var newName = document.getElementById(id + "name").value;
// var newEmail = document.getElementById(id + "email").value;
// var newTele = document.getElementById(id + "telephone").value;
// var newStreet = document.getElementById(id + "street").value;
// var newCity = document.getElementById(id + "city").value;
// var newState = document.getElementById(id + "state").value;
// var newZip = document.getElementById(id + "zip").value;
//
// // bring up dialog here to allow user to change values
//
// // send change request to database servlet
// $.ajax({
// type : 'get',
// url : 'AddressesServletNew',
// dataType : 'JSON',
// data : {
// 'action' : JSON.stringify("updateAddress"),
// 'id' : JSON.stringify(id),
// 'newName' : JSON.stringify(newName),
// 'newEmail' : JSON.stringify(newEmail),
// 'newTele' : JSON.stringify(newTele),
// 'newStreet' : JSON.stringify(Street),
// 'newCity' : JSON.stringify(newCity),
// 'newState' : JSON.stringify(newState),
// 'newZip' : JSON.stringify(newZip)
// },
// success : function(data) {
// alert(data);
// // populateAddressTable(data);
// },
// error : function(data) {
// alert('fail');
// }
// });
// }
