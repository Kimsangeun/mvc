package ttt;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;
import model.BoardVO;

public class DeleteReg implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		
		int page =  Integer.parseInt(request.getParameter("page"));
		
		BoardVO vo = new BoardVO();
		int id = Integer.parseInt(request.getParameter("id"));
		
		vo.setId(id);
		vo.setPw(request.getParameter("pw"));
		
		BoardDAO dao = new BoardDAO();
	
		BoardVO res = dao.search(vo);
		//DAO에서 id와 입력한 pw가 일치하면 DAO에서 res를 반환
		//일치하지 않으면  null반환해서 res에 대입한다.
		
		String msg = "삭제실패";
		String url= "DeleteForm?id="+vo.getId()+"&page="+page ;
		
		if(res!=null) {
			
			if(!res.getUpfile().equals("")) {
				String path = request.getRealPath("up")+"\\";
				path = "C:\\Users\\tkddm\\mvcWork\\mvcJsp\\WebContent\\up";
				
				new File(path+"\\"+res.getUpfile()).delete();
					
			}

			msg = "삭제성공";
			url= "List?page="+page;
			
			dao.delete(id);
			
		}
		
		dao.close();
		
		request.setAttribute("main", "alert.jsp");
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return new ActionData();
	}

}
