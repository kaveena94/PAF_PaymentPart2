$(document).ready(function()
{
	$("#alertSuccess").hide();
	
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
	
	//Form validation-------------------
	var status = validatePaymentForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	

	// If valid-------------------------
	
	//$("#formPayment").submit();
	
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
			{
				url : "PaymentAPI",
				type : type,
				data : $("#formPayment").serialize(),
				dataType : "text",
				complete : function(response, status) {
					onPaymentSaveComplete(response.responseText, status);
				}
			});
 });

function onPaymentSaveComplete(response, status) {
	if (status == "success")
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success")
				{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divPaymentGrid").html(resultSet.data);
				}else if (resultSet.status.trim() == "error")
					{
					$("#alertError").text(resultSet.data);
					$("#alertError").show();
					
					}
		}else if (status == "error")
		{
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		}else
			{
			$("#alertError").text("UnKnown error while saving..");
			$("#alertError").show();
			}
	
		$("#hidPaymentIDSave").val("");
		$("#formPayment")[0].reset();
	
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
	$("#payName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#payAmount").val($(this).closest("tr").find('td:eq(1)').text());
	$("#payDate").val($(this).closest("tr").find('td:eq(2)').text());
	$("#payCardType").val($(this).closest("tr").find('td:eq(3)').text());
	$("#payCardNo").val($(this).closest("tr").find('td:eq(4)').text());
});

// REMOVE=========================================================
$(document).on("click", ".btnRemove", function(event)
		{
			$.ajax(
					{
						url : "PaymentAPI",
						type : "DELETE",
						data : "payID=" + $(this).data("payid"),
						dataType : "text",
						complete : function(response, status)
						{
							onPaymentDeleteComplete(response.responseText, ststus);
						}
					});
		});

function onPaymentDeleteComplete(response, status) {
	if (status == "success")
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success")
				{
				$("#alertSuccess").text("Successfully Delete.");
				$("#alertSuccess").show();
				
				$("#divPaymentGrid").html(resultSet.data);
				}else if (resultSet.status.trim() == "error")
					{
					$("#alertError").text(resultSet.data);
					$("#alertError").show();
					
					}
		}else if (status == "error")
		{
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		}else
			{
			$("#alertError").text("UnKnown error while deleting..");
			$("#alertError").show();
			}
	
		$("#hidPaymentIDSave").val("");
		$("#formPayment")[0].reset();
	
}

//CLIENT-MODEL================================================================
function validatePaymentForm()
{
	// name
	if ($("#payName").val().trim() == "")
	{
		return "Insert name.";
	}
	
	// amount
	if ($("#payAmount").val().trim() == "")
	{
		return "Insert amount.";
	}

	// is numerical value
	var tmpPrice = $("#payAmount").val().trim();
	if (!$.isNumeric(tmpPrice))
	{
		return "Insert a numerical value for amount.";
	}	

	// convert to decimal amount
	$("#payAmount").val(parseFloat(tmpPrice).toFixed(2));

	// date
	if ($("#payDate").val().trim() == "")
	{
		return "Insert date.";
	}
	
	// card type
	if ($("#payCardType").val().trim() == "")
	{
		return "Insert card type.";
	}
	
	// card no
	if ($("#payCardNo").val().trim() == "")
	{
		return "Insert card no.";
	}
	
	
	return true;
	}



