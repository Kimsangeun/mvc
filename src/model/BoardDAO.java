package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO {
	
	Connection con = null;
	PreparedStatement ptmt = null;
	ResultSet rs = null;
	
	String sql = null;
	public BoardDAO() {
		super();
		Context init;
		
		try {
			init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/oooo");
		
			con = ds.getConnection();
			System.out.println("con:"+con); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public int totalCount() {
		
		try {
			sql="select count(*) from mvcboard";
//전체 행의 수를 세는 sql문		
			ptmt = con.prepareStatement(sql);
			
			rs = ptmt.executeQuery();
			
			rs.next();
			
			return rs.getInt(1);
//테이블에는 count(*)에 대한 하나의 정보만 가지고 있다 . rs.getInt(1)은 첫번째 컬럼의 값이다. 여기서는 첫번쨰행의 값을 리턴한다.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	
		public ArrayList<BoardVO> list(int start ,int end){
			
			ArrayList<BoardVO> res = new ArrayList<>();
			
			
			try {
				//sql = "select * from mvcBoard order by gid desc, seq";
				
				sql = "select * from " + 
						"(select rownum rnum, tt.* from " + 
						"(select * from mvcboard order by gid desc, seq)tt) " + 
						"where rnum >=? and rnum <=?";
				
				ptmt = con.prepareStatement(sql);
				
				ptmt.setInt(1, start);
				ptmt.setInt(2, end);
			
				rs = ptmt.executeQuery();
				
				while(rs.next()) {
					
					BoardVO vo = new BoardVO();
					
					vo.setGid(rs.getInt("gid"));
					vo.setSeq(rs.getInt("seq"));
					vo.setLev(rs.getInt("lev"));
					vo.setId(rs.getInt("id"));
					vo.setTitle(rs.getString("title"));
					vo.setPname(rs.getString("pname"));
					vo.setReg_date(rs.getTimestamp("reg_date"));
					
					
					res.add(vo);
				}
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close();
			}
		
			return res;
			
		}
		
		
		public int insert(BoardVO vo) {
			
			try {
			
				sql="insert into mvcboard"
					+ "(id, gid, seq, lev, cnt, reg_date, pname, pw, title, content, upfile)"
					+ " values(mvcBoard_Seq.nextval, mvcBoard_Seq.nextval, 0, 0, -1, sysdate, ?, ?, ?, ?, ?)";
			
				ptmt = con.prepareStatement(sql);
			
				
				ptmt.setString(1, vo.getPname());
				ptmt.setString(2, vo.getPw());
				ptmt.setString(3, vo.getTitle());
				ptmt.setString(4, vo.getContent());
				ptmt.setString(5, vo.getUpfile());
				
				ptmt.executeUpdate();
				
				sql="select max(id) from mvcboard";
				
				ptmt = con.prepareStatement(sql);
				
				rs = ptmt.executeQuery();
				
				rs.next();
				
				return rs.getInt(1);
//방금 넣은 행의 id값을 리턴해주어 디테일을 보여주는데에 쓰인다.
//rs.getInt()에는 두가지 값을 넣어줄 수 있는데
//만약 스트링값이면 그 컬럼명에 해당하는 컬럼을 말하고
//인덱스값을 입력하면 테이블의 index번째 컬럼을 말한다.
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close();
			}
			
			return 0;
		}
		
		public BoardVO detail(int id) {
			
			try {
				
				sql="select * from mvcboard where id=?";
				
				ptmt = con.prepareStatement(sql);
				
				ptmt.setInt(1, id);
				
				rs = ptmt.executeQuery();
				
				if(rs.next()) {
					
					BoardVO vo = new BoardVO();
					
					vo.setId(id);
					vo.setCnt(rs.getInt("cnt"));
					vo.setGid(rs.getInt("gid"));
					vo.setSeq(rs.getInt("seq"));
					vo.setLev(rs.getInt("lev"));
					vo.setReg_date(rs.getTimestamp("reg_date"));
					vo.setTitle(rs.getString("title"));
					vo.setPname(rs.getString("pname"));
					vo.setContent(rs.getString("content"));
					vo.setUpfile(rs.getString("upfile"));
					
					return vo;
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*finally {
				close();
			}*/ //reply에서 불러야해서
			
			return null; 
		}
		
		public void addCount(int id) {
//조회수를 입력하기 위해 한번 실행될때마다 cnt숫자를 1씩 증가시킨다.
			try {
				sql="update mvcboard set cnt = cnt+1 where id=?";
			
				ptmt = con.prepareStatement(sql);
			
				ptmt.setInt(1, id);
			
				ptmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		public BoardVO search(BoardVO vo) {
			
			sql="select * from mvcboard where id=? and pw=?";
			
			try {
				ptmt = con.prepareStatement(sql);
				
				ptmt.setInt(1, vo.getId());
				ptmt.setString(2, vo.getPw());
				
				rs = ptmt.executeQuery();
				
				if(rs.next()) { //id와 pw가 일치하는 데이터가 있다면
					BoardVO res = new BoardVO();
					
					res.setUpfile(rs.getString("upfile")); //폴더안의 파일을 지워야해서
					
					return res;
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
			
		}
		
		public void delete(int id) {
			
			
			try {
				sql = "delete from mvcboard where id=?";
				
				ptmt = con.prepareStatement(sql);
				
				ptmt.setInt(1, id);
				
				ptmt.executeUpdate();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//finally {close();} 를 쓰지 않는 이유는 dao가 호출되는 파일을 보면 알수있다.
			//dao생성 후 한번 호출되고 끝나는 것이 아니라 여러 메소드를 쓸 경우 호출후 닫아버리면 안되기 떄문
		}
		
		public void modify(BoardVO vo) {
			
			
			try {
				sql = "update mvcboard set pname=?, title=?, content=?, upfile=? where id=?";
				
				ptmt = con.prepareStatement(sql);
				
				ptmt.setString(1, vo.getPname());
				ptmt.setString(2, vo.getTitle());
				ptmt.setString(3, vo.getContent());
				ptmt.setString(4, vo.getUpfile());
				ptmt.setInt(5, vo.getId());
				
				ptmt.executeUpdate();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		public void fileDelete(int id) {
			
			
			try {
				sql = "update mvcboard set upfile = null where id=?";
			
				ptmt = con.prepareStatement(sql);
				
				ptmt.setInt(1, id);
				
				ptmt.executeUpdate();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public int reply(BoardVO vo) {
//ReplyReg로부터 입력된 vo객체를 받아온다.			
			int res = 0;
			
			BoardVO ori = detail(vo.id);
			
			try {
				sql="update mvcboard set seq = seq+1 where gid=? and seq>?";
				
				ptmt = con.prepareStatement(sql);
				
				ptmt.setInt(1, ori.getGid());
				ptmt.setInt(2, ori.getSeq());
				
				ptmt.executeUpdate();
				
				sql = "insert into mvcboard "
						+ "(id, gid, seq, lev, reg_date, cnt, pname, pw, title, content) "
						+ "values (mvcboard_Seq.nextval, ?, ?, ?, sysdate, -1, ?, ?, ?, ?)";
				
				ptmt = con.prepareStatement(sql);
				ptmt.setInt(1, ori.getGid());
				ptmt.setInt(2, ori.getSeq()+1);
				ptmt.setInt(3, ori.getLev()+1);
				ptmt.setString(4, vo.getPname());
				ptmt.setString(5, vo.getPw());
				ptmt.setString(6, vo.getTitle());
				ptmt.setString(7, vo.getContent());
				
				ptmt.executeUpdate();
				
				sql="select max(id) from mvcboard";
				
				ptmt = con.prepareStatement(sql);
				
				rs = ptmt.executeQuery();
				
				rs.next();
				
				res = rs.getInt(1);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				close();
			}
			
			return res;
		}
		
		public void close() {
			
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
			if(ptmt!=null) try {ptmt.close();} catch(SQLException e) {}
			if(con!=null) try {con.close();} catch(SQLException e) {}
			
		}
	
}
