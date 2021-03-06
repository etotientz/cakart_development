<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.suphalaam.hbase.*"%>

<%
	List<Exam> tmp =  MasterData.getExamList();
	List<Group>gtemp=MasterData.getGrpList();
	List<Subject>stemp=MasterData.getSubList();
	//List<ResultRow> result = MasterData.fetchResult(String,String,String,String,String,String,String,String);

	String book = request.getParameter("book");
	String bookChecked = "";
	String course = request.getParameter("course");
	String courseChecked = "";
	String download = request.getParameter("download");
	String downloadChecked = "";
	String blog= request.getParameter("blog");
	String blogChecked = "";
	String qna = request.getParameter("qna");
	String qnaChecked = "";
	if (book != null){ bookChecked = "checked";}
	if (course!= null){ courseChecked = "checked";}
	if (download != null){ downloadChecked = "checked";}
	if (blog != null){ blogChecked = "checked";}
	if (qna!=null){ qnaChecked="checked";}

	String selectedExam = request.getParameter("exam");
	String selectedGroup = request.getParameter("group");
	String selectedSubject = request.getParameter("subject");

	//once all the criteria are captured, then call a function from java and display the result
	List<ResultRow> results= null;
	if (book != null || course!=null || download!=null ||blog!=null || qna!=null || selectedExam!=null || selectedGroup!=null || selectedSubject!=null) {
		//result = "this ismy result part";
		results = MasterData.fetchResult(book,course,download,blog,qna,selectedExam,selectedGroup,selectedSubject);
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
			<label><input type="checkbox" name="course"<%=courseChecked%>>Course </label>
			<label><input type="checkbox" name="download"<%=downloadChecked%>> download </label>
			<label><input type="checkbox" name="blog"<%=blogChecked%>>Blog </label>
			<label><input type="checkbox" name="qna"<%=qnaChecked%>>QnA </label>
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
			 	<option value="<%=exam.getId()%>" id="<%=exam.getId()%>" selected><%=exam.getName()%></option>
			 <%
			
		 	}
		 	else{
		 	 %>
			 	<option value="<%=exam.getId()%>" id="<%=exam.getId()%>"><%=exam.getName()%></option>
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
		 if(selectedGroup != null && selectedGroup.equalsIgnoreCase(grp.getName()) )
		 %>
		 	<option value="<%=grp.getId()%>" id="<%=grp.getId()%>"><%=grp.getName()%></option>
		 <%
			
		 }
		 %>

			  
		</select> 
		</div>
		<div class="col-xs-3">
			Subject
		 <select name="subject" id="subject">
		 <option value="Select Subject">Select Subject</option>
		 <%
		 for (Subject subject : stemp) {
		 %>
		 	<option value="<%=subject.getId()%>" id="<%=subject.getId()%>"><%=subject.getName()%></option>
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
<%
if (results != null && results.size() > 0 ) {
out.println("The result size is :- "+ results.size());
}
%>
	<div class="col-xs-12">
		<table border = "1px">
		<%
			if (results != null && results.size() > 0 ) {
				for (ResultRow rResult : results) {
		%>
			<tr>
			<td> <%=rResult.getCookieID()%></td>
			<td> <%=rResult.getDate()%></td>
			<td> <%=rResult.getUrl()%></td>
			</tr>
		<%
				}//end-of-for
			}//end-of-if
			else{
		%>
			<tr>
			<td> No Data Found in the criteria</td>
			</tr>
		<%
			}
		%>
		</table>
	</div>
</form>
</body>
</html>    