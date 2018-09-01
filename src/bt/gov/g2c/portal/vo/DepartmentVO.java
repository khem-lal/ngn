package bt.gov.g2c.portal.vo;

import java.util.List;

public class DepartmentVO {
 private int departmentId;
 private String departmentName;
 private List<ServiceVO> serviceList;
 
 public int getDepartmentId() {
	return departmentId;
}
public void setDepartmentId(int departmentId) {
	this.departmentId = departmentId;
}
public String getDepartmentName() {
	return departmentName;
}
public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}
public List<ServiceVO> getServiceList() {
	return serviceList;
}
public void setServiceList(List<ServiceVO> serviceList) {
	this.serviceList = serviceList;
}
 
}
