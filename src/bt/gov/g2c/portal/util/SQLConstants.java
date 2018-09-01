package bt.gov.g2c.portal.util;

public class SQLConstants {

	public static final String GET_MINISTRY_LIST = "SELECT MINISTRY_ID,MINISTRY_NAME from G2C.T_MINISTRY_MASTER";
	public static final String GET_USER_TYPE = "SELECT USER_TYPE from G2C.T_USER_MASTER WHERE LOGIN_ID = ?";
	public static final String GET_DEPARTMENT_LIST ="SELECT DEPERTAMENT_ID,DEPARTMENT_NAME FROM G2C.T_DEPARTMENT_MASTER WHERE MINISTRY_ID = ?";
	/*public static final String GET_TREE_LIST = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,D.DEPARTMENT_ID,D.DEPARTMENT_NAME,M.MINISTRY_ID,M.MINISTRY_NAME FROM T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M WHERE m.ministry_id = d.ministry_id
			"AND d.department_id = s.department_id"+ 
			"AND s.service_id = p.service_id"+
			"AND p.priv_id = r.priv_id"+
			"AND r.role_id IN (+?+)"+
			"AND d.department_id = ?";*/
	public static final String GET_EMP_MIN_DEP = "SELECT U.DEPARTMENT_ID,U.MINISTRY_ID,D.DEPARTMENT_NAME,M.MINISTRY_NAME FROM G2C.T_USER_REG_DTLS U,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M WHERE D.DEPARTMENT_ID = U.DEPARTMENT_ID AND D.MINISTRY_ID = M.MINISTRY_ID AND  U.LOGIN_ID = ?";
	public static final String GET_EMP_SERVICE_LIST = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME, U.USER_ID FROM T_USER_ROLE_MAPPING U ,T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,T_SERVICE_MASTER S WHERE U.ROLE_ID = R.ROLE_ID AND R.PRIV_ID = P.PRIV_ID AND P.SERVICE_ID = S.SERVICE_ID AND U.USER_ID = ?";
	public static final String GET_EMPLOYEES_LIST_ROLE = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,REG.DEPARTMENT_ID,D.DEPARTMENT_NAME,REG.MINISTRY_ID,M.MINISTRY_NAME,U.USER_ID FROM T_USER_ROLE_MAPPING U ,T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,"+
	"T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M,T_USER_REG_DTLS REG WHERE reg.department_id = d.department_id "+
	"AND reg.ministry_id = m.ministry_id AND U.ROLE_ID = R.ROLE_ID AND R.PRIV_ID = P.PRIV_ID AND REG.DEPARTMENT_ID = S.DEPARTMENT_ID AND "+
    "REG.MINISTRY_ID = D.MINISTRY_ID AND P.SERVICE_ID = S.SERVICE_ID AND U.USER_ID =? and U.ROLE_ID = ?";
	public static final String GET_EMPLOYEES_LIST = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,REG.DEPARTMENT_ID,D.DEPARTMENT_NAME,REG.MINISTRY_ID,M.MINISTRY_NAME,U.USER_ID FROM T_USER_ROLE_MAPPING U ,T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,"+
	"T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M,T_USER_REG_DTLS REG WHERE reg.department_id = d.department_id "+
	"AND reg.ministry_id = m.ministry_id AND U.ROLE_ID = R.ROLE_ID AND R.PRIV_ID = P.PRIV_ID AND REG.DEPARTMENT_ID = S.DEPARTMENT_ID AND "+
    "REG.MINISTRY_ID = D.MINISTRY_ID AND P.SERVICE_ID = S.SERVICE_ID AND U.USER_ID =?" ;
	public static final String GET_HQ_SERVICES = "SELECT M.MINISTRY_ID,M.MINISTRY_NAME,D.DEPARTMENT_ID,D.DEPARTMENT_NAME,S.SERVICE_ID,S.SERVICE_NAME "+
	"FROM G2C.T_MINISTRY_MASTER M,G2C.T_DEPARTMENT_MASTER D,G2C.T_SERVICE_MASTER S "+
	"WHERE M.MINISTRY_ID = D.MINISTRY_ID AND D.DEPARTMENT_ID = S.DEPARTMENT_ID"+
	"AND D.DEPARTMENT_NAME=?";
	public static final String GET_DCRCO_SERVICES = "SELECT DISTINCT S.SERVICE_ID, S.SERVICE_NAME,D.DEPARTMENT_ID,D.DEPARTMENT_NAME,M.MINISTRY_ID,M.MINISTRY_NAME"+ 
		"FROM T_ROLE_PRIV_MAPPING R,T_PRIVILEGE_MASTER P,T_SERVICE_MASTER S,T_DEPARTMENT_MASTER D,T_MINISTRY_MASTER M"+
		"WHERE m.ministry_id = d.ministry_id"+
		"AND d.department_id = s.department_id"+ 
		"AND s.service_id = p.service_id"+
		"AND p.priv_id = r.priv_id"+
		"AND r.role_id = ?"+
		"AND d.department_name = ?";
	public static final String GET_ROLE_IDS = "SELECT ROLE_ID FROM T_USER_ROLE_MAPPING WHERE USER_ID = ?";
	public static final String GET_DEP_ID = "SELECT DEPARTMENT_ID FROM T_USER_REG_DTLS WHERE LOGIN_ID = ?";
	public static final String GET_URL = "SELECT URL FROM T_URL_MASTER WHERE DEPARTMENT_ID = ?";
	public static final String GET_ROLE_COUNT = "SELECT COUNT(*) AS TOTAL_COUNT FROM T_USER_ROLE_MAPPING WHERE USER_ID = ?";
	public static final String ROLE_URL = "SELECT r.department_id , r.login_id , u.url FROM t_user_reg_dtls r,t_url_master u WHERE r.department_id = u.department_id AND r.login_id = ?";
	public static final String ROLE_DETAILS = "SELECT R.ROLE_ID,M.ROLE_NAME FROM T_USER_ROLE_MAPPING R,T_ROLE_MASTER M WHERE R.ROLE_ID = M.ROLE_ID AND R.USER_ID = ?";
	public static final String GET_DEP_EMPLOYEES_LIST = "SELECT DISTINCT s.service_id,s.service_name,d.department_id,d.department_name,m.ministry_id,m.ministry_name FROM  t_service_master s , t_ministry_master m, t_department_master d,t_user_reg_dtls u,t_user_role_mapping r , t_role_priv_mapping rp , t_privilege_master p WHERE u.login_id = r.user_id AND r.role_id = rp.role_id AND rp.priv_id  = p.priv_id AND p.service_id  = s.service_id AND s.department_id = u.department_id AND u.department_id = d.department_id AND u.ministry_id = m.ministry_id AND m.ministry_id = d.ministry_id AND u.login_id =?";
	public static final String GET_MIN_EMPLOYEES_LIST = "SELECT DISTINCT s.service_id,s.service_name,d.department_id,d.department_name,m.ministry_id,m.ministry_name FROM  t_service_master s , t_ministry_master m, t_department_master d,t_user_reg_dtls u,t_user_role_mapping r , t_role_priv_mapping rp , t_privilege_master p,t_department_master dep LEFT OUTER JOIN t_user_reg_dtls ureg ON dep.department_id = ureg.department_id WHERE u.login_id = r.user_id AND r.role_id = rp.role_id AND rp.priv_id  = p.priv_id AND p.service_id  = s.service_id AND u.ministry_id = m.ministry_id AND m.ministry_id = d.ministry_id AND u.login_id = ?";
	public static final String GET_DEP_ROLE_EMPLOYEES_LIST = "SELECT DISTINCT s.service_id,s.service_name,d.department_id,d.department_name,m.ministry_id,m.ministry_name FROM  t_service_master s , t_ministry_master m, t_department_master d,t_user_reg_dtls u,t_user_role_mapping r , t_role_priv_mapping rp , t_privilege_master p WHERE u.login_id = r.user_id AND r.role_id = rp.role_id AND rp.priv_id  = p.priv_id AND p.service_id  = s.service_id AND s.department_id = u.department_id AND u.department_id = d.department_id AND u.ministry_id = m.ministry_id AND m.ministry_id = d.ministry_id AND u.login_id =? and r.role_id = ?";
	public static final String GET_MIN_ROLE_EMPLOYEES_LIST = "SELECT DISTINCT s.service_id,s.service_name,d.department_id,d.department_name,m.ministry_id,m.ministry_name FROM  t_service_master s , t_ministry_master m, t_department_master d,t_user_reg_dtls u,t_user_role_mapping r , t_role_priv_mapping rp , t_privilege_master p,t_department_master dep LEFT OUTER JOIN t_user_reg_dtls ureg ON dep.department_id = ureg.department_id WHERE u.login_id = r.user_id AND r.role_id = rp.role_id AND rp.priv_id  = p.priv_id AND p.service_id  = s.service_id AND u.ministry_id = m.ministry_id AND m.ministry_id = d.ministry_id AND u.login_id = ? and r.role_id = ?";

}


	