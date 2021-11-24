package member.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import member.dto.User;
import member.service.face.MemberService;

@Controller
@RequestMapping("/password")
public class PasswordResetController {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);
	@Autowired MemberService memberService;
	@Autowired JavaMailSenderImpl mailSender;
	
	@RequestMapping(value="/request", method=RequestMethod.GET)
	public void inputFindEmail() {
		//1. /password/request, 비밀번호 찾기 초기 페이지로 보냄
		//  - form action="/password/request" method="post" 로 이메일 전달
		logger.debug("/password/request [GET]");
	}
	
	@RequestMapping(value="/request", method=RequestMethod.POST)
	public String resetPassword(User user, RedirectAttributes ra) {
		//2. 입력된 이메일로 변경 링크 송부 
		//  - 링크 클릭 시 /password/change?token=토큰 으로 가게 됨 
		logger.debug("user : {}", user);
		String email = user.getUserEmail();
		//유효한 이메일인지 확인 
		if (memberService.checkEmail(email) > 0) {
			//메일 송부(토큰은 service에서 생성) 
			try {
				
				MimeMessage mail = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mail, "UTF-8");
				helper.setFrom("thesoundofgoodby+noreply@gmail.com");
				helper.setTo(email);
				helper.setSubject("[nEVigation] 비밀번호 초기화 안내");
				helper.setText(memberService.generateTokenContent(email), true);
				
				//이메일 전송 Thread 처리
				new Thread() {
					@Override
					public void run() {
						mailSender.send(mail);
					}
				}.start();
				
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return "redirect:/member/login";
		} else {
			logger.debug("유효하지 않은 이메일");
			ra.addFlashAttribute("isNotJoin", true);
			return "redirect:/password/request";
		}
//		return null;
	}
	
	@RequestMapping(value="/change", method=RequestMethod.GET)
	public String inputPassword(@RequestParam(required=false)String token, HttpSession session, RedirectAttributes ra) {
		//3. 신규 비밀번호 입력창으로 보낸 뒤 token값을 session에 입력
		// - 신규비밀번호 입력 시 /password/change [POST]로 비밀번호 전달
		logger.debug("[GET] token : {}", token);
		if(memberService.checkToken(token)>0) {
			logger.debug("[GET] 올바른token");
			session.setAttribute("token", token);
			return "redirect:/password/change";
		} else {
			logger.debug("[GET] token is not valid");
			ra.addFlashAttribute("status", "token is not valid");
			return "redirect:/password/request";
		}
	}
	
	@RequestMapping(value="/change", method=RequestMethod.POST)
	public String changePassword(String password, String password2, HttpSession session) {
		//4. token이 일치하는 데이터를 찾아서 비밀번호 update하는 로직
		// - 변경완료 후 완료페이지 전환 
		String token = (String)session.getAttribute("token");
		logger.debug("password1 : {}", password);
		logger.debug("password2 : {}", password2);
		logger.debug("[POST] token : {}", token);
		if(password.equals(password2)) { //비밀번호 확인 절차
			logger.debug("password matched");
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
		
	}
}
