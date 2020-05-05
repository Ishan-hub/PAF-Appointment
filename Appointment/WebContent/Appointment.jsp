<%@page import="model.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointments Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/appointments.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Appointments Management</h1>

				<form id="formAppointment" name="formAppointment" method="post"
					action="Appointment.jsp">

					Appointment Number: <input id="number" name="number" type="text"
						class="form-control form-control-sm"> <br>
					Appointment Type: <input id="type" name="type" type="text"
						class="form-control form-control-sm"> <br>
					Appointment Date: <input id="date" name="date" type="date"
						class="form-control form-control-sm"> <br>
					Appointment Description: <input id="des" name="des" type="text"
						class="form-control form-control-sm"> <br> Doctor ID:
					<input id="did" name="did" type="text"
						class="form-control form-control-sm"> <br> Hospital
					ID: <input id="hid" name="hid" type="text"
						class="form-control form-control-sm"> <br> Patient
					ID: <input id="pid" name="pid" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidAppointmentIDSave" name="hidAppointmentIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br>
				<div id="divAppointmentsGrid">
					<%
						Appointment appointmentObj = new Appointment();
						out.print(appointmentObj.readAppointments());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
