<%@ page import="java.util.List" %>
<%@ page import="com.entity.Booking" %>

<%@ include file="../all_component/navbar.jsp" %>

<div class="container mt-4">
<h4><i class="fas fa-sign-in-alt"></i> Check-In / Check-Out</h4>

<table class="table table-bordered">
<thead class="thead-dark">
<tr>
    <th>Booking ID</th>
    <th>Guest Name</th>
    <th>Room</th>
    <th>Status</th>
    <th>Check-In</th>
    <th>Check-Out</th>
</tr>
</thead>

<tbody>
<%
List<Booking> list = (List<Booking>) request.getAttribute("bookingList");
for (Booking b : list) {
%>
<tr>
<td><%= b.getBookingId() %></td>
<td><%= b.getGuestName() %></td>
<td><%= b.getRoomNo() %></td>
<td><%= b.getStatus() %></td>

<td>
<% if ("BOOKED".equals(b.getStatus())) { %>
<form action="../checkInOut" method="post">
    <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
    <input type="hidden" name="action" value="checkin">

    <input type="datetime-local"
           name="checkinDatetime"
           class="form-control mb-2"
           required>

    <button class="btn btn-success btn-sm">
        Check-In
    </button>
</form>

<% } else { %>
<%= b.getCheckinDatetime() %>
<% } %>
</td>

<td>
<% if ("CHECKED_IN".equals(b.getStatus())) { %>
<form action="../checkInOut" method="post">
    <input type="hidden" name="bookingId" value="<%= b.getBookingId() %>">
    <input type="hidden" name="action" value="checkout">

    <input type="datetime-local"
           name="checkoutDatetime"
           class="form-control mb-2"
           required>

    <button class="btn btn-danger btn-sm">
        Check-Out
    </button>
</form>

<% } else { %>
<%= b.getCheckoutDatetime() %>
<% } %>
</td>
</tr>
<% } %>
</tbody>
</table>
</div>

<%@ include file="../all_component/footer.jsp" %>
