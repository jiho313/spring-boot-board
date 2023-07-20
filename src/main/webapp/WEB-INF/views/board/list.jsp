<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>애플리케이션</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
<c:set var="menu" value="게시글" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1 class="border bg-light p-2 fs-4">게시글 목록</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-12">
			<p>게시글 목록을 확인하세요.</p>
			<table class="table">
				<thead>
					<tr>
						<th style="width: 10%;">번호</th>
						<th style="width: 35%;">제목</th>
						<th style="width: 10%;">조회수</th>
						<th style="width: 10%;">댓글수</th>
						<th style="width: 20%;">작성자</th>
						<th style="width: 15%;">등록일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty result.boards }">
							<c:forEach var="board" items="${result.boards }">
								<tr>
									<td>${board.no }</td>
									<td><a href="read?no=${board.no }" >${board.title }</a></td>
									<td>${board.readCount }</td>
									<td>${board.reviewCount }</td>
									<td>${board.user.email }</td>
									<td><fmt:formatDate value="${board.createDate }" pattern="yyyy년 MM월 dd일"/></td>
								</tr>
							</c:forEach>
						</c:when>
							<c:otherwise>
								<tr >
									<td colspan="6" class="text-center">게시글이 존재하지 않습니다.</td>
								</tr>
							</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<sec:authorize access="isAuthenticated()">
				<div class="text-end">
					<a href="register" class="btn btn-primary btn-sm">새 게시글</a>
				</div>
			</sec:authorize>
		</div>
	</div>
<c:if test="${result.pagination.totalRows gt 0 }">
	<!-- 현재 pageContext안에서 사용할 변수 재정의  -->
	<c:set var="currentPage" value="${result.pagination.page }"></c:set>
	<c:set var="first" value="${result.pagination.first }"></c:set>
	<c:set var="last" value="${result.pagination.last }"></c:set>
	<c:set var="prePage" value="${result.pagination.prePage }"></c:set>
	<c:set var="nextPage" value="${result.pagination.nextPage }"></c:set>
	<c:set var="beginPage" value="${result.pagination.beginPage }"></c:set>
	<c:set var="endPage" value="${result.pagination.endPage }"></c:set>
	<div class="row mb-3" >
		<div class="col-12">
			<nav>
				<ul class="pagination justify-content-center">
					<li class="page-item ${first ? 'disabled' : '' }">
						<a class="page-link"  href="list?page=${prePage }" onclick="changePage(event, ${prePage})">이전</a>
					</li>
					<c:forEach var="num" begin="${beginPage }" end="${endPage }">
						<li class="page-item ${currentPage eq num ? 'active' :'' }" >
							<a href="" class="page-link" onclick="changePage(event, ${num} )">${num }</a>
						</li>
					</c:forEach>
					<li class="page-item ${last ? 'disabled' : '' }">
						<a class="page-link" href="list?page=${nextPage }" onclick="changePage(event, ${nextPage})" >다음</a>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</c:if>
<div >
	<form id="form-board-search" class="row row-cols-md-auto g-3 align-items-center" method="get" action="list">
		<input type="hidden" name="page" value="${param.page }">
	</form>	
</div>
</div>
<script type="text/javascript">
	function changePage(event, page) {
		// a태그의 링크 이동을 막음
		event.preventDefault();
		document.querySelector("input[name=page]").value = page;
		document.querySelector("#form-board-search").submit();
	} 
</script>
</body>
</html>