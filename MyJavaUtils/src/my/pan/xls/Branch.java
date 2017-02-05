package my.pan.xls;

public class Branch {

	private String brno;
	private String name;
	private String upbrno;
	private String deptlv;
	public String getBrno() {
		return brno;
	}
	public void setBrno(String brno) {
		this.brno = brno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpbrno() {
		return upbrno;
	}
	public void setUpbrno(String upbrno) {
		this.upbrno = upbrno;
	}
	public String getDeptlv() {
		return deptlv;
	}
	public void setDeptlv(String deptlv) {
		this.deptlv = deptlv;
	}
	@Override
	public String toString() {
		return "Branch [brno=" + brno + ", name=" + name + ", upbrno=" + upbrno
				+ ", deptlv=" + deptlv + "]";
	}
	
}
