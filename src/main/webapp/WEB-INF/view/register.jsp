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
  <title>Register</title>
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
    <h1>Register</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <form action="/register" method="POST">
      <label for="username">Username: </label>
      <br/>
      <input type="text" name="username" id="username">
      <br/>
      <label for="password">Password: </label>
      <br/>
      <input type="password" name="password" id="password" oninput="checkReq()">
      <font size="2"><ul id="passCheck">
        <li>At least 6 characters long</li>
        <li>Has an uppercase character</li>
        <li>Has a lowercase character</li>
        <li>Has a digit</li>
        <li>Has a special character</li>
      </ul></font>
      <script>
        function checkReq(){
          var passVal = document.getElementById("password").value;
          var len = (passVal.length >= 6);
          var upper = new RegExp("[A-Z]").test(passVal);
          var lower = new RegExp("[a-z]").test(passVal);
          var digit = new RegExp("\\d").test(passVal);
          var special = new RegExp("[!@#$%^&*']").test(passVal);

          var list = document.getElementsByTagName("UL")[0];
          if (len){
            list.getElementsByTagName("LI")[0].innerHTML = "At least 6 characters long &#x2713";
          }
          else{
            list.getElementsByTagName("LI")[0].innerHTML = "At least 6 characters long";
          }
          if (upper){
            list.getElementsByTagName("LI")[1].innerHTML = "Has an uppercase character &#x2713";
          }
          else{
            list.getElementsByTagName("LI")[1].innerHTML = "Has an uppercase character";
          }
          if (lower){
            list.getElementsByTagName("LI")[2].innerHTML = "Has a lowercase character &#x2713";
          }
          else{
            list.getElementsByTagName("LI")[2].innerHTML = "Has a lowercase character";
          }
          if (digit){
            list.getElementsByTagName("LI")[3].innerHTML = "Has a digit &#x2713";
          }
          else{
            list.getElementsByTagName("LI")[3].innerHTML = "Has a digit";
          }
          if (special){
            list.getElementsByTagName("LI")[4].innerHTML = "Has a special character &#x2713";
          }
          else{
            list.getElementsByTagName("LI")[4].innerHTML = "Has a special character";
          }
        }
      </script>
      <br/>
      <button type="submit">Submit</button>
    </form>
  </div>
</body>
</html>
