package ttt;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.BoardDAO;
import model.BoardVO;

public class ModifyReg implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardVO vo = new BoardVO();
//modifyForm.jsp로부터 가져온 값을 VO에 저장하기위해 객체를 생성하였다.		
		int id = Integer.parseInt(request.getParameter("id"));
//id값을 int형으로 저장하기위해 id변수를 생성하여 대입해주었다.		
		vo.setId(id);
		vo.setPw(request.getParameter("pw"));
		vo.setPname(request.getParameter("pname"));
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
//vo객체에 parameter로 받아온 값을 저장해주었다.		
		if(request.getParameter("upfile")!=null) {
//파라미터로 받아온 값중 파일값이 없다면			
			vo.setUpfile(request.getParameter("upfile"));
//기존 파일이 있으면 수정시 파일을 수정하지 않으면 기존 파일이 쓰임	
		}else {			
//파일이 있다면			
			vo.setUpfile(new InsertReg().fileUpload(request));
//insertReg.java클래스의 fileUpload()메소드를 이용해 기존의 파일을 지우고 새파일을 업로드시켜준다			
		}
		
	//	vo.setUpfile(new InsertReg().fileUpload(request));
		//파일이 없을 수도 있다.그래서 위험하다.
		//수정할 떄 파일을 올리지 않을 시에 기존 파일이 디비에서 삭제된다.
		//파일을 삭제하고 새파일을 올리겠는냐 아니면 기존의 파일을 쓰겠느냐~

		String page = request.getParameter("page");
//페이지를 넘겨주기위해 파라미터로 받아온 page값을 page변수에 대입한다.		
		BoardDAO dao = new BoardDAO();
//DAO안의 메소드를 사용하기 위해 dao객체를 생성한다.	
		BoardVO res = dao.search(vo);
		//DAO에서 id와 입력한 pw가 일치하면 DAO에서 res를 반환
		//일치하지 않으면  null반환해서 res에 대입한다.
		
		String msg = "수정실패";
		String url = "ModifyForm?id="+vo.getId()+"&page="+page;
//search()메소드로부터 반환받은 값이 null일때		
		
		if(res != null) {
//id와 입력한 pw값이 일치하여 res가 null이 아닌경우			
			msg = "수정성공";
			url= "Detail?id="+vo.getId()+"&page="+page;
			
			dao.modify(vo);
//DAO에서 modify()메소드를 통해 해당하는 행을 디비에서 업데이트한다.			
		}
		
		dao.close();
//DAO에서 닫아주지 않았으므로 dao변수를 닫아주어야한다.		
		request.setAttribute("main", "alert.jsp");
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		return new ActionData();
	}

}
