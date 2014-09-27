<%@page contentType="image/jpeg"%>
<jsp:useBean id="image" scope="page" class="com.isoftstone.veco.controller.portal.HomeController"/>
<%image.mkRandCodePic(request,response);
out.clear();
out = pageContext.pushBody();%>