<%@page import="model.Hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/main.js"></script>
</head>
<body>
<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Hospital Details</h1>        
				
				<form id="formHospital" name="formHospital" method="post" action = "Hospital.jsp">  
					 Hospital Name:  
					<input id="hName" name="hName" type="text" class="form-control form-control-sm"> 
					
					<br> 
					 Address:  
					 <input id="hAddress" name="hAddress" type="text" class="form-control form-control-sm">  
					
					<br> 
					Phone Number:  
					<input id="hPhone" name="hPhone" type="text" class="form-control form-control-sm">  
					
					<br>
					 Email:  
					 <input id="hEmail" name="hEmail" type="text" class="form-control form-control-sm">  
					 

					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidAppIDSave" name="hidAppIDSave" value=""> 
					 
				</form> 
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>  
				<div id="divItemsGrid">   
					<%
   						Hospital hosObj = new Hospital();
   									out.print(hosObj.readHospital());
   					%>  
					
				</div> 
				  
 			</div>
 		 
 		</div>    
 		
 
	</div> 

</body>

</html>