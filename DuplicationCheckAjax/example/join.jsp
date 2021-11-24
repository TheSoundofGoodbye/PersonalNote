<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<c:import url="../../layout/header.jsp" />

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">

<style type="text/css">
.hide {
	display: none;
}
.help-container{
	display: inline-flex;
}
#helppannel{
    position: relative;
}
#helppannel img {
	position: absolute;
	top: 0px;
	right: -85px;
}
#emailCheckText{
	padding-top: 7px;
	margin-left: 5px;
}
</style>     

	<div class="container">
		<h1>회원가입 페이지</h1>
		<hr>
		<div id="joinForm" class="">
			<form action="/member/join" method="post" class="form-horizontal" name="joinForm" onsubmit="return validateForm()">
				<div class="form-group">
					<label for="id" class="col-xs-4 control-label">이메일 : </label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="id" name="userEmail"
							placeholder="이메일" />
					</div>
					<div class="help-container">
						<input id="emailCheck" class="btn" type="button" value="중복확인" />
						<div class="hide" id="emailCheckText"></div>
					</div>
				</div>
				<div class="form-group">
					<label for="nick" class="col-xs-4 control-label">닉네임 : </label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="nick" name="userNick"
							placeholder="닉네임" />
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-xs-4 control-label">비밀번호 :
					</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" id="password" name="userPw" placeholder="비밀번호" />
					</div>
				</div>
				<div class="form-group">
					<label for="chargeTypeSelector" class="col-xs-4 control-label" >충전타입 : </label>
					<div class="col-xs-4">
						<select class="form-control" name="chargeTypeNo">
							<option value="1">1 - AC단상 5핀</option>
							<option value="2">2 - AC3상 7핀 급속</option>
							<option value="3">3 - AC3상 7핀 완속</option>
							<option value="4">4 - DC차데모 10핀</option>
							<option value="5">5 - DC콤보 7핀</option>
							<option value="6">6 - 슈퍼차저</option>
						</select>
					</div>
					<div class="help-container">
					<span class="helpicon material-icons" style="margin-top: 5px;">help</span>
						<div class="hide" id="helppannel">
								<img style="border: 1px solid teal;" width="600px"
									src="/resources/image/charger_type.png" />
						</div>
					</div>
				</div>
				<br>
				<br>
				<div class="form-group">
					<div class="col-xs-offset-5">
						<button class="btn btn-primary">회원가입</button>
						<input type="reset" id="cancel" class="btn btn-danger" value="취소" />
					</div>
				</div>
			</form>
		</div>
	</div>

<script type="text/javascript">
$(document).on("mouseenter",".helpicon",function(){
	$('#helppannel').addClass('hide');
	$(this).next('#helppannel').removeClass('hide');
});

$(document).on("mouseleave","#helppannel", function(){
	$(this).addClass('hide');
});

$(document).on("click","#emailCheck", function(){
	checkEmail();
});

function checkEmail(){
	var data;
	data = $("input#id").val();
	console.log(data);
	
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/member/checkemail",
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			$("#emailCheckText").removeClass('hide');
			if(data == 1){
				$("#emailCheckText").html("이미 등록된 이메일 입니다");
			} else if (data == 0) {
				$("#emailCheckText").html("사용 가능한 이메일 입니다");
			} else if (data == 2){
				$("#emailCheckText").html("이메일을 입력해 주십시오");
			} else {
				$("#emailCheckText").html("올바른 형식의 이메일을 입력해 주십시오");
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}
function validateForm() {
	if(document.getElementById('id').value == "" ){
		alert("이메일을 입력해 주세요");
		return false;
	}
	if(!document.getElementById('id').value.includes('@')){
		alert("올바른 이메일을 입력해 주세요")
		return false;
	}
	if(!document.getElementById('id').value.includes('.')){
		alert("올바른 이메일을 입력해 주세요")
		return false;
	}
	if(document.getElementById('nick').value == "" ){
		alert("닉네임을 입력해 주세요");
		return false;
	}
	if(document.getElementById('password').value == "" ){
		alert("비밀번호를 입력해 주세요");
		return false;
	}
}
</script>
<c:import url="../../layout/footer.jsp" />    