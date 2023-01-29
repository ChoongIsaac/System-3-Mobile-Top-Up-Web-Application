<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OTP</title>
</head>
<body>
<%
    String id = (String) session.getAttribute("id");
    String payment = (String) session.getAttribute("payment"); 
  
%>
<form method="post" action="verifyOTPServlet">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="payment" value="<%=payment%>">
OTP: <input type ="text" name="otp"><input type="submit" value="submit">
</form>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if(errorMessage != null){
        out.println("<p style='color:red'>" + errorMessage + "</p>");
    }
%>
</body>
</html>