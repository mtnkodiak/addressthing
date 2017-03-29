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

<h1>Address List</h1>
<table width="600" id="address_table">
  <tr>
    <td><strong>ID</strong></td>
    <td><strong>Name</strong></td>
    <td><strong>Email </strong></td>
    <td><strong>Actions</strong></td>
  </tr>  
</table>


<form id="contacts-form">
    <div class="item text">
        <label>First name:</label>
        <div class="field"><input type="text" name="first_name" /></div>
    </div>
    <div class="item text">
        <label>Last name:</label>
        <div class="field"><input type="text" name="last_name" /></div>
    </div>
    <div class="item text">
        <label>Email:</label>
        <div class="field"><input type="text" name="email" /></div>
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