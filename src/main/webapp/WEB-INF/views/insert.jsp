<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vocabulary Insertion</title>

<style>
.error {
	color: #ff0000;
}
</style>

</head>

<body>

	<h2>Insertion Form</h2>

	<form:form method="POST" modelAttribute="word">
		<form:input type="hidden" path="wordId" id="wordId" />
		<table>
			<tr><td><form:input path="title" id="title" placeholder="Title"/></td></tr>
			<tr><td><form:input path="description" id="description" placeholder="Description"/></td></tr>
			<tr><td><form:input path="translation" id="translation" placeholder="Translation"/></td></tr>
			<tr><td><form:input path="pronunciation" id="pronunciation" placeholder="Pronunciation"/></td></tr>
			<tr><td><form:input path="key" id="key" placeholder="Key"/></td></tr>
			<tr><td><form:input path="method" id="method" placeholder="Method"/></td></tr>
			<tr><td><form:input path="synonyms" id="synonyms" placeholder="Synonyms"/></td></tr>
			<tr><td><form:input path="antonyms" id="antonyms" placeholder="Antonyms"/></td></tr>
			<tr><td><form:input path="image" id="image" placeholder="Image"/></td></tr>
			<tr><td><form:input path="status" id="status" placeholder="Difficulty"/></td></tr>
			<tr><td><form:input path="category" id="category" placeholder="Category"/></td></tr>
			<tr><td><input type="submit" value="Insert" /></td></tr>
		</table>
	</form:form>
	message : ${returning}
</body>
</html>