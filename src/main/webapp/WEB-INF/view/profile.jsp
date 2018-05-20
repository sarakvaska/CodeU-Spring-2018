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
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/profile">Profile</a>
    <% } else{ %>
      <a href="/about.jsp">About</a>
    <% } %>
    <a href="/activityfeed">Activity Feed</a>
  </nav>

  <div id="container">
    <h1>Profile</h1>
    <p> This is <%= request.getSession().getAttribute("user") %>'s profile page</p>
  </div>
</body>
</html>