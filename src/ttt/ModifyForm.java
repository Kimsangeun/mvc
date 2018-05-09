package ttt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;

public class ModifyForm implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
//DAO의 datail() 메소드에는 finally{ close(); }가 없기 떄문에 
//dao객체 생성후 메소드를 사용하고 마지막에 직접 close()메소드를 써줘야한다
		
		request.setAttribute("main", "modifyForm.jsp");
//template의 main에 modifyForm.jsp를 뿌리도록 요청한다.		
		request.setAttribute("data",
				dao.detail(Integer.parseInt(request.getParameter("id"))));
//data라는 이름으로 DAO의 datail()메소드에서 반환된 vo객체를 요청한다.		
		dao.close();
//dao를 닫아준다.	
		return new ActionData();
//redirect할 필요가 없으므로 ActionData객체를 생성할 필요가 없다.
	}

}
