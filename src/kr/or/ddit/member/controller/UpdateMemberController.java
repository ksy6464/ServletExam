package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.comm.service.AtchFileServiceImpl;
import kr.or.ddit.comm.service.IAtchFileService;
import kr.or.ddit.comm.vo.AtchFileVO;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

@MultipartConfig
@WebServlet("/member/update.do")
public class UpdateMemberController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String memId = req.getParameter("memId");
		IMemberService memService = MemberServiceImpl.getInstance();
		
		MemberVO mv = memService.getMember(memId);
		
		req.setAttribute("mv", mv);
		
		if (mv.getAtchFileId() > 0) { //첨부파일이 존재 하는 경우....
			IAtchFileService fileService = AtchFileServiceImpl.getInstance();
			
			AtchFileVO atchFileVO = new AtchFileVO();
			atchFileVO.setAtchFileId(mv.getAtchFileId());
			atchFileVO =fileService.getAtchFile(atchFileVO);
			/// atchFileVO는 파라미터 겸 리턴 값을 받아준다, 변수 선언 하나더 안하고 그냥 이렇게 하셨다
			
			req.setAttribute("atchFileVO", atchFileVO);
		}
		
		req.getRequestDispatcher("/views/member/updateForm.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		req.setCharacterEncoding("UTF-8"); ///핕터 만들어볼거다
		
		String memId = req.getParameter("memId");
		String memName = req.getParameter("memName");
		String memTel = req.getParameter("memTel");
		String memAddr = req.getParameter("memAddr");
		
		long atchFileId = req.getParameter("atchFileId") == null ?-1
				: Long.parseLong(req.getParameter("atchFileId")) ; // 기존 첨부파일ID
		///다 String으로 가져와서 형변환 해줘야 한다
		
		IMemberService memService = MemberServiceImpl.getInstance();
		
		IAtchFileService fileService = AtchFileServiceImpl.getInstance();
		
		//첨부파일 저장하기
		AtchFileVO atchFileVO = fileService.saveAtchFileList(req.getParts());
		
		MemberVO mv = new MemberVO(memId, memName, memTel, memAddr);
		
		if(atchFileVO != null) { // 새로 업로드 파일을 선택한 경우
			mv.setAtchFileId(atchFileVO.getAtchFileId());
			
		}else { // 새로운 첨부파일을 선택하지 않은 경우...(기존 첨부파일은 유지하고 싶은경우...) 
			///null인경우
			mv.setAtchFileId(atchFileId);
			
		}
		
		
		int cnt = memService.modifyMember(mv);
		
		String msg="";
		
		if(cnt > 0) {
			// 회원정보 삭제 처리 성공...
			msg="SUCCESS";
			
		}else {
			// 회원정보 실패...
			msg="FAIL";
		}
//		req.setAttribute("msg", msg); ///alert창에 띄울때 뭔지 알아야 하니깐 저장
		req.getSession().setAttribute("msg", msg); ///alert창에 띄울때 뭔지 알아야 하니깐 저장
		
		
		resp.sendRedirect(req.getContextPath()+"/member/list.do");
		
		
	}
}
