package ttt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;

public class Detail implements Action{

	
	
	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
//Controller에서 execute()함수가 실행될때 함수안의 코드가 실행된다. 
		
		//ActionData data = new ActionData();
		
		int id = Integer.parseInt(request.getParameter("id"));
//list.jsp에서 받아온 파라미터 아이디 값을 변수 id 에 저장한다.		
		BoardDAO dao = new BoardDAO();
//DAO의 메소드를 사용하기위해 dao변수를 생성해주었다.		
		
		dao.addCount(id);
//addCount()메소드로 Datail.java가 실행될 떄마다 받은 id값에 해당하는 cnt를 증가시켜주기위해 사용한다.
		
		//request.setAttribute("data", dao.detail(Integer.parseInt(request.getParameter("id"))));
	
		request.setAttribute("data", dao.detail(id));
//datail()메소드를 통해 vo객체를 리턴받아 data라는 이름으로 보내준다.		
		request.setAttribute("main", "detail.jsp");
//template의 main에 datail.jsp를 뿌려주도록 요청한다.
		
		dao.close();
//DAO에서 finally{close(); }해주지 않아서 여기서 닫아주어야한다.
		return new ActionData();
//redirect해주지 않아도 되므로 변수를 따로 생성해주지 않고 바로 리턴해주어도된다.
	}

	

}
