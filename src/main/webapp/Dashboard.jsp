<html>
<head>
    <title>Dough - Dashboard</title>
</head>
<body>
<h1>Dough</h1>
<ul>
    <%  java.util.List<String> errors = (java.util.List<String>) request.getAttribute("errors");
        for (String error : errors) { %>
            <li class="error"><%=error%></li>
    <%  }
        java.util.List<String> messages = (java.util.List<String>) request.getAttribute("messages");
        for (String message : messages) { %>
            <li class="message"><%=message%></li>
    <%  } %>
</ul>
<h2>Dashboard</h2>

<table border="1">
    <tr>
        <th>Payable Id</th>
        <th>Payee Name</th>
        <th>Amount</th>
        <th>Due Date</th>
    </tr>
    <%  java.util.List<name.mutant.dough.service.dto.BillToPay> payableResultList = (java.util.List<name.mutant.dough.service.dto.BillToPay>) request.getAttribute("billsToPay");
        for (name.mutant.dough.service.dto.BillToPay billToPay : payableResultList) {
            String style = "font-style: italic;";
            if (billToPay.isActual()) {
                style = "font-style: normal;";
            }
            if (billToPay.isOverDue()) {
                style += " color: red;";
            } else if (billToPay.isAlmostDue()) {
                style += " color: orange;";
            } %>
            <tr style="<%= style %>">
                <td><%= billToPay.getPayableId() %></td>
                <td><%= billToPay.getPayeeName() %></td>
                <td><%= billToPay.getAmount() %></td>
                <td><%= billToPay.getDueDate() %></td>
            </tr>
    <%  } %>
</table>

<table border="1">
    <tr>
        <th>Acct Name</th>
        <th>Acct Type</th>
        <th>Balance</th>
        <th>Last Tran Date</th>
    </tr>
    <%  String baseUrl = response.encodeURL(request.getContextPath() + "/AcctEditLoad");
        java.util.List<name.mutant.dough.service.dto.AcctBalance> acctBalances = (java.util.List<name.mutant.dough.service.dto.AcctBalance>) request.getAttribute("acctBalances");
        for (name.mutant.dough.service.dto.AcctBalance acctBalance : acctBalances) {
            String s = String.valueOf(acctBalance.getAcctId());
            String fullUrl = baseUrl + "?acctId=" + java.net.URLEncoder.encode(s, "UTF-8"); %>
            <tr>
                <td><a href="<%= fullUrl %>"><%= acctBalance.getAcctName() %></a></td>
                <td><%= acctBalance.getAcctType() %></td>
                <td><%= acctBalance.getBalance() %></td>
                <td><%= acctBalance.getLastTranDate() %></td>
            </tr>
    <%  } %>
</table>

</body>
</html>
