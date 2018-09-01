package bt.gov.g2c.portal.constant;

public class BtConstants {


    public static final String PROPERTIES_FILE_PATH = "/WEB-INF/g2c.properties";
    public static final String HTTP_PROXY_SERVER = "http.proxyHost";
    public static final String HTTP_PROXY_SERVER_PORT = "http.proxyPort";
    public static final String HTTP_PROXY_SERVER_IP = "10.31.8.30";
    public static final String HTTP_PROXY_SERVER_PORT_ADDRESS = "8080";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_MIME_ENCODING = "mail.mime.encoding";
    public static final String UTF8 = "UTF-8";
    public static final String SMTP_HOST = "10.31.8.201";
    public static final String SMTP_PORT = "25";

    public static final String DATASOURCE_NAME_G2CDB = "java:comp/env/jdbc/G2CDB";
    public static final String DATASOURCE_NAME_DCRCDB = "java:comp/env/jdbc/DCRCDB";

    public static final String ALFRESCO_DATASOURCE_NAME = "java:/jdbc/alfresco";
    public static final String ALFRESCO_PROPERTIES_FILE = "alfresco";

    public static final String VIEW_STATUS_IP_DATE_FORMAT = "yyyy-MM-dd";
    public static final String VIEW_STATUS_OP_DATE_FORMAT = "dd/MM/yyyy";

    public static final String W_STATUS_INVALID = "invalid";
    public static final String STATUS_DTO = "status_dto";
    public static final String PRINT_CONFIRMATION_FLAG = "printed";
    public static final String PRINT_CONFIRMATION_FLAG_STATUS_Y = "Y";
    public static final String WORKFLOW_STATUS_PRINTED = "W_Printed";

    public static final String CID_ISSUANCE_APPRESOURCE = "bt.gov.g2c.dcrc.common.messageresource.ApplicationResources_CIDIssuance";
    public static final String CID_ISSUANCE_PRIV_MAPPING = "bt.gov.g2c.dcrc.common.CIDIssuance";
    public static final String CENSUS_TRANSFER_APPLICATION_RESOURCE = "bt.gov.g2c.dcrc.common.messageresource.ApplicationResources_censustransfer";
    

    /**
     * Added for new datasources
     */
    public static final String DCRCDB_DATASOURCE_NAME = "java:DCRCDB";
    public static final String G2CDB_DATASOURCE_NAME = "java:G2CDB";


    public static final String DCRC_ADMIN = "Dcrc Administrator";
	public static final String DOC_ADMIN = "Doc Administrator";
	public static final String PROTOCOL_ADMIN = "Protocol Administrator";
	public static final String DOA_ADMIN = "Doa Administrator";
	public static final String BLO_ADMIN = "Blo Administrator";
	public static final String DOLS_ADMIN = "Dols Administrator";
	public static final String DOFPS_ADMIN = "Dofps Administrator";
	public static final String DOT_ADMIN = "Dot Administrator";
	public static final String DCSI_ADMIN = "Dcsi Administrator";
	public static final String DOI_ADMIN = "Doi Administrator";
	public static final String DRA_ADMIN = "Dra Administrator";
	public static final String DAHE_ADMIN = "dahe Administrator";
	public static final String NPPF_ADMIN = "nppf Administrator";
	public static final String BCSEA_ADMIN = "bcsea Administrator";
	public static final String DOES_ADMIN = "does Administrator";
	public static final String NHDCL_ADMIN = "nhdcl Administrator";
	public static final String PASSPORT_ADMIN = "Passport Administrator";
	public static final String CC_ADMIN = "CC Supervisor";
	public static final String CITIZEN = "Citizen";
	public static final String ORGANIZATION= "Organization";
	public static final String CC_OPERATOR= "CC Operator";
    public static final String USER_TYPE_EMPLOYEE = "EMPLOYEE";
    public static final String USER_TYPE_PMO = "PMO";
    public static final String USER_TYPE_CCOPERATOR = "CC OPERATOR";
    public static final String USER_TYPE_ORGANIZATION = "ORGANIZATION";
    public static final String USER_TYPE_CITIZEN = "CITIZEN";
    public static final int CC_OPERATOR_ROLEID = 7;
    
    public static final String USER_TYPE_STUDENT = "STUDENT";
}
