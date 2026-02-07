<%@ page import="com.entity.PrintBill" %>
<%
PrintBill b = (PrintBill) request.getAttribute("bill");
%>

<!DOCTYPE html>
<html>
<head>
<title>Guest House Bill</title>

<style>
body {
    font-family: Arial;
}
.bill-box {
    width: 210mm;
    margin: auto;
    padding: 20px;
    border: 1px solid #000;
}
h3, h4 {
    text-align: center;
    margin: 5px;
}
table {
    width: 100%;
    border-collapse: collapse;
}
td, th {
    border: 1px solid #000;
    padding: 6px;
}
.no-print {
    margin: 15px;
    text-align: center;
}
@media print {
    .no-print {
        display: none;
    }
}
</style>
</head>

<body>

<div class="bill-box">

<h3>NATIONAL FERTILIZERS LIMITED</h3>
<h4>Satkar Guest House Panipat Unit</h4>
<hr>

<table>
<tr><th>Booking ID</th><td><%= b.getBookingId() %></td></tr>
<tr><th>Guest Name</th><td><%= b.getGuestName() %></td></tr>
<tr><th>Mobile</th><td><%= b.getMobile() %></td></tr>
<tr><th>Address</th><td><%= b.getAddress() %></td></tr>
<tr><th>Room No</th><td><%= b.getRoomNo() %></td></tr>
<tr><th>Check-in</th><td><%= b.getCheckinDatetime() %></td></tr>
<tr><th>Check-out</th><td><%= b.getCheckoutDatetime() %></td></tr>
<tr><th>No. of Days</th><td><%= b.getNoOfDays() %></td></tr>
</table>

<br>

<table>
<tr>
    <th>Description</th>
    <th>Amount (&#8377;)</th>
</tr>
<tr>
    <td>Room Charges</td>
    <td><%= b.getRoomCharge() %></td>
</tr>
<tr>
    <td>Food Charges</td>
    <td><%= b.getFoodTotal() %></td>
</tr>
<tr>
    <td>Other Charges</td>
    <td><%= b.getOtherCharges() %></td>
</tr>
<tr>
    <th>Total</th>
    <th><%= b.getGrandTotal() %></th>
</tr>
</table>

<p><b>Payment Mode:</b> <%= b.getPaymentMode() %></p>

<br><br>
<p>Signature (Reception)</p>
<p>Date: <%= new java.util.Date() %></p>

</div>

<div class="no-print">
    <button onclick="window.print()">Print Bill</button>
    <button onclick="window.history.back()">Back</button>
</div>

</body>
</html>
