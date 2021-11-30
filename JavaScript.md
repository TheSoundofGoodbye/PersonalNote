+ preventDefault() example 객체의 모든 기본동작을 막는다

in html
```
	<form id="login-form">
```
in JS
```
	const loginForm = document.querySelector("#login-form");
	loginForm.addEventListener("submit", onLoginSubmit);
	function onLoginSubmit(event){
		event.preventDefault();
	}
```

+ localStorage 사용하기
```
localStorage.setItem("key", "value");
localStorage.getItem("key");
localStorage.removeItem("key");
```
+ 클래스 추가 제거
```
object.classList.add();
object.classList.remove();
```

+ String 표시하기 ``사용
```
greeting.innerText = `Hello ${username}`;
greeting.innerText = "Hello " + username;
```
