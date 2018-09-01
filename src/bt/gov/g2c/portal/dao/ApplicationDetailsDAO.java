package bt.gov.g2c.portal.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import bt.gov.g2c.portal.util.SQLConstants;
import bt.gov.g2c.portal.vo.ApplicationDetailsVO;
import bt.gov.g2c.portal.vo.DepartmentVO;
import bt.gov.g2c.portal.vo.MinistryVO;
import bt.gov.g2c.portal.vo.RolesVO;
import bt.gov.g2c.portal.vo.ServiceVO;
import bt.gov.g2c.portal.connection.ConnectionManager;
import bt.gov.g2c.portal.constant.BtConstants;

public class ApplicationDetailsDAO {
	
	public List<MinistryVO> getMinistryList(String roleIds) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";
		try {
			con = ConnectionManager.getDirectConnection();
		    sql = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,D.DEPARTMENT_ID,D.DEPARTMENT_NAME,M.MINISTRY_ID,M.MINISTRY_NAME FROM T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M WHERE m.ministry_id = d.ministry_id AND d.department_id = s.department_id AND s.service_id = p.service_id AND p.priv_id = r.priv_id and r.role_id = ? ";
		    preparedStatement = con.prepareStatement(sql);
		    preparedStatement.setInt(1, BtConstants.CC_OPERATOR_ROLEID);
		    resultSet = preparedStatement.executeQuery();
		    int ministryRsId = -1;
		    String ministryRsName = "";
		    int deptRsId = -1;
		    String deptRsName = "";
		    int serviceRsId = -1;
		    String serviceRsName = "";
		    List<MinistryVO> resultList = new ArrayList<MinistryVO>();
		    Map<Integer, String> ministryMap = new HashMap<Integer, String>();
		    Map<Integer, String> deptMap = new HashMap<Integer, String>();
		    Map<Integer, String> serviceMap = new HashMap<Integer, String>();
		    
		    // Ministry Id, Dept Id, Service Id
		    Map<Integer, Map<Integer, List<Integer>>> tree = new HashMap<Integer, Map<Integer,List<Integer>>>();
		    while (resultSet.next()) {
		    	
		    	ministryRsId = resultSet.getInt("MINISTRY_ID");
		    	ministryRsName = resultSet.getString("MINISTRY_NAME");
		    	ministryMap.put(ministryRsId, ministryRsName);
		    	deptRsId = resultSet.getInt("DEPARTMENT_ID");
		    	deptRsName = resultSet.getString("DEPARTMENT_NAME");
		    	deptMap.put(deptRsId, deptRsName);
		    	serviceRsId = resultSet.getInt("SERVICE_ID");
		    	serviceRsName = resultSet.getString("SERVICE_NAME");
		    	serviceMap.put(serviceRsId, serviceRsName);
		    	
		    	if (tree.containsKey(ministryRsId)) {
		    		Map<Integer, List<Integer>> innerMap = tree.get(ministryRsId);
		    		if (innerMap.containsKey(deptRsId)) {
		    			innerMap.get(deptRsId).add(serviceRsId);
		    			} else {
		    			List<Integer> serviceList = new ArrayList<Integer>();
			    		serviceList.add(serviceRsId);
		    			innerMap.put(deptRsId, serviceList);
		    		}
		    	} else {
		    		
		    		List<Integer> serviceList = new ArrayList<Integer>();
		    		serviceList.add(serviceRsId);
		    		Map<Integer, List<Integer>> innerMap = new HashMap<Integer, List<Integer>>();
		    		innerMap.put(deptRsId, serviceList);
		    		tree.put(ministryRsId, innerMap);
		    	}

		    	
		    }
		    
		    
		    List<MinistryVO> ministryVOList = new ArrayList<MinistryVO>();
		    for (Entry<Integer, Map<Integer, List<Integer>>> entry : tree.entrySet()) {
		    	MinistryVO ministryVO = new MinistryVO();
		    	int ministryId = entry.getKey();
		    	Map<Integer, List<Integer>> innerMap = entry.getValue();
		    	List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		    	for (Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
		    		DepartmentVO deptVO = new DepartmentVO();
		    		int deptId = innerEntry.getKey();
		    		List<Integer> serviceIdList = innerEntry.getValue();
		    		List<ServiceVO> serviceVOList = new ArrayList<ServiceVO>();
		    		for (Integer serviceId : serviceIdList) {
		    			ServiceVO serviceVO = new ServiceVO();
		    			serviceVO.setServiceId(serviceId);
		    			serviceVO.setServiceName(serviceMap.get(serviceId));
		    			serviceVOList.add(serviceVO);
					}
		    		
		    		deptVO.setDepartmentId(deptId);
		    		deptVO.setDepartmentName(deptMap.get(deptId));
		    		deptVO.setServiceList(serviceVOList);
		    		
		    		deptVOList.add(deptVO);
				}
		    	
		    	ministryVO.setMinistryId(ministryId);
		    	ministryVO.setMinistryName(ministryMap.get(ministryId));
		    	ministryVO.setDepartmentList(deptVOList);
		    	ministryVOList.add(ministryVO);
			}
		    
		    
		    return ministryVOList;
		    
		}
		catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }
	
	public List<MinistryVO> getServiceTreeList(String roleIds) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "";
		try {
			System.out.println("The control goes within service tree details dao");
		    con = ConnectionManager.getDirectConnection();
		//    sql = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,D.DEPARTMENT_ID,D.DEPARTMENT_NAME,M.MINISTRY_ID,M.MINISTRY_NAME FROM T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M WHERE m.ministry_id = d.ministry_id AND d.department_id = s.department_id AND s.service_id = p.service_id AND p.priv_id = r.priv_id AND r.role_id IN ("+roleIds+")";
		    sql = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,D.DEPARTMENT_ID,D.DEPARTMENT_NAME,M.MINISTRY_ID,M.MINISTRY_NAME FROM T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M WHERE m.ministry_id = d.ministry_id AND d.department_id = s.department_id AND s.service_id = p.service_id AND p.priv_id = r.priv_id and r.role_id = ?";
		    preparedStatement = con.prepareStatement(sql);
		    preparedStatement.setString(1, roleIds);
		    resultSet = preparedStatement.executeQuery();
		    int ministryRsId = -1;
		    String ministryRsName = "";
		    int deptRsId = -1;
		    String deptRsName = "";
		    int serviceRsId = -1;
		    String serviceRsName = "";
		    List<MinistryVO> resultList = new ArrayList<MinistryVO>();
		    Map<Integer, String> ministryMap = new HashMap<Integer, String>();
		    Map<Integer, String> deptMap = new HashMap<Integer, String>();
		    Map<Integer, String> serviceMap = new HashMap<Integer, String>();
		    
		    // Ministry Id, Dept Id, Service Id
		    Map<Integer, Map<Integer, List<Integer>>> tree = new HashMap<Integer, Map<Integer,List<Integer>>>();
		    while (resultSet.next()) {
		    	
		    	ministryRsId = resultSet.getInt("MINISTRY_ID");
		    	ministryRsName = resultSet.getString("MINISTRY_NAME");
		    	ministryMap.put(ministryRsId, ministryRsName);
		    	deptRsId = resultSet.getInt("DEPARTMENT_ID");
		    	deptRsName = resultSet.getString("DEPARTMENT_NAME");
		    	deptMap.put(deptRsId, deptRsName);
		    	serviceRsId = resultSet.getInt("SERVICE_ID");
		    	serviceRsName = resultSet.getString("SERVICE_NAME");
		    	serviceMap.put(serviceRsId, serviceRsName);
		    	
		    	if (tree.containsKey(ministryRsId)) {
		    		Map<Integer, List<Integer>> innerMap = tree.get(ministryRsId);
		    		if (innerMap.containsKey(deptRsId)) {
		    			innerMap.get(deptRsId).add(serviceRsId);
		    		} else {
		    			List<Integer> serviceList = new ArrayList<Integer>();
			    		serviceList.add(serviceRsId);
		    			innerMap.put(deptRsId, serviceList);
		    		}
		    	} else {
		    		
		    		List<Integer> serviceList = new ArrayList<Integer>();
		    		serviceList.add(serviceRsId);
		    		Map<Integer, List<Integer>> innerMap = new HashMap<Integer, List<Integer>>();
		    		innerMap.put(deptRsId, serviceList);
		    		tree.put(ministryRsId, innerMap);
		    	}
		    	
		    }
		    
		    
		    List<MinistryVO> ministryVOList = new ArrayList<MinistryVO>();
		    for (Entry<Integer, Map<Integer, List<Integer>>> entry : tree.entrySet()) {
		    	MinistryVO ministryVO = new MinistryVO();
		    	int ministryId = entry.getKey();
		    	Map<Integer, List<Integer>> innerMap = entry.getValue();
		    	List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		    	for (Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
		    		DepartmentVO deptVO = new DepartmentVO();
		    		int deptId = innerEntry.getKey();
		    		List<Integer> serviceIdList = innerEntry.getValue();
		    		List<ServiceVO> serviceVOList = new ArrayList<ServiceVO>();
		    		for (Integer serviceId : serviceIdList) {
		    			ServiceVO serviceVO = new ServiceVO();
		    			serviceVO.setServiceId(serviceId);
		    			serviceVO.setServiceName(serviceMap.get(serviceId));
		    			serviceVOList.add(serviceVO);
					}
		    		
		    		deptVO.setDepartmentId(deptId);
		    		deptVO.setDepartmentName(deptMap.get(deptId));
		    		deptVO.setServiceList(serviceVOList);
		    		
		    		deptVOList.add(deptVO);
				}
		    	
		    	ministryVO.setMinistryId(ministryId);
		    	System.out.println("The ministry id in the dao is"+ministryId);
		    	ministryVO.setMinistryName(ministryMap.get(ministryId));
		    	System.out.println("The ministry name in the dao is"+(ministryMap.get(ministryId)));
		    	ministryVO.setDepartmentList(deptVOList);
		    	ministryVOList.add(ministryVO);
			}
		    
		    
		    return ministryVOList;
		    
		}
		catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }

	
	public String getUserType(String screenName)
	{
		String userType = "";
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	    try
	    {
	     con = ConnectionManager.getDirectConnection();
		 preparedStatement = con.prepareStatement(SQLConstants.GET_USER_TYPE);
		 preparedStatement.setString(1, screenName);
		 resultSet = preparedStatement.executeQuery();
		 while (resultSet.next()) {
			userType = resultSet.getString("USER_TYPE"); 
		 }
		 return userType;
		 }
	    catch (SQLException sqlException) {
			   sqlException.printStackTrace();
			    return null;
			} 
	    catch (Exception exception) {
		    exception.printStackTrace();
		    return null;
		}
	    finally {
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
		
	}
/*	public ArrayList<ApplicationDetailsVO> getDepartmentList(int ministryId) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
		   System.out.println("The control goes within aaplication details dao");;
		   con = ConnectionManager.getConnection(BtConstants.DATASOURCE_NAME_G2CDB);
		    preparedStatement = con.prepareStatement(SQLConstants.GET_DEPARTMENT_LIST);
		    preparedStatement.setInt(1, ministryId);
		    resultSet = preparedStatement.executeQuery();
		    ApplicationDetailsVO appDetailsVO = null;
		    ArrayList<ApplicationDetailsVO> resultList = new ArrayList<ApplicationDetailsVO>();
		    while (resultSet.next()) {
		    	appDetailsVO = new ApplicationDetailsVO();
		    	appDetailsVO.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
		    	appDetailsVO.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
		    	resultList.add(appDetailsVO);
		    	appDetailsVO = null;
		    }
		    return resultList;
		} catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }*/
	public ArrayList<ApplicationDetailsVO> getEmpMinDep(String loginId)
	{
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
		   System.out.println("The control goes within aaplication details dao");
		   con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_EMP_MIN_DEP);
		    preparedStatement.setString(1, loginId);
		    resultSet = preparedStatement.executeQuery();
		    ApplicationDetailsVO appDetailsVO = null;
		    ArrayList<ApplicationDetailsVO> minDepList = new ArrayList<ApplicationDetailsVO>();
		    while (resultSet.next()) {
		    	appDetailsVO = new ApplicationDetailsVO();
		    	appDetailsVO.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
		    	appDetailsVO.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
		    	appDetailsVO.setMinistryId(resultSet.getInt("MINISTRY_ID"));
		    	appDetailsVO.setMinistryName(resultSet.getString("MINISTRY_NAME"));
		    	minDepList.add(appDetailsVO);
		    	appDetailsVO = null;
		    }
		    return minDepList;
		} catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	}
	public ArrayList<ApplicationDetailsVO> getServiceList(String loginId)
	{
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
		   System.out.println("The control goes within aaplication details dao");
		   con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_EMP_SERVICE_LIST);
		    preparedStatement.setString(1, loginId);
		    resultSet = preparedStatement.executeQuery();
		    ApplicationDetailsVO appDetailsVO = null;
		    ArrayList<ApplicationDetailsVO> serviceList = null;
		    serviceList = new ArrayList<ApplicationDetailsVO>();
		    while (resultSet.next()) {
		    	appDetailsVO = new ApplicationDetailsVO();
		    	appDetailsVO.setServiceId(resultSet.getInt("SERVICE_ID"));
		    	System.out.println("The value of service id is"+appDetailsVO.getServiceId());
		    	appDetailsVO.setServiceName(resultSet.getString("SERVICE_NAME"));
		    	serviceList.add(appDetailsVO);
		    	appDetailsVO = null;
		    }
		    return serviceList;
		} catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	}
	
	public List<MinistryVO> getDepEmployeesList(String loginId) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			System.out.println("The control goes within aaplication details dao for get employees list");
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_DEP_EMPLOYEES_LIST);
		    preparedStatement.setString(1,loginId);
		    resultSet = preparedStatement.executeQuery();
		    int ministryRsId = -1;
		    String ministryRsName = "";
		    int deptRsId = -1;
		    String deptRsName = "";
		    int serviceRsId = -1;
		    String serviceRsName = "";
		    List<MinistryVO> resultList = new ArrayList<MinistryVO>();
		    Map<Integer, String> ministryMap = new HashMap<Integer, String>();
		    Map<Integer, String> deptMap = new HashMap<Integer, String>();
		    Map<Integer, String> serviceMap = new HashMap<Integer, String>();
		    
		    // Ministry Id, Dept Id, Service Id
		    Map<Integer, Map<Integer, List<Integer>>> tree = new HashMap<Integer, Map<Integer,List<Integer>>>();
		    while (resultSet.next()) {
		    	
		    	ministryRsId = resultSet.getInt("MINISTRY_ID");
		    	ministryRsName = resultSet.getString("MINISTRY_NAME");
		    	ministryMap.put(ministryRsId, ministryRsName);
		    	deptRsId = resultSet.getInt("DEPARTMENT_ID");
		    	deptRsName = resultSet.getString("DEPARTMENT_NAME");
		    	deptMap.put(deptRsId, deptRsName);
		    	serviceRsId = resultSet.getInt("SERVICE_ID");
		    	serviceRsName = resultSet.getString("SERVICE_NAME");
		    	System.out.println("The value of service name is"+serviceRsName);
		    	serviceMap.put(serviceRsId, serviceRsName);
		    	
		    	if (tree.containsKey(ministryRsId)) {
		    		System.out.println("The value of ministry id is"+ministryRsId);
		    		Map<Integer, List<Integer>> innerMap = tree.get(ministryRsId);
		    		if (innerMap.containsKey(deptRsId)) {
		    			System.out.println("Before adding service id");
		    			innerMap.get(deptRsId).add(serviceRsId);
		    			System.out.println("After adding service id");
		    		} else {
		    			List<Integer> serviceList = new ArrayList<Integer>();
			    		serviceList.add(serviceRsId);
		    			innerMap.put(deptRsId, serviceList);
		    		}
		    	} else {
		    		
		    		List<Integer> serviceList = new ArrayList<Integer>();
		    		serviceList.add(serviceRsId);
		    		Map<Integer, List<Integer>> innerMap = new HashMap<Integer, List<Integer>>();
		    		innerMap.put(deptRsId, serviceList);
		    		tree.put(ministryRsId, innerMap);
		    	}
		    	
//		    	ministryVO = new MinistryVO();
//		    	ministryVO.setMinistryId(resultSet.getInt("MINISTRY_ID"));
//		    	ministryVO.setMinistryName(resultSet.getString("MINISTRY_NAME"));
//		        
//		    	
//		    	
//		    	resultList.add(ministryVO);
		    	
		    }
		    
		    
		    List<MinistryVO> ministryVOList = new ArrayList<MinistryVO>();
		    for (Entry<Integer, Map<Integer, List<Integer>>> entry : tree.entrySet()) {
		    	MinistryVO ministryVO = new MinistryVO();
		    	int ministryId = entry.getKey();
		    	Map<Integer, List<Integer>> innerMap = entry.getValue();
		    	List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		    	for (Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
		    		DepartmentVO deptVO = new DepartmentVO();
		    		int deptId = innerEntry.getKey();
		    		List<Integer> serviceIdList = innerEntry.getValue();
		    		List<ServiceVO> serviceVOList = new ArrayList<ServiceVO>();
		    		for (Integer serviceId : serviceIdList) {
		    			ServiceVO serviceVO = new ServiceVO();
		    			serviceVO.setServiceId(serviceId);
		    			serviceVO.setServiceName(serviceMap.get(serviceId));
		    			serviceVOList.add(serviceVO);
					}
		    		
		    		deptVO.setDepartmentId(deptId);
		    		deptVO.setDepartmentName(deptMap.get(deptId));
		    		deptVO.setServiceList(serviceVOList);
		    		
		    		deptVOList.add(deptVO);
				}
		    	
		    	ministryVO.setMinistryId(ministryId);
		    	System.out.println("The ministry id in the dao is"+ministryId);
		    	ministryVO.setMinistryName(ministryMap.get(ministryId));
		    	System.out.println("The ministry name in the dao is"+(ministryMap.get(ministryId)));
		    	ministryVO.setDepartmentList(deptVOList);
		    	ministryVOList.add(ministryVO);
			}
		    
		    
		    return ministryVOList;
		    
		}
		catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }

	public List<MinistryVO> getMinEmployeesList(String loginId) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			System.out.println("The control goes within aaplication details dao for get employees list");
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_MIN_EMPLOYEES_LIST);
		    preparedStatement.setString(1,loginId);
		    resultSet = preparedStatement.executeQuery();
		    int ministryRsId = -1;
		    String ministryRsName = "";
		    int deptRsId = -1;
		    String deptRsName = "";
		    int serviceRsId = -1;
		    String serviceRsName = "";
		    List<MinistryVO> resultList = new ArrayList<MinistryVO>();
		    Map<Integer, String> ministryMap = new HashMap<Integer, String>();
		    Map<Integer, String> deptMap = new HashMap<Integer, String>();
		    Map<Integer, String> serviceMap = new HashMap<Integer, String>();
		    
		    // Ministry Id, Dept Id, Service Id
		    Map<Integer, Map<Integer, List<Integer>>> tree = new HashMap<Integer, Map<Integer,List<Integer>>>();
		    while (resultSet.next()) {
		    	
		    	ministryRsId = resultSet.getInt("MINISTRY_ID");
		    	ministryRsName = resultSet.getString("MINISTRY_NAME");
		    	ministryMap.put(ministryRsId, ministryRsName);
		    	deptRsId = resultSet.getInt("DEPARTMENT_ID");
		    	deptRsName = resultSet.getString("DEPARTMENT_NAME");
		    	deptMap.put(deptRsId, deptRsName);
		    	serviceRsId = resultSet.getInt("SERVICE_ID");
		    	serviceRsName = resultSet.getString("SERVICE_NAME");
		    	System.out.println("The value of service name is"+serviceRsName);
		    	serviceMap.put(serviceRsId, serviceRsName);
		    	
		    	if (tree.containsKey(ministryRsId)) {
		    		System.out.println("The value of ministry id is"+ministryRsId);
		    		Map<Integer, List<Integer>> innerMap = tree.get(ministryRsId);
		    		if (innerMap.containsKey(deptRsId)) {
		    			System.out.println("Before adding service id");
		    			innerMap.get(deptRsId).add(serviceRsId);
		    			System.out.println("After adding service id");
		    		} else {
		    			List<Integer> serviceList = new ArrayList<Integer>();
			    		serviceList.add(serviceRsId);
		    			innerMap.put(deptRsId, serviceList);
		    		}
		    	} else {
		    		
		    		List<Integer> serviceList = new ArrayList<Integer>();
		    		serviceList.add(serviceRsId);
		    		Map<Integer, List<Integer>> innerMap = new HashMap<Integer, List<Integer>>();
		    		innerMap.put(deptRsId, serviceList);
		    		tree.put(ministryRsId, innerMap);
		    	}
		    	
//		    	ministryVO = new MinistryVO();
//		    	ministryVO.setMinistryId(resultSet.getInt("MINISTRY_ID"));
//		    	ministryVO.setMinistryName(resultSet.getString("MINISTRY_NAME"));
//		        
//		    	
//		    	
//		    	resultList.add(ministryVO);
		    	
		    }
		    
		    
		    List<MinistryVO> ministryVOList = new ArrayList<MinistryVO>();
		    for (Entry<Integer, Map<Integer, List<Integer>>> entry : tree.entrySet()) {
		    	MinistryVO ministryVO = new MinistryVO();
		    	int ministryId = entry.getKey();
		    	Map<Integer, List<Integer>> innerMap = entry.getValue();
		    	List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		    	for (Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
		    		DepartmentVO deptVO = new DepartmentVO();
		    		int deptId = innerEntry.getKey();
		    		List<Integer> serviceIdList = innerEntry.getValue();
		    		List<ServiceVO> serviceVOList = new ArrayList<ServiceVO>();
		    		for (Integer serviceId : serviceIdList) {
		    			ServiceVO serviceVO = new ServiceVO();
		    			serviceVO.setServiceId(serviceId);
		    			serviceVO.setServiceName(serviceMap.get(serviceId));
		    			serviceVOList.add(serviceVO);
					}
		    		
		    		deptVO.setDepartmentId(deptId);
		    		deptVO.setDepartmentName(deptMap.get(deptId));
		    		deptVO.setServiceList(serviceVOList);
		    		
		    		deptVOList.add(deptVO);
				}
		    	
		    	ministryVO.setMinistryId(ministryId);
		    	System.out.println("The ministry id in the dao is"+ministryId);
		    	ministryVO.setMinistryName(ministryMap.get(ministryId));
		    	System.out.println("The ministry name in the dao is"+(ministryMap.get(ministryId)));
		    	ministryVO.setDepartmentList(deptVOList);
		    	ministryVOList.add(ministryVO);
			}
		    
		    
		    return ministryVOList;
		    
		}
		catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }

	public List<MinistryVO> getDepRoleEmployeesList(String loginId,int roleIds) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			System.out.println("The control goes within aaplication details dao for get employees list");
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_DEP_ROLE_EMPLOYEES_LIST);
		    preparedStatement.setString(1,loginId);
		    preparedStatement.setInt(2,roleIds);
		    resultSet = preparedStatement.executeQuery();
		    int ministryRsId = -1;
		    String ministryRsName = "";
		    int deptRsId = -1;
		    String deptRsName = "";
		    int serviceRsId = -1;
		    String serviceRsName = "";
		    List<MinistryVO> resultList = new ArrayList<MinistryVO>();
		    Map<Integer, String> ministryMap = new HashMap<Integer, String>();
		    Map<Integer, String> deptMap = new HashMap<Integer, String>();
		    Map<Integer, String> serviceMap = new HashMap<Integer, String>();
		    
		    // Ministry Id, Dept Id, Service Id
		    Map<Integer, Map<Integer, List<Integer>>> tree = new HashMap<Integer, Map<Integer,List<Integer>>>();
		    while (resultSet.next()) {
		    	
		    	ministryRsId = resultSet.getInt("MINISTRY_ID");
		    	ministryRsName = resultSet.getString("MINISTRY_NAME");
		    	ministryMap.put(ministryRsId, ministryRsName);
		    	deptRsId = resultSet.getInt("DEPARTMENT_ID");
		    	deptRsName = resultSet.getString("DEPARTMENT_NAME");
		    	deptMap.put(deptRsId, deptRsName);
		    	serviceRsId = resultSet.getInt("SERVICE_ID");
		    	serviceRsName = resultSet.getString("SERVICE_NAME");
		    	System.out.println("The value of service name is"+serviceRsName);
		    	serviceMap.put(serviceRsId, serviceRsName);
		    	
		    	if (tree.containsKey(ministryRsId)) {
		    		System.out.println("The value of ministry id is"+ministryRsId);
		    		Map<Integer, List<Integer>> innerMap = tree.get(ministryRsId);
		    		if (innerMap.containsKey(deptRsId)) {
		    			System.out.println("Before adding service id");
		    			innerMap.get(deptRsId).add(serviceRsId);
		    			System.out.println("After adding service id");
		    		} else {
		    			List<Integer> serviceList = new ArrayList<Integer>();
			    		serviceList.add(serviceRsId);
		    			innerMap.put(deptRsId, serviceList);
		    		}
		    	} else {
		    		
		    		List<Integer> serviceList = new ArrayList<Integer>();
		    		serviceList.add(serviceRsId);
		    		Map<Integer, List<Integer>> innerMap = new HashMap<Integer, List<Integer>>();
		    		innerMap.put(deptRsId, serviceList);
		    		tree.put(ministryRsId, innerMap);
		    	}
		    	
//		    	ministryVO = new MinistryVO();
//		    	ministryVO.setMinistryId(resultSet.getInt("MINISTRY_ID"));
//		    	ministryVO.setMinistryName(resultSet.getString("MINISTRY_NAME"));
//		        
//		    	
//		    	
//		    	resultList.add(ministryVO);
		    	
		    }
		    
		    
		    List<MinistryVO> ministryVOList = new ArrayList<MinistryVO>();
		    for (Entry<Integer, Map<Integer, List<Integer>>> entry : tree.entrySet()) {
		    	MinistryVO ministryVO = new MinistryVO();
		    	int ministryId = entry.getKey();
		    	Map<Integer, List<Integer>> innerMap = entry.getValue();
		    	List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		    	for (Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
		    		DepartmentVO deptVO = new DepartmentVO();
		    		int deptId = innerEntry.getKey();
		    		List<Integer> serviceIdList = innerEntry.getValue();
		    		List<ServiceVO> serviceVOList = new ArrayList<ServiceVO>();
		    		for (Integer serviceId : serviceIdList) {
		    			ServiceVO serviceVO = new ServiceVO();
		    			serviceVO.setServiceId(serviceId);
		    			serviceVO.setServiceName(serviceMap.get(serviceId));
		    			serviceVOList.add(serviceVO);
					}
		    		
		    		deptVO.setDepartmentId(deptId);
		    		deptVO.setDepartmentName(deptMap.get(deptId));
		    		deptVO.setServiceList(serviceVOList);
		    		
		    		deptVOList.add(deptVO);
				}
		    	
		    	ministryVO.setMinistryId(ministryId);
		    	System.out.println("The ministry id in the dao is"+ministryId);
		    	ministryVO.setMinistryName(ministryMap.get(ministryId));
		    	System.out.println("The ministry name in the dao is"+(ministryMap.get(ministryId)));
		    	ministryVO.setDepartmentList(deptVOList);
		    	ministryVOList.add(ministryVO);
			}
		    
		    
		    return ministryVOList;
		    
		}
		catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }
	
	public List<MinistryVO> getMinRoleEmployeesList(String loginId,int roleIds) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			System.out.println("The control goes within aaplication details dao for get employees list");
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_MIN_ROLE_EMPLOYEES_LIST);
		    preparedStatement.setString(1,loginId);
		    preparedStatement.setInt(2,roleIds);
		    resultSet = preparedStatement.executeQuery();
		    int ministryRsId = -1;
		    String ministryRsName = "";
		    int deptRsId = -1;
		    String deptRsName = "";
		    int serviceRsId = -1;
		    String serviceRsName = "";
		    List<MinistryVO> resultList = new ArrayList<MinistryVO>();
		    Map<Integer, String> ministryMap = new HashMap<Integer, String>();
		    Map<Integer, String> deptMap = new HashMap<Integer, String>();
		    Map<Integer, String> serviceMap = new HashMap<Integer, String>();
		    
		    // Ministry Id, Dept Id, Service Id
		    Map<Integer, Map<Integer, List<Integer>>> tree = new HashMap<Integer, Map<Integer,List<Integer>>>();
		    while (resultSet.next()) {
		    	
		    	ministryRsId = resultSet.getInt("MINISTRY_ID");
		    	ministryRsName = resultSet.getString("MINISTRY_NAME");
		    	ministryMap.put(ministryRsId, ministryRsName);
		    	deptRsId = resultSet.getInt("DEPARTMENT_ID");
		    	deptRsName = resultSet.getString("DEPARTMENT_NAME");
		    	deptMap.put(deptRsId, deptRsName);
		    	serviceRsId = resultSet.getInt("SERVICE_ID");
		    	serviceRsName = resultSet.getString("SERVICE_NAME");
		    	System.out.println("The value of service name is"+serviceRsName);
		    	serviceMap.put(serviceRsId, serviceRsName);
		    	
		    	if (tree.containsKey(ministryRsId)) {
		    		System.out.println("The value of ministry id is"+ministryRsId);
		    		Map<Integer, List<Integer>> innerMap = tree.get(ministryRsId);
		    		if (innerMap.containsKey(deptRsId)) {
		    			System.out.println("Before adding service id");
		    			innerMap.get(deptRsId).add(serviceRsId);
		    			System.out.println("After adding service id");
		    		} else {
		    			List<Integer> serviceList = new ArrayList<Integer>();
			    		serviceList.add(serviceRsId);
		    			innerMap.put(deptRsId, serviceList);
		    		}
		    	} else {
		    		
		    		List<Integer> serviceList = new ArrayList<Integer>();
		    		serviceList.add(serviceRsId);
		    		Map<Integer, List<Integer>> innerMap = new HashMap<Integer, List<Integer>>();
		    		innerMap.put(deptRsId, serviceList);
		    		tree.put(ministryRsId, innerMap);
		    	}
		    	
//		    	ministryVO = new MinistryVO();
//		    	ministryVO.setMinistryId(resultSet.getInt("MINISTRY_ID"));
//		    	ministryVO.setMinistryName(resultSet.getString("MINISTRY_NAME"));
//		        
//		    	
//		    	
//		    	resultList.add(ministryVO);
		    	
		    }
		    
		    
		    List<MinistryVO> ministryVOList = new ArrayList<MinistryVO>();
		    for (Entry<Integer, Map<Integer, List<Integer>>> entry : tree.entrySet()) {
		    	MinistryVO ministryVO = new MinistryVO();
		    	int ministryId = entry.getKey();
		    	Map<Integer, List<Integer>> innerMap = entry.getValue();
		    	List<DepartmentVO> deptVOList = new ArrayList<DepartmentVO>();
		    	for (Entry<Integer, List<Integer>> innerEntry : innerMap.entrySet()) {
		    		DepartmentVO deptVO = new DepartmentVO();
		    		int deptId = innerEntry.getKey();
		    		List<Integer> serviceIdList = innerEntry.getValue();
		    		List<ServiceVO> serviceVOList = new ArrayList<ServiceVO>();
		    		for (Integer serviceId : serviceIdList) {
		    			ServiceVO serviceVO = new ServiceVO();
		    			serviceVO.setServiceId(serviceId);
		    			serviceVO.setServiceName(serviceMap.get(serviceId));
		    			serviceVOList.add(serviceVO);
					}
		    		
		    		deptVO.setDepartmentId(deptId);
		    		deptVO.setDepartmentName(deptMap.get(deptId));
		    		deptVO.setServiceList(serviceVOList);
		    		
		    		deptVOList.add(deptVO);
				}
		    	
		    	ministryVO.setMinistryId(ministryId);
		    	System.out.println("The ministry id in the dao is"+ministryId);
		    	ministryVO.setMinistryName(ministryMap.get(ministryId));
		    	System.out.println("The ministry name in the dao is"+(ministryMap.get(ministryId)));
		    	ministryVO.setDepartmentList(deptVOList);
		    	ministryVOList.add(ministryVO);
			}
		    
		    
		    return ministryVOList;
		    
		}
		catch (SQLException sqlException) {
		   sqlException.printStackTrace();
		    return null;
		} catch (Exception exception) {
		   exception.printStackTrace();
		    return null;
		} finally {
		    
		    ConnectionManager.close(con, null, resultSet, preparedStatement);
		}
	    }
	
	
	public String getRoleIds(String loginId)
	{
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<String> list = new ArrayList<String>();
		String listString = "";
		String comSepString = "";
		int roleId = -1;
		String roles = "";
		try {
		
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_ROLE_IDS);
		    preparedStatement.setString(1, loginId);
		    resultSet = preparedStatement.executeQuery();
		    if(resultSet!=null)
		    {
		    while(resultSet.next())
		    {
		      roleId = resultSet.getInt("ROLE_ID");
		      roles = Integer.toString(roleId);
		      list.add(roles);
		    }
		    }
		    for(String s:list)
		    {
		    	listString += s+",";
		    	System.out.println("The value of list string is"+listString);
		    }
		    System.out.println("The value of list string is"+listString);
		    if(!(listString.trim().equals("")))
		    {
		    comSepString = listString.substring(0,listString.lastIndexOf(','));
		    }
		    System.out.println("The value of final list string is"+comSepString);
			return comSepString; 
		}
		catch (SQLException sqlException) {
			   sqlException.printStackTrace();
			    return null;
			} 
		catch (Exception exception) {
			   exception.printStackTrace();
			    return null;
			} finally {
			    
			    ConnectionManager.close(con, null, resultSet, preparedStatement);
			}
	 
	}
	
	public int getDepartmentId(String loginId)
	{
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int depId = -1;
		try {
		
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_DEP_ID);
		    preparedStatement.setString(1, loginId);
		    resultSet = preparedStatement.executeQuery();
		    if(resultSet!=null)
		    {
		    while(resultSet.next())
		    {
		      depId = resultSet.getInt("DEPARTMENT_ID");
		     
		    }
		    }
		   
		}
		catch (SQLException sqlException) {
			   sqlException.printStackTrace();
			   
			} 
		catch (Exception exception) {
			   exception.printStackTrace();
			  
			} finally {
			    
			    ConnectionManager.close(con, null, resultSet, preparedStatement);
			}
		return depId;  
	}
	
	public String getUrl(int depId)
	{
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String url = "";
		try {
		
		    con = ConnectionManager.getDirectConnection();
		    preparedStatement = con.prepareStatement(SQLConstants.GET_URL);
		    preparedStatement.setInt(1, depId);
		    resultSet = preparedStatement.executeQuery();
		    if(resultSet!=null)
		    {
		    while(resultSet.next())
		    {
		      url = resultSet.getString("URL");
		     
		    }
		    }
		   
		}
		catch (SQLException sqlException) {
			   sqlException.printStackTrace();
			   
			} 
		catch (Exception exception) {
			   exception.printStackTrace();
			  
			} finally {
			    
			    ConnectionManager.close(con, null, resultSet, preparedStatement);
			}
		return url;  
	}

	public int getRoleCount(String loginId) throws Exception{
    	String sqlQuery = null;
        ResultSet rs = null;
		Connection con = null;
        PreparedStatement pstmt = null;
        Statement stmt = null;
		int roleCount = -1;
        sqlQuery = " ";
        try {
            con =ConnectionManager.getDirectConnection();
            pstmt = con.prepareStatement(SQLConstants.GET_ROLE_COUNT);
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();
			while(rs.next())
			{
            roleCount  = rs.getInt("TOTAL_COUNT");
			}
        } catch (SQLException SqlExcp) {
            SqlExcp.printStackTrace();
        } finally {
           ConnectionManager.close(con, stmt, rs, pstmt);
        }

    	return roleCount;
    }
	
	public  ArrayList<ApplicationDetailsVO> getDepUrl(String loginId) throws Exception
	{
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 Connection con = null;
		 Statement st = null;
		 ArrayList<ApplicationDetailsVO> arrDepUrl = null;
		 ApplicationDetailsVO goToApp = null;
		 try {
			 arrDepUrl = new ArrayList<ApplicationDetailsVO>();
			 con = ConnectionManager.getDirectConnection();
			 pstmt = con.prepareStatement(SQLConstants.ROLE_URL);
			 pstmt.setString(1, loginId);
			 rs = pstmt.executeQuery();
			 	if (rs != null) 
		            {
		                while (rs.next())
		                {
		                	goToApp = new ApplicationDetailsVO();
		                	goToApp.setDepartmentIdEmp(rs.getInt("DEPARTMENT_ID"));
		                	System.out.println("The value for DAO layer is"+goToApp.getDepartmentId());
		                	goToApp.setUrl(rs.getString("URL"));
		                	System.out.println("The value for DAO layer is"+goToApp.getUrl());
		                	arrDepUrl.add(goToApp);
		                	
		                }
		                	
		             }
			 	else
			 	{
			 		goToApp = new ApplicationDetailsVO();
                	goToApp.setDepartmentIdEmp(-1);
                	goToApp.setUrl("");
                	arrDepUrl.add(goToApp);
			 	}
		                	
		     }
		           
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   finally 
		   			{
		               ConnectionManager.close(con,st ,rs, pstmt);
		            }
		   return arrDepUrl;
	}
	
	public  ArrayList<RolesVO> getRoleTypeNames(String loginId) throws Exception
	{
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 Connection con = null;
		 Statement st = null;
		 ArrayList<RolesVO> arrRoleTypeName = null;
		 RolesVO roleVo = null;
		 try {
			 arrRoleTypeName = new ArrayList<RolesVO>();
			 con = ConnectionManager.getDirectConnection();
			 pstmt = con.prepareStatement(SQLConstants.ROLE_DETAILS);
			 pstmt.setString(1, loginId);
			 rs = pstmt.executeQuery();
			 	if (rs != null) 
		            {
		                while (rs.next())
		                {
		                	roleVo = new RolesVO();
		                	roleVo.setRoleId(rs.getInt("ROLE_ID"));
		                	System.out.println("The value for DAO layer is"+roleVo.getRoleId());
		                	roleVo.setRoleName(rs.getString("ROLE_NAME"));
		                	System.out.println("The value for DAO layer is"+roleVo.getRoleName());
		                	arrRoleTypeName.add(roleVo);
		                
		                	
		                }
		                	
		             }
		                	
		     }
		           
		 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   finally 
		   			{
		               ConnectionManager.close(con,st ,rs, pstmt);
		            }
		   return arrRoleTypeName;
	}
	
}
