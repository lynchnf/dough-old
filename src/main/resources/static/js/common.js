var MyCommon = new Object();

MyCommon.goToFirstPage = function (event) {
    event.preventDefault();
    event.stopPropagation();
    var listFormId = $(this).attr("data-list-form");
    var oldVal = Number($("#" + listFormId + " .pageNumber").val());
    var newVal = 0;
    console.log("goToFirstPage: changing pageNumber from " + oldVal + " to " + newVal + ".");
    $("#" + listFormId + " .pageNumber").val(newVal);
    $("#" + listFormId).submit();
};

MyCommon.goToPreviousPage = function (event) {
    event.preventDefault();
    event.stopPropagation();
    var listFormId = $(this).attr("data-list-form");
    var oldVal = Number($("#" + listFormId + " .pageNumber").val());
    var newVal = Number($("#" + listFormId + " .currentPage").val()) - 1;
    console.log("goToPreviousPage: changing pageNumber from " + oldVal + " to " + newVal + ".");
    $("#" + listFormId + " .pageNumber").val(newVal);
    $("#" + listFormId).submit();
};

MyCommon.goToNextPage = function (event) {
    event.preventDefault();
    event.stopPropagation();
    var listFormId = $(this).attr("data-list-form");
    var oldVal = Number($("#" + listFormId + " .pageNumber").val());
    var newVal = Number($("#" + listFormId + " .currentPage").val()) + 1;
    console.log("goToNextPage: changing pageNumber from " + oldVal + " to " + newVal + ".");
    $("#" + listFormId + " .pageNumber").val(newVal);
    $("#" + listFormId).submit();
};

MyCommon.goToLastPage = function (event) {
    event.preventDefault();
    event.stopPropagation();
    var listFormId = $(this).attr("data-list-form");
    var oldVal = Number($("#" + listFormId + " .pageNumber").val());
    var newVal = Number($("#" + listFormId + " .totalPages").val()) - 1;
    console.log("goToLastPage: changing pageNumber from " + oldVal + " to " + newVal + ".");
    $("#" + listFormId + " .pageNumber").val(newVal);
    $("#" + listFormId).submit();
};

MyCommon.changePageSize = function (event) {
    var listFormId = $(this).attr("data-list-form");
    console.log("changePageSize: resetting pageNumber to 0.");
    $("#" + listFormId + " .pageNumber").val(0);
    var oldVal = Number($("#" + listFormId + " .pageSize").val());
    var newVal = Number($(this).val());
    console.log("changePageSize: changing pageSize from " + oldVal + " to " + newVal + ".");
    $("#" + listFormId + " .pageSize").val(newVal);
    $("#" + listFormId).submit();
};

MyCommon.changeSort = function (event) {
    event.preventDefault();
    event.stopPropagation();
    var listFormId = $(this).attr("data-list-form");
    var oldCol = $("#" + listFormId + " .sortColumn").val();
    var oldDir = $("#" + listFormId + " .sortDirection").val();
    var newCol = $(this).attr("data-sort-column");
    if (newCol == oldCol) {
        var newDir = "DESC";
        if (oldDir == "DESC") {
            newDir = "ASC";
        }
        console.log("changeSort: changing sortDirection from " + oldDir + " to " + newDir + ".");
        $("#" + listFormId + " .sortDirection").val(newDir);
    } else {
        console.log("changeSort: resetting sortDirection to ASC.");
        $("#" + listFormId + " .sortDirection").val("ASC");
        console.log("changeSort: changing sortColumn from " + oldCol + " to " + newCol + ".");
        $("#" + listFormId + " .sortColumn").val(newCol);
    }
    $("#" + listFormId).submit();
};

MyCommon.filterList = function() {
    var listFormId = $(this).attr("data-list-form");
    console.log("changePageSize: resetting pageNumber to 0.");
    $("#" + listFormId + " .pageNumber").val(0);
    var oldName = $("#" + listFormId + " .whereNameContains").val();
    var newName = $("#" + listFormId + " .newWhereNameContains").val();
    console.log("filterList: changing whereNameContains from " + oldName + " to " + newName + ".");
    $("#" + listFormId + " .whereNameContains").val(newName);
    $("#" + listFormId).submit();
}


$(document).ready(function () {
    // Put sort indicator on column heading.
    $(".changeSort").each(function (index) {
        var listFormId = $(this).attr("data-list-form");
        var oldCol = $("#" + listFormId + " .sortColumn").val();
        var oldDir = $("#" + listFormId + " .sortDirection").val();
        var newCol = $(this).attr("data-sort-column");
        if (newCol == oldCol) {
            var innerText = $(this).html();
            if (oldDir == "DESC") {
                innerText += " <i class='fa fa-caret-down'></i>";
            } else {
                innerText += " <i class='fa fa-caret-up'></i>";
            }
            $(this).html(innerText);
        }
    });

    $(".goToFirstPage").on("click", MyCommon.goToFirstPage);
    $(".goToPreviousPage").on("click", MyCommon.goToPreviousPage);
    $(".goToNextPage").on("click", MyCommon.goToNextPage);
    $(".goToLastPage").on("click", MyCommon.goToLastPage);
    $(".changePageSize").on("change", MyCommon.changePageSize);
    $(".changeSort").on("click", MyCommon.changeSort);
    $(".filterList").on("click", MyCommon.filterList);
});