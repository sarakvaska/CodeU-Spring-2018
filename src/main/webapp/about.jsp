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
<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
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
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>About the CodeU Chat App</h1>

      <h2>Meet The Salvatorians</h2>
      <h3>Sara Kvaska</h3>
      <p>
        Hi! My name is Sara Kvaska. I'm currently a freshman at Harvard planning
        to concentrate in Computer Science with a secondary in Astrophysics.
        I'm from Miami, and that's where I'll be working this summer. :)
      </p>

      <h3>Cindy Lee</h3>
      <p>
        Hi, I'm Cindy. I'm from NYC and a rising junior from NYU. I am a
        Computer Science major and a Game Engineering minor. I love Marvel
        (have yet to watch Infinity Wars though due to finals). I am also an
        avid anime fan and would love to hear recommendations! Though I don't
        watch TV often, I would totally recommend the show Once Upon A Time!
      </p>

      <h3>Esmeralda Nava</h3>
      <p>
        Hello! I am Esmeralda Nava, a freshman at Stanford. I am studying
        Computer Science and Architecture Design (hoping to one day merge
        the two of them), and I enjoy swimming and traveling.
      </p>

      <h3>Aljon Pineda</h3>
      <p>
        Hello! My name is Aljon Pineda. I just finished my freshman year
        at Georgia Tech. I'm majoring in Computer Science and I'm considering
        having a minor in Linguistics. I was born in Staten Island, NY, then
        moved to GA when I turned 10. I enjoy playing the piano,
        reading murder mystery novels, and playing D&D and video games.
      </p>

      <h2>Features</h2>
      <ul>
        <li><strong>Friends List:</strong> Each user has their own friends list.
          To add a friend, simply click on another user's profile, then click
          the Add Friend button. This will send a friend request to the other user,
          who can go on their friends list to either accept or reject the request.</li>
        <li><strong>Style Text Bar:</strong> Above the message text area are a set
          of buttons that you can use to style your text. You can bold, italicize,
          underline, strikethrough, and add links to your text.</li>
        <li><strong>Emoji:</strong> In addition to styling, you can also chat with
          emoji by clicking on the Emojis button, which brings up a collapsible
          emoji box. You can also type in symbols that would represent emoji, and
          the app will automatically convert the symbols into emoji.</li>
        <li><strong>Search Through Messages:</strong> On your profile page, there
          is a box containing a list of all messages you have sent in the chat app.
          You can search through this list for a particular message by using the
          search box above the list.</li>
        <li><strong>Notifications:</strong> Users are notified of when new messages
          are sent and when new conversations are made.</li>
        <li><strong>Sending Your Location:</strong> Users can also send their current
          location through their message.</li>
        <li><strong>Logout:</strong> Once you have finished chatting, make sure
          to log out by clicking the Logout button at the bottom of your profile
          page.</li>
      </ul>
    </div>
  </div>
</body>
</html>
