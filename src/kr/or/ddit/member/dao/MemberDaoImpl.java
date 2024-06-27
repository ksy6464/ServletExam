package kr.or.ddit.member.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.MyBatisUtil;

public class MemberDaoImpl implements IMemberDao {
	
	private static IMemberDao memDao = new MemberDaoImpl();
	///외부에성
	
	private MemberDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static IMemberDao getInstance() {
		return memDao;
	}

	@Override
	public int insertMember(MemberVO mv) {
		
		SqlSession session = MyBatisUtil.getSqlSession();
		
		int cnt = 0;
		
		try {
			cnt = session.insert("member.insertMember", mv);
			
			if(cnt > 0) {
				session.commit();
			}
			
		} catch (PersistenceException e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return cnt;
	}

	@Override
	public int updateMember(MemberVO mv) {
		SqlSession session = MyBatisUtil.getSqlSession();
				
				int cnt = 0;
				
				try {
					cnt = session.update("member.updateMember", mv);
					
					if(cnt > 0) {
						session.commit();
					}
					
				} catch (PersistenceException e) {
					session.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
				
				
				
		return cnt;
	}

	@Override
	public boolean chechMember(String memId) {
		
		boolean isExist = false;
		SqlSession session = MyBatisUtil.getSqlSession(true);
				
		
		try {
			int cnt = session.selectOne("member.checkMember", memId);
			
			if(cnt > 0) {
				isExist = true;
			}
			
		} catch (PersistenceException e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
				
				
		return isExist;
	}

	@Override
	public int deleteMember(String memId) {
		SqlSession session = MyBatisUtil.getSqlSession();
		
		int cnt = 0;
		
		try {
			cnt = session.delete("member.deleteMember", memId);
			
			if(cnt > 0) {
				session.commit();
			}
			
		} catch (PersistenceException e) {
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		
		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		SqlSession session = MyBatisUtil.getSqlSession(true);
		
		try {
			memList = session.selectList("member.selectAllMember");
			
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return memList;
	}

	@Override
	public List<MemberVO> searchMember(MemberVO mv) {

		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		SqlSession session = MyBatisUtil.getSqlSession(true);
		
		try {
			memList = session.selectList("member.searchMember",mv);
			
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return memList;
	}

	@Override
	public MemberVO getMember(String memId) {
		
		MemberVO mv = null;
		
		SqlSession session = MyBatisUtil.getSqlSession(true);
		
		try {
			mv = session.selectOne("member.getMember", memId);
		} catch (PersistenceException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return mv;
	}

}
