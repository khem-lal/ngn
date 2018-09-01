package bt.gov.g2c.portal.vo;

public class ApplicationDetailsVO {

	
	private int ministryId;
	private int departmentId;
	private String ministryName;
	private String departmentName;
	private int serviceId;
	private String serviceName;
	private int departmentIdEmp;
	private String url;
	public int getDepartmentIdEmp() {
	return departmentIdEmp;
	}
	public void setDepartmentIdEmp(int departmentIdEmp) {
		this.departmentIdEmp = departmentIdEmp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getMinistryId() {
		return ministryId;
	}
	public void setMinistryId(int ministryId) {
		this.ministryId = ministryId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getMinistryName() {
		return ministryName;
	}
	public void setMinistryName(String ministryName) {
		this.ministryName = ministryName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
