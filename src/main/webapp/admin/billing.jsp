<%@ page import="com.entity.Billing" %>
<%@ include file="../all_component/navbar.jsp" %>
<%@ include file="/all_component/allCss.jsp" %>

<%
Billing b = (Billing) request.getAttribute("bill");
%>

<div class="container mt-4">
<h4>Generate Bill</h4>

<form action="<%=request.getContextPath()%>/billing" method="post">

<input type="hidden"
       name="checkoutDatetime"
       value="<%= request.getAttribute("checkoutTs") != null
                ? request.getAttribute("checkoutTs").toString().replace(" ", "T").substring(0,16)
                : "" %>">

<input type="hidden"
       name="bookingId"
       value="<%= b.getBookingId() %>">


<table class="table table-bordered">
<tr><th>Guest</th><td><%= b.getGuestName() %></td></tr>
<tr><th>Room</th><td><%= b.getRoomNo() %></td></tr>
<tr><th>Check-in</th><td><%= b.getCheckinDatetime() %></td></tr>


<tr><th>Room Rent / Day</th><td>&#8377; <%= b.getRoomRent() %></td></tr>

<tr>
    <th>No. of Days</th>
    <td>
        <input type="text" id="noOfDays"
               class="form-control"
               readonly>
               
               
               <input type="hidden"
       id="checkoutTs"
       value="<%= request.getAttribute("checkoutTs") %>">
               
    </td>
</tr>

<tr>
    <th>Room Charge</th>
    <td>
        <input type="text" id="roomCharge"
               class="form-control"
               readonly>
    </td>
</tr>

</table>



<label>Breakfast Charge</label>
<input type="number" step="0.01"
       name="breakfast"
       class="form-control" value="0">

<label class="mt-2">Lunch Charge</label>
<input type="number" step="0.01"
       name="lunch"
       class="form-control" value="0">

<label class="mt-2">Dinner Charge</label>
<input type="number" step="0.01"
       name="dinner"
       class="form-control" value="0">

<label class="mt-2">Other Charges</label>
<input type="number" step="0.01"
       name="otherCharges"
       class="form-control" value="0">


<label class="mt-2">Payment Mode</label><br>
<input type="radio" name="paymentMode" value="CASH" required> Cash
<input type="radio" name="paymentMode" value="ONLINE"> Online

<button class="btn btn-danger mt-4">
Confirm Checkout & Generate Bill
</button>

</form>
</div>

<%@ include file="../all_component/footer.jsp" %>

<script>
const checkinTs = new Date("<%= b.getCheckinDatetime().toString().replace(" ", "T").substring(0,16) %>");
const checkoutTs = new Date("<%= request.getAttribute("checkoutTs") != null
        ? request.getAttribute("checkoutTs").toString().replace(" ", "T").substring(0,16)
        : "" %>");
const roomRent = <%= b.getRoomRent() %>;

if (checkoutTs <= checkinTs) {
    alert("Invalid checkout time");
}

// PSU logic: minimum 1 day
const diffMs = checkoutTs - checkinTs;
const days = Math.max(1, Math.ceil(diffMs / (1000 * 60 * 60 * 24)));

document.getElementById("noOfDays").value = days;
document.getElementById("roomCharge").value = (days * roomRent).toFixed(2);
</script>

