package model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	
	public ActionData execute(
			HttpServletRequest request,
			HttpServletResponse response);
	//이름만 있는 추상메소드
	//나중에 구현할놈이 알아서 오버라이딩하라구.
}