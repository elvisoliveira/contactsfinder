<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br" dir="ltr">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <title>ContactsFinder</title>
        <style type="text/css">
            @import url("/assets/css/default.css");
        </style>
        <script type="text/javascript" src="/assets/js/jquery-1.4.3.min.js"></script>
        <script type="text/javascript" src="/assets/js/default.js"></script>
    </head>
    <body>
        <div class="navigation">
            <div class="navigation-inner">
                <div class="navigation-brand">
                    <a href="#">Contacts Finder - Belo Horizonte</a>
                </div>
                <div class="navigation-menu">
                    <ul>
                        <li>
                            <a href="#">Profile</a>
                            <ul>
                                <li><a href="#">Change Password</a></li>
                                <li><a href="#">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="container-inner">
                <form id="search" action="#" method="get">
                    <table>
                        <colgroup>
                            <col span="1" style="width: 70px;" />
                            <col span="1" style="width: 223px;" />
                            <col span="1" style="width: 135px;" />
                        </colgroup>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Address</th>
                                <th>Options</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr id="filter">
                                <td><input type="text" name="for_name" /></td>
                                <td></td>
                                <td><button type="submit" class="bottom">Submit</button></td>
                            </tr>
                            <tr>
                                <td>2189</td>
                                <td>Centro Sul</td>
                                <td>
                                    <div class="btn-group">
                                        <ul>
                                            <li><a href="#" class="btn-danger"><!--<i class="icon-user"></i>User--></a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>