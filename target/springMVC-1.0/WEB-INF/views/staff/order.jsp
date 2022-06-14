<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:url value="/resources/static" var="url"></c:url>
<c:url value="/api/order" var="api"></c:url>
<!DOCTYPE html>
<html>
<head>
<script src="<c:url value="/ckeditor/ckeditor.js" />"></script>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Edit User</title>
<!-- BOOTSTRAP STYLES-->
<link href="${url}/css/bootstrap.css" rel="stylesheet" />
<!-- FONTAWESOME STYLES-->
<link href="${url}/css/font-awesome.css" rel="stylesheet" />
<!-- CUSTOM STYLES-->
<link href="${url}/css/custom.css" rel="stylesheet" />
<!-- GOOGLE FONTS-->
<link href="${url}/js/dataTables/dataTables.bootstrap.css"
	rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
	<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.0/font/bootstrap-icons.css">
	<link href="<c:url value='resources/assets/dist/signin.css' />"
	rel="stylesheet">
</head>
	<style>
.order_sell {
	float: right !important;
}

.btn-setting:hover {
	opacity: 0.5;
}

.btn-setting {
	cursor: pointer
}

.btn:hover {
	background-color: #41464b;
	color: white;
}

#btn_huy, .btn_Oke {
	width: 90px;
}

.modal {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #00000040;
	display: none;
}

.modal-form {
	margin-top: 25px;
	margin-left: 18%;
}

.modal-body {
	font-size: medium;
	
}
.modal-title{
        margin-left: 7%;
    font-weight: 600;
    line-height: 30px;
    font-size: 19px;
    }
.modal-content{
	border-radius: 16px;
	width: 500px;
}
#modal-delete{
    padding-top: 6%;
    padding-left: 8%;

}

#modal-success {
	display: none;
	margin-top: 9%;
    margin-left: 14%;
}

.modal-nd{
	max-width: 265px;
    margin: auto;
    margin-top: 5%;
}
.bi-gear {
	color: black
}


.labels{
font-weight: 700;
}
.table {
	
}
.input_order{
	width: 92%;
	margin-bottom: 10px;
	background: #0000001c;
	border-radius: 4px;
	border: 1px solid black;
	font-family: revert;
	font-weight: 600;
}
 .btn_detail{
	margin-right: 38px;
	margin-left: 12px;
}
 .btn_submit{
 	margin-right: 5px;
 }
.order {
	display: none;
}

.btn-mr20 {
	margin-right: 20px
}

a {
	color: white ;
	text-decoration: none;
}

.sign-out:hover {
	opacity: 0.5;
}

.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.product_image {
	width: 16px;
	height: 16px;
}

.input_order {
}

.bi-trash{
	color:#000000bf;
	padding-left: 5px;
}	
.bi-check-circle-fill{
	color: blue;
}
.modal-ct{

   	 	height: 50px;
   	 	font-size: large;
   	 	
}	
	
.border-form{
	width: 100%;
	margin-top: 3%;
	border: 1px solid black;
	border-radius:5px;
	margin-left: 3%;
    margin-right: 3%;
	}
	.table-view{
	width: 96%;
    margin-left: 2%;
    margin-top: 5%;
	}
	.bi-gear {
    color: #df1919;
  
    
}
.bi-trash {
    color: #23af19;
}
	.title-table{
	background: #120d0db0;
	}
 	.content-title{
 	text-align: inherit;
    padding-top: 11px;
    margin-left: 21px;
    padding-bottom: 14px;
 	color: white;
 	font-family: auto;
 	}
 	.bi-check-circle-fill{
	color: blue;
	padding-right: 5px;
	
}
.bi-exclamation-circle-fill{
	color: #ff5000;
	padding-right: 5px;
}	
.link-history
{
	color: #dc3545!important;
	font-weight: 600;
	float: right;
    font-size: large;
    margin-bottom: -15px;
    margin-top: 32px;
}
.link-history:hover {
	opacity: 0.8;
}
.modal-footer{
	margin-top: 0;
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
						<h2>Danh sách đơn đặt hàng</h2>
						<h5>Bạn có thể xem, xác nhận,hủy bỏ đơn đặt đặt hàng tại đây</h5>
						<a style=""  href="/springMVC/staff/order/personal.htm" class="link-history">Xem lịch sử đặt hàng của bạn</a>
					</div>
				</div>
				<!-- /. ROW  -->
				<hr />

				<div class="row">
					<div class="col-md-12">
						<!-- Advanced Tables -->
						
						<div class="panel panel-default">
							<div class="panel-heading">Bảng thông tin</div>
							<div class="panel-body">
								<div  class="table-responsive">
									<table  class="table table-striped table-bordered table-hover"
										id="dataTables-example">
										<thead style="background-color: #0000000d;">
					<tr>
						<th scope="col">ID</th>
						<th scope="col">Khách hàng</th>
						<th scope="col">Điện thoại</th>
						<th scope="col">Email</th>
						<th scope="col">Địa chỉ</th>
						<th scope="col">Trạng thái</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<tbody id="table_sanpham">

					<c:forEach var="u" items="${dondh}">
						<tr>
							<th scope="row">${u.msddh}</th>
							<td>${u.hotenkh}</td>
							<td>${u.SDT}</td>
							<td>${u.email}</td>
							<td>${u.diachi}</td>
							<td>${u.trangthai}</td>
							<td><a class="btn-setting ${u.trangthai.trim()}" 
								><i class="bi bi-gear"></i></a>
								<a class="btn-delete ${u.trangthai.trim()}" ><i class="bi bi-trash" ></i></a>
								</td>
								
						</tr>
					</c:forEach>


				</tbody>
									</table>
								</div>

							</div>
						</div>
						<!--End Advanced Tables -->
					</div>
				</div>

			</div>
		</div>
			
		<div class="modal" id="modal">


			<form:form action="order.htm" method="POST" >
			<div class="modal-form" id="modal-form" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Xác nhận đơn đặt hàng <button style="    float: right;
    margin-right: 5%;" type="button" class="btn-close" onclick="Close()"
								data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button></h5>
							
						</div>
						
							<div class="modal-body" style="    margin-left: 7%;">
							<label class="labels">ID:</label><input
									class="input_order form-control" type="text" id="ID" name="id" value=""  readonly="readonly">
								<label class="labels" >Khách hàng:</label><input
									class="input_order form-control" type="text" id="name" value=""  readonly="readonly"> <label
									class="labels">Số Điện thoại:</label><input id="SDT" class="input_order form-control"
									type="text" value=""  readonly="readonly"> <label class="labels">Email:</label><input
									class="input_order form-control" type="text" id="email" value=""  readonly="readonly"> <label
									class="labels">Địa chỉ:</label><input class="input_order form-control" id="diachi"
									type="text" value=""  readonly="readonly"> 
							</div>
						
						<div class="modal-footer">
				
							<button type="submit" id="btnConfirm" name="btnAccept" class="btn btn-primary btn_submit">Xác
								nhận</button>
								<button type="submit" name="btnCT" class="btn btn-primary btn_detail" formaction="order/detail.htm" >
							Xem chi tiết
							</button>
							
						</div>
					</div>
				</div>
			</div>
			
			<div id="modal-success" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Notification<button style=" float: right; margin-right: 5%;" type="button" class="btn-close" onclick="Close()"
								data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button></h5>
							
						</div>
						<div class=" modal-ct" style=""><div class="modal-nd">
						<i class="bi check " style=""></i>
						<b id="Content" style="align-content: center"> </b>
						</div>
							
						</div>
						<div class="modal-footer">
							<a onclick="Close()" ><button type="button" class="btn btn-primary btn_Oke" style="border-color: none !"
								onclick="Close()">OK</button></a>
								<button name="btnCT"  class="btn btn-primary btn_detail" formaction="order/detail.htm">
							Xem chi tiết
							</button>
						</div>
					</div>
				</div>
			</div>
			<div id="modal-delete" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Notification<button style="float: right;
    margin-right: 5%;" type="button" class="btn-close" onclick="Close()"
								data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button> </h5>
							
						</div>
						<div class="modal-body" style="margin-left: 13%;">
						<b style="align-content: center"><i class="bi check-icon bi-exclamation-circle-fill" style="color: #ff5000;padding-right: 10px"></i>Bạn có thực sự muốn xóa đơn hàng này ?</b>
							
						</div>
						<div class="modal-footer">
							
								<button name="btnCT"  class="btn btn-primary btn_detail" formaction="order/delete.htm">
								Xác nhận
							</button>
						</div>
					</div>
				</div>
			</div>
			</form:form>
		</div>
			<!-- /. PAGE INNER  -->
		</div>
		<!-- /. PAGE WRAPPER  -->
	<!-- /. WRAPPER  -->
	<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
	<!-- JQUERY SCRIPTS -->
	<script src="${url}/js/jquery-1.10.2.js"></script>
	<!-- BOOTSTRAP SCRIPTS -->
	<script src="${url}/js/bootstrap.min.js"></script>
	<!-- METISMENU SCRIPTS -->
	<script src="${url}/js/jquery.metisMenu.js"></script>
	<!-- DATA TABLE SCRIPTS -->
	<script src="${url}/js/dataTables/jquery.dataTables.js"></script>
	<script src="${url}/js/dataTables/dataTables.bootstrap.js"></script>
	<script>
		$(document).ready(function() {
			$('#dataTables-example').dataTable();
		});
	</script>
	
	<!-- CUSTOM SCRIPTS -->
	<script src="${url}/js/custom.js"></script>
	
<script type="text/javascript">
	var btnContainer = document.getElementById("list_item");
	var btns = btnContainer.getElementsByClassName("nav-link");

	// Loop through the buttons and add the active class to the current/clicked button
	for (var i = 0; i < btns.length; i++) {
		btns[i].addEventListener("click", function() {
			var current = document.getElementsByClassName("active");
			current[0].className = current[0].className.replace(" active", "");
			this.className += " active";
		});
	}
</script>

<script type="text/javascript">
	var btns = document.getElementsByClassName("NEW");

	// Loop through the buttons and add the active class to the current/clicked button
	for (var i = 0; i < btns.length; i++) {
		btns[i].addEventListener("click", function() {
			document.getElementById('modal').style.display = "block";
			document.getElementById('modal-success').style.display = "none";
			document.getElementById('modal-form').style.display = "block";
			document.getElementById('modal-delete').style.display = "none";
		});
	}
	
	var btns = document.getElementsByClassName("btn-delete ");

	// Loop through the buttons and add the active class to the current/clicked button
	for (var i = 0; i < btns.length; i++) {
		btns[i].addEventListener("click", function() {
			document.getElementById('modal').style.display = "block";
			document.getElementById('modal-success').style.display = "none";
			document.getElementById('modal-form').style.display = "none";
			document.getElementById('modal-delete').style.display = "block";
			
		});
	}
	
	
</script>


<script type="text/javascript">
	var btns = document.getElementsByClassName("btn-setting CONFIRMED");

	// Loop through the buttons and add the active class to the current/clicked button
	for (var i = 0; i < btns.length; i++) {
		
		btns[i].addEventListener("click", function() {
			var current = document.getElementsByClassName("bi check");
			console.log(current);
			current[0].className = current[0].className="bi check bi-check-circle-fill";
			
			document.getElementById('Content').innerHTML = "Đơn hàng này đã xác nhận !"; 
			document.getElementById('modal').style.display = "block";
			document.getElementById('modal-success').style.display = "block";
			document.getElementById('modal-form').style.display = "none";
			document.getElementById('modal-delete').style.display = "none";
			 
		});
	}
	
	var btnss = document.getElementsByClassName("CANCELLED");
	

	// Loop through the buttons and add the active class to the current/clicked button
	for (var i = 0; i < btnss.length; i++) {
		btnss[i].addEventListener("click", function() {
			var current = document.getElementsByClassName("bi check");
			current[0].className = current[0].className="bi check bi-exclamation-circle-fill"
			console.log(current);
			document.getElementById('Content').innerHTML = "Đơn hàng này đã bị hủy !"; 
			document.getElementById('modal').style.display = "block";
			document.getElementById('modal-success').style.display = "block";
			document.getElementById('modal-form').style.display = "none";
			document.getElementById('modal-delete').style.display = "none";
			
			
		});
	}
</script>

<script type="text/javascript">

	function Close() {

		document.getElementById('modal').style.display = "none";

	}

</script>
<script>
	var table = document.getElementById('dataTables-example');
	for (var i = 1; i < table.rows.length; i++) {
		table.rows[i].onclick = function() {
			//rIndex = this.rowIndex;
			document.getElementById("ID").value = this.cells[0].innerHTML;
			document.getElementById("name").value = this.cells[1].innerHTML;
			document.getElementById("SDT").value = this.cells[2].innerHTML;
			document.getElementById("email").value = this.cells[3].innerHTML;
			document.getElementById("diachi").value = this.cells[4].innerHTML;
			
		};
	}
</script>

<script type="text/javascript">
		$('#btnConfirm').click(function(e) {
			var id = document.getElementById('ID').value;
			confirmOrder(id);
		});
		
		function confirmOrder(data){
			$.ajax({
				url: '${api}',
				type: 'POST',
				contentType: 'application/json',
				data: data,
				dataType: 'json',
				success: function (result) {
					window.location.href = "#";
				},
				error: function (error) {
					window.location.href = "#";
				}
			});
		}
		
	</script>

</body>
</html>