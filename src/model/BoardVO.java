package model;

import java.util.ArrayList;
import java.util.Date;

public class BoardVO {
	
    int id , gid , seq , lev ,  cnt ;
    Date reg_date ;
    String pname , pw ,  title ,content, upfile;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getLev() {
		return lev;
	}
	public void setLev(int lev) {
		this.lev = lev;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUpfile() {
		if(upfile == null || upfile.equals("")) {
			upfile = "";
		}
		return upfile;
	}
	public void setUpfile(String upfile) {
		this.upfile = upfile;
	}
	
	public boolean isImg() {
		
		ArrayList<String> ext = new ArrayList<>();
		
		ext.add("png");
		ext.add("jpg");
		ext.add("jpeg");
		ext.add("gif");
		ext.add("bnp");
		
		int le = upfile.lastIndexOf(".");
		String fileExt = upfile.substring(le+1).toLowerCase();
		
		return ext.contains(fileExt);
	
	}
	@Override
	public String toString() {
		return "BoardVO [id=" + id + ", gid=" + gid + ", seq=" + seq + ", lev=" + lev + ", cnt=" + cnt + ", reg_date="
				+ reg_date + ", pname=" + pname + ", pw=" + pw + ", title=" + title + ", content=" + content
				+ ", upfile=" + upfile + "]";
	}
    
}
