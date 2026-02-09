<%@ page import="java.util.List" %>
<%@ page import="com.entity.Room" %>
<%@ page import="com.entity.Booking" %>

<%@ include file="../all_component/navbar.jsp" %>
<%@ include file="/all_component/allCss.jsp" %>

<div class="container mt-4">

<h4 class="mb-4">
<i class="fas fa-door-open"></i> Check-In / Check-Out
</h4>

<!-- ================= CARDS ================= -->
<div class="row text-center">

    <div class="col-md-3 mb-3">
        <div class="card shadow-sm p-3 option-card"
             onclick="showSection('booking')">
            <i class="fas fa-calendar-check fa-2x text-primary"></i>
            <h6 class="mt-2">Check-In<br>(With Booking)</h6>
        </div>
    </div>

    <div class="col-md-3 mb-3">
        <div class="card shadow-sm p-3 option-card"
             onclick="showSection('direct')">
            <i class="fas fa-walking fa-2x text-success"></i>
            <h6 class="mt-2">Direct Check-In<br>(Walk-In)</h6>
        </div>
    </div>

    <div class="col-md-3 mb-3">
        <div class="card shadow-sm p-3 option-card"
             onclick="showSection('checkout')">
            <i class="fas fa-sign-out-alt fa-2x text-danger"></i>
            <h6 class="mt-2">Check-Out</h6>
        </div>
    </div>

    <div class="col-md-3 mb-3">
        <div class="card shadow-sm p-3 option-card"
             onclick="showSection('cancel')">
            <i class="fas fa-times-circle fa-2x text-warning"></i>
            <h6 class="mt-2">Cancel Booking</h6>
        </div>
    </div>

</div>

<hr>

<!-- ================= WITH BOOKING ================= -->
<div id="bookingDiv" class="section" style="display:none;">
<h5>Check-In (With Booking)</h5>

<form action="<%=request.getContextPath()%>/checkInOut"
      method="post"
      enctype="multipart/form-data">
<input type="hidden" name="mode" value="BOOKING">

<label>Booking</label>
<select name="bookingId" class="form-control" required>
    <option value="">-- Booking ID | Guest | Date --</option>

    <%
        List<Booking> bookedList =
            (List<Booking>) request.getAttribute("bookedList");
        if (bookedList != null) {
            for (Booking b : bookedList) {
    %>
        <option value="<%= b.getBookingId() %>">
            <%= b.getBookingId() %> |
            <%= b.getGuestName() %> |
            <%= b.getCreatedDate() %>
        </option>
    <%
            }
        }
    %>
</select>

<label class="mt-2">Check-In Date & Time</label>
<input type="datetime-local" name="checkinDatetime"
       class="form-control" required>
       
       <label class="mt-2">Address</label>
<textarea name="address"
          class="form-control"
          required></textarea>

<label class="mt-2">ID Type</label>
<select name="idType" class="form-control" required>
    <option value="">-- Select --</option>
    <option>Aadhaar</option>
    <option>Passport</option>
    <option>Voter ID</option>
    <option>Official ID</option>
</select>

<label class="mt-2">ID Number</label>
<input type="text" name="idNumber"
       class="form-control" required>

<label class="mt-2">ID Proof Photo</label>
<input type="file" name="idPhoto"
       class="form-control"
       accept="image/*"
       required>

<label class="mt-2">Guest Live Photo</label>
<input type="file" name="guestPhoto"
       class="form-control"
       accept="image/*"
       required>
       

<button class="btn btn-primary mt-3">Confirm Check-In</button>
</form>
</div>

<!-- ================= DIRECT CHECK-IN ================= -->
<div id="directDiv" class="section" style="display:none;">
<h5>Direct Check-In (Walk-In)</h5>

<form action="<%=request.getContextPath()%>/checkInOut"
      method="post"
      enctype="multipart/form-data">
<input type="hidden" name="mode" value="DIRECT">

<label>Room</label>
<select name="roomId" class="form-control" required>
    <option value="">-- Select Room --</option>
    <%
        List<Room> roomList =
            (List<Room>) request.getAttribute("availableRooms");
        if (roomList != null) {
            for (Room r : roomList) {
    %>
        <option value="<%= r.getRoomId() %>">
            <%= r.getRoomNo() %> - <%= r.getRoomType() %>
        </option>
    <%
            }
        }
    %>
</select>

<label class="mt-2">Guest Name</label>
<input type="text" name="guestName" class="form-control" required>

<label class="mt-2">Mobile</label>
<input type="text" name="mobile" class="form-control" required>

<label class="mt-2">Guest Category</label>
<select name="guestCategory" class="form-control">
    <option>NFL</option>
    <option>GOVT</option>
    <option>PRIVATE</option>
</select>

<label class="mt-2">Guest Type</label>
<select name="guestType" class="form-control">
    <option>OFFICIAL</option>
    <option>NON_OFFICIAL</option>
</select>

<label class="mt-2">Check-In Date & Time</label>
<input type="datetime-local" name="checkinDatetime"
       class="form-control" required>
       <label class="mt-2">Address</label>
<textarea name="address"
          class="form-control"
          required></textarea>

<label class="mt-2">ID Type</label>
<select name="idType" class="form-control" required>
    <option value="">-- Select --</option>
    <option>Aadhaar</option>
    <option>Passport</option>
    <option>Voter ID</option>
    <option>Official ID</option>
</select>

<label class="mt-2">ID Number</label>
<input type="text" name="idNumber"
       class="form-control" required>

<label class="mt-2">ID Proof Photo</label>
<input type="file" name="idPhoto"
       class="form-control"
       accept="image/*"
       required>

<label class="mt-2">Guest Live Photo</label>
<input type="file" name="guestPhoto"
       class="form-control"
       accept="image/*"
       required>
       

<button class="btn btn-success mt-3">Direct Check-In</button>
</form>
</div>

<!-- ================= CHECK-OUT ================= -->
<%-- <div id="checkoutDiv" class="section" style="display:none;">
<h5>Check-Out</h5>

<form action="<%=request.getContextPath()%>/checkInOut" method="post">
<input type="hidden" name="mode" value="CHECKOUT">

<label>Booking ID</label>
<input type="number" name="bookingId" class="form-control" required>

<label class="mt-2">Check-Out Date & Time</label>
<input type="datetime-local" name="checkoutDatetime"
       class="form-control" required>

<button class="btn btn-danger mt-3">Confirm Check-Out</button>
</form>
</div> --%>

<!-- ================= CHECK-OUT ================= -->
<div id="checkoutDiv" class="section" style="display:none;">
<h5>Check-Out (Generate Bill)</h5>

<form action="<%=request.getContextPath()%>/billing" method="get">

<input type="hidden" name="fromCheckout" value="true">

<label>Select Checked-In Guest</label>
<select name="bookingId" class="form-control" required>
    <option value="">-- Booking | Guest | Room | Check-In --</option>

    <%
        List<Booking> checkedInList =
            (List<Booking>) request.getAttribute("checkedInList");

        if (checkedInList != null) {
            for (Booking b : checkedInList) {
    %>
        <option value="<%= b.getBookingId() %>">
            <%= b.getBookingId() %> |
            <%= b.getGuestName() %> |
            Room <%= b.getRoomNo() %> |
            <%= b.getCheckinDatetime() %>
        </option>
    <%
            }
        }
    %>
</select>

<label class="mt-3">Check-Out Date & Time</label>
<input type="datetime-local"
       name="checkoutDatetime"
       class="form-control"
       required>
<p class="text-muted mt-2">
Only currently checked-in guests are shown.
</p>

<button class="btn btn-danger mt-3">
Proceed to Billing
</button>

</form>
</div>



<!-- ================= CANCEL BOOKING ================= -->
<!-- ================= CANCEL BOOKING ================= -->
<div id="cancelDiv" class="section" style="display:none;">
<h5>Cancel Booking</h5>

<form action="<%=request.getContextPath()%>/checkInOut" method="post">
<input type="hidden" name="mode" value="CANCEL">

<label>Select Booking to Cancel</label>
<select name="bookingId" class="form-control" required>
    <option value="">-- Booking ID | Guest | Date --</option>

    <%
        bookedList =
            (List<Booking>) request.getAttribute("bookedList");

        if (bookedList != null) {
            for (Booking b : bookedList) {
    %>
        <option value="<%= b.getBookingId() %>">
            <%= b.getBookingId() %> |
            <%= b.getGuestName() %> |
            <%= b.getCreatedDate() %>
        </option>
    <%
            }
        }
    %>
</select>

<p class="text-muted mt-2">
Only <b>BOOKED</b> reservations are shown.
</p>

<button class="btn btn-warning mt-3">
Cancel Booking
</button>
</form>
</div>


</div>

<script>
function showSection(id) {
    document.querySelectorAll('.section')
        .forEach(s => s.style.display = 'none');

    document.getElementById(id + "Div").style.display = "block";
}
</script>

<style>
.option-card {
    cursor: pointer;
    transition: 0.2s;
}
.option-card:hover {
    transform: scale(1.03);
    background: #f8f9fa;
}
</style>

<%@ include file="../all_component/footer.jsp" %>
