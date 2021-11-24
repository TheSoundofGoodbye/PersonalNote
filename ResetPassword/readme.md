Reset Password Module (Spring)
=====================

Using javax.mail and Spring Framework
(not Spring boot)

### Structure
1. Input Email 
2. Send token 
3. Update password and reset token

### Details
* Get input email and generate token (Controller)
```
//Create Mail Object
MimeMessage mail = mailSender.createMimeMessage();

//Use MimeMessageHelper
MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");

//Input mail info
helper.setFrom("Sender Email");
helper.setTo("Receiver email");
helper.setSubject("Mail Subject");
helper.setText(service.generateTokenContent(email), true); //true for HTML
```

* Generate Token and content (Service)

```
@Override
public String generateTokenContent(String email) {
    //create token and insert into DB
    RandomNumberGenerator rng = new RandomNumberGenerator();
    String token = rng.randomString(50);
    User user = new User();
    user.setUserEmail(email);
    user.setToken(token);
    memberDao.updateToken(user);
    
    //create content
    String content = 
        ("<div style=\"color:black;\">아래 링크를 클릭하시면 비밀번호를 다시 설정할 수 있는 페이지로 이동합니다 <br>"
        + "<br>"
        + "http://localhost:8088/password/change?token=" + token +"<br>"
        + "<br>"
        + "from nEVigation</div>");

    return content;
}
```

* Generate token method (Utility Class)
```
import java.security.SecureRandom;

public class RandomNumberGenerator {
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	public String randomString(int len){
	   StringBuilder sb = new StringBuilder(len);
	   for(int i = 0; i < len; i++)
	      sb.append(AB.charAt(rnd.nextInt(AB.length())));
	   return sb.toString();
	}
}
```

* Send email (Controller)
    * Use threading to minimize waiting time.
```
new Thread() {
    @Override
    public void run() {
        mailSender.send(mail);
    }
}.start();
```

* Get request + token checking (Controller)

```
if(memberService.checkToken(token)>0) {
    session.setAttribute("token", token);
    return "redirect:/password/change";
} else {
    ra.addFlashAttribute("status", "token is not valid");
    return "redirect:/password/request";
}
```

* Update password and reset token (Controller)
```
String token = (String)session.getAttribute("token");
if(password.equals(password2)) { //비밀번호 확인 절차
    if(memberService.setPassword(password, token)>0) {
        logger.debug("password changed");
        session.removeAttribute("token");
        return "redirect:/member/login";
    } else {
        logger.debug("token is expired");
        session.removeAttribute("token");
        return "redirect:/password/request";
    }
} else {
    logger.debug("password not matched");
    return "redirect:/password/change";
}
```
