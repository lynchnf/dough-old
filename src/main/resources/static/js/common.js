var MyCommon = new Object();

MyCommon.goToFirstPage = function() {
    var oldVal = Number(document.getElementById("pageNumber").value);
    var newVal = 0;
    document.getElementById("pageNumber").value = newVal;
    console.log("pageNumber changed from " + oldVal + " to " + newVal + ".");
    document.getElementById("listForm").submit();
}

MyCommon.goToPreviousPage = function() {
    var oldVal = Number(document.getElementById("pageNumber").value);
    var newVal = Number(document.getElementById("currentPage").value) - 1;
    document.getElementById("pageNumber").value = newVal;
    console.log("pageNumber changed from " + oldVal + " to " + newVal + ".");
    document.getElementById("listForm").submit();
}

MyCommon.goToNextPage = function() {
    var oldVal = Number(document.getElementById("pageNumber").value);
    var newVal = Number(document.getElementById("currentPage").value) + 1;
    document.getElementById("pageNumber").value = newVal;
    console.log("pageNumber changed from " + oldVal + " to " + newVal + ".");
    document.getElementById("listForm").submit();
}

MyCommon.goToLastPage = function() {
    var oldVal = Number(document.getElementById("pageNumber").value);
    var newVal = Number(document.getElementById("totalPages").value) - 1;
    document.getElementById("pageNumber").value = newVal;
    console.log("pageNumber changed from " + oldVal + " to " + newVal + ".");
    document.getElementById("listForm").submit();
}

MyCommon.changePageSize = function() {
    document.getElementById("pageNumber").value = 0;
    console.log("pageNumber reset to 0.");
    var oldVal = document.getElementById("pageSize").value;
    var newVal = document.getElementById("selectPageSize").value;
    document.getElementById("pageSize").value = newVal;
    console.log("pageSize changed from " + oldVal + " to " + newVal + ".");
    document.getElementById("listForm").submit();
}

MyCommon.changeSort = function(newCol) {
    document.getElementById("pageNumber").value = 0;
    console.log("pageNumber reset to 0.");
    var oldCol = document.getElementById("sortColumn").value;
    var oldDir = document.getElementById("sortDirection").value;
    if (newCol == oldCol) {
        var newDir = "DESC";
        if (oldDir == "DESC") {
            newDir = "ASC";
        }
        document.getElementById("sortDirection").value = newDir;
        console.log("sortDirection changed from " + oldDir + " to " + newDir + ".");
    } else {
        document.getElementById("sortDirection").value = "ASC";
        console.log("sortDirection reset to ASC.");
        document.getElementById("sortColumn").value = newCol;
        console.log("sortColumn changed from " + oldCol + " to " + newCol + ".");
    }
    document.getElementById("listForm").submit();
}

MyCommon.filter = function() {
    document.getElementById("pageNumber").value = 0;
    console.log("pageNumber reset to 0.");
    var oldName = document.getElementById("whereNameContains").value;
    var newName = document.getElementById("newWhereNameContains").value;
    document.getElementById("whereNameContains").value = newName;
    console.log("whereNameContains changed from " + oldName + " to " + newName + ".");
    document.getElementById("listForm").submit();
}

$('#goToFirstPage')