$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateAppointmentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "AppointmentsAPI",
		type : type,
		data : $("#formAppointment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentSaveComplete(response.responseText, status);
		}
	});
});

function onAppointmentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();

			$("#divAppointmentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidAppointmentIDSave").val("");
	$("#formAppointment")[0].reset();
}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidAppointmentIDSave")
					.val(
							$(this).closest("tr").find(
									'#hidAppointmentIDUpdate').val());
			$("#number").val($(this).closest("tr").find('td:eq(0)').text());
			$("#type").val($(this).closest("tr").find('td:eq(1)').text());
			$("#date").val($(this).closest("tr").find('td:eq(2)').text());
			$("#des").val($(this).closest("tr").find('td:eq(3)').text());
			$("#did").val($(this).closest("tr").find('td:eq(4)').text());
			$("#hid").val($(this).closest("tr").find('td:eq(5)').text());
			$("#pid").val($(this).closest("tr").find('td:eq(6)').text());
		});

// REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "AppointmentsAPI",
		type : "DELETE",
		data : "id=" + $(this).data("appointmentid"),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentDeleteComplete(response.responseText, status);
		}
	});
});

function onAppointmentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();

			$("#divAppointmentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENT-MODEL=========================================================================
function validateAppointmentForm() {

	// Number
	if ($("#number").val().trim() == "") {
		return "Insert Appointment Number.";
	}

	// is numerical value
	var tmpNumber = $("#number").val().trim();
	if (!$.isNumeric(tmpNumber)) {
		return "Insert a numerical value for Appointment Number.";
	}

	// Type
	if ($("#type").val().trim() == "") {
		return "Insert Appointment Type.";
	}
	// Date-------------------------------
	if ($("#date").val().trim() == "") {
		return "Insert Appointment Date.";
	}

	// DESCRIPTION------------------------
	if ($("#des").val().trim() == "") {
		return "Insert Appointment Description.";
	}
	
	// DoctorID
	if ($("#did").val().trim() == "") {
		return "Insert Doctor ID.";
	}
	
	// HospitalID
	if ($("#hid").val().trim() == "") {
		return "Insert Hospital ID.";
	}
	
	// PatientID
	if ($("#pid").val().trim() == "") {
		return "Insert Patient ID.";
	}
	
	// is numerical value
	var tmpdid = $("#did").val().trim();
	if (!$.isNumeric(tmpdid)) {
		return "Insert a numerical value for Doctor ID.";
	}
	
	// is numerical value
	var tmphid = $("#hid").val().trim();
	if (!$.isNumeric(tmphid)) {
		return "Insert a numerical value for Hospital ID.";
	}
	
	// is numerical value
	var tmppid = $("#pid").val().trim();
	if (!$.isNumeric(tmppid)) {
		return "Insert a numerical value for Patient ID.";
	}

	return true;
}