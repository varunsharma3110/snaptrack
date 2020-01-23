
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Dashboard</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
 <script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
 <script
	src="http://cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/a549aa8780dbda16f6cff545aeabc3d71073911e/src/js/bootstrap-datetimepicker.js"></script>
	<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<style type="text/css">

</style>
</head>
<body>
<br>
<br>
<center>
<button type="button" class="btn btn-default" id="fetchAllRecords">Fetch All Records </button>
</center>
<br>
<br>
<center>
<table class="table" id="tab_logic">
  </table>
</center>

</body>

<script type="text/javascript">

$('#fetchAllRecords').click(function() {
        $.ajax({
				type : "GET",
				dataType : 'json',
				async : false,
				url : "getFromData",
				success : function(data) {
					t = data.results;
					console.log(t);
					fetchAllRecords(t)
					for( var i=0 ;i <t.length ;i++) {
					console.log(t[i].orderId);
					}

				},
				error : function() {
					alert("error");
				}

			});

        ;
    });

    function fetchAllRecords(t) {
    $('#tab_logic').empty();
    var html="";
    html+="<thead> <tr>  <th scope='col'>Order Id</th> <th scope='col'>Latitude</th> <th scope='col'>Longitude</th> <th scope='col'>OTP</th> <th scope='col'>Caller Status</th> <th scope='col'>Called Status</th> <th scope='col'>Call Unit</th> </tr> </thead> <tbody> ";
    for (var i = 0; i < t.length ; i++) {
      html+="<tr> <td onclick='tableFunction(this)'> <p id='trow"+i+"'>" + t[i].orderId  + "</p></td> <td> <p> " + t[i].latitude +"</p></td> <td> <p>  " + t[i].longitude + "</p></td> <td> <p> " +t[i].otp +"</p></td> <td> <p>  " + t[i].callerStatus + "</p></td> <td>  <p> " +  t[i].calledStatus + " </p></td> <td> <p> "+  t[i].callDuration  +" </p></td></tr>"
    }
    html+="</tbody>";
    $('#tab_logic').append(html);

    }
    function tableFunction(i){

    var txt = i.innerText;
     alert(txt);

    }

</script>
</html>
