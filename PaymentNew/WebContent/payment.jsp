<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="model.Payment"%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Details</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>
	<div class="container">  
		<div class="row">
			<div class="col-6">
				<h1>Payment Details</h1>
				<form id="formPayment" name="formPayment">
					Pay User Name: 
					<input id="payName" name="payName" type="text" class="form-control form-control-sm"> 
					<br> Pay Amount:
					<input id="payAmount" name="payAmount" type="text" class="form-control form-control-sm"> 
					<br> Pay Date: 
					<input id="payDate" name="payDate" type="text" class="form-control form-control-sm"> 
					<br> Pay CardType: 
					<input id="payCardType" name="payCardType" type="text" class="form-control form-control-sm">
					<br> Pay CardNo: 
					<input id="payCardNo" name="payCardNo" type="text" class="form-control form-control-sm">
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
					<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
					Payment payObj = new Payment();
					out.print(payObj.readPay());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
