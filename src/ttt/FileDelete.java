package ttt;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;
import model.BoardVO;

public class FileDelete implements Action {
//FileDelete를 서블렛에서 실행시키 위해서 Action 인터페이스를 implement한다.
	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
//인터페이스를 implement하면 반드시 추상메소드를 사용해주어야한다.
//request와 response를 사용하기 위해서 request와 response의 클래스를 자료형으로 받는 변수를 넣어준다.?
		// TODO Auto-generated method stub
		
		BoardVO vo = new BoardVO();
//vo에 받아온 값들을 넣어주기위해 BoardVO객체 vo를 생성하였다.
		
		vo.setId(Integer.parseInt(request.getParameter("id")));
		vo.setPw(request.getParameter("pw"));
		vo.setPname(request.getParameter("pname"));
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		vo.setUpfile(request.getParameter("upfile")); 
//modifyForm의 파라미터값을 받아와서 vo에 넣어준다.
		
		String msg = "인증실패";
//파일 삭제 실행전 메시지를 실패로 넣어둔다.
//if(dao.search(vo)!=null) 이게 false일 경우
//파일 삭제코드가 실행되지 않을 시에 실패메시지가 뜬다.		
		
		//String url = "ModifyForm?id="+vo.getId();
		//request.setAttribute("main", "modifyForm.jsp");
		
		BoardDAO dao = new BoardDAO();
//BoardDAO안의 여러 메소드를 사용하기 위해
//BoardDAO를 자료형으로 받는 변수 dao를 생성해준다.		

		if(dao.search(vo)!=null) {
//id pw값이 일치해서 리턴값이 null이 아닌경우
			
			msg = "파일 삭제 성공!";
//메시지를 성공으로 입력한다.			
			vo.setUpfile("");
//파일 삭제시 파일명을 비워주어야 한다. 안그러면 삭제후에도 파일명이 그대로 남아있음			
			String path = request.getRealPath("up");
			
//path에 파일의 절대경로를 저장한다.
//C:\\Users\\tkddm\\mvcWork\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\mvcJsp\\up
			
			path = "C:\\Users\\tkddm\\mvcWork\\mvcJsp\\WebContent\\up";
//저장할 폴더의 파일경로를 적어준다.		
			new File(path+"\\"+request.getParameter("upfile")).delete();
//파라미터로 받아온 파일명을 찾아 폴더에서 삭제한다.			
			dao.fileDelete(vo.getId());
//파일을 삭제했으므로 id에 해당하는 값을 찾아 디비에서 파일명을 null로 바꿔준다. 
		}
		
		request.setAttribute("msg", msg);
//메시지를 요청한다.
		request.setAttribute("main", "modifyForm.jsp");
//main에 jsp파일을 뿌려주도록 요청한다.		
		request.setAttribute("data", vo);
//data라는 이름으로 vo객체를 넘겨준다.		
		dao.close();
//BoardDAO에서 여러 메소드를 사용하기 떄문에 DAO에서 finally로 닫아주지않고 여기서 닫아주어야한다.
		
		return new ActionData();
//뷰에서 url이 변경될 필요가 없기 때문에
//ActionData의 redirect는 기본값이 false 이므로 따로 객체를 생성해서 사용하지않고
//바로 리턴해주어도 무방하답
	}

}
