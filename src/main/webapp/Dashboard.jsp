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

<table>
    <tr>
        <th>Payee Name</th>
        <th>Amount Due</th>
        <th>Due Date</th>
    </tr>
    <% java.util.List<name.mutant.dough.domain.Payable> payableResultList = (java.util.List<name.mutant.dough.domain.Payable>) request.getAttribute("payableResultList");
        for (name.mutant.dough.domain.Payable payable : payableResultList) { %>
    <tr>
        <td><%= payable.getPayee().getName() %></td>
        <td><%= payable.getActAmount() == null ? payable.getEstAmount() : payable.getActAmount() %></td>
        <td><%= payable.getActDueDate() == null ? payable.getEstDueDate() : payable.getActDueDate() %></td>
    </tr>
    <% } %>
</table>

</body>
</html>
