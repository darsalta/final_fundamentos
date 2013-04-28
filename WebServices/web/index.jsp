<%-- 
    Document   : index
    Created on : Apr 28, 2013, 4:51:48 AM
    Author     : marcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="js/json2.js"></script>
        <script type="text/javascript" src="js/jquery-2.0.0.js"></script>
        <script type="text/javascript" src="js/hiperheuristicas.js"></script>
        <script type="text/javascript" src="js/webservices.js"></script>
    </head>
    <body>
        If any test fails, an alert for each failure should pop-up.
        <script type="text/javascript">
            hiper.Test();
            ws.Test();
        </script>
    </body>
</html>