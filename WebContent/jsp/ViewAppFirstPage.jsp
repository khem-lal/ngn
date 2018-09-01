
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%@page import="bt.gov.g2c.portal.business.ApplicationDetailsBusiness"%>
<%@page import="bt.gov.g2c.portal.constant.BtConstants"%>
<%@page import="bt.gov.g2c.portal.vo.MinistryVO"%>
<%@page import="bt.gov.g2c.portal.vo.DepartmentVO"%>
<%@page import="bt.gov.g2c.portal.vo.ServiceVO"%>
<%@page import="bt.gov.g2c.portal.vo.ApplicationDetailsVO"%>
<%@page import="bt.gov.g2c.portal.vo.RolesVO"%>
<%@page import="bt.gov.g2c.framework.common.vo.UserRolePrivilege"%>
<%@page import="java.util.Arrays"%>
<%@page import="bt.gov.g2c.framework.userdetail.InvokeWS"%>
<%@page import="bt.gov.g2c.framework.common.vo.Role"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<liferay-theme:defineObjects/>
<portlet:defineObjects/>
<LINK href="<%=request.getContextPath()%>/css/styles.css" type="text/css" rel="stylesheet">
<LINK href="<%=request.getContextPath()%>/css/inst.css" type="text/css" rel="stylesheet">
<LINK href="<%=request.getContextPath()%>/css/nav.css" type="text/css" rel="stylesheet">
		 <script type="text/javascript">
			//ADDED BY POOJAN SHARMA FOR ACCORDION MENU 

				$(document).ready(function () 
				{
					$('#navi > li > a').click(function()
					{
						if ($(this).attr('class') != 'active')
						{
								$('#navi li ul').slideUp();
								$(this).next().slideToggle();
								$('#navi li a').removeClass('active');
								$(this).addClass('active');
							}
					});
				});

			//END

			function populateTable()
			  {
				  alert("Within populate table");
				  document.forms[0].action = '<%=request.getContextPath()%>/FetchTreeDetailsServlet';
				  document.forms[0].submit();
			  }
			 function clickOnLoad()
			 {
				 alert('load method');
				 document.forms[0].submit();
			 }

			// You probably should factor this out to a .js file
			function toggle(elm) {
			var newDisplay = "none";
			 //elm.style.backgroundImage = url('../images/folder-closed.gif');
			 
			 //alert("The image is"+elm.style.backgroungImage);
			 var e = elm.nextSibling; 
			 while (e != null) {
			  if (e.tagName == "OL" || e.tagName == "ol") {
			   if (e.style.display == "none") {
				newDisplay = "block";
			   // elm.style.backgroundImage = url('../images/folder-open.gif');
			   }
			   break;
			  }
			  e = e.nextSibling;
			 }
			 while (e != null) {
			  if (e.tagName == "OL" || e.tagName == "ol") e.style.display = newDisplay;
			  e = e.nextSibling;
			 }
			}

			function collapseAll(tags) {
			 for (i = 0; i < tags.length; i++) {
			  var lists = document.getElementsByTagName(tags[i]);
			  for (var j = 0; j < lists.length; j++) 
			   lists[j].style.display = "none";
			   var e = document.getElementById("root");
			   e.style.display = "block";
			 }
			}

			function openBookMark() {
			 var h = location.hash;
			 if (h == "") h = "default";
			 if (h == "#") h = "default";
			 var ids = h.split(/[#.]/);
			 for (i = 0; i < ids.length; i++) {
			  if (ids[i] != "") 
				  {
				  toggle(document.getElementById(ids[i]));
				  }
			 }
			 }
			 
			 function goToApp()
			 {
				 alert('before submit');
				 document.forms[0].submit();
				 
			 }

			 function submitForm(){
			   var roleId = document.getElementById('roleId').value;
			   var depId = document.getElementById('depId').value;
			   var urlEmp = document.getElementById('url').value;
			   var errorFound = false;
			   var msg  = "<ul>";
			   //if(roleId.value == -1){
			   if(roleId == -1){
				  msg += "<li><span style='color:#ff0000;'>Please select a valid role</span></li>";
				  errorFound = true;
				}
			   msg += "</ul>";
			   if(errorFound)
					{
						document.getElementById("checkError").style.display = "block";
						document.getElementById("checkError").innerHTML = msg;
						return false;
					}
				else{
					document.getElementById("checkError").style.display = "none";
					window.location.href=urlEmp + "?roleId=" +roleId;
					//document.forms["RedirectOnRole"].action = "http://119.2.120.36/web/guest/login?p_p_id=ApplicationLevelPortlet_WAR_ApplicationLevelPortlet&p_p_lifecycle=1&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=3&_ApplicationLevelPortlet_WAR_ApplicationLevelPortlet__spage=%2FrolesRedirectAction.do&roleId="+roleId+"&depId="+depId;
					//document.forms["RedirectOnRole"].submit();
				}
			  }
		</script>

		<style type="text/css">
			#navi {
				float: left;
				width: 350px;
			}
			#navi li a {
				display: block;
				padding: 10px 15px;
				background: #ccc;
				border-top: 1px solid #eee;
				border-bottom: 1px solid #999;
				text-decoration: none;
				color: #000;
				
			}
			#navi li a:hover, #nav li a.active {
				background: #999;
				color: #fff;
			}
			

			#navi li ul {
				display: none; // used to hide sub-menus
			}
			#navi li ul li {
				display: block;
				padding: 0px 19px;
				text-decoration: none;
				color: #000;
				
			}
			#navi li ul li a {
				padding: 10px 25px;
				background: #ececec;
				border-bottom: 1px dotted #ccc;
			}
		</style>

<%
ApplicationDetailsBusiness appDetailsBusiness = new ApplicationDetailsBusiness();
String uid = null;   


//ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);     
//User user2 = null;
//user2.setUserId(Long.parseLong(request.getParameter("userId")));
//user2.setScreenName(request.getParameter("userId"));
//System.out.println("ArrTreeList =======================> "+user2.getUserId());
List<Role> roles = new ArrayList<Role>();
UserRolePrivilege userRolePriv = new UserRolePrivilege();
userRolePriv.setUserId(request.getParameter("userId"));
InvokeWS invokews = new InvokeWS("http://192.168.124.38:8080/G2CCommonBusinessV2/services/G2CCommonBusiness");
userRolePriv = invokews.populateUserRolePrivilegeHierarchy(userRolePriv,"","");
roles = Arrays.asList(userRolePriv.getRoles());

String screenName = "";
String roleIds= "";
String userType = "";
int depId = -1;
screenName = request.getParameter("userId");
String serviceName = "";
long portalRoleId = -1;
String portalRoleName = "";
Boolean isPortalAdmin = false;
Boolean isDepAdmin = false;
Boolean isCitizen = false;
Boolean isOrg = false;
Boolean isCcSupervisor = false;
Boolean isCcOperator = false;
Boolean isEmployee = false;
Boolean isPmo = false;
String departmentId = "";
String urlCc = "";
String urlEmp = "";
Boolean isMinister = false;
Boolean isDepEmployee = false;
Boolean isStudent = false;
try
{
	userType = appDetailsBusiness.getUserType(screenName);
	System.out.println("portalRoleName11 =======================> "+userType.equalsIgnoreCase(BtConstants.USER_TYPE_EMPLOYEE));
	roleIds = appDetailsBusiness.getRolesIds(screenName);
	//depId = appDetailsBusiness.getDepartmentId(screenName);
	
	
	//List<Role> roles = (List<Role>)RoleServiceUtil.getUserRoles(Long.parseLong(request.getParameter("userId")));
	
	for (Role role : roles) {
	//portalRoleId=role.getRoleId();
	portalRoleName = role.getRoleName();
	System.out.println("portalRoleName22 =======================> "+portalRoleName);
	if(portalRoleName.equalsIgnoreCase("Portal Administrator"))
	{
		isPortalAdmin = true;
	
	}
	else if((portalRoleName.equalsIgnoreCase(BtConstants.DCRC_ADMIN))||(portalRoleName.equalsIgnoreCase(BtConstants.DCSI_ADMIN))
			||(portalRoleName.equalsIgnoreCase(BtConstants.DOC_ADMIN))||(portalRoleName.equalsIgnoreCase(BtConstants.PROTOCOL_ADMIN))
			||(portalRoleName.equalsIgnoreCase(BtConstants.DOA_ADMIN))||(portalRoleName.equalsIgnoreCase(BtConstants.BLO_ADMIN))
			||(portalRoleName.equalsIgnoreCase(BtConstants.DOLS_ADMIN))||(portalRoleName.equalsIgnoreCase(BtConstants.DOFPS_ADMIN))||
			(portalRoleName.equalsIgnoreCase(BtConstants.DOT_ADMIN))||(portalRoleName.equalsIgnoreCase(BtConstants.DOI_ADMIN))||
			(portalRoleName.equalsIgnoreCase(BtConstants.DRA_ADMIN)) || (portalRoleName.equalsIgnoreCase(BtConstants.DAHE_ADMIN))||
			(portalRoleName.equalsIgnoreCase(BtConstants.NPPF_ADMIN))||
			(portalRoleName.equalsIgnoreCase(BtConstants.BCSEA_ADMIN))||
			(portalRoleName.equalsIgnoreCase(BtConstants.DOES_ADMIN))||
			(portalRoleName.equalsIgnoreCase(BtConstants.NHDCL_ADMIN)))
	{
		isDepAdmin = true;
	}
	else if(portalRoleName.equalsIgnoreCase(BtConstants.CC_ADMIN))
	{
		isCcSupervisor = true;
	}
	}
	if(userType.equalsIgnoreCase(BtConstants.USER_TYPE_CITIZEN))
	{
		 isCitizen = true;
	}
	else if(userType.equalsIgnoreCase(BtConstants.USER_TYPE_ORGANIZATION))
	{
		isOrg = true;
	}
	else if(userType.equalsIgnoreCase(BtConstants.USER_TYPE_CCOPERATOR))
	{
		isCcOperator = true;
	}
	
	else if(userType.equalsIgnoreCase(BtConstants.USER_TYPE_EMPLOYEE))
	{
		isEmployee = true;
	}
	else if(userType.equalsIgnoreCase(BtConstants.USER_TYPE_PMO) || userType.equalsIgnoreCase("PSGRD"))
	{
		System.out.println("**************************inside pmo flag"+isPmo);
		isPmo = true;
	}
	else if(userType.equalsIgnoreCase(BtConstants.USER_TYPE_STUDENT))
	{
		isStudent = true;
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	///String path = PortalUtil.getPathFriendlyURLPublic();
	List<MinistryVO> arrTreeList = null;
%>
<table>
<tr>
<td>
<table>
<tr>
<td>
<%if(isPortalAdmin)
  {		
%>
	 <div class="box_header3">My Task List</div>
	 <div class="search_container3">
	 <br/><br/>
		 <div class="documents_btn3"><a href="/web/guest/approve-emp">Pending Employee Registrations</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/approve-cc">Pending CC Registrations</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="">Register Ministry Level User</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/pmo-level-approved">Register PMO Level User</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/edit-users">Edit/Delete Users</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/add-circulars">Add/Delete Manuals And Circulars</a></div>
		 <div class="clear1"></div>
	 </div>
<%}

else if(isPmo){
	System.out.println("**************************pmo url"+isPmo);
    String path1 = PortalUtil.getPathFriendlyURLPublic();
    //String redirecturl1=path1 + "/guest/pmo-page";
    String redirecturl1="https://www.citizenservices.gov.bt/PaymentTracker";
    System.out.println("**************************pmo url"+redirecturl1);
%>
    <script type="text/javascript">
    	window.location.href='<%=redirecturl1 %>';
    </script>
<%
}
else if(isDepAdmin)
{%>
	 <div class="box_header3">My Task List</div>
	 <div class="search_container3">
			<br/><br/>
		 <div class="documents_btn3"><a href="">Register New Employee</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/view-emp">View All Employee Registrations</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/edit-users">Edit/Delete Employees</a></div>
		 <div class="clear1"></div>
	 </div>

<%}
else if(isCcSupervisor) 
{
%>
	 <div class="box_header3">My Task List</div>
	 <div class="search_container3">
	 <br/><br/>
		 <div class="documents_btn3"><a href="">Register New CC Operator</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/web/guest/view-cc">View All CC Registrations</a></div>
		 <div class="clear1"></div>
		 <div class="documents_btn3"><a href="/PaymentTracker/pagePM.do?reportType=pmCCLoginExp">View CC Login Irregularities</a></div>
		 <div class="clear1"></div>
	 
	 </div>
<%} 
else if(isStudent)
{
%>
	<div style="overflow-y: auto; border: 1px dashed white; background-color: #fff;">
	<div class="box_header3">List of Services</div>
<%
	if(!(roleIds.trim().equals("")))
	{
		arrTreeList = appDetailsBusiness.getServiceTreeList(roleIds);
	}
	if(arrTreeList != null)
	{
%>
	<div class="adjust3">
	       <OL id=root>
	       		   <%
      try
      {
      for(int i=0;i<arrTreeList.size();i++)
      { 
    	  MinistryVO minVo = (MinistryVO)arrTreeList.get(i);%>
    	    
	    	  <li class="panel">
				<a href="#" style="margin-left:40px"  id="default"><%=minVo.getMinistryName()%></a>
			  <OL> 
	    	     
    	     <%List<DepartmentVO> arDepList = minVo.getDepartmentList();%>   
  
    	  <%for(int j=0;j<arDepList.size();j++)
    	    {
    	    	DepartmentVO depVo = (DepartmentVO)arDepList.get(j);
    	    	List<ServiceVO> arServiceList = depVo.getServiceList(); 
				String allServices = "";   	   
      	   		for(int k=0;k<arServiceList.size();k++)
     	   		 {
     	    	  	ServiceVO serviceVo = (ServiceVO)arServiceList.get(k);
     	    	  	serviceName = serviceVo.getServiceName();
		          	allServices = allServices + serviceName + " ";
     	 		 }
	      	   departmentId = Integer.toString(depVo.getDepartmentId());
	      	   System.out.println("The value of dep id is"+departmentId);
	      	   request.setAttribute("depId",departmentId);
	      	   urlCc = appDetailsBusiness.getUrl(depVo.getDepartmentId());
    	    %>
    	   <li>
			
		<img style="margin-left:45px" src="<%=request.getContextPath()%>/images/bullet.jpg">&nbsp;
		<a href="#" onclick="window.open('<%=urlCc%>', 'windowemp')" id="default">
			<%=depVo.getDepartmentName()%>
		</a>
     </li>
      <%}%>      
		</OL>
		</li>	
<%}
      
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
}
%> 
	       </OL>
	</div>
</div>
<% 	
}
else if(isCitizen)
{
%>
	<div style="overflow-y: auto; border: 1px dashed white; background-color: #fff;">
	<div class="box_header3">List of Services</div>

<%
if(!(roleIds.trim().equals("")))
{
	arrTreeList = appDetailsBusiness.getServiceTreeList(roleIds);
}
if(arrTreeList != null)
{
%>

<div class="adjust3">
       
	       <OL id=root>
          
      <%
      try
      {
      for(int i=0;i<arrTreeList.size();i++)
      { 
    	  MinistryVO minVo = (MinistryVO)arrTreeList.get(i);%>
    	    
	    	  <li class="panel">
			<a href="#" style="margin-left:40px"  id="default"><%=minVo.getMinistryName()%></a>
			  <OL> 
	    	     
    	     <%List<DepartmentVO> arDepList = minVo.getDepartmentList();%>   
  
    	  <%for(int j=0;j<arDepList.size();j++)
    	    {
    	    	DepartmentVO depVo = (DepartmentVO)arDepList.get(j);
    	    	List<ServiceVO> arServiceList = depVo.getServiceList(); 
				String allServices = "";   	   
      	   		for(int k=0;k<arServiceList.size();k++)
     	   		 {
     	    	  	ServiceVO serviceVo = (ServiceVO)arServiceList.get(k);
     	    	  	serviceName = serviceVo.getServiceName();
		          	allServices = allServices + serviceName + " ";
     	 		 }
	      	   departmentId = Integer.toString(depVo.getDepartmentId());
	      	   System.out.println("The value of dep id is"+departmentId);
	      	   request.setAttribute("depId",departmentId);
	      	   urlCc = appDetailsBusiness.getUrl(depVo.getDepartmentId());
    	    %>
    	   <li>
			
		<img style="margin-left:45px" src="<%=request.getContextPath()%>/images/bullet.jpg">&nbsp;
		<a href="#" onclick="window.open('<%=urlCc%>', 'windowemp')" id="default">
			<%=depVo.getDepartmentName()%>
		</a>
     </li>
      <%}%>      
		</OL>
		</li>	
<%}
      
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
}
%>
</OL>	


</div>
</div>
<%
}
else if(isOrg)
{
%>
	 <div class="box_header3">My Task List</div>
	 <div class="search_container3">
	 <div class="documents_btn3"><a href="#">Submit Quarterly Industry Report</a></div>
	 <div class="clear1"></div>
	 <div class="documents_btn3"><a href="#">Submit Explosive Usage Report</a></div>
	 <div class="clear1"></div>
	 </div>
<%}
else if(isCcOperator)
{
%>

	<div style="overflow-y: auto; border: 1px dashed white; background-color: #fff;">
	<div class="box_header3">List of Services</div>

<%
	if(!(roleIds.trim().equals("")))
	{
		arrTreeList = appDetailsBusiness.getMinistryList(roleIds);
	}
	if(arrTreeList != null)
	{
%>

<div class="adjust300">
	<ul id="navi">    
      <%
      try
      {
      for(int i=0;i<arrTreeList.size();i++)
      { 
    	  MinistryVO minVo = (MinistryVO)arrTreeList.get(i);%>
    	    
	    	  <li style="list-style: none">
				<a href="#" style="margin-left:40px"><%=minVo.getMinistryName()%></a>
			  <ul> 
	    	     
    	     <%List<DepartmentVO> arDepList = minVo.getDepartmentList();%>   
  
    	  <%for(int j=0;j<arDepList.size();j++)
    	    {
    	    	DepartmentVO depVo = (DepartmentVO)arDepList.get(j);
    	    List<ServiceVO> arServiceList = depVo.getServiceList(); 
		String allServices = "";   	   
      	   for(int k=0;k<arServiceList.size();k++)
     	   		 {
     	    	  ServiceVO serviceVo = (ServiceVO)arServiceList.get(k);
     	    	  serviceName = serviceVo.getServiceName();
		          allServices = allServices + serviceName + " ";
     	 		}
      	   departmentId = Integer.toString(depVo.getDepartmentId());
      	   request.setAttribute("depId",departmentId);
      	   urlCc = appDetailsBusiness.getUrl(depVo.getDepartmentId());
    	    %>
    	   <li style="list-style: none">
				<a href="#" onclick="window.open('<%=urlCc%>', 'windowemp')"><%=depVo.getDepartmentName()%></a> 
		   </li>
      <%}%>      
		</ul>
		</li>
<%}
      
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }

%>
</ul>	
</div>
<%
}
else
{%>
<div class="adjust3">
<div>
<OL id=root>
<li>
You do not have the permissions to navigate to any services.
</li>
</OL>
</div>
</div>
</div>
<%}
}
else if(isEmployee)
{
int count = -1;
int depIdEmp = -1;
ArrayList<ApplicationDetailsVO> depUrlDet = null;
ArrayList<RolesVO> arrRoleList = null;
try
{
	depUrlDet = appDetailsBusiness.getDepUrl(screenName);
	for(int i=0;i<depUrlDet.size();i++)
	{
		//ApplicationDetailsVO goToApp = (ApplicationDetailsVO)depUrlDet.get(i);
		depIdEmp = depUrlDet.get(i).getDepartmentIdEmp();
		urlEmp = appDetailsBusiness.getUrl(depIdEmp);
		
	}
	count = appDetailsBusiness.getRoleCount(screenName);
	arrRoleList = appDetailsBusiness.getRoleTypeNames(screenName);
	request.setAttribute("roleList",arrRoleList);
	if(depIdEmp == -1)
	{
		isMinister = true;
	}
	else
	{
		isDepEmployee = true;
	}
}
catch(Exception ex)
{
	ex.printStackTrace();
}
	
if(count > 1)
{
%>
<form styleId="RedirectOnRole" name="RedirectOnRole" action="/rolesRedirectAction">
<input type="hidden" id="depId" name="depId" value="<%=depIdEmp %>">
<input type="hidden" id="url" name="url" value="<%=urlEmp %>">
<table align="center" width="630px" border="0">
<tr>
  <td width="100%">
	<div id="checkError" style="display:none"></div>
</td>
</tr>
	<tr align="center">
       <td align="center" width="100%" valign="middle" class="Caption" height="30">Select the Role with which you want to proceed.</td>
	 </tr>
	 <tr>
	 <td>&nbsp;</td>
	 </tr>
	 <tr align="center">
	   <td align="center">
	  <select class="DropDown" name="roleId" id="roleId" >
         	<option value="-1">Please Select the appropriate Role</option>
         	<% for(int i =0; i<arrRoleList.size(); i++){ %>
				<option value="<%=arrRoleList.get(i).getRoleId() %>"><%=arrRoleList.get(i).getRoleName() %></option>
			<%} %>	
			
	  </select>
	   </td>
	 </tr>
	 <tr>
	 <td>&nbsp;</td>
	 </tr>
	 <tr align="center">
	 <td align="center">
	 	<input class="Button2" id="btnLogin" type="button" value="Submit" name="btnLogin"  onclick="submitForm();">
	 </td>
	 </tr>
    <tr>
    	<td>&nbsp;</td>
    </tr>
    <tr align="center">
    <td>&nbsp;</td>
    </tr>
 </table>
</form>
<% }

else if(count == 1)
{
	if(depIdEmp == -1)
	{
%>	
<div style="overflow-y: auto; border: 1px dashed white; background-color: #fff;">
<div class="box_header3">List of Services</div>

<%
if(!(roleIds.trim().equals("")))
{
	arrTreeList = appDetailsBusiness.getMinEmployeesList(screenName);
}
if(arrTreeList != null)
{
%>

<div class="adjust300">
       
	       <OL id=root>
          
      <%
      try
      {
      for(int i=0;i<arrTreeList.size();i++)
      { 
    	  MinistryVO minVo = (MinistryVO)arrTreeList.get(i);%>
    	    
	    	  <li class="panel">
			<a href="#default" style="margin-left:40px" id="default"><%=minVo.getMinistryName()%></a>
			  <OL> 
	    	     
    	     <%List<DepartmentVO> arDepList = minVo.getDepartmentList();%>   
  
    	  <%for(int j=0;j<arDepList.size();j++)
    	    {
    	    	DepartmentVO depVo = (DepartmentVO)arDepList.get(j);
    	    	%>
    	  
			  <%
    	   	   urlEmp = appDetailsBusiness.getUrl(depVo.getDepartmentId());
    	    %>
    	   <li >
			
		<img style="margin-left:45px" src="<%=request.getContextPath()%>/images/bullet.jpg">&nbsp;<a href="#" onclick="window.open('<%=urlEmp%>?roleId=<%=roleIds%>', 'windowemp')" id="default">
		<%=depVo.getDepartmentName()%></a>
		
			 
     </li>
   
     
      <%}%>      
		</OL>
		</li>	
<%}
      
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
      %>
      </OL>
      </div>
<% }
	else
	{%>
	<div class="adjust3">
	<div>
	<OL id=root>
	<li>
	You do not have the permissions to navigate to any services.
	</li>
	</OL>
	</div>
	</div>
<%}%>
</div>
<%	}
	else
	{
%>
	
	
<div style="overflow-y: auto; border: 1px dashed white; background-color: #fff;">
<div class="box_header3">List of Services</div>

<%
if(!(roleIds.trim().equals("")))
{
	arrTreeList = appDetailsBusiness.getDepEmployeesList(screenName);
}
if(arrTreeList != null)
{
%>

<div class="adjust300">
       
	       <OL id=root>
          
      <%
      try
      {
      for(int i=0;i<arrTreeList.size();i++)
      { 
    	  MinistryVO minVo = (MinistryVO)arrTreeList.get(i);%>
    	    
	    	  <li class="panel" style="list-style: none">
			<a href="#" style="margin-left:40px" id="default"><%=minVo.getMinistryName()%></a>
			  <OL> 
	    	     
    	     <%List<DepartmentVO> arDepList = minVo.getDepartmentList();%>   
  
    	  <%for(int j=0;j<arDepList.size();j++)
    	    {
    	    	DepartmentVO depVo = (DepartmentVO)arDepList.get(j);
    	    	%>
    	  <li style="list-style: none" >
			<a href="#" style="margin-left:45px" id="planning"><%=depVo.getDepartmentName()%></a>
			  <OL> 
			  <%
    	    List<ServiceVO> arServiceList = depVo.getServiceList();  	   
      	   for(int k=0;k<arServiceList.size();k++)
     	   		 {
     	    	  ServiceVO serviceVo = (ServiceVO)arServiceList.get(k);
     	    	  serviceName = serviceVo.getServiceName();
     	 		
      	   			urlEmp = appDetailsBusiness.getUrl(depVo.getDepartmentId());
    	    %>
    	   <li style="list-style: none">
			
		<img style="margin-left:50px" src="<%=request.getContextPath()%>/images/bullet.jpg">&nbsp;<a href="#" onclick="window.open('<%=urlEmp%>?roleId=<%=roleIds%>', 'windowemp')" id="default">
		<%=serviceName%></a>
		
			 
     </li>
     <%}%>
     
     </OL>
     </li>
      <%}%>      
		</OL>
		</li>	
<%}
      
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
      %>
      </OL>
      </div>
<% }
	else
	{%>
	<div class="adjust3">
	<div>
	<OL id=root>
	<li>
	You do not have the permissions to navigate to any services.
	</li>
	</OL>
	</div>
	</div>
<%}
%>
</div>	
<%
}
}
}
else
{%>
	<div id="left_container_login_portlet">
<table align="center" cellspacing="0" cellpadding="0" border="0" style="width: 675px; height: 344px">
    <tbody>
        <tr>
            <td align="center" valign="top"><img width="686" height="20" alt="" style="" src="<%=request.getContextPath()%>/images/box_top.jpg" /></td>
        </tr>
        <tr>
            <td align="left" valign="top" class="box_bg_login_portlet">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div style="text-align: left; float: left"><img width="246" height="265" alt="" src="<%=request.getContextPath()%>/images/hpm.png" /></div>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <p style="text-align: justify">
                  You cannot access any of the G2C services.
            </td>
        </tr>
        <tr>
            <td align="center" valign="top"><img width="686" height="10" alt="" src="<%=request.getContextPath()%>/images/box_bottm.jpg" /></td>
        </tr>
    </tbody>
</table>
</div>
<p>&nbsp;</p>
<%}
%>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td>
<%
Boolean isSignedIn = false;
//isSignedIn = themeDisplay.isSignedIn();
%>
<a href="#" onClick="window.open('http://www.citizenservices.gov.bt/VOICE?roleId=<%=roleIds%>&userId=<%=screenName%>','windowemp')"><img src="<%=request.getContextPath()%>/images/voice_customer.jpg"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<% if(!isCitizen && !isStudent)
{%>
<a href="#" onClick="window.open('http://www.citizenservices.gov.bt/PaymentTracker?roleId=<%=roleIds%>&userId=<%=screenName%>','windowemp')"><img src="<%=request.getContextPath()%>/images/payment_tracker2.jpg"></a><%
}
%>
</td>
</tr>
</table>
</td>
</tr>
</table>
 <table border = "0" width="100%">
<tr>
 <td>
  &nbsp;
 </td>
</tr>
</table>
