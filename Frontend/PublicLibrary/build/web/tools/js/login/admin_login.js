/*
 * PROJECT: PUBLIC LIBRARY.
 * LUIS J. BRAVO ZÚÑIGA.
 * ADMIN_LOGIN JS
 */

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
 * FOR LOGIN
 */
function login() {
    $.ajax({
        type: "GET",
        url: "api/login/{0}/{1};charset=UTF-8".format($("#username").val(), $("#password").val())
    })
            .done(function (data) {
                doneLogin(data);
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                failLogin();
            });
}

function doneLogin(data) {
    if (data === undefined) {
        failLogin();
    } else {
        location.href = "book.jsp";
    }
}

function failLogin() {
    var modal = $("#myModal"),
            close = $(".close").eq(0);
    modal.css("display", "block");

    close.click(function () {
        modal.css("display", "none");
    });

}