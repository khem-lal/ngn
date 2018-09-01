<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	
		
</script>
</head>
<body>
	<form action="<%=request.getContextPath()%>/Login" method="POST">
    <%
    session.invalidate();
    %>
	<jsp:include page="/jsp/header.jsp" />
	<div class="">
		<div class="container">
			<div class="row">
				<div class="col-md-12 col-lg-12">
					<div class="col-md-12">
						
						
						<div class="panel panel-default">
							
							<div class="panel-body">
								<div class="form-group">
									<label class="col-lg-4 control-label"></label>
									<div class="col-lg-4">
										<input type="text" class="form-control" name="userId" id="txtUserId" placeholder="User Id">
									</div>
									<div class="col-lg-4">
										<input class="btn btn-primary" id="btnLogin" type="submit" value="Sign In" name="btnLogin1">
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>			
		</div>
	</div>
	</form>
</body>
<jsp:include page="/jsp/footer.jsp"></jsp:include>	
</html>