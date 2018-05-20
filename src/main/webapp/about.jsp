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
      <a href="/profile"><%= request.getSession().getAttribute("user") %>'s Profile</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Activity Feed</a>
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
        moved to GA when I was turning 10. I enjoy playing the piano,
        reading murder mystery novels, watching anime (currently obsessed
        with Detective Conan), and playing video games.
      </p>

      <h2>Features</h2>
      <p>
        This is an example chat application designed to be a starting point
        for your CodeU project team work. Here's some stuff to think about:
      </p>

      <ul>
        <li><strong>Algorithms and data structures:</strong> We've made the app
            and the code as simple as possible. You will have to extend the
            existing data structures to support your enhancements to the app,
            and also make changes for performance and scalability as your app
            increases in complexity.</li>
        <li><strong>Look and feel:</strong> The focus of CodeU is on the Java
          side of things, but if you're particularly interested you might use
          HTML, CSS, and JavaScript to make the chat app prettier.</li>
        <li><strong>Customization:</strong> Think about a group you care about.
          What needs do they have? How could you help? Think about technical
          requirements, privacy concerns, and accessibility and
          internationalization.</li>
      </ul>

      <p>
        This is your code now. Get familiar with it and get comfortable
        working with your team to plan and make changes. Start by updating the
        homepage and this about page to tell your users more about your team.
        This page should also be used to describe the features and improvements
        you've added.
      </p>
    </div>
  </div>
</body>
</html>
