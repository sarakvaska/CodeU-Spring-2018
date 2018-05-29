<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.data.Activity.ActivityType" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

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
        <a href="/profile"><%= request.getSession().getAttribute("user") %>'s Profile</a>
      <% } else{ %>
        <a href="/login">Login</a>
      <% } %>
      <a href="/about.jsp">About</a>
      <a href="/activityfeed">Activity Feed</a>
      <a href="/admin">Admin</a>
    </nav>

  <div id="container">
    <h1>Activity Feed</h1>
    <p>This is the activity feed.</p>

    <div id="feed">
      <ul>
        <%
        List<Activity> activities =
          (List<Activity>) request.getAttribute("activities");

        if (activities == null || activities.isEmpty()) {
        %>
          <li>No activity yet.</li>
        <%
        } else {
          UserStore userStore = UserStore.getInstance();
          ConversationStore conversationStore = ConversationStore.getInstance();
          for (Activity activity : activities) {
            if (activity.getType() == ActivityType.NEW_USER) {
              User user = userStore.getUser(activity.getId());
        %>
          <li><%= activity.getCreationTime().toString() %>
            <%= user.getName() %> joined.</li>
        <%
            } else if (activity.getType() == ActivityType.NEW_CONVERSATION) {
              Conversation conversation =
                  conversationStore.getConversationById(activity.getId());
              if (conversation == null) {
                System.out.println("Conversation doesn't exist!!");
              } else {
                User user = userStore.getUser(conversation.getOwnerId());
        %>
          <li><%= activity.getCreationTime().toString() %>
            <%= user.getName() %> created a new conversation:
            <a href="/chat/<%= conversation.getTitle() %>"><%= conversation.getTitle() %></a>
          </li>
        <%
              }
            }
          }
        }
        %>
      </ul>
    </div>
  </div>
</body>
</html>
