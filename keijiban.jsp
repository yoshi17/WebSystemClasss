<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%
ArrayList<String> msgList = (ArrayList<String>)session.getAttribute("msgList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>孤独の掲示板</title>
</head>
<body>
	<h1>孤独の掲示板</h1>
	<form action="/s1832091/Keijiban" method="POST">
		<table>
		<tr>
		<td>本文：</td>
		<td><textarea name="msg" rows="4" cols="40" placeholder ="ここに本文を記入してください" ></textarea></td>
		</tr>
		</table>
		<br>
		<button type="submit" name="action" value="送信">送信</button>
		<button type="submit" name="action" value="リセット">リセット</button>
		<button type="submit" name="action" value="ダウンロード">ダウンロード</button>
	</form>
	<hr>
	<h2>追加機能について</h2>
	<ul>
		<li>リセットボタンの追加(セッションを破棄し、新規セッションの開始)</li>
		<li>ダウンロードボタンの追加(.txtで保存可)</li>
	</ul>
	<hr>
	<% if (msgList != null) { %>
		<ul class="msg-group">
		<%for(String msg : msgList) {%>
			<li class="msg-group-elm"><%= msg %></li>
		<%}%>
		</ul>
	<% } %>
</body>
</html>