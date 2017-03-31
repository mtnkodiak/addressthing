<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="JS/application.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>jQuery UI Dialog - Default functionality</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.13/js/dataTables.jqueryui.min.js"></script>

<link rel="stylesheet" type="text/css" href="CSS/webui.css">

<title>Addresses Database</title>

</head>


<body>
	<h1>Addresses coding assignment for Kenzan</h1>

	<h2>Address List:</h2>
	<table id="address_table">
	</table>

	<br>
	<br>

	<button id="create_button" onclick="createNewEntry();" title="Add New Address Entry">
	Add New Address Entry
	</button>
	
	<form id="new_address_form" style="display:none">
		<div class="item text">
			<label>Name:</label>
			<div class="field">
				<input type="text" name="name" />
			</div>
		</div>
		<div class="item text">
			<label>Email:</label>
			<div class="field">
				<input type="text" name="email" />
			</div>
		</div>
		<div class="item text">
			<label>Telephone:</label>
			<div class="field">
				<input type="text" name="telephone" />
			</div>
		</div>
		<div class="item text">
			<label>Street:</label>
			<div class="field">
				<input type="text" name="street" />
			</div>
		</div>
		<div class="item text">
			<label>City:</label>
			<div class="field">
				<input type="text" name="city" />
			</div>
		</div>
		<div class="item text">
			<label>State:</label>
			<div class="field">
				<input type="text" name="state" />
			</div>
		</div>
		<div class="item text">
			<label>ZipCode:</label>
			<div class="field">
				<input type="text" name="zipcode" />
			</div>
		</div>
		<div class="button-wrapper">
			<div class="item button">
				<div class="field">
					<input type="button" id="contacts-op-discard" value="Discard" />
				</div>
			</div>
			<div class="item button button-default">
				<div class="field">
					<input type="submit" id="contacts-op-save" value="Save" />
				</div>
			</div>
		</div>
		<input type="hidden" name="id_entry" value="0" />
	</form>

<!-- HIDDEN FORM ELEMENTS -->
<div id="edit-dialog-form" title="Edit entry" style="display:none">
  <form>
    <fieldset>
      <label for="edit_name">Name</label>
      <input type="text" name="name" id="edit_name" class="text ui-widget-content ui-corner-all">
      <label for="edit_email">Email</label>
      <input type="text" name="email" id="edit_email" class="text ui-widget-content ui-corner-all">
      <label for="edit_telephone">Telephone</label>
      <input type="text" name="telephone" id="edit_telephone" class="text ui-widget-content ui-corner-all">
      <label for="edit_street">Street</label>
      <input type="text" name="street" id="edit_street" class="text ui-widget-content ui-corner-all">
      <label for="edit_city">City</label>
      <input type="text" name="city" id="edit_city" class="text ui-widget-content ui-corner-all">
      <label for="edit_state">State</label>
      <input type="text" name="state" id="edit_state" class="text ui-widget-content ui-corner-all">
      <label for="edit_zip">ZipCode</label>
      <input type="text" name="zip" id="edit_zip" class="text ui-widget-content ui-corner-all">
      <input type="text" name="id" id="edit_id" style="display:none" class="text ui-widget-content ui-corner-all">
 
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>

<div id="create-dialog-form" title="Create entry" style="display:none">
  <form>
    <fieldset>
      <label for="create_name">Name</label>
      <input type="text" name="name" id="create_name" class="text ui-widget-content ui-corner-all">
      <label for="create_email">Email</label>
      <input type="text" name="email" id="create_email" class="text ui-widget-content ui-corner-all">
      <label for="create_telephone">Telephone</label>
      <input type="text" name="telephone" id="create_telephone" class="text ui-widget-content ui-corner-all">
      <label for="create_street">Street</label>
      <input type="text" name="street" id="create_street" class="text ui-widget-content ui-corner-all">
      <label for="create_city">City</label>
      <input type="text" name="city" id="create_city" class="text ui-widget-content ui-corner-all">
      <label for="create_state">State</label>
      <input type="text" name="state" id="create_state" class="text ui-widget-content ui-corner-all">
      <label for="create_zip">ZipCode</label>
      <input type="text" name="zip" id="create_zip" class="text ui-widget-content ui-corner-all">
      <input type="text" name="id" id="create_id" style="display:none" class="text ui-widget-content ui-corner-all">
 
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>

</body>
</html>