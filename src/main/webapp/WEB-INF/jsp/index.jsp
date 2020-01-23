
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
 <style>

	.node circle {
	  fill: #fff;
	  stroke: steelblue;
	  stroke-width: 3px;
	}

	.node text { font: 12px sans-serif; }

	.link {
	  fill: none;
	  stroke: #ccc;
	  stroke-width: 2px;
	}

    </style>

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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js"></script>

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

<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body" id="modalInnerText">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

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


    });

    function fetchAllRecords(t) {
    $('#tab_logic').empty();
    var html="";
    html+="<thead> <tr>  <th scope='col'>Order Id</th> <th scope='col'>Latitude</th> <th scope='col'>Longitude</th> <th scope='col'>OTP</th> <th scope='col'>Caller Status</th> <th scope='col'>Called Status</th> <th scope='col'>Call Unit</th> <th scope='col'>Date</th><th scope='col'>RTO Reason</th></tr> </thead> <tbody> ";
    for (var i = 0; i < t.length ; i++) {
      html+="<tr> <td onclick='tableFunction(this)' data-toggle='modal' data-target='#myModal'> <p id='trow"+i+"'>" + t[i].orderId  + "</p></td> <td> <p> " + t[i].latitude +"</p></td> <td> <p>  " + t[i].longitude + "</p></td> <td> <p> " +t[i].otp +"</p></td> <td> <p>  " + t[i].callerStatus + "</p></td> <td>  <p> " +  t[i].calledStatus + " </p></td> <td> <p> "+  t[i].callDuration  +" </p></td> <td> <p> "+  t[i].created  +" </p></td><td> <p> "+  t[i].rtoReason  +" </p></td></tr>"
    }
    html+="</tbody>";
    $('#tab_logic').append(html);

    }
    function tableFunction(i){

    var txt = i.innerText;
     $.ajax({
    				type : "GET",
    				data : {
                    		orderId : txt
                    },
    				dataType : 'json',
    				async : false,
    				url : "getDecisonTreeFromOrderId",
    				success : function(data) {
    				callTree(data);
    					console.log(data);

    				},
    				error : function() {
    					alert("error");
    				}

    			});

    }
    function callTree(treeString) {
        var margin = {top: 20, right: 120, bottom: 20, left: 120},
        	width = 960 - margin.right - margin.left,
        	height = 500 - margin.top - margin.bottom;

        var i = 0;

        var tree = d3.layout.tree()
        	.size([height, width]);

        var diagonal = d3.svg.diagonal()
        	.projection(function(d) { return [d.y, d.x]; });

        var svg = d3.select("#modalInnerText").append("svg")
        	.attr("width", width + margin.right + margin.left)
        	.attr("height", height + margin.top + margin.bottom)
          .append("g")
        	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        // load the external data
        //d3.json("treeData.json", function(error, treeData) {
        //  root = treeData[0];
        //  update(root);
        //});
        //var jsonObj = new Packages.com.snapdeal.snaptrack.SnapTrackApplication()
        var jsonStr = "[\n" +
                       "  {\n" +
                       "  \"name\": \"OTPAvailable\",\n" +
                       "  \"parent\": \"null\",\n" +
                       "  \"children\": []\n" +
                       "}\n" +
                       "]";
        var root = JSON.parse(jsonStr);
        var nodes = tree.nodes(root).reverse(),
            	  links = tree.links(nodes);

              // Normalize for fixed-depth.
              nodes.forEach(function(d) { d.y = d.depth * 180; });

              // Declare the nodes…
              var node = svg.selectAll("g.node")
            	  .data(nodes, function(d) { return d.id || (d.id = ++i); });

              // Enter the nodes.
              var nodeEnter = node.enter().append("g")
            	  .attr("class", "node")
            	  .attr("transform", function(d) {
            		  return "translate(" + d.y + "," + d.x + ")"; });

              nodeEnter.append("circle")
            	  .attr("r", 10)
            	  .style("fill", "#fff");

              nodeEnter.append("text")
            	  .attr("x", function(d) {
            		  return d.children || d._children ? -13 : 13; })
            	  .attr("dy", ".35em")
            	  .attr("text-anchor", function(d) {
            		  return d.children || d._children ? "end" : "start"; })
            	  .text(function(d) { return d.name; })
            	  .style("fill-opacity", 1);

              // Declare the links…
              var link = svg.selectAll("path.link")
            	  .data(links, function(d) { return d.target.id; });

              // Enter the links.
              link.enter().insert("path", "g")
            	  .attr("class", "link")
            	  .attr("d", diagonal);

    }

    function update(source) {

      // Compute the new tree layout.


    }

</script>
</html>
