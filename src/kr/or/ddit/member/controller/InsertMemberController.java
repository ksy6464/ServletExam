package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.dao.AtchFileDaoImpl;
import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.comm.vo.AtchFileVO;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

@MultipartConfig
@WebServlet("/member/insert.do")
public class InsertMemberController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/member/insertForm.jsp").forward(req, resp);;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String memId = req.getParameter("memId");
		String memName = req.getParameter("memName");
		String memTel = req.getParameter("memTel");
		String memAddr = req.getParameter("memAddr");
		
		IMemberService memService = MemberServiceImpl.getInstance();
		
		IAtchFileService fileService = AtchFileServiceImpl.getInstance();
		
		//첨부파일 저장하기
		AtchFileVO atchFileVO = fileService.saveAtchFileList(req.getParts());
		
		MemberVO mv = new MemberVO(memId, memName, memTel, memAddr);

		if(atchFileVO != null) { ///atchFileVO == null 이면 파일이없다는거니깐 그건 신경쓰지 말자
			mv.setAtchFileId(atchFileVO.getAtchFileId());
			
		}
		
		
		int cnt = memService.registerMember(mv);
		String msg="";
		
		if(cnt > 0) {
			// 회원 등록 작업 성공...
			msg="SUCCESS";
			
		}else {
			// 회원 등록 작업 실패...
			msg="FAIL";
		}
//		req.setAttribute("msg", msg); ///alert창에 띄울때 뭔지 알아야 하니깐 저장
		///redirect 할때 다른 request가 돼서 창이 안뜬다
		
		req.getSession().setAttribute("msg", msg); ///alert창에 띄울때 뭔지 알아야 하니깐 저장
		
		
		// 포워딩 처리하기...
		//req.getRequestDispatcher("/member/list.do").forward(req, resp);
		
		//redirect(리다이렉트) 처리하기...
		resp.sendRedirect(req.getContextPath()+"/member/list.do");
		///이렇게 해주면 이제 화면과 url 동일하게 list.do가 나온다
		
		
	}

}
