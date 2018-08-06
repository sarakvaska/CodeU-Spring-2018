<%--
  Copyright 2017 Google Inc.
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
  <title>Admin</title>
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
    <h1>Admin</h1>
    <% Map<String, String> adminStatsMap =
          (Map<String, String>) request.getAttribute("adminStatsMap");
       String userName = adminStatsMap.get("lastUserName");
       String userTime = adminStatsMap.get("lastUserTime");
       String convName = adminStatsMap.get("lastConversationName");
       String convTime = adminStatsMap.get("lastConversationTime");
       String messageName = adminStatsMap.get("lastMessageContent");
       String messageTime = adminStatsMap.get("lastMessageTime");
       String messageUser = adminStatsMap.get("lastMessageUser");
          %>
    <p> <b>Total users: </b> <%= adminStatsMap.get("userSize")%></p>
    <p> <b>Total conversations: </b> <%= adminStatsMap.get("convSize")%></p>
    <p> <b>Total messages: </b> <%= adminStatsMap.get("messageSize")%></p>
    <p> <b>Last user created: </b> <a href="/users/<%= userName %>"><%= userName %></a> at <%= userTime %></p>
    <p> <b>Last conversation created: </b> <a href="/chat/<%= convName %>"><%= convName %></a> at <%= convTime %></p>
    <p> <b>Last message sent: </b> "<%= messageName %>" by <a href="/users/<%= messageUser %>"> <%= messageUser %></a> at <%= messageTime %></p>
    
  </div>
</body>
</html>

  </div>
</body>
</html>

  </div>
</body>
</html>
