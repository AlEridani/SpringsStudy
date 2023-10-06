<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<meta charset="UTF-8">
<title>${vo.boardTitle }</title>
</head>
<body>
	<h2>글 보기</h2>
	<div>
		<p>글 번호 : ${vo.boardId }</p>
	</div>
	<div>
		<p>제목 : </p>
		<p>${vo.boardTitle }</p>
	</div>
	<div>
		<p>작성자 : ${vo.memberId }</p>
		<p>작성일 : ${vo.boardDateCreated }</p>
	</div>
	<div>
		<textarea rows="20" cols="120" readonly>${vo.boardContent }</textarea>
	</div>
	
	<a href="list?page=${page }"><input type="button" value="목록"></a>
	
	
		<a href="update?boardId=${vo.boardId }&page=${page}"><input type="button" value="수정"></a>
		
		<form action="delete" method="POST">
			<input type="hidden" id="boardId" name="boardId" value="${vo.boardId }">
			<input type="submit" value="삭제"> 
		</form>
	
	<input type="hidden" id="boardId" name="boardId" value="${vo.boardId }">

	<c:if test="${empty sessionScope.memberId }">
		* 댓글은 로그인이 필요한 서비스입니다.
		<a href="login.go">로그인하기</a>
	</c:if>
	
	<c:if test="${not empty sessionScope.memberId }">
		${sessionScope.memberId }님, 이제 댓글을 작성할 수 있어요!
		<div style="text-align: center;">
			<input type="text" id="memberId" value="${sessionScope.memberId }" readonly="readonly">
			<input type="text" id="replyContent">
			<button id="btnAdd">작성</button>
		</div>
	</c:if>
	
	<hr>
	<div style="text-align: center;">
		<div id="replies"></div>
	</div>
	
	<div>
		<br><br><br><br><br><br><br><br><br><br>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			getAllReplies();
			
			$('#btnAdd').click(function(){
				var boardId = $('#boardId').val(); // 게시판 번호 데이터
				var memberId = $('#memberId').val(); // 작성자 데이터
				var replyContent = $('#replyContent').val(); // 댓글 내용
				var obj = {
						'boardId' : boardId, 
						'memberId' : memberId,
						'replyContent' : replyContent
				};
				console.log(obj);
				
				// $.ajax로 송수신
				$.ajax({
					type : 'POST', 
					url : 'replies/add',
					data : {'obj' : JSON.stringify(obj)}, // JSON으로 변환
					success : function(result){
						console.log(result);
						if(result == 'success') {
							alert('댓글 입력 성공');
							getAllReplies();
						}
					}
				}); // end ajax()
			}); // end btnAdd.click()
			
			// 게시판 댓글 전체 가져오기
			function getAllReplies() {
				var boardId = $('#boardId').val();
				
				var url = 'replies/all?boardId=' + boardId;
				$.getJSON(
					url, 
					function(data) {
						// data : 서버에서 전송받은 list 데이터가 저장되어 있음.
						// getJSON()에서 json 데이터는
						// javascript object로 자동 parsing됨.
						console.log(data);
					
						var list =''; // 댓글 데이터를 HTML에 표현할 문자열 변수	
						
						var sessionLogin = $('#memberId').val();
						var disabled = 'disabled';
						
						// $(컬렉션).each() : 컬렉션 데이터를 반복문으로 꺼내는 함수
						$(data).each(function(){
							// this : 컬렉션의 각 인덱스 데이터를 의미
							console.log(this);
							
							var replyDateCreated = new Date(this.replyDateCreated);
							
							list += '<div class="reply_item">'
								+ '<pre>'
								+ '<input type="hidden" id="replyId" value="' + this.replyId +'">'
								+ this.memberId
								+ '&nbsp;&nbsp;' // 공백
								+ '<input type="text" id="replyContent" value="' + this.replyContent + '">'
								+ '&nbsp;&nbsp;' // 공백
								+ replyDateCreated
								+ '&nbsp;&nbsp;'; // 공백
								if (sessionLogin === this.memberId) {
						            list += '<button class="btn_update">수정</button>'
						                + '<button class="btn_delete">삭제</button>';
						        }
							   list += '</pre>'
							        + '</div>';
						}); // end each()

						$('#replies').html(list);
					}
				); // end getJSON()
			} // end getAllReplies()
			
			// 수정 버튼을 클릭하면 선택된 댓글 수정
			$('#replies').on('click', '.reply_item .btn_update', function(){
				console.log(this);
				
				// 선택된 댓글의 replyId, replyContent 값을 저장
				// prevAll() : 선택된 노드 이전에 있는 모든 형제 노드를 접근
				var replyId = $(this).prevAll('#replyId').val();
				var replyContent = $(this).prevAll('#replyContent').val();
				console.log("선택된 댓글 번호 : " + replyId + ", 댓글 내용 : " + replyContent);
				
				// ajax 요청
				$.ajax({
					type : 'POST', 
					url : 'replies/update', 
					data : {
						'replyId' : replyId,
						'replyContent' : replyContent
					},
					success : function(result) {
						console.log(result);
						if(result == 'success') {
							alert('댓글 수정 성공!');
							getAllReplies();
						}
					}
				}); // end ajax()
			}); // end replies.on()
			
			
			// 삭제 버튼을 클릭하면 선택된 댓글 삭제
			$('#replies').on('click', '.reply_item .btn_delete', function(){
				console.log(this);
				var replyId = $(this).prevAll('#replyId').val();
				console.log("선택된 댓글 번호 : " + replyId);
				
				// ajax 요청
				$.ajax({
					type : 'POST', 
					url : 'replies/delete', 
					data : {
						'replyId' : replyId
					},
					success : function(result) {
						console.log(result);
						if(result == 'success') {
							alert('댓글 삭제 성공!');
							getAllReplies();
						}
					}
				}); // end ajax()
			}); // end replies.on()
			
		}); // end document
	</script>
	
</body>
</html>






















