package com.ssafy.yumyume.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import com.ssafy.yumyume.user.model.dto.UserDto;
import com.ssafy.yumyume.user.model.service.UserService;
import com.ssafy.yumyume.user.model.service.UserServiceImpl;
import com.ssafy.yumyume.util.helper.ControllerHelper;

@WebServlet("/user")
public class UserController extends HttpServlet implements ControllerHelper {
	private static final long serialVersionUID = 1L;
       
	private final UserService userService = UserServiceImpl.getService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response);
		
		try {
			if("regist".equals(action)) {
				forward(request, response, "/view/user/regist.jsp");
			} else if("idduplicate".equals(action)) {
				checkDuplicate(request, response);
			} else if("login".equals(action)) {
				redirect(request, response, "/view/user/login.jsp");
			} else if("mypage".equals(action)) {
				mypage(request, response);
			} else if("updateMypage".equals(action)) {
				updateMyapge(request, response);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("alertMsg", "컨트롤러 에러!!!");
			forward(request, response, "/error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response);
		
		try {
			if("regist".equals(action)) {
				regist(request, response);
			} else if("login".equals(action)) {
				login(request, response);
			} else if("logout".equals(action)) {
				logout(request, response);
			} else if("update".equals(action)) {
				update(request, response);
			} else if("delete".equals(action)) {
				delete(request, response);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("alertMsg", "컨트롤러 에러!!!");
			forward(request, response, "/error.jsp");
		}
	}

	private void updateMyapge(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String id = request.getParameter("id");
		
		UserDto user = userService.getUserInfobyId(id);
		request.setAttribute("update", "yes");
		request.setAttribute("userInfo", user);
		forward(request, response, "/view/user/mypage.jsp");
		
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String id = request.getParameter("id");
		userService.deleteUser(id);
//		redirect(request, response, "/index.jsp");
		logout(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
//		String isUpdate = request.getParameter("isUpdate");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String emailDomain = request.getParameter("email_domain");
		double weight = Double.parseDouble(request.getParameter("weight"));
		double height = Double.parseDouble(request.getParameter("height"));
		String gender = request.getParameter("gender");
		int age = Integer.parseInt(request.getParameter("age"));
		String activity_level = request.getParameter("activity_level");
		
		UserDto user = new UserDto();
		user.setId(id);
//		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		user.setEmailDomain(emailDomain);
		user.setWeight(weight);
		user.setHeight(height);
		user.setGender(gender);
		user.setAge(age);
		user.setActivityLevel(activity_level);

//		request.setAttribute("userInfo", user);
		
		userService.updateUser(user);
		
		UserDto user_send = userService.getUserInfobyId(id);
	    HttpSession session = request.getSession();
	    session.setAttribute("userInfo", user_send);
		redirect(request, response, "/user?action=mypage&id=" + id);
		
	}


	private void mypage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String id = request.getParameter("id");
		
		if(id.isEmpty()) {
			redirect(request, response, "/view/user/login.jsp");
		} else {
			UserDto user = userService.getUserInfobyId(id);
			request.setAttribute("userInfo", user);
			forward(request, response, "/view/user/mypage.jsp");
		}
	}

	private void regist(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String emailDomain = request.getParameter("email_domain");
			double weight = Double.parseDouble(request.getParameter("weight"));
			double height = Double.parseDouble(request.getParameter("height"));
			String gender = request.getParameter("gender");
			String role = request.getParameter("role");
			int age = Integer.parseInt(request.getParameter("age"));
			String activity_level = request.getParameter("activity_level");
			
			UserDto user = new UserDto();
			user.setId(id);
			user.setPassword(password);
			user.setName(name);
			user.setEmail(email);
			user.setEmailDomain(emailDomain);
			user.setWeight(weight);
			user.setHeight(height);
			user.setGender(gender);
			user.setRole(role);
			user.setAge(age);
			user.setActivityLevel(activity_level);
			
			userService.registUser(user);
			
//			request.setAttribute("alertMsg", "회원가입이 완료되었습니다.");
//			forward(request, response, "/user?action=mvlogin");
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "회원가입이 완료되었습니다.");
			redirect(request, response, "/user?action=login");
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("alertMsg", "회원가입 에러!!!");
			forward(request, response, "/view/user/regist.jsp");
		}
	}
	
	private void checkDuplicate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
		try {
			String id = request.getParameter("id");
			
			if(id == null || id.isEmpty()) {
				request.setAttribute("alertMsg", "아이디를 입력해주세요.");
				forward(request, response, "/user?action=regist");
				return;
			}
			
			if(id.length() < 4) {
				request.setAttribute("alertMsg", "4~20자로 입력해주세요.");
				forward(request, response, "/user?action=regist");
				return;
			}
			
			boolean isDuplicate = userService.idDuplicate(id);
			
//			if(isDuplicate) {
//				request.setAttribute("idCheckResult", "duplicate");
//			} else {
//				request.setAttribute("idCheckResult", "ok");
//				request.setAttribute("checkedId", id);
//			}
			if(isDuplicate) {
				request.setAttribute("idCheckResult", "duplicate");
//				request.setAttribute("alertMsg", "이미 사용중인 아이디입니다.");
			} else {
				request.setAttribute("idCheckResult", "ok");
				request.setAttribute("checkedId", id);
//				request.setAttribute("alertMsg", "사용 가능한 아이디입니다.");
			}
			
			// 모든 파라미터를 다시 request에 설정하여 입력값 유지
			String[] paramNames = {"name", "password", "password_confirm", "email", "email_domain", "weight", "height", "gender", "age", "activity_level"};
			for (String param : paramNames) {
				String value = request.getParameter(param);
				if (value != null && !value.isEmpty()) {
					request.setAttribute(param, value);
				}
			}
			
			forward(request, response, "/user?action=regist");
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("alertMsg", "중복 확인 에러!!!");
			forward(request, response, "/user?action=regist");
		}
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		try {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String saveId = request.getParameter("rememberme");
			
			UserDto user = userService.login(id, password);
			
			if(user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("userInfo", user);
				
				if("rememberme".equals(saveId)) {
					setupCookie("rememberme", id, 60*60*24, request.getContextPath(), response);
//					Cookie cookie = new Cookie("savedId", id);
//					cookie.setMaxAge(60 * 60 * 24);
//					cookie.setPath(request.getContextPath());
//					response.addCookie(cookie);
				} else {
					Cookie[] cookies = request.getCookies();
					for(Cookie c : cookies) {
						if("rememberme".equals(c.getName())) {
							c.setMaxAge(0);
							response.addCookie(c);
							break;
						}
					}
				}
				
				redirect(request, response, "/index.jsp");
			} else {
				request.setAttribute("alertMsg", "아이디 혹은 비밀번호가 일치하지 않습니다.");
				forward(request, response, "/view/user/login.jsp");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("alertMsg", "로그인 에러!!!");
			forward(request, response, "/g/user/login.jsp");
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.invalidate();
//		request.setAttribute("alertMsg", "로그아웃 되었습닌다.");
//		forward(request, response, "/index.jsp");
//		session = request.getSession();
//		session.setAttribute("alertMsg", "로그아웃 되었습니다.");
		redirect(request, response, "/index.jsp");
		
	}
}
