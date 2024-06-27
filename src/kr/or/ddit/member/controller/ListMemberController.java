package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;
import oracle.net.aso.d;

@WebServlet(value = "/member/list.do")
public class ListMemberController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		///사용할 데이터 준비해보자
		// 서비스 객체 생성하기
		IMemberService memService = MemberServiceImpl.getInstance();
		
		List<MemberVO> memList = memService.getTotalMember();
		///이제 jsp가 사용할 수 있도록 해줘야한다
		
		req.setAttribute("memList", memList);
		
		
		
		req.getRequestDispatcher("/views/member/list.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
