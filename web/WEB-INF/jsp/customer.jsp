<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/17
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>商户列表</title>
</head>
<body>
<table>
    <tr>
        <th>客户id</th>
        <th>客户姓名</th>
        <th>客户联系人</th>
        <th>客户手机号</th>
        <th>客户邮箱</th>
        <th>客户备注</th>
        <th>操作</th>
    </tr>
    <c:forEach var="customer" items="${customerList}">
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telphone}</td>
            <td>${customer.email}</td>
            <td>${customer.remark}</td>
            <td><a href="/customer_delete?id=${customer.id}">删除</a>
                <a href="/customer_update?id=${customer.id}">操作</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
