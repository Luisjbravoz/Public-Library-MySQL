<%--
    PROJECT: PUBLIC LIBRARY.
    LUIS J. BRAVO ZÚÑIGA.
    BOOK PAGE (CRUD BOOK).
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Newton PL book</title>
        <link href="boostrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="tools/css/book/styles.css" rel="stylesheet" type="text/css"/>
        <link href="tools/css/modal/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body onload="javascript:init()">
        <!-- The Message Modal -->
        <div id="myModal" class="modal">
            <div class="modal-content" style=" width: 50%;">
                <span class="close">&times;</span>
                <p class="info-error" id="text"></p>
            </div>
        </div>
        <!-- The Add Modal -->
        <div id="modalAdd" class="modal">
            <div class="modal-content" style=" width: 50%;">
                <form id="formAdd">
                    <div class="modal-header">
                        <h2 id="modalTitle" class="modal-title"></h2></div>
                    <div class="modal-body">
                        <div class="container">
                            <div class="form-row">
                                <div class="col"><label>ISBN</label><input class="form-control" type="text" id="isbn" pattern="[0-9].{10,13}" title="format ISBN" autocomplete="off" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Title</label><input class="form-control" type="text" id="title" autocomplete="off" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Author first name</label><input class="form-control" type="text" id="fname" pattern="^[a-zA-Z]*$" title="only letters" autocomplete="off" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Author last name</label><input class="form-control" type="text" id="lname" pattern="^[a-zA-Z]*$" title="only letters" autocomplete="off" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Editorial</label><input class="form-control" type="text" id="editorial" autocomplete="off" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Year</label><input class="form-control" type="text" id="year" pattern="^[0-9]*$" title="only numbers" autocomplete="off" minlength="4" maxlength="4" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Pages</label><input class="form-control" type="text" id="pages" pattern="^[0-9]*$" title="only numbers" autocomplete="off" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Quantity</label><input class="form-control" type="number" id="quantity" min="1" step="1" required></div>
                            </div>
                            <div class="form-row">
                                <div class="col"><label>Idiom</label>
                                    <select id="idiom" class="form-control">
                                        <option value="English" selected>English</option>
                                        <option value="Spanish">Spanish</option>
                                        <option value="German">German</option>
                                        <option value="Russian">Russian</option>
                                        <option value="French">French</option>
                                        <option value="Italian">Italian</option>
                                    </select>
                                </div>
                                <div class="col"><label>Subgenre</label>
                                    <select id="subgenre" class="form-control">
                                        <option value="null" disabled selected>Choose</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-success" style="background-color: #0069d9; min-width: 20%;"type="submit">Ready</button>
                        <button class="btn btn-light" type="button" data-dismiss="modal" style="background-color: #cccccc; min-width: 20%;" onclick="javascript:closeModal();">Close</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row row-space">
                <div class="col back">
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div  class="row row-space" >
                <div class="col d-flex justify-content-center align-items-center align-content-center">
                    <p class="text-center title">Newton Public Library</br></p>
                </div>
            </div>
            <div class="row row-space" >
                <div class="col d-flex justify-content-center align-items-center align-content-center">
                    <p class="text-center subtitle">SEARCH A BOOK</p>
                </div>
            </div>
            <div class="row row-space">
                <div class="col">
                    <div class="row row-space">
                        <div class="col">
                            <div class="row row-space" >
                                <div class="col">
                                    <div class="input-group">
                                        <div class="input-group-prepend"><span class="input-group-text" style="width:151.516px;">Author last name</span></div><input class="form-control" id="lnameFilter" type="text" name="lnameFilter" placeholder="Harris"autocomplete="off" style="margin:0px;width:635.469px;">
                                        <div
                                            class="input-group-append"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="row row-space" >
                                <div class="col">
                                    <div class="input-group">
                                        <div class="input-group-prepend"><span class="input-group-text" style="width:151.516px;">Title</span></div><input class="form-control" id="titleFilter" type="text" name="titleFilter" placeholder="Munich" autocomplete="off" style="width:635.469px;">
                                        <div class="input-group-append"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="row row-space" >
                                <div class="col">
                                    <div class="input-group">
                                        <div class="input-group-prepend"><span class="input-group-text" style="width:151.516px;">ISBN</span></div><input class="form-control" id="isbnFilter" type="text" name="isbnFilter" placeholder="9788466348102" autocomplete="off">
                                        <div class="input-group-append"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-2">
                    <div class="row row-space" >
                        <div class="col d-flex justify-content-center align-items-center align-content-center"><button class="btn btn-primary operation-button" type="button" onclick="javascript:filter()">Search</button></div>
                    </div>
                    <div class="row row-space" >
                        <div class="col d-flex justify-content-center align-items-center align-content-center"><button class="btn btn-primary operation-button" type="button" onclick="javascript:reset()">Reset</button></div>
                    </div>
                    <div class="row row-space" >
                        <div class="col d-flex justify-content-center align-items-center align-content-center"><button class="btn btn-primary operation-button" type="button" onclick="javascript:showModalAdd()">Add</button></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <div class="row row-space" style="padding:20px;">
                <div class="col d-flex justify-content-center align-items-center align-content-center">
                    <p class="text-center subtitle">LIST OF BOOKS</p>
                </div>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead style="color:rgb(148,75,34);">
                        <tr class="d-table-row justify-content-center align-items-center align-content-center" style="color:rgb(0,0,0);">
                            <th class="header-row">ISBN</th>
                            <th class="header-row">Title</th>
                            <th class="header-row">Author</th>
                            <th class="header-row">Editorial</th>
                            <th class="header-row">Year</th>
                            <th class="header-row">Idiom</th>
                            <th class="header-row">Pages</th>
                            <th class="header-row">Subgenre</th>
                            <th class="header-row">Quantity</th>
                            <th class="header-row">Delete</th>
                            <th class="header-row">Update</th>
                        </tr>
                    </thead>
                    <tbody class="body-table" id = "tableBody">
                    </tbody>
                </table>
            </div>
        </div>
        <script src="tools/js/jquery.min.js" type="text/javascript"></script>
        <script src="boostrap/bootstrap.min.js" type="text/javascript"></script>
        <script src="tools/js/book/admin_book.js" type="text/javascript"></script>
    </body>

</html>