1. JSP form
```
<label for="id">이메일 : </label>
<div>
  <input type="text" id="id" name="userEmail" placeholder="이메일" />
</div>
<div>
  <input id="emailCheck" type="button" value="중복확인" />
</div>
```
2. Ajax
```
//버튼 클릭 function 지정
$(document).on("click","#emailCheck", function(){
	checkEmail();
});

//Ajax function
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
```
3. Controller
```
@ResponseBody
@RequestMapping(value="/checkemail", method=RequestMethod.POST)
public int checkEmailExist(@RequestBody String email) {
  Gson gson = new Gson();
  String checkEmail = gson.fromJson(email, String.class);
  //1-중복아이디 발견, 0-이메일 생성가능, 2-빈칸, 3-이메일형식이 아님
  int result;
  if("".equals(checkEmail) || checkEmail == null) {
    result = 2;
  } else {
    result = memberService.checkEmail(checkEmail);
    if(!checkEmail.contains("@") && !checkEmail.contains(".")) {
      result = 3;
    }
  }
  return result;
}
```

4. Result div
```
<div class="hide" id="emailCheckText"></div>
```
