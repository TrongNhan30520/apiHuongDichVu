<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:url value="/resources/static" var="url"></c:url>
<c:url value="/admin/NV/insert1" var="nvInsert"></c:url>	
<c:url value="/admin/NV/updated" var="nvUpdated"></c:url>	
<c:url value="/api/nv" var="apiNV"></c:url>	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Nhân viên</title>
<!-- BOOTSTRAP STYLES-->
<link href="${url}/css/bootstrap.css" rel="stylesheet" />
<!-- FONTAWESOME STYLES-->
<link href="${url}/css/font-awesome.css" rel="stylesheet" />
<!-- CUSTOM STYLES-->
<link href="${url}/css/custom.css" rel="stylesheet" />
<!-- GOOGLE FONTS-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
<style type="text/css">
	*[id$=errors]{
		color: red;
		font-style: italic;
	}
</style>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/views/admin/view/nav-bar.jsp"></jsp:include>
		<!-- /. NAV TOP  -->
		<jsp:include page="/WEB-INF/views/admin/view/slide-bar.jsp"></jsp:include>
		<!-- /. NAV SIDE  -->
		<div id="page-wrapper">
			<div id="page-inner">
				<div class="row">
					<div class="col-md-12">
						<h2 style="margin-left: 30px">Employee</h2>
						<h5 style="margin-left: 30px">Fill in information</h5>
					</div>
				</div>
				<!-- /. ROW  -->
				
				<div class="row">
					<div class="col-md-12">
						<!-- Form Elements -->
						<div class="panel panel-default">
							<div class="panel-heading"></div>
							<div class="panel-body">
								<div class="row">
									<div class="col-md-6">
										
										<form:form role="form" id="formSubmit" modelAttribute="NV">
											<div class="form-group">
												<label>ID</label> <form:input class="form-control"
													 id="manv" name="manv" path="manv" readonly="true"/>
											</div>
											<div class="form-group">
												<label>Full name</label> <form:input class="form-control"
													placeholder="Please enter your full name" id="hoten" name="hoten" path="hoten"/>
													<form:errors path="hoten"/>
											</div>
											<div class="form-group">
												<label>Picture</label> <form:input class="form-control"
													placeholder="Please enter an image" id="hinhanh" name="hinhanh" path="hinhanh"/>
													<form:errors path="hinhanh"/>
											</div>
											<div class="form-group">
												<label>Email</label> <form:input type="email" class="form-control"
													placeholder="Please enter an email" id="email" name="email" path="email"/>
													<form:errors path="email"/>
											</div>
											<div class="form-group">
												<label>Address</label> <form:input class="form-control"
													placeholder="Please enter an address" id="diachi" name="diachi" path="diachi"/>
													<form:errors path="diachi"/>
											</div>
											<div class="form-group">
												<label>Phone number</label> <form:input pattern="[0]{1}[0-9]{9}" class="form-control"
													title="0xxx xxx xxx" placeholder="Please enter the phone number" type="text"
													id="sdt" name="sdt" path="sdt"/>
													<form:errors path="sdt"/>
											</div>
											<div class="form-group">
											
												<label>Working date</label> <input type="date" id="${da}" value="${da}"  class="form-control da"
													placeholder="Please enter time to work"   name="da" />
													<span style="color: red; font-style: italic;">${message}</span>
											</div>
											<div class="form-group">
												<label>Account</label> <form:input class="form-control account"
													placeholder="Please enter your account" id="dstaikhoan.taikhoan" name="dstaikhoan.taikhoan" path="dstaikhoan.taikhoan"/>
													<form:errors path="dstaikhoan.taikhoan"/>
													<span style="color: red; font-style: italic;">${message1}</span>
											</div>
											<div class="form-group">
												<label>Password</label> <form:input pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" class="form-control"
													title="Must contain at least one  number and one uppercase and lowercase letter, and at least 8 or more characters" placeholder="Please enter a password" id="dstaikhoan.matkhau" name="dstaikhoan.matkhau" path="dstaikhoan.matkhau" type="password"/>
													<form:errors path="dstaikhoan.matkhau"/>
											</div>
											<div class="form-group">
											
												<label>Position</label>
												
												<form:select id="dstaikhoan.chucvu" name="dstaikhoan.chucvu" path="dstaikhoan.chucvu">
													<form:option value="Manager">Manager</form:option>
													<form:option value="Staff">Staff</form:option>
												</form:select>

											</div>
											<form:hidden path="manv" id="maNV"/>
											<div>
												<c:if test="${not empty NV.manv}">
													<button type="submit" class="btn btn-default" id="insertNV">Add</button>
												</c:if>
												<c:if test="${empty NV.manv }">
													<button type="submit" class="btn btn-default" id="updateNV">Update</button>
												</c:if>
												<button type="reset" class="btn btn-primary">Reset</button>
											</div>
										</form:form>


									</div>
								</div>
							</div>
						</div>
						<!-- End Form Elements -->
					</div>
				</div>
				<!-- /. ROW  -->
				<div class="row">
					<div class="col-md-12"></div>
				</div>
				<!-- /. ROW  -->
			</div>
			<!-- /. PAGE INNER  -->
		</div>
		<!-- /. PAGE WRAPPER  -->
	</div>
	<!-- /. WRAPPER  -->
	<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
	<!-- JQUERY SCRIPTS -->
	<script src="${url}/js/jquery-1.10.2.js"></script>
	<!-- BOOTSTRAP SCRIPTS -->
	<script src="${url}/js/bootstrap.min.js"></script>
	<!-- METISMENU SCRIPTS -->
	<script src="${url}/js/jquery.metisMenu.js"></script>
	<!-- CUSTOM SCRIPTS -->
	<script src="${url}/js/custom.js"></script>
	<script type="text/javascript">
		const temp = document.querySelector('.da');
		const ac = document.querySelector('.account');
		if(temp.getAttribute("id") !="")
			{
				ac.setAttribute("readonly","true");
			}
	</script>
	
	<script type="text/javascript">
		$('#insertNV').click(function(e) {
			e.preventDefault();
			var data = {};
			var formData = $('#formSubmit').serializeArray();
			$.each(formData, function(i, v){
				data[""+v.name+""] = v.value;
			});
			const temp = document.querySelector('.da');
			if(temp.getAttribute("id") !=""){
				updateNV(data);
			}else {
				insertNV(data);
			}
		});
		
		function insertNV(data){
			$.ajax({
				url: '${apiNV}',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(data),
				dataType: 'json',
				success: function (result) {
					window.location.href = "${nvInsert}";
				},
				error: function (error) {
					window.location.href = "/admin/NV/insert";
				}
			});
		}
		
		function updateNV(data){
			$.ajax({
				url: '${apiNV}',
				type: 'PUT',
				contentType: 'application/json', 	
				data: JSON.stringify(data),
				dataType: 'json',
				success: function (result) {
					window.location.href = "${nvUpdated}";
				},
				error: function (error) {
					window.location.href = "/admin/NV/insert";
				}
			});
		}
	</script>
</body>
</html>