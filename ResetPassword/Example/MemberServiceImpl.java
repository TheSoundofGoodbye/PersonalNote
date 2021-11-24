package member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.dao.MemberDao;
import member.dto.User;
import member.service.face.MemberService;
import member.util.RandomNumberGenerator;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired private MemberDao memberDao;

	@Override
	public int checkEmail(String email) {
		logger.debug("checkEmail : {}", email);
		int result = memberDao.selectCntEmail(email);
		logger.debug("count : {}",result);
		return result;
	}
	
	@Override
	public String generateTokenContent(String email) {
		logger.debug("email : {}", email);

		RandomNumberGenerator rng = new RandomNumberGenerator();
		String token = rng.randomString(50);
		logger.debug(token);
		User user = new User();
		user.setUserEmail(email);
		user.setToken(token);
		memberDao.updateToken(user);
		
		String content = 
			("<div style=\"color:black;\">아래 링크를 클릭하시면 비밀번호를 다시 설정할 수 있는 페이지로 이동합니다 <br>"
			+ "<br>"
			+ "http://localhost:8088/password/change?token=" + token +"<br>"
			+ "<br>"
			+ "from nEVigation</div>");
		
		return content;
	}
	
	@Override
	public int setPassword(String password, String token) {
		logger.debug("setPassword()");
		User user = new User();
		user.setUserPw(password);
		user.setToken(token);
		user.setUserEmail(memberDao.selectEmailByToken(token));
		if (memberDao.updatePassword(user) > 0) {
			logger.debug("password changed");
			memberDao.updateTokenToVoid(user);
			return 1;
		} else {
			logger.debug("token is not matched!");
			return 0;
		}
	}
	
	@Override
	public int changePw(User user, String newPw) {
		logger.debug("password change invoked");
		int result;
		HashMap<String, String> password = new HashMap<>();
		password.put("userEmail", user.getUserEmail());
		password.put("userPw", user.getUserPw());
		password.put("newPw", newPw);
		logger.debug(password.toString());
		if (memberDao.checkCntPw(user)>0){
			result = memberDao.updatePw(password); // 1-업데이트 성공, 0-업데이트 실패
		} else {
			logger.debug("password is not matched");
			result = 2; // 2-비밀번호 불일치
		}
		return result;
	}
	
	@Override
	public int checkToken(String token) {
		return memberDao.selectCntToken(token);
	}
}
