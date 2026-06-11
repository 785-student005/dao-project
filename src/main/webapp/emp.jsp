<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>empテーブル検索</title>
</head>
<body>
	年齢の下限（必須）<input type="text" name="minAge" size="5">,
	年齢の上限（必須）<input type="text" name="maxAge" size="5">
	<button>検索</button>
	<hr>
	
	<c:forEach items="${emps }}" var="emp">
			<tr>
				<td>${emp.code}</td>
				<td>${emp.name}</td>
				<td>${emp.age}</td>
				<td>${emp.tel}</td>
				
			</tr>
		</c:forEach> 

</body>
</html>