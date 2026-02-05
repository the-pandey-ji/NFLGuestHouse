<%@ page import="java.util.List" %>
<%@ page import="com.entity.Room" %>

<%@ include file="../all_component/navbar.jsp" %>
<%@ include file="/all_component/allCss.jsp" %>

<div class="container mt-4">

    <h4 class="mb-3">
        <i class="fas fa-bed"></i> Room Master
    </h4>

    <table class="table table-bordered table-striped table-sm">
        <thead class="thead-dark">
            <tr>
                <th>Room No</th>
                <th>Room Type</th>
                <th>Capacity</th>
                <th>NFL Rent</th>
                <th>Govt Rent</th>
                <th>Private Rent</th>
                <th>Booking ID</th>
                <th>Guest Name</th>
                <th>Status</th>
                <!-- <th>Action</th> -->
            </tr>
        </thead>

        <tbody>
<%
    List<Room> list = (List<Room>) request.getAttribute("roomList");

    if (list == null) {
%>
            <tr>
                <td colspan="10" class="text-center text-danger">
                    roomList is NULL (Servlet not forwarding data)
                </td>
            </tr>
<%
    } else if (list.isEmpty()) {
%>
            <tr>
                <td colspan="10" class="text-center text-warning">
                    No rooms found in database
                </td>
            </tr>
<%
    } else {
        for (Room r : list) {

            String st = r.getStatus() == null ? "AVAILABLE" : r.getStatus();
%>
            <tr>
                <td><%= r.getRoomNo() %></td>
                <td><%= r.getRoomType() %></td>
                <td><%= r.getCapacity() %></td>

                <td>&#8377; <%= r.getNflRent() %></td>
                <td>&#8377; <%= r.getGovtRent() %></td>
                <td>&#8377; <%= r.getPrivateRent() %></td>

                <td><%= r.getBookingId() == null ? "-" : r.getBookingId() %></td>
                <td><%= r.getGuestName() == null ? "-" : r.getGuestName() %></td>

                <!-- STATUS -->
                <td>
                    <% if ("AVAILABLE".equals(st)) { %>
                        <span class="badge badge-success">AVAILABLE</span>
                    <% } else if ("BOOKED".equals(st)) { %>
                        <span class="badge badge-warning">BOOKED</span>
                    <% } else if ("OCCUPIED".equals(st)) { %>
                        <span class="badge badge-danger">OCCUPIED</span>
                    <% } else { %>
                        <span class="badge badge-secondary">MAINTENANCE</span>
                    <% } %>
                </td>

                <!-- ACTION -->
                <%-- <td>
                    <% if ("BOOKED".equals(st)) { %>
                        <a href="viewBooking?bookingId=<%= r.getBookingId() %>"
                           class="btn btn-info btn-sm">
                            View
                        </a>

                    <% } else if ("OCCUPIED".equals(st)) { %>
                        <a href="checkoutPage?bookingId=<%= r.getBookingId() %>"
                           class="btn btn-danger btn-sm">
                            Checkout
                        </a>

                    <% } else if ("AVAILABLE".equals(st)) { %>
                        <button class="btn btn-secondary btn-sm" disabled>
                            Maintenance
                        </button>
                    <% } else { %>
                        <button class="btn btn-dark btn-sm" disabled>
                            Locked
                        </button>
                    <% } %>
                </td> --%>
            </tr>
<%
        }
    }
%>
        </tbody>
    </table>

</div>

<%@ include file="../all_component/footer.jsp" %>
