<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.entity.User" %>

<%
    User user = (User) session.getAttribute("userObj");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guest House Dashboard | NFL Panipat</title>

<%@ include file="all_component/allCss.jsp" %>

<style>
.dashboard-container {
    padding: 30px;
}

.welcome-box {
    background: #ffffff;
    border-radius: 12px;
    padding: 20px;
    margin-bottom: 25px;
    box-shadow: 0 6px 16px rgba(0,0,0,0.08);
}

.dashboard-card {
    background: #ffffff;
    border-radius: 14px;
    padding: 25px;
    text-align: center;
    box-shadow: 0 8px 18px rgba(0,0,0,0.1);
    transition: transform 0.2s ease;
}

.dashboard-card:hover {
    transform: translateY(-5px);
}

.dashboard-card h5 {
    margin-top: 15px;
    font-weight: 600;
    color: #2c3e50;
}

.dashboard-card a {
    text-decoration: none;
}

.icon-box {
    font-size: 40px;
    color: #0984e3;
}
</style>

</head>

<body>

<%@ include file="all_component/navbar.jsp" %>

<div class="dashboard-container container-fluid">

    <!-- WELCOME -->
    <div class="welcome-box">
        <h4>Welcome, <%= user.getFullName() %></h4>
        <p class="text-muted mb-0">
            NFL Panipat Guest House Management System
        </p>
    </div>

    <!-- DASHBOARD -->
    <div class="row g-4">

        <div class="col-md-3">
            <a href="admin/room_master.jsp">
                <div class="dashboard-card">
                    <div class="icon-box">
                        <i class="fas fa-bed"></i>
                    </div>
                    <h5>Room Master</h5>
                </div>
            </a>
        </div>

        <div class="col-md-3">
            <a href="booking/new_booking.jsp">
                <div class="dashboard-card">
                    <div class="icon-box">
                        <i class="fas fa-calendar-check"></i>
                    </div>
                    <h5>New Booking</h5>
                </div>
            </a>
        </div>

        <div class="col-md-3">
            <a href="booking/checkin.jsp">
                <div class="dashboard-card">
                    <div class="icon-box">
                        <i class="fas fa-sign-in-alt"></i>
                    </div>
                    <h5>Check-In / Check-Out</h5>
                </div>
            </a>
        </div>

        <div class="col-md-3">
            <a href="billing/bill_list.jsp">
                <div class="dashboard-card">
                    <div class="icon-box">
                        <i class="fas fa-file-invoice mr-1"></i>
                    </div>
                    <h5>Billing</h5>
                </div>
            </a>
        </div>

        <div class="col-md-3">
            <a href="reports/reports.jsp">
                <div class="dashboard-card">
                    <div class="icon-box">
                        <i class="fas fa-chart-bar"></i>
                    </div>
                    <h5>Reports</h5>
                </div>
            </a>
        </div>

    </div>

</div>

<%@ include file="all_component/footer.jsp" %>

</body>
</html>
