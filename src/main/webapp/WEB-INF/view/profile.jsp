<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<% User getProfile = (User) request.getAttribute("getProfile"); %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Friendship" %>
<%@ page import="codeu.model.data.Friendship.Status" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.UUID" %>
<%
List<Message> messages = (List<Message>) request.getAttribute("messages");
Map<UUID, List<Friendship>> friendships =
    (Map<UUID, List<Friendship>>) request.getAttribute("friendships");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
  <link rel="icon" href="https://greggarcia.org/img/exp/10-1-1-exp.png">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <script src="/javascript/search.js"></script>
  </head>

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
  <h1> <%= getProfile.getName() %>'s Profile Page</h1>

  <%
  if (request.getSession().getAttribute("user") != null &&
  !request.getSession().getAttribute("user").equals(getProfile.getName())) {
        UserStore userStore = UserStore.getInstance();
        String username = (String) request.getSession().getAttribute("user");
        User user = userStore.getUser(username);

        UUID userId = user.getId();
        List<Friendship> userFriendsList = friendships.get(userId);

        UUID friendId = getProfile.getId();
        boolean isFriend = false;
        boolean pending = false;

        if (userFriendsList != null) {
          for (Friendship friendship : userFriendsList) {

            if (friendship.getFriendId().equals(friendId)) {
              if (friendship.getStatus() == Status.PENDING) {
                pending = true;
              } else {
                isFriend = true;
              }

              break;
            } else if (friendship.getUserId().equals(friendId)) {
              /* This condition is true if the friendship is pending because FriendshipStore
              adds the same friendship twice to the two users in the map who are involved
              in this friendship. */
              pending = true;
              break;
            }

          }
        }


        if (isFriend) {
      %>
          <p>Friends</p>
      <%
        } else if (pending) {
      %>
          <p>Friend request pending.</p>
      <%
        } else {
      %>
          <form action="/user/<%= getProfile.getName() %>" method="POST">
            <input type="submit" name="addFriend" value="Add Friend" />
          </form>
      <%
        }
      }
      %>

    <hr/>
    <h3> About <%= getProfile.getName() %> </h3>
    <% if(getProfile.getAboutMe() != null){%>
      <p> <%=getProfile.getAboutMe()%> </p>
    <% } else {%>
      <p> This user has no description </p>
    <% } %>
      <% if(request.getSession().getAttribute("user") != null) { %>
          <% if(request.getSession().getAttribute("user").equals(getProfile.getName())) { %>
            <h4> Edit your About Me (only you can see this) </h4>
            <form action="/user/<%= request.getAttribute("user") %>" method="POST">
            <% if(getProfile.getAboutMe().equals("This user has no description")) {%>
              <div class="form-group">
                <textarea id = "box" type ="text" name="aboutMe"
                  placeholder = "Write about yourself!"></textarea>
              </div>
            <hr/>
            <% } else { %>
              <div class="form-group">
                <textarea id = "box" type ="text" name="aboutMe"
                    placeholder = "<%=getProfile.getAboutMe()%>"></textarea>
              </div>
              <hr/>
              <% } %>
            <button type ="submit" name="about" value="About">Submit</button>
          </form>

          <h3><%= getProfile.getName() %>'s Sent Messages</h3>
          <input type="text" id="searchInput" onkeyup="mySearch()" placeholder="Search for messages...">
          <div id="messageList">
            <ul>
              <%
                for (Message message : messages) {
                  Date date = Date.from(message.getCreationTime());
                  %><li><p><b><small><%=date %>: </small></b><%= message.getContent() %></p></li>
              <% } %>
            </ul>
          </div>

        <br>
        <form action="/user/<%= getProfile.getName()%>" method="POST">
        <button style="margin-left: 90%;" type="submit" name="logout" value="Logout">Logout</button>
       </form>
        <br><br>
          <% } %>
          <% } %>
</body>
</html>
