package ttt;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Action;
import model.ActionData;
import model.BoardDAO;
import model.BoardVO;

public class InsertReg implements Action{

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		ActionData data = new ActionData();
//path redirect를 위해 data변수를 생성해주었다.		
		BoardVO vo = new BoardVO();
//vo객체에 insertForm.jsp로부터 받아온 값을 저장시킨다.
		vo.setTitle(request.getParameter("title"));
		vo.setPname(request.getParameter("pname"));
		vo.setPw(request.getParameter("pw"));
		vo.setContent(request.getParameter("content"));
		
		vo.setUpfile(fileUpload(request));
		
		
		int id = new BoardDAO().insert(vo);
//DAO에서 insert()메소드로부터 insert한 행의 id값을 리턴받아 id변수에 대입했다.		
		
		data.setRedirect(true);
//redirect를 위해 값을 true로 바꿔 주었다.		
		data.setPath("Detail?id="+id);
//redirect할 path를 설정해주었다.		
		System.out.println(vo);
		
		return data;
//컨트롤러로 보내기위해 data를 리턴한다.
	}
	
	public String fileUpload(HttpServletRequest request) {
//파일 업로드를 위한 메소드로 파일명을 리턴한다.		
		try {
			Part pp = request.getPart("upfile");
//multipart/form-data 형식으로 전송된 데이터는 request.getParameter()와 같은 메서드로접근할 수 있으며, 
//서블릿 3에서는 Part 인터페이스를 사용하여 접근한다.
//HttpServletRequest의 getPart(name)으로 해당 파일명의 Part를 구한다.
			System.out.println("pp : "+pp);
//pp : org.apache.catalina.core.ApplicationPart@60c94408			
		
			if(pp.getContentType()!=null) { 
//HttpServletRequest.getContentType()을 이용해서 multipart/form-data인지 확인한다.
//if문으로 multipart/form-data인경우 코드를 실행한다.
				String fileName="";
//				
				for(String hh: pp.getHeader("Content-Disposition").split(";")) {
					System.out.println("hh: "+hh);
					if(hh.trim().startsWith("filename")) {
//Part의 Content-Disposition헤더가 filename을 포함하면 파일에 해당하므로 코드를 실행시킨다.
//hh:  filename="gong.png"
						fileName=
					hh.substring(hh.indexOf("=")+1).trim().replaceAll("\"", "");
//fileName에 filename에 있는 파일명을 저장시킨다
						System.out.println(fileName);
					}
				}
				
				if(!fileName.equals("")) {
//파일명이 비어있지 않다면
					return fileSave(pp, fileName, request);
//중복파일확인후 파일명을 String값으로 리턴한다.
				}
				
				System.out.println("fileName:"+fileName);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
		return "";
	}
	
	String fileSave(Part pp, String fileName, HttpServletRequest request) {
	
		int pos = fileName.lastIndexOf(".");
//확장자명 전까지의 파일명의 길이를 구한다.		
		String fileDo = fileName.substring(0, pos);
//확장자명을 뺀 파일이름을 구한다.
		String exp = fileName.substring(pos);
//확장자명을 pos에 저장한다.		
		String path = request.getRealPath("up")+"\\";
//파일이 저장되는 절대경로를 저장한다.		
		path = "C:\\Users\\tkddm\\mvcWork\\mvcJsp\\WebContent\\up\\";
//파일을 저장할 폴더경로를 저장한다.		
		int cnt=0;
//중복파일의 갯수를 세기위해 cnt를 설정한다.		
		File ff = new File(path+fileName) ;
//해당파일을 저장하기위해 파일 객체를 생성한다.		
		while(ff.exists()) {
//파일이 존재하면
			fileName = fileDo+ "_" +(cnt++) + exp;
//fileName에 저장할 파일명을 새로 설정한 후 저장한다.
			ff = new File(path+fileName);
//새 파일명으로 바꾼 파일을 파일객체에 저장한다.
		}
		
		try {
			pp.write(path+fileName);
//파일을 part객체에 저장한다.
			pp.delete();
//톰캣폴더의 임시파일을 삭제한다.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	
}
