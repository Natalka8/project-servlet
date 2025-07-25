<%@ page import="com.tictactoe.Sign" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tic-Tac-Toe</title>
    <link href="static/main.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .winner-message {
            color: green;
            font-size: 24px;
            margin: 20px 0;
        }
        .restart-btn {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 20px;
        }
        .restart-btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Tic-Tac-Toe</h1>
<table>
    <tr>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=0'">${data.get(0).getSign()}</td>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=1'">${data.get(1).getSign()}</td>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=2'">${data.get(2).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=3'">${data.get(3).getSign()}</td>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=4'">${data.get(4).getSign()}</td>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=5'">${data.get(5).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=6'">${data.get(6).getSign()}</td>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=7'">${data.get(7).getSign()}</td>
        <td onclick="window.location='${pageContext.request.contextPath}/logic?click=8'">${data.get(8).getSign()}</td>
    </tr>
</table>

<hr>

<c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
<c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>

<c:if test="${winner == CROSSES}">
    <div class="winner-message">CROSSES WIN!</div>
    <button class="restart-btn" onclick="restart()">Rematch</button>
</c:if>
<c:if test="${winner == NOUGHTS}">
    <div class="winner-message">NOUGHTS WIN!</div>
    <button class="restart-btn" onclick="restart()">Rematch</button>
</c:if>
<c:if test="${draw}">
    <div class="winner-message">IT'S A DRAW!</div>
    <button class="restart-btn" onclick="restart()">Play Again</button>
</c:if>

<script>
    function restart() {
        $.ajax({
            url: '${pageContext.request.contextPath}/restart',
            type: 'POST',
            success: function() {
                location.reload();
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                location.reload();
            }
        });
    }
</script>

</body>
</html>