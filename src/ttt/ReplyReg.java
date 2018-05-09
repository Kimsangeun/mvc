package ttt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;
import model.BoardVO;

public class ReplyReg implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardVO vo = new BoardVO(); 
		
		vo.setId(Integer.parseInt(request.getParameter("id")));
		vo.setTitle(request.getParameter("title"));
		vo.setPname(request.getParameter("pname"));
		vo.setContent(request.getParameter("content"));
		vo.setPw(request.getParameter("pw"));
		
		int id = new BoardDAO().reply(vo);
		
		ActionData data = new ActionData();
//redirect로 url을 변경하기위해 ActionData를 자료형으로 하는 data객체를 생성해 주었다.
		
		data.setRedirect(true);
//url을 변경하기위해 redirect를 true로 변경해준다.
		data.setPath("Detail?id="+id+"&page="+request.getParameter("page"));
//경로를 새로 설정해준다.		
		return data;
//바뀐 url값을 반환한다.
	}

}
