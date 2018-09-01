//validate add user form
function checkRequiredField(){
	debugger;
	var result = true;
	if($('#cidNo').val()==''){
		$('#idErrorMessage').html("Enter CID Number");
		$('#errorDiv').show();
		$('#errorDiv').focus();
		result=false;
	}
	
	if($('#employeeId').val()==''){
		$('#idErrorMessage').html("Enter Employee ID");
		$('#errorDiv').show();
		$('#errorDiv').focus();
		result=false;
	}
	
	if($('#email').val()==''){
		$('#idErrorMessage').html("Enter Email ID");
		$('#errorDiv').show();
		$('#errorDiv').focus();
		result=false;
	}
	
	if($('#mobileNo').val()==''){
		$('#idErrorMessage').html("Enter Mobile Number");
		$('#errorDiv').show();
		$('#errorDiv').focus();
		result=false;
	}
	return result;
}




function editUser(userid){
	alert("hi");
	$('document').ready(function(){
		$.ajax({
 			type: 'GET',
 			url: "<%= request.getContextPath()%>/UserAction.do?method=fetchUserDtls&userId="+userid,
 			data : $('form').serialize(),
 			cache : false,
 			success: function(xml){
 				$(xml).find('xml-response').each(function(){
	                	i=i+10;
	                    var firstName = $(this).find('first-name').text();
	                    var middleName = $(this).find('middle-name').text();
	                    var lastName = $(this).find('last-name').text();
	                    
	                    
	                    if(firstName==''){
	        				//this means no record has been found
	        				$('#idErrorMessage').html('This CID number is invalid');
	        				$('#firstName').val('');
	        				$('#middlename').val('');
	        				$('#lastname').val('');	
	        				$('#errorDiv').show();
		        			}
	                    else{
	        				$('#idErrorMessage').html('');
	        				$('#firstName').val(firstName);
	        				$('#firstName').attr('readonly', 'readonly');
	        				$('#middlename').val(middleName);
	        				$('#middlename').attr('readonly', 'readonly');
	        				$('#lastname').val(lastName);	
	        				$('#lastname').attr('readonly', 'readonly'); 
	        				$('#errorDiv').hide();
	        			}  
	        			$('#progress').hide();
	        	});                       
			},
			error: function(data, textStatus, errorThrown) {
				// if there is some error handling mechanism that needs to be implemented
				alert("there is an error");
			}
		});
	});
}