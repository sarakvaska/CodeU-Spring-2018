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
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Activity Feed</a>
  </nav>

  <div id="container">
    <h1>Welcome to your profile page.</h1>
    <p>Here, you can write an About Me to let people know who you are.<p>
    <form action="/user/<%= request.getSession().getAttribute("user") %>" method="POST">
      <button type="submit">Logout</button>
    </form>
  </div>
</body>
</html>
