package ttt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;
import model.BoardVO;

public class ReplyForm implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		BoardDAO dao = new BoardDAO();
//DAO에서 detail()메소드 사용 후 close()해주어야 하기때문에 dao객체를 생성해 주어야 한다.
		
		BoardVO vo = dao.detail(Integer.parseInt(request.getParameter("id")));
//DAO의 detail()메소드는 해당하는 id값을 받아 vo객체를 리턴한다.		
		dao.close();
//detail()메소드에서 close()해주지 않으므로 메소드를 사용해서 닫아주어야한다.		
		vo.setTitle("[답변]"+vo.getTitle());
//답변을 달 때에는 기존의 title에 [답변] 을 추가한 제목으로 작성해주기위해서
		vo.setContent("[답변]"+vo.getContent());
//내용을 작성할 떄에는 기존의 content내용에 [답변]을 추가한 내용으로 작성해주기 위해서		
		request.setAttribute("data", vo);
//data라는 이름으로 vo객체를 넘겨준다.
		request.setAttribute("main", "replyForm.jsp");
//main에 replyForm을 뿌려주도록 요청한다.		
		return new ActionData();
//redirect를 해주지 않으므로 ActionData객체를 생성해주지않고 반환해도 무방하다.
	}

}
