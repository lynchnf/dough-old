<html>
<head>
    <title>Dough - Dashboard</title>
</head>
<body>
<h1>Dough</h1>
<ul>
    <% java.util.List<String> errors = (java.util.List<String>) request.getAttribute("errors");
        for (String error : errors) { %>
    <li class="error"><%=error%>
    </li>
    <% }
        java.util.List<String> messages = (java.util.List<String>) request.getAttribute("messages");
        for (String message : messages) { %>
    <li class="message"><%=message%>
    </li>
    <% } %>
</ul>
<h2>Dashboard</h2>

<table border="1">
    <tr>
        <th>Payee Name</th>
        <th>Amount Due</th>
        <th>Due Date</th>
    </tr>
    <% java.util.List<name.mutant.dough.domain.Payable> payableResultList = (java.util.List<name.mutant.dough.domain.Payable>) request.getAttribute("payableResultList");
        for (name.mutant.dough.domain.Payable payable : payableResultList) {
            String rowStyle = "font-style: normal;";
            if (payable.getActDueDate() == null) rowStyle = "font-style: italic;"; %>
    <tr style="<%= rowStyle %>>">
        <td><%= payable.getPayee().getName() %></td>
        <td><%= payable.getActAmount() == null ? payable.getEstAmount() : payable.getActAmount() %></td>
        <td><%= payable.getActDueDate() == null ? payable.getEstDueDate() : payable.getActDueDate() %></td>
    </tr>
    <% } %>
</table>

<table border="1">
    <tr>
        <th>Acct Id</th>
        <th>Acct Name</th>
        <th>Acct Type</th>
        <th>Balance</th>
        <th>Last Tran Date</th>
    </tr>
    <% java.util.List<name.mutant.dough.service.dto.AcctBalance> acctBalances = (java.util.List<name.mutant.dough.service.dto.AcctBalance>) request.getAttribute("acctBalances");
        for (name.mutant.dough.service.dto.AcctBalance acctBalance : acctBalances) { %>
    <tr>
        <td><%= acctBalance.getAcctId() %></td>
        <td><%= acctBalance.getAcctName() %></td>
        <td><%= acctBalance.getAcctType() %></td>
        <td><%= acctBalance.getBalance() %></td>
        <td><%= acctBalance.getLastTranDate() %></td>
    </tr>
    <% } %>
</table>

</body>
</html>
