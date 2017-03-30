<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="JS/application.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/webui.css">

<title>Addresses Database</title>
</head>


<body>
<h1>Addresses coding assignment for Kenzan</h1>

<h2>Address List:</h2>
<table id="address_table">
  <tr>
    <td><strong>ID</strong></td>
    <td><strong>Name</strong></td>
    <td><strong>Email </strong></td>
    <td><strong>Actions</strong></td>
  </tr>  
</table>

<br>
<br>

<h2>Add New Entry:</h2>
<form id="contacts-form">
    <div class="item text">
        <label>Name:</label>
        <div class="field"><input type="text" name="name" /></div>
    </div>
    <div class="item text">
        <label>Email:</label>
        <div class="field"><input type="text" name="email" /></div>
    </div>
    <div class="item text">
        <label>Telephone:</label>
        <div class="field"><input type="text" name="telephone" /></div>
    </div>
    <div class="item text">
        <label>Street:</label>
        <div class="field"><input type="text" name="street" /></div>
    </div>
    <div class="item text">
        <label>City:</label>
        <div class="field"><input type="text" name="city" /></div>
    </div>
    <div class="item text">
        <label>State:</label>
        <div class="field"><input type="text" name="state" /></div>
    </div>
    <div class="item text">
        <label>ZipCode:</label>
        <div class="field"><input type="text" name="zipcode" /></div>
    </div>
    <div class="button-wrapper">
        <div class="item button">
            <div class="field"><input type="button" id="contacts-op-discard" value="Discard" /></div>
        </div>
        <div class="item button button-default">
            <div class="field"><input type="submit" id="contacts-op-save" value="Save" /></div>
        </div>
    </div>
    <input type="hidden" name="id_entry" value="0" />
</form>

</body>
</html>