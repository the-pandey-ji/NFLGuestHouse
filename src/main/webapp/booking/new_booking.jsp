<%@ page import="java.util.List" %>
<%@ page import="com.entity.Room" %>

<%@ include file="../all_component/navbar.jsp" %>
<%@ include file="/all_component/allCss.jsp" %>
<!-- SUCCESS MESSAGE -->
<%
    String succMsg = (String) session.getAttribute("succMsg");
    if (succMsg != null) {
%>
    <div class="alert alert-success text-center mt-3">
        <%= succMsg %>
    </div>
<%
        session.removeAttribute("succMsg");
    }
%>

<!-- ERROR MESSAGE -->
<%
    String errorMsg = (String) session.getAttribute("errorMsg");
    if (errorMsg != null) {
%>
    <div class="alert alert-danger text-center mt-3">
        <%= errorMsg %>
    </div>
<%
        session.removeAttribute("errorMsg");
    }
%>

<div class="container mt-4">
<h4><i class="fas fa-calendar-plus"></i> New Booking</h4>

<form action="newBooking" method="post">

<div class="form-group">
<label>Guest Name</label>
<input type="text" name="guestName" class="form-control" required>
</div>

<div class="form-group">
<label>Mobile No</label>
<input type="text" name="mobile" class="form-control">
</div>

<div class="form-group">
<label>Guest Category</label>
<select name="guestCategory" class="form-control" required>
    <option value="NFL">NFL Employee</option>
    <option value="GOVT">Govt Employee</option>
    <option value="PRIVATE">Private</option>
</select>
</div>

<div class="form-group">
<label>Guest Type</label>
<select name="guestType" class="form-control" required>
    <option value="OFFICIAL">Official</option>
    <option value="NON_OFFICIAL">Non Official</option>
</select>
</div>

<div class="form-group">
<label>Check-in Date</label>
<input type="date" name="checkinDate" class="form-control" required>
</div>

<div class="form-group">
<label>Select Room</label>
<select name="roomId" class="form-control" required>
<%
List<Room> list = (List<Room>) request.getAttribute("roomList");
if (list != null) {
    for (Room r : list) {
%>
<option value="<%= r.getRoomId() %>">
Room <%= r.getRoomNo() %> - <%= r.getRoomType() %>
</option>
<%
    }
}
%>
</select>
</div>

<button class="btn btn-success mt-3">
<i class="fas fa-save"></i> Save Booking
</button>

</form>
</div>

<%@ include file="../all_component/footer.jsp" %>
