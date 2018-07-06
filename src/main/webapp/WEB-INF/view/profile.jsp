<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<% User getProfile = (User) request.getAttribute("getProfile"); %>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="java.util.Date" %>
<%
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  </head>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/user/<%= request.getSession().getAttribute("user") %>"><%= request.getSession().getAttribute("user") %>'s Profile</a>
      <a href="/friendslist">Friends</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Activity Feed</a>
    <a href="/admin">Admin</a>
  </nav>

  <div id="container">
  <h1> <%= getProfile.getName() %>'s Profile Page</h1>
  </form>
    <hr/>
    <h3> About <%= getProfile.getName() %> </h3>
    <% if(getProfile.getAboutMe() != null){%>
      <p> <%=getProfile.getAboutMe()%> </p>
    <% } else {%>
      <p> This user has no description </p>
    <% } %>
      <% if(request.getSession().getAttribute("user") != null) {%>
          <% if(request.getSession().getAttribute("user").equals(getProfile.getName())) {%>
            <h4> Edit your About Me (only you can see this) </h4>
            <form action="/user/<%= request.getAttribute("user") %>" method="POST">
            <% if(getProfile.getAboutMe() != null) {%>
              <div class="form-group">
              <textarea id = "box" font-size: 25px  type ="text" name="aboutMe" placeholder = "<%=getProfile.getAboutMe()%>"></textarea>
              </div>
            <hr/>
            <% } else { %>
              <div class="form-group">
                <textarea id = "box" type ="text" name="aboutMe"
                  placeholder = "Write about yourself!"></textarea>
              </div>
              <hr/>
              <% } %>
          </form>
          <button type ="submit" name="about" value="About">Submit</button>
          <h3><%= getProfile.getName() %>'s Sent Messages</h3>
          <input type="text" id="searchInput" onkeyup="mySearch()" placeholder="Search for messages...">

          <ul id="messageList">
              <%
                for (Message message : messages) {
                  Date date = Date.from(message.getCreationTime());
                  %><li><a href="#"><b><small><%=date %>: </b></small><%= message.getContent() %></a></li>
              <% } %>
        </ul>

        <script>
        function mySearch() {
            var input, filter, listMessage, li, a, i;
            input = document.getElementById("searchInput");
            filter = input.value.toUpperCase();
            listMessage = document.getElementById("messageList");
            indivMessage = listMessage.getElementsByTagName("li");
            for (i = 0; i < indivMessage.length; i++) {
                a = indivMessage[i].getElementsByTagName("a")[0];
                if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    indivMessage[i].style.display = "";
                } else {
                    indivMessage[i].style.display = "none";
                }
            }
        }
        </script>
        
        <br>
        <form action="/user/<%= getProfile.getName()%>" method="POST">
        <button style="margin-left: 90%;" type="submit" name="logout" value="Logout">Logout</button>
       </form>
        <br><br>
          <% } %>
          <% } %>
</body>
</html>
