package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;

/**
 * Servlet implementation class F_Controller
 */
@WebServlet("/board/*") 
//입력받은  url이 컨트롤러를 거치게 하기위해 어노테이션을 *로 설정해주었다.
@MultipartConfig( 
//파일 업로드와 다운로드를 위한 어노테이션 
//어노테이션 : Java 5.0 부터 지원되는 기술로, 기존 설정 파일 (web.xml 등) 에서 제공 하는 설정 내용들을 
//설정 파일에서 설정하지 않아도 해당 소스 내에 설정할 수 있는 방법을 제공함으로써 설정 파일의 크기를 줄이거나 설정 파일 자체를 없앨 수 있는 역할을 하는 기능이다. 

		location="C:\\tomcat\\temp",
		maxFileSize = 1024*5000,
		maxRequestSize = 1024*1024*100,
		fileSizeThreshold = 1024*1024*10

)

public class F_Controller extends HttpServlet {
//Http프로토콜을 위한 Http전용 서블릿인 HttpServlet을 상속받은 컨트롤러 클래스객체응 생성하였다.
	private static final long serialVersionUID = 1L;
//어쩄든 전송을 바이트단위로 해서 보내기때문에 시리얼라이즈해주어야하므로 시리얼번호를 final로 명시해준다.
    /**
     * @see HttpServlet#HttpServlet()
     */
    public F_Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		
		String service = request.getRequestURI().substring(
				(request.getContextPath()+"/board/").length());
//request.getRequestURI() 메소드는 전체 파일경로를 얻어온다. /mvcJsp/board/List
//request.getContextPath() 메소드는 프로젝트 path를 얻어온다. /mvcJsp
//받아온 uri에서 경로를 뗀 클래스명을 스트링으로 저장시킨다.		
		System.out.println(request.getRequestURI());
		System.out.println(request.getContextPath());
		System.out.println(service);
		
		try {
			
			Action action = (Action)Class.forName("ttt."+service).newInstance();
//object라서 Action으로 형변환
//forName으로 받은 클래스명으로하는 object를  Action 자료형으로 형변환 . newInstance()메소드를 호출했기때문에 객체가 생성되었다.
//board.[service] 객체를 생성(newInstance)하여 구현된 인터페이스 자료형으로 형변환하여 인터페이스 객체에 저장. -건환	
			ActionData data = action.execute(request, response);
//게터와 세터가 저장된 ActionData클래스에 forName으로 받은 클래스명을 Action자료형 인터페이스로 저장한 action변수의 execute()메소드를 호출한 후 , data변수에 저장한다.			
//ttt패키지의 클래스가 실행될때마다 controller를 거쳐 execute()메소드가실행된다.
//각 클래스는 실행 후 null이거나 null이 아닌값을 반환한다.
			
			//System.out.println(data);
			
			//System.out.println("data.isRedirect()"+data.isRedirect());
			if(data != null) {
				if(data.isRedirect())
	//data 값의 isRedirect가 true이면 실행한다.
				{
					response.sendRedirect(data.getPath());
	//data의 경로로 리다이렉트하자. 
	//sendRedirect(url)은 브라우저의헤더에 redirect할 페이지의 정보만 설정할 뿐 jsp의 처리흐름을 바꾸지 않는다.
	// 따라서 sendRedirect(url)이후의 코드를 모두 실행한 후 설정된 페이지로 redirect한다.
				}else
				{
					//System.out.println(data.getPath());
					//request.setAttribute("main", data.getPath());
					request.getRequestDispatcher("../view/template.jsp").forward(request, response);
	//template.jsp로 포워딩된다. 페이지전환됨
				}
			}
			
			//System.out.println(action);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//e.printStackTrace()는 에러메세지 발생 시 근원지를 찾아서 단계별로 에러를 출력한다.
		}
		
		//System.out.println(service);
		
		
		//System.out.println(request.getRequestURI());
		//System.out.println(request.getContextPath()+"/board/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
