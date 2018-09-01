package bt.gov.g2c.portal.business;

import java.util.ArrayList;
import java.util.List;

import bt.gov.g2c.portal.dao.ApplicationDetailsDAO;
import bt.gov.g2c.portal.vo.ApplicationDetailsVO;
import bt.gov.g2c.portal.vo.MinistryVO;
import bt.gov.g2c.portal.vo.RolesVO;

public class ApplicationDetailsBusiness {

	 ApplicationDetailsDAO applicationDetailsDAO;
	   public ApplicationDetailsBusiness() {
		   applicationDetailsDAO = new ApplicationDetailsDAO();
		}
			
	 public List<MinistryVO> getMinistryList(String roleIds) {
			try {
			    return applicationDetailsDAO.getMinistryList(roleIds);
			} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 
	
	 public List<MinistryVO>  getServiceTreeList(String roleIds) {
			try {
			    return applicationDetailsDAO.getServiceTreeList(roleIds);
			} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 public List<MinistryVO> getDepEmployeesList(String screenName) {
			try {
			    return applicationDetailsDAO.getDepEmployeesList(screenName);
			} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 public List<MinistryVO> getMinEmployeesList(String screenName) {
			try {
			    return applicationDetailsDAO.getMinEmployeesList(screenName);
			} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 public List<MinistryVO> getDepRoleEmployeesList(String screenName,int roleId) {
			try {
			    return applicationDetailsDAO.getDepRoleEmployeesList(screenName,roleId);
			} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 public List<MinistryVO> getMinRoleEmployeesList(String screenName,int roleId) {
			try {
			    return applicationDetailsDAO.getMinRoleEmployeesList(screenName,roleId);
			} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 public String getUserType(String screenName)
	 {
		 String userType = null;
		 try
		 {
			 userType = applicationDetailsDAO.getUserType(screenName);
			 return userType;
		 }
		 catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
		
	 }
	
	 public ArrayList<ApplicationDetailsVO> getEmpMinDep(String screenName) {
			try {
			    return applicationDetailsDAO.getEmpMinDep(screenName);
				} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 public ArrayList<ApplicationDetailsVO> getServiceList(String screenName) {
			try {
			    return applicationDetailsDAO.getServiceList(screenName);
				} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 
	 public String getUrl(int depId) {
			try {
			    return applicationDetailsDAO.getUrl(depId);
				} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 
	 public String getRolesIds(String loginId) {
			try {
			    return applicationDetailsDAO.getRoleIds(loginId);
				} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return null;
			}
	 }
	 
	 public int getDepartmentId(String loginId) {
			try {
			    return applicationDetailsDAO.getDepartmentId(loginId);
				} 
			catch (Exception exception) {
			    exception.printStackTrace();
			    return -1;
			}
			
	 }
	 
	 public int getRoleCount(String loginId)
		{
			int roleCount = -1;
			try
			{
				roleCount = applicationDetailsDAO.getRoleCount(loginId);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return roleCount;
		}
	 public ArrayList<ApplicationDetailsVO> getDepUrl(String loginId)
		{
			
			ArrayList<ApplicationDetailsVO> arrDepUrl = new ArrayList<ApplicationDetailsVO>();
			
			 try {				
				 arrDepUrl = applicationDetailsDAO.getDepUrl(loginId);
							 
				} catch (Exception e) {
					e.printStackTrace();
				} 
		  return arrDepUrl;
		  
		}
	 
	 public ArrayList<RolesVO> getRoleTypeNames(String loginId)
		{
			
			ArrayList<RolesVO> arrRoleNames = new ArrayList<RolesVO>();
			
			 try {				
				 arrRoleNames = applicationDetailsDAO.getRoleTypeNames(loginId);
				
							 
				} catch (Exception e) {
					e.printStackTrace();
				} 
		  return arrRoleNames;
		  		
		}
}
