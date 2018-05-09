package ttt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;

public class List implements Action{

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		
		//리스트나눠 한페이마다 한정된 리스트를 보여주고 페이지를 넘기도록
		
		ActionData res = new ActionData();

		
		int page = 1;
//기본값으로 첫페이지를 보여주기위해 page를 1로 설정해주었다.		
		int limit = 3, pageLimit = 4;
//limit는 한 페이지당 보여주는 리스트의 갯수이고 pageLimit는 한페이지에서 이동할 수 있는 페이지의 갯수이다.
		
		
		if(request.getParameter("page")!=null && !request.getParameter("page").equals("")) {
			page = Integer.parseInt(request.getParameter("page"));		
		}
		
		int start = (page-1)*limit+1;
		int end = page*limit;
//원하는 페이지에서 보여줄 리스트의 첫번째 값과 마지막 값을 설정해준다.
		
		
		int startPage = (page-1)/pageLimit*pageLimit+1;
		int endPage = startPage+pageLimit-1;
//만약 보여지길 원하는 페이지가 5페이지일 경우 5,6,7,8페이지 이동버튼을 보여주어야 하기 때문에 startPage와 endPage를 설정해준다.		

		
		BoardDAO dao = new BoardDAO();
		
		int total = dao.totalCount(); //DAO의 totalCount()메소드에서 전체 행의 수를 가져와 total에 저장한다.
		
		int totalPage = total/limit; //전체 페이지수는 전체 행의 수에서 한페이지당 보여주는 행의 수로 나눈 값이다.
		//여기서 totalPage는 전체행을 limit인 3으로 나눈 몫이다. 그러므로 나눈 나머지의 페이지를 위해 totalPage를 하나더 추가시켜주어야한다.
		
		if(total%limit !=0) // 전체행이 limit값으로 딱 나눠떨어지지않을 때 totalPage를 하나더 추가해준다.
			totalPage++;
		
		if(endPage > totalPage) //페이지는 4개씩 보여지도록 설정해놓았다. 만약 입력한 행이 6페이지가 끝이라면 7,8페이지는 비어있다.
			endPage = totalPage;//이때 마지막 페이지를 6페이지로 설정해준다.
	
		request.setAttribute("page", page);
		request.setAttribute("start", start);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);
		
		
		request.setAttribute("data", new BoardDAO().list(start, end));
//한페이지당 한정된 리스트를 보여주기위해 시작과 끝값을 입력받아 데이터를 가져온다.
		
		//request.setAttribute("data", new BoardDAO().list());
//List는 DAO에서 list()메소드를 실행시켜 ArrayList를 data라는 이름으로 request한다.

		request.setAttribute("main", "list.jsp");
//template의 main에 list.jsp를 뿌리도록 request한다.
		
		//return new ActionData();
//forward해주므로 객체를 생성할 필요없이 new ActionData()를 바로 리턴해준다.

		dao.close();
		
		return res;
	}
}
