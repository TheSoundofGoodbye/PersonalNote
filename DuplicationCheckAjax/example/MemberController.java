package member.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import member.dto.User;
import member.service.face.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired private MemberService memberService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void login(HttpSession session) {
		logger.debug("/member/login");
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginProc(User user, HttpSession session, RedirectAttributes ra) {
		logger.debug("User : {}", user);
		
		session.removeAttribute("attempt");
		//1-로그인성공, 2-아이디없음, 3-비밀번호 다름
		int isLogin = memberService.login(user);
		
		if (isLogin == 1) { 
			logger.debug("로그인 성공");
			session.setAttribute("login", true);
			session.setAttribute("id", user.getUserEmail());
			User userinfo = memberService.getUserinfo(user);
			session.setAttribute("nick", userinfo.getUserNick());
			session.setAttribute("chargeType", userinfo.getChargeTypeNo());
		} else if (isLogin == 2) {
			logger.debug("아이디 없음");
			ra.addFlashAttribute("status", isLogin);
			return "redirect:/member/login";
		} else if (isLogin == 3) {
			logger.debug("비밀번호 불일치");
			ra.addFlashAttribute("status", isLogin);
			return "redirect:/member/login";
		}
		return "redirect:/main";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public void join() {
		logger.debug("/member/join");
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinProc(User user, RedirectAttributes ra) {
		logger.debug("USer : {}", user);
		
		boolean isJoin = memberService.join(user);
		
		if(isJoin) {
			logger.info("회원가입 성공");
			ra.addFlashAttribute("status", 4);
			ra.addFlashAttribute("tempEmail", user.getUserEmail());
			return "redirect:/member/login";
		} else {
			logger.info("회원가입 실패");
			return "redirect:/member/join";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/checkemail", method=RequestMethod.POST)
	public int checkEmailExist(@RequestBody String email) {
		logger.debug("/checkemail");
		logger.debug("email : {}", email);
		Gson gson = new Gson();
		String checkEmail = gson.fromJson(email, String.class);
		logger.debug("email(after unmarshal)  : {}", checkEmail);
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
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		logger.debug("/logout");
		session.invalidate();
		return "redirect:/main";
	}
	
	@RequestMapping(value="/notlogin")
	public void notlogin() {
		logger.debug("/member/notlogin");
	}
}