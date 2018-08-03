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
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
  <link rel="icon" href="https://greggarcia.org/img/exp/10-1-1-exp.png">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll;
    }
  </style>
  <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
  <script src="/javascript/textChange.js"></script>
  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">

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

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
    <%
      for (Message message : messages) {
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName();
    %>
    <li><strong><a href="/user/<%= author %>"><%= author %></a>:</strong> <%= message.getContent() %> 
      <% if(request.getSession().getAttribute("user") != null){ %>
        <% if(request.getSession().getAttribute("user").equals(author)){ %>
            <button type="button" value="<%=message.getId()%>" onclick="deleteButton(this)"> 
              <i class="fa fa-trash"></i>
            </button>
            <% } 
        %>
      <% }  %>
    </li>
    <% } %>

      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
      <button type="button" id ="bold" style="border-style:outset; border-width:2px;" onclick="boldFunction()"><b>B</b></button>
      <button type="button" id ="italic" style="border-style:outset; border-width:2px;" onclick="italicFunction()"><i>I</i></button>
      <button type="button" id ="underline" style="border-style:outset; border-width:2px;" onclick="underlineFunction()"><u>U</u></button> 
      <button type="button" id ="strike" style="border-style:outset; border-width:2px;" onclick="strikeFunction()"><s>S</s></button> 
      <button class = "collapsible" style="border-style:outset; border-width:2px;"> Emojis </button>
      <div class="content">
        <table>
            <tr>
              <td><button class="emoji" onclick="angelEmoji()">&#x1F607;</button></td>
              <td><button class="emoji" onclick="devilEmoji()">&#x1F608;</button></td>
              <td><button class="emoji" onclick="smileEmoji()">&#x1F642;</button></td>
              <td><button class="emoji" onclick="happyEmoji()">&#x1F601;</button></td>
              <td><button class="emoji" onclick="tearEmoji()">&#x1F602;</button></td>
            </tr>
            <tr>
              <td><button class="emoji" onclick="angryEmoji()">&#x1F620;</button></td>
              <td><button class="emoji" onclick="sadEmoji()">&#x1F641;</button></td>
              <td><button class="emoji" onclick="tearCryEmoji()">&#x1F622;</button></td>
              <td><button class="emoji" onclick="stoicEmoji()">&#x1F610;</button></td>
              <td><button class="emoji" onclick="mehEmoji()">&#x1F615;</button></td>
            </tr>
            <tr>
              <td><button class="emoji" onclick="coolEmoji()">&#x1F60E;</button></td>
              <td><button class="emoji" onclick="smoochEmoji()">&#x1F617;</button></td>
              <td><button class="emoji" onclick="woahEmoji()">&#x1F62E;</button></td>
              <td><button class="emoji" onclick="tongueEmoji()">&#x1F61B;</button></td>
              <td><button class="emoji" onclick="xPEmoji()">&#x1F61D;</button></td>
            </tr>
            <tr>
              <td><button class="emoji" onclick="winkEmoji()">&#x1F609;</button></td>
              <td><button class="emoji" onclick="twoArrowEmoji()">&#x1F616;</button></td>
              <td><button class="emoji" onclick="superMehEmoji()">&#x1F611;</button></td>
              <td><button class="emoji" onclick="happyGoLuckyEmoji()">&#x1F60A;</button></td>
              <td><button class="emoji" onclick="superCryEmoji()">&#x1F62D;</button></td>
            </tr>
            <tr>
              <td><button class="emoji" onclick="wahEmoji()">&#x1F629;</button></td>
              <td><button class="emoji" onclick="noTalkEmoji()">&#x1F636;</button></td>
              <td><button class="emoji" onclick="sickEmoji()">&#x1F637;</button></td>
              <td><button class="emoji" onclick="heartEmoji()">&#x1F499;</button></td>
            </tr>
        </table>
      </div>
      <input type="text" name="link" placeholder="URL">
      <button type="button" onclick="addLink()">Add Link to Message</button>
      <br/>
      <input type="text" name="imagery" placeholder="URL">
      <button type="button" onclick="addImageLink()">Add Image Link to Message</button>
    <br/>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST" id = "postForm">
        <input type="hidden" style="font-size: 14pt" name="indexMessage"> 
        <textarea rows="4" cols="40" type="text" style="font-size: 14pt" name="message" 
          onchange="setButtonsInset()"
          oninput= "setButtonsInset()" 
          onselect="setButtonsInset()" 
          onkeydown="setButtonsInset()" 
          onclick="setButtonsInset()" 
          onfocus="hidePreview()" required></textarea>
        <br/>
        <div id = "preview" style="border-width: 1px; border-color: gray; border-style: solid; background-color: white; height: 90px; width: 420px; overflow:auto; display:none">
        </div>
        <button type="button" onclick="loadPreview()">Preview</button>
        <button type="submit" id="sendData">Send</button>
        <br/>
    </form>
    <script>
      var coll = document.getElementsByClassName("collapsible");
      coll[0].addEventListener("click", function() {
        if (this.style.borderStyle == "outset") {
          this.style.borderStyle = "inset";
        }
        else {
          this.style.borderStyle = "outset";
        }
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.maxHeight){
          content.style.maxHeight = null;
        } else {
          content.style.maxHeight = content.scrollHeight + "px";
        } 
      });
    </script>
    <script>
      function deleteButton(trashButton){
        document.getElementsByName("indexMessage")[0].value = trashButton.value;
        console.log(document.getElementsByName("indexMessage")[0].value);
        document.getElementById("postForm").submit();
      }
    </script>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

</body>
</html>
