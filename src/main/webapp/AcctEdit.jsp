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
    <input type="hidden" name="importInProgress" value="<%= request.getAttribute("importInProgress") %>">
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
            <td>
                <select name="type">
                    <option value="">Please select a type ...</option>
                    <%  name.mutant.dough.domain.AcctType[] acctTypes = name.mutant.dough.domain.AcctType.values();
                        for (name.mutant.dough.domain.AcctType acctType : acctTypes) {
                            if (acctType.name().equals(request.getAttribute("type"))) { %>
                                <option value="<%= acctType.name() %>" selected="true"><%= acctType %></option>
                        <%  } else { %>
                                <option value="<%= acctType.name() %>"><%= acctType %></option>
                        <%  }
                        } %>
                </select>
            </td>
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
            <td>
                <%  Boolean importInProgress = (Boolean) request.getAttribute("importInProgress");
                    if (importInProgress != null && importInProgress.booleanValue()) { %>
                        <input type="submit" name="saveButton" value="Save &amp; Exit">
                        <input type="submit" name="continueButton" value="Save &amp; Continue">
                <%  } else { %>
                        <input type="submit" name="saveButton" value="Save">
                <%  } %>
                <input type="submit" name="reloadButton" value="Reload Page">
                <input type="submit" name="cancelButton" value="Cancel">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
