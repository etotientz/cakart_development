<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.suphalaam.hbase.*"%>

<%
	List<Exam> tmp =  MasterData.getExamList();
	List<Group>gtemp=MasterDatagrp.getGrpList();
	List<Subject>stemp=MasterDatasub.getSubList();

	String book = request.getParameter("book");
	String bookChecked = "";
	if (book != null){ bookChecked = "checked";}

	String selectedExam = request.getParameter("exam");
	String selectedGroup = request.getParameter("grp");
	String selectedSubject = request.getParameter("subject");

	//once all the criteria are captured, then call a function from java and display the result
	String result = null;
	if (book != null ) {
		result = "this ismy result part";
		//List<ResultRow> result = MasterDatagrp.fetchResult()
	}

	String course = request.getParameter("course");
	String courseChecked = "";
	if (course!= null){ courseChecked = "checked";}

	String selectedExam = request.getParameter("exam");
	String selectedGroup = request.getParameter("grp");
	String selectedSubject= request.getParameter("subject");

	//once all the criteria are captured, then call a function from java and display the result
	String result = null;
	if (course != null ) {
		result = "this ismy result part";
		//List<ResultRow> result = MasterDatagrp.fetchResult()
	}

	String download = request.getParameter("download");
	String downloadChecked = "";
	if (download != null){ downloadChecked = "checked";}

	String selectedExam = request.getParameter("exam");

	//once all the criteria are captured, then call a function from java and display the result
	String result = null;
	if (download != null ) {
		result = "this ismy result part";
		//List<ResultRow> result = MasterDatagrp.fetchResult()
	}
	String blog= request.getParameter("blog");
	String blogChecked = "";
	if (blog != null){ blogChecked = "checked";}

	String selectedExam = request.getParameter("exam");

	//once all the criteria are captured, then call a function from java and display the result
	String result = null;
	if (blog != null ) {
		result = "this ismy result part";
		//List<ResultRow> result = MasterDatagrp.fetchResult()
	}
	String qna = request.getParameter("qna");
	String qnaChecked = "";
	if (qna != null){ qnaChecked = "checked";}

	String selectedExam = request.getParameter("exam");

	//once all the criteria are captured, then call a function from java and display the result
	String result = null;
	if (qna != null ) {
		result = "this ismy result part";
		//List<ResultRow> result = MasterDatagrp.fetchResult()
	}

	
%>

<!DOCTYPE html>
<html lang="en">
<!-- the head part would be include in this -->
<%@include file="./common/header.jsp" %>
<body>

<div class="container">
	<!-- this jsp include only the navigation bar -->
    <%@include file="./common/navbar.jsp" %>
<form action="index.jsp" method="post">
    <div class="form-group">
		<div class="col-xs-2">Select Criteria </div>
		<div class="col-xs-10">
			<label><input type="checkbox" name="book" <%=bookChecked%>> Book </label>
			<label><input type="checkbox" name="course"> Course </label>
			<label><input type="checkbox" name="download"> Download </label>
			<label><input type="checkbox" name="blog"> Blog </label>
			<label><input type="checkbox" name="qna"> QnA </label>
		</div>
		<br/>
		<div class="col-xs-2">Select Applicability</div>
		<div class="col-xs-3">
			Exam
		 <select name="exam" id="exam" >
		 <option value="Select Exam">Select Exam</option>
		 <%
		 for (Exam exam : tmp) {
		 	if(selectedExam != null && selectedExam.equalsIgnoreCase(exam.getName()) ){
		 	 %>
			 	<option value="<%=exam.getName()%>" id="<%=exam.getId()%>" selected><%=exam.getName()%></option>
			 <%
			
		 	}
		 	else{
		 	 %>
			 	<option value="<%=exam.getName()%>" id="<%=exam.getId()%>"><%=exam.getName()%></option>
			 <%
			
		 	}
			
		 }
		 %>
		</select> 
		</div>
		<div class="col-xs-3">
			Group
		 <select name="group" id="group">
		 <option value="Select Group">Select Group</option>
		 <%
		 for (Group grp : gtemp) {
		 if(selectedExam != null && selectedExam.equalsIgnoreCase(exam.getName()) )
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
<input type="submit" value="Submit" name="Fetch Result" />
<br/><br/><br/>
	<div class="col-xs-12">
		<table>
			<tr>
			<td> <%=result%></td>
			</tr>
		</table>
	</div>
</form>
</body>
</html>    