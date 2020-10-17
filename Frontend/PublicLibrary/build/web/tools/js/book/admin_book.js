/*
 * PROJECT: PUBLIC LIBRARY.
 * LUIS J. BRAVO ZÚÑIGA.
 * ADMIN_LOGIN JS
 */

/*
 * PROJECT: PUBLIC LIBRARY.
 * LUIS J. BRAVO ZÚÑIGA.
 * ADMIN_BOOK JS
 */

/****
 * GLOBAL
 */
var listData = undefined, table = undefined, listSubgenreTitle = undefined, book = undefined,
        rowFormat = "<td>{0}</td><td>{1}</td><td>{2}</td><td>{3}</td><td>{4}</td><td>{5}</td><td>{6}</td><td>{7}</td><td>{8}</td><td>{9}</td><td>{10}</td>",
        optFormat = "<option value=\"{0}\">{1}</option>",
        deleteIcon = "<img class='image' src='tools/css/book/images/delete.png' onclick='deleteBook(\"{0}\");'>",
        updateIcon = "<img class='image' src='tools/css/book/images/update.png' onclick='showUpdateModal(\"{0}\");'>";

/****
 * Format a String
 * var username = "admin", password = "abC1";
 * e.g. : "api/login/{0}/{1}".format(username, password);
 * output: api/login/admin/abC1
 */
String.prototype.format = String.prototype.f = function () {
    var data = this, i = arguments.length;
    while (i--) {
        data = data.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return data;
};

/****
 * FOR BODY ONLOAD
 */
function init() {
    listSubgenre();
}

/****
 * FOR LIST SUBGENRE.
 */
function listSubgenre() {
    $.ajax({
        type: "GET",
        url: "api/subgenre;charset=UTF-8"
    })
            .done(function (data) {
                doneListSubgenre(data);
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                failListSubgenre();
            });
}

function doneListSubgenre(data) {
    if (data === undefined) {
        failListSubgenre();
    } else {
        listSubgenreTitle = data;
        initSubgenre();
        listBook();
    }
}

function failListSubgenre() {
    modalMessage("Error was ocurred. Please try again!");
}

/****
 * FOR LIST BOOKS.
 */
function listBook() {
    $.ajax({
        type: "GET",
        url: "api/book;charset=UTF-8"
    })
            .done(function (data) {
                listData = data;
                table = $("#tableBody");
                doneListBook();
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                failListBook();
            });
}

function doneListBook() {
    table.html("");
    for (var item in listData) {
        row(table, listData[item]);
    }
}

function row(table, item) {
    var tr = $("<tr/>");
    tr.html(
            rowFormat.format(item.isbn, item.title, "{0} {1}".format(item.lname, item.fname), item.editorial, item.year, item.idiom,
                    item.pages, getSubgenreText(item.subgenre), item.quantity, deleteIcon.format(item.isbn), updateIcon.format(item.isbn))
            );
    table.append(tr);
}

function failListBook() {
    modalMessage("Error was ocurred. Please try again!");
}

function getSubgenreText(id) {
    return listSubgenreTitle[id - 1];
}

/****
 * FOR MODAL MESSAGE.
 */
function modalMessage(message) {
    var modal = $("#myModal"),
            close = $(".close").eq(0),
            writeHere = $("#text");
    writeHere.html(message);
    modal.css("display", "block");

    close.click(function () {
        modal.css("display", "none");
    });
}

/****
 * FOR FILTER.
 */
function filter() {
    var lname = $("#lnameFilter").val().trim().toLowerCase(),
            title = $("#titleFilter").val().trim().toLowerCase(),
            isbn = $("#isbnFilter").val().trim(),
            listFilter = new Array(),
            current = undefined;
    if (lname === "" && title === "" && isbn === "") {
        doneListBook();
        return;
    }
    for (var item in listData) {
        current = listData[item];
        if (current.lname.toLowerCase() === lname) {
            listFilter.push(listData[item]);
            continue;
        }
        if (current.title.toLowerCase() === title) {
            listFilter.push(listData[item]);
            continue;
        }
        if (current.isbn === isbn) {
            listFilter.push(listData[item]);
            continue;
        }
    }
    if (listFilter.length === 0) {
        noMatches();
    } else {
        setTable(listFilter);
    }
}

function noMatches() {
    table.html("");
    var tr = $("<tr/>");
    tr.html("<td colspan=\"11\" class=\"subtitle\"> Sorry, no matches!</td>");
    table.append(tr);
}

function setTable(listFilter) {
    table.html("");
    for (var item in listFilter) {
        row(table, listFilter[item]);
    }
}

/****
 * FOR RESET.
 */
function reset() {
    $("#lnameFilter").val("");
    $("#titleFilter").val("");
    $("#isbnFilter").val("");
    doneListBook();
}

/****
 * FOR DELETE.
 */
function deleteBook(isbn) {
    $.ajax({
        type: "DELETE",
        url: "api/book/{0};charset=UTF-8".format(isbn)
    })
            .done(function (data) {
                doneDeleteBook(data, isbn);
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                failDeleteBook();
            });
}

function doneDeleteBook(data, isbn) {
    if (data === false) {
        modalMessage("The operation could not be completed, please try again!");
    } else {
        deleteListData(isbn);
        doneListBook();
        modalMessage("The book ({0}) was successfully deleted!".format(isbn));
    }
}

function failDeleteBook() {
    modalMessage("The operation could not be completed, please try again!");
}


/****
 * FOR KEEP LIST DATA UPDATED.
 */

function deleteListData(isbn) {
    for (var item in listData) {
        if (listData[item].isbn === isbn) {
            listData.splice(item, 1);
        }
    }
}

function insertListData(data) {
    listData.unshift(data);
}

function updateListData(data) {
    for (var item in listData) {
        if (listData[item].isbn === data.isbn) {
            listData.splice(item, 1, data);
        }
    }
}

/*****
 * FOR ADD
 */

function addBook() {
    console.log("AGREGANDO");
    book = {
        isbn: $("#isbn").val(),
        title: $("#title").val(),
        fname: $("#fname").val(),
        lname: $("#lname").val(),
        editorial: $("#editorial").val(),
        year: $("#year").val(),
        idiom: $("#idiom").val(),
        pages: $("#pages").val(),
        subgenre: $("#subgenre").val(),
        quantity: $("#quantity").val()
    };
    $.ajax({
        type: "POST",
        url: "api/book;charset=UTF-8",
        data: JSON.stringify(book),
        contentType: "application/json"
    })
            .done(function (data) {
                doneAddBook(data);
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                failAddBook();
            });
}

function doneAddBook(data) {
    closeModal();
    if (data === false) {
        failAddBook();
    } else {
        insertListData(book);
        doneListBook();
        modalMessage("The book was successfully added!");
    }
}

function failAddBook() {
    closeModal();
    modalMessage("The operation could not be completed, please try again!");
}

function showModalAdd() {
    $("#formAdd").attr("action", "javascript:addBook()");
    $("#isbn").prop("disabled", false);
    $("#modalTitle").html("Add new book");
    $("#formAdd").trigger("reset");
    $("#modalAdd").css("display", "block");

}

function initSubgenre() {
    var select = $("#subgenre");
    for (var item in listSubgenreTitle) {
        select.append(optFormat.format((parseInt(item) + 1).toString(), listSubgenreTitle[item]));
    }
}

function closeModal() {
    $("#modalAdd").css("display", "none");
}

/*****
 * FOR UPDATE
 */

function updateBook() {
    book = {
        isbn: $("#isbn").val(),
        title: $("#title").val(),
        fname: $("#fname").val(),
        lname: $("#lname").val(),
        editorial: $("#editorial").val(),
        year: $("#year").val(),
        idiom: $("#idiom").val(),
        pages: $("#pages").val(),
        subgenre: $("#subgenre").val(),
        quantity: $("#quantity").val()
    };
    $.ajax({
        type: "PUT",
        url: "api/book;charset=UTF-8",
        data: JSON.stringify(book),
        contentType: "application/json"
    })
            .done(function (data) {
                doneUpdateBook(data);
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                failUpdateBook();
            });
}

function doneUpdateBook(data) {
    closeModal();
    if (data === false) {
        failUpdateBook();
    } else {
        updateListData(book);
        doneListBook();
        modalMessage("The book was successfully updated!");
    }
}

function failUpdateBook() {
    closeModal();
    modalMessage("The operation could not be completed, please try again!");
}

function showUpdateModal(isbn) {
    var item = getElement(isbn);
    $("#formAdd").attr("action", "javascript:updateBook()");
    $("#modalTitle").html("Update book");
    $("#formAdd").trigger("reset");
    $("#isbn").val(item.isbn);
    $("#isbn").prop("disabled", true);
    $("#title").val(item.title);
    $("#fname").val(item.fname);
    $("#lname").val(item.lname);
    $("#editorial").val(item.editorial);
    $("#year").val(item.year);
    $("#idiom").val(item.idiom);
    $("#pages").val(item.pages);
    $("#subgenre").val(item.subgenre);
    $("#quantity").val(item.quantity);
    $("#modalAdd").css("display", "block");
}

function getElement(isbn) {
    for (var item in listData) {
        if (listData[item].isbn === isbn) {
            return listData[item];
        }
    }
}