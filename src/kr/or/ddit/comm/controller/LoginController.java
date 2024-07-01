package kr.or.ddit.comm.controller;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

@WebServlet("/login.do")
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.getRequestDispatcher("/views/login/loginForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memId = req.getParameter("memId");
		String memPass = req.getParameter("memPass");
		
		// 서비스 생성
		IMemberService memService = MemberServiceImpl.getInstance();
		
		MemberVO mv = memService.getMember(memId); /// memPass 컬럼 추가 했으므로 이제 가져올 수가 있다
		
		boolean isAuthenticated = false; ///인증된것인지 확인할 용도의 변수
		
		if(mv != null) {
			if(memPass.equals(mv.getMemPass())) { // 패스워드 일치하는 경우...
				isAuthenticated = true;
			}
		} ///여기까지 인증처리 다 했다
		
		////////////////////////////////////////////////////////////////////////////////
		
		if(isAuthenticated) {
			// 세션에 로그인 정보 설정하기
			req.getSession().setAttribute("LOGIN_USER", mv);
			///나중에 세션을 뒤져서 LOGIN_USER가 있다면 로그인을 정상적으로 한 사람이고 이 사람에 대한 정보들을 꺼내 쓸 수가 있다
			
			/// 우리가 원하는 페이지로 보내는 작업을 하고 시팓
			// 메인 페이지로 이동하기
			///redirect 이용
			resp.sendRedirect(req.getContextPath()+"/main.do");///어플리케이션이름(req.getContextPath())이 포함되어야 한다
		}else { //인증 실패시...
			// 로그인 화면으로 이동
			resp.sendRedirect(req.getContextPath()+"/login.do");
//			resp.sendRedirect(req.getContextPath()+"/main.do");///이렇게 하면 로그인 실패시 
			///<p>아직 로그인을 하지 않으셨군요, 로그인 먼저 해주세요....&nbsp;&nbsp; 이거 나옴
		}
		
	}
}
