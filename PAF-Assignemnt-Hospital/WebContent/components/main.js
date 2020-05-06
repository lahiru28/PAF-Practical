$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateHospitalForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidAppIDSave").val() == "") ? "POST" : "PUT";
	var hidAppIDSave = $('#hidAppIDSave').val();
	var hName = $('#hName').val();
	var hAddress = $('#hAddress').val();
	var hPhone = $('#hPhone').val();
	var hEmail = $('#hEmail').val();
	
	$.ajax(
	{
		url : "HospitalAPI",
		type : t,
		data : "hName=" + hName + "&hAddress=" + hAddress  + "&hPhone=" + hPhone + "&hEmail=" + hEmail  + "&hidAppIDSave=" + hidAppIDSave,
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalSaveComplete(response.responseText, status);
		}
	});
}); 

function onHospitalSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidAppIDSave").val("");
	$("#formHospital")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidAppIDSave").val($(this).closest("tr").find('#hidAppIDUpdate').val());     
	$("#hName").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#hAddress").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#hPhone").val($(this).closest("tr").find('td:eq(2)').text());      
	$("#hEmail").val($(this).closest("tr").find('td:eq(3)').text()); 


});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "HospitalAPI",
		type : "DELETE",
		data : "hId=" + $(this).data("id"),
		dataType : "text",
		complete : function(response, status)
		{
			onHospitalDeletedComplete(response.responseText, status);
		}
	});
});

function onHospitalDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateHospitalForm() {  
	// NAME  
	if ($("#hName").val().trim() == "")  {   
		return "Insert hospital name.";  
		
	} 
	
	// Address  
	if ($("#hAddress").val().trim() == "")  {   
		return "Insert address.";  
		
	} 
	
	 // MOBILE  
	if ($("#hPhone").val().trim() == "")  {   
		return "Insert Mobile.";  
		
	} 
	 
	 // is numerical value  
	var tmpMobile = $("#hPhone").val().trim();  
	if (!$.isNumeric(tmpMobile))  {   
		return "Insert a numerical value for Mobile Number.";  
		
	}
	 
	 // Email 
	if ($("#hEmail").val().trim() == "")  {   
		return "Insert Email.";  
		
	} 
	
 
	 return true; 
	 
}
