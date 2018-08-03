<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.data.Friendship" %>
<%@ page import="codeu.model.data.Friendship.Status" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<!DOCTYPE html>
<html>
<head>
  <title>Friends List</title>
  <link rel="icon" href="https://greggarcia.org/img/exp/10-1-1-exp.png">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/"><i class="fa fa-home"></i></a>
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
    <h1>Friends List</h1>

    <ul>
      <%
      Map<UUID, List<Friendship>> friendships =
          (Map<UUID, List<Friendship>>) request.getAttribute("friendships");

      String username = (String) request.getSession().getAttribute("user");

      UserStore userStore = UserStore.getInstance();
      User user = userStore.getUser(username);
      UUID userId = user.getId();

      List<Friendship> friendsList = friendships.get(userId);

      if (friendsList == null || friendsList.isEmpty()) {
      %>
        <li>Your friends list is empty.</li>
      <%
      } else {
        for (Friendship friendship : friendsList) {
          UUID friendId = friendship.getFriendId();
          User friend = userStore.getUser(friendId);
          Status status = friendship.getStatus();

          if (userId.equals(friendId)) {
            UUID pendingFriendId = friendship.getUserId();
            User pendingFriend = userStore.getUser(pendingFriendId);
            String pendingFriendName = pendingFriend.getName();
      %>
            <li>
              <a href="/user/<%= pendingFriendName %>"><%= pendingFriendName %></a>
              <em>PENDING</em>
              <form action="/friendslist" method="POST">
                <input type="hidden" name="friendId" value="<%= pendingFriendId.toString() %>">
                <input type="submit" name="accept" value="Accept" />
                <input type="submit" name="reject" value="Reject" />
              </form>
            </li>
      <%
          } else {
      %>
            <li>
              <a href="/user/<%= friend.getName() %>"><%= friend.getName() %></a>
              <%
              if (status == Status.PENDING) {
              %>
                <em>PENDING</em>
      <%
              }
      %>
            </li>
      <%
          }
        }
      }
      %>
    </ul>
  </div>
</body>
