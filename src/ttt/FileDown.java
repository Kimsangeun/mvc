package ttt;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.StandardSocketOptions;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;

public class FileDown implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		String path = request.getRealPath("up")+"\\";
//path에 파일이 실제 저장되는 절대경로를 저장한다.
		path = "C:\\Users\\tkddm\\mvcWork\\mvcJsp\\WebContent\\up\\";
//이클립스 폴더에 파일을 저장하기위해 파일 경로를 새로 설정해 주었다.		
		String fileName = request.getParameter("file");
//detail.jsp로부터 받아온 file 파라미터 파일이름을 fileName에 저장한다.		
		try {
			String en = URLEncoder.encode(fileName,"utf-8");
//파일 업로드시 한글이름이 깨지지 않게 하기위해 인코딩한 fileName을 en에 저장한다.			
			response.setHeader("Content-Disposition", "attachment;filename="+en);
//		
			FileInputStream fis = new FileInputStream(path+fileName);
//경로안의 파일을 읽어온다.		
//fis : java.io.FileInputStream@48fc7b9e
			ServletOutputStream sos = response.getOutputStream();
//읽어온 파일을 브라우저에 출력할 때 쓰인다.
//sos : org.apache.catalina.connector.CoyoteOutputStream@463b9cf1
			byte[] buf = new byte[1024];
//한번에 읽어올 바이트수를 지정한다.			
			
			while(fis.available() > 0) {
//파일에서 읽을 수 있는 바이트수를 얻고 바이트수가 0이되기 전까지 while문을돌린다.
//fis.available() : 488
				int len = fis.read(buf);
//주어진 바이트 길이에 해당하는 만큼의 길이를 불러서 len에 저장한다.				
//fis.read(buf) : 488				
				sos.write(buf,0,len);
//구한 길이만큼을 브라우저에 출력한다.				
			}
			
			fis.close();
			sos.close();
//Stream을 사용한 후에는 반드시 닫아주어야 한다.			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	

}
