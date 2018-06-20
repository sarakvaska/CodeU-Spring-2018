<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<% User getProfile = (User) request.getAttribute("getProfile"); %>
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

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
    <hr/>
    <h3> About <%= getProfile.getName() %> </h3>
    <%-- Still working on AboutMe datastore and editing only logged in user's AboutMe --%>
    <% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getAboutMe() != null){%>
      <p> <%=UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getAboutMe()%> </p>
    <% } else {%>
      <p> This user has no description </p>
    <%} %>
      <% if(request.getSession().getAttribute("user").equals(request.getSession().getAttribute("user"))) {%>
      <form action="/user/<%= request.getSession().getAttribute("user") %>" method="POST">
      <h4> Edit your About Me (only you can see this) </h4>
        <% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getAboutMe() != null){%>
        <div class="form-group">
          <input type ="text" name="aboutMe"
            placeholder = "<%=UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getAboutMe()%>">
        </div>
        <% } else {%>
        <div class="form-group">
          <input type ="text" name="aboutMe" style="width:50%;height:100%"
            placeholder = "Write about yourself!">
        </div> <% } %>
            <button type ="submit" name="about" value="About">Submit</button>
          </form>
          <% } %>
        <hr/>
        <h3><%= getProfile.getName() %>'s Sent Messages</h3>
    <%-- Insert user's sent messages here! (Not Done)--%>
      <form action="/user/<%= getProfile.getName()%>" method="POST">
      <button type="submit" name="logout" value="Logout">Logout</button>
    </form>
  </div>
</body>
</html>
