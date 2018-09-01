package bt.gov.g2c.portal.vo;

import java.util.List;

public class MinistryVO {
	
	private int ministryId;
	private String ministryName;
	private List<DepartmentVO> departmentList;
	
	public int getMinistryId() {
		return ministryId;
	}
	public void setMinistryId(int ministryId) {
		this.ministryId = ministryId;
	}
	public String getMinistryName() {
		return ministryName;
	}
	public void setMinistryName(String ministryName) {
		this.ministryName = ministryName;
	}
	public List<DepartmentVO> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<DepartmentVO> departmentList) {
		this.departmentList = departmentList;
	}

}
