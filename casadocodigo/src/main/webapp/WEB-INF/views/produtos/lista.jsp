<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:url value="/resources/css" var="cssPath" />
<c:url value="/resources/js" var="jsPath" />
<link rel="stylesheet" href="resources/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="resources/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<!-- Tem que habilitar o bootstrap no appwebconfig -->
</head>
<body>
	
	
    
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">Casa do Código</a>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item">
	        <a class="nav-link" href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos </a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a>
	      </li>
	      <li class="nav-item dropdown">
	       
	        
	      </li>
	      
	    </ul>
	    <form class="form-inline my-2 my-lg-0">
	      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
	      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
	    </form>
	  </div>
	</nav>
	<div class="container">
	
	<h1>Lista de Produtos</h1>
    <p> ${sucesso} </p>
    <p> ${pagamentoSucesso} </p>
    <p> ${falha} </p>
    
	<table class="table table-bordered">
	
	    <tr>
	        <td>Título</td>
	        <td>Descrição</td>
	        <td>Páginas</td>
	    </tr>
	
	    <c:forEach items="${produtos}" var="produto">
	        <tr>
	            
	            <td>${produto}</td>
	        </tr>
	    </c:forEach>
	</table>
	</div>
</body>
</html>