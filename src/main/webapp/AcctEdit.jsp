<html>
<head>
    <title>Dough - Acct Edit</title>
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
<h2>Acct Edit</h2>

<%  String actionUrl = response.encodeURL(request.getContextPath() + "/AcctEditProcess"); %>
<form method="post" action="<%= actionUrl %>">
    <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">
    <input type="hidden" name="version" value="<%= request.getAttribute("version") %>">
    <table border="1">
        <tr>
            <th>Acct Nbr</th>
            <td><input type="text" name="acctNbr" value="<%= request.getAttribute("acctNbr") %>"></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><input type="text" name="name" value="<%= request.getAttribute("name") %>"></td>
        </tr>
        <tr>
            <th>Organization</th>
            <td><input type="text" name="organization" value="<%= request.getAttribute("organization") %>"></td>
        </tr>
        <tr>
            <th>FID</th>
            <td><input type="text" name="fid" value="<%= request.getAttribute("fid") %>"></td>
        </tr>
        <tr>
            <th>OFX Bank ID</th>
            <td><input type="text" name="ofxBankId" value="<%= request.getAttribute("ofxBankId") %>"></td>
        </tr>
        <tr>
            <th>OFX Acct ID</th>
            <td><input type="text" name="ofxAcctId" value="<%= request.getAttribute("ofxAcctId") %>"></td>
        </tr>
        <tr>
            <th>Type</th>
            <td><input type="text" name="type" value="<%= request.getAttribute("type") %>"></td>
        </tr>
        <tr>
            <th>Begin Date</th>
            <td><input type="text" name="beginDate" value="<%= request.getAttribute("beginDate") %>"></td>
        </tr>
        <tr>
            <th>Begin Balance</th>
            <td><input type="text" name="beginBalance" value="<%= request.getAttribute("beginBalance") %>"></td>
        </tr>
        <tr>
            <th></th>
            <td><input type="submit" name="saveButton" value="Save"></td>
        </tr>
    </table>
</form>

</body>
</html>
