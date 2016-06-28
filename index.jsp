<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.suphalaam.hbase.*"%>

<%
List<Exam> tmp =  MasterData.getExamList();

List<Group>gtemp=MasterDatagrp.getGrpList();

List<Subject>stemp=MasterDatasub.getSubList();
%>

<!DOCTYPE html>
<html lang="en">
<!-- the head part would be include in this -->
<%@include file="./common/header.jsp" %>
<body>
<div class="container">
	<!-- this jsp include only the navigation bar -->
    <%@include file="./common/navbar.jsp" %>

    <div class="form-group">
		<div class="col-xs-2">Select Criteria </div>
		<div class="col-xs-10">
			<label><input type="checkbox" name="book"> Book </label>
			<label><input type="checkbox" name="course"> Course </label>
			<label><input type="checkbox" name="download"> Download </label>
			<label><input type="checkbox" name="blog"> Blog </label>
			<label><input type="checkbox" name="qna"> QnA </label>
		</div>
		<br/>
		<div class="col-xs-2">Select Applicability</div>
		<div class="col-xs-3">
			Exam
		 <select>
		 <%
		 for (Exam exam : tmp) {
		 %>
		 	<option value="<%=exam.getName()%>" id="<%=exam.getId()%>"><%=exam.getName()%></option>
		 <%
			
		 }
		 %>
		</select> 
		</div>
		<div class="col-xs-3">
			Group
		 <select>
		 <%
		 for (Group grp : gtemp) {
		 %>
		 	<option value="<%=grp.getName()%>" id="<%=grp.getId()%>"><%=grp.getName()%></option>
		 <%
			
		 }
		 %>

			  
		</select> 
		</div>
		<div class="col-xs-3">
			Subject
		 <select>
		 <%
		 for (Subject subject : stemp) {
		 %>
		 	<option value="<%=subject.getName()%>" id="<%=subject.getId()%>"><%=subject.getName()%></option>
		 <%
			
		 }
		 %>
			  
		</select> 
		</div>
	</div>

	<%@include file="./common/footer.jsp" %>
</div>

</body>
</html>    