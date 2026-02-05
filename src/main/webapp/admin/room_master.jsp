<%@ page import="java.util.List" %>
<%@ page import="com.entity.Room" %>

<%@ include file="../all_component/navbar.jsp" %>
<%@ include file="/all_component/allCss.jsp" %>

<div class="container mt-4">

    <h4 class="mb-3">
        <i class="fas fa-bed"></i> Room Master
    </h4>

    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>
                <th>Room No</th>
                <th>Room Type</th>
                <th>Capacity</th>
                <th>NFL Rent</th>
                <th>Govt Rent</th>
                <th>Private Rent</th>
                <th>Status</th>
            </tr>
        </thead>

        <tbody>
<%
    List<Room> list = (List<Room>) request.getAttribute("roomList");

    if (list == null) {
%>
        <tr>
            <td colspan="7" class="text-center text-danger">
                roomList is NULL (Servlet not forwarding data)
            </td>
        </tr>
<%
    } else if (list.isEmpty()) {
%>
        <tr>
            <td colspan="7" class="text-center text-warning">
                No rooms found in database
            </td>
        </tr>
<%
    } else {
        for (Room r : list) {
%>
        <tr>
            <td><%= r.getRoomNo() %></td>
            <td><%= r.getRoomType() %></td>
            <td><%= r.getCapacity() %></td>
            <td>&#8377 <%= r.getNflRent() %></td>
            <td>&#8377 <%= r.getGovtRent() %></td>
            <td>&#8377 <%= r.getPrivateRent() %></td>
            <td>
                <span class="badge badge-success">
                    <%= r.getStatus() %>
                </span>
            </td>
        </tr>
<%
        }
    }
%>
</tbody>

    </table>

</div>

<%@ include file="../all_component/footer.jsp" %>
