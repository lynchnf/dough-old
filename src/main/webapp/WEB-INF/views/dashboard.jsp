<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Dough - Dashboard</title>
</head>
<body>
<h1>Dough</h1>
<h2>Dashboard</h2>

<table border="1">
    <tr>
        <th>Payable Id</th>
        <th>Payee Name</th>
        <th>Amount</th>
        <th>Due Date</th>
    </tr>
    <c:forEach var="billToPay" items="${billsToPay}">
        <c:set var="style" value="font-style: italic;"/>
        <c:if test="${billToPay.actual}">
            <c:set var="style" value="font-style: normal;"/>
        </c:if>
        <c:choose>
            <c:when test="${billToPay.overDue}">
                <c:set var="style" value="${style} color: red;"/>
            </c:when>
            <c:when test="${billToPay.almostDue}">
                <c:set var="style" value="${style} color: orange;"/>
            </c:when>
        </c:choose>
        <tr style="${style}">
            <td>${billToPay.payableId}</td>
            <td>${billToPay.payeeName}</td>
            <td>${billToPay.amount}</td>
            <td>${billToPay.dueDate}</td>
        </tr>
    </c:forEach>
</table>

<table border="1">
    <tr>
        <th>Acct Id</th>
        <th>Acct Name</th>
        <th>Acct Type</th>
        <th>Balance</th>
        <th>Last Tran Date</th>
    </tr>
    <c:forEach var="acctBalance" items="${acctBalances}">
        <tr>
            <td>${acctBalance.acctId}</td>
            <td>${acctBalance.acctName}</td>
            <td>${acctBalance.acctType}</td>
            <td>${acctBalance.balance}</td>
            <td>${acctBalance.lastTranDate}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
