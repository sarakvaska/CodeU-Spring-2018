// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet class responsible for the chat page. */
public class ChatServlet extends HttpServlet {

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user navigates to the chat page. It gets the conversation title from
   * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
   * It then forwards to chat.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      System.out.println("Conversation was null: " + conversationTitle);
      response.sendRedirect("/conversations");
      return;
    }

    UUID conversationId = conversation.getId();

    List<Message> messages = messageStore.getMessagesInConversation(conversationId);

    request.setAttribute("conversation", conversation);
    request.setAttribute("messages", messages);
    request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the chat page. It gets the logged-in
   * username from the session, the conversation title from the URL, and the chat message from the
   * submitted form data. It creates a new Message from that data, adds it to the model, and then
   * redirects back to the chat page.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

    String messageContent = request.getParameter("message");

    // this removes any HTML from the message content
    String cleanedMessageContent = Jsoup.clean(messageContent, Whitelist.none());

    // replaces BBcode with HTML tags

    // bolded text
    cleanedMessageContent = cleanedMessageContent.replace ("[b]", "<b>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/b]", "</b>");

    // italic text
    cleanedMessageContent = cleanedMessageContent.replace ("[i]", "<i>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/i]", "</i>");

    // underlined text
    cleanedMessageContent = cleanedMessageContent.replace ("[u]", "<u>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/u]", "</u>");

    // strikethrough text
    cleanedMessageContent = cleanedMessageContent.replace ("[s]", "<s>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/s]", "</s>");

    // line break
    cleanedMessageContent = cleanedMessageContent.replace ("[br]", "<br>");

    // creating a table with rows and data
    cleanedMessageContent = cleanedMessageContent.replace ("[table]", "<table>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/table]", "</table>");
    cleanedMessageContent = cleanedMessageContent.replace ("[tr]", "<tr>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/tr]", "</tr>");
    cleanedMessageContent = cleanedMessageContent.replace ("[td]", "<td>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/td]", "</td>");

    // hyperlinks for just [url] 
    while (cleanedMessageContent.contains ("[url]")) {
      int startTag = cleanedMessageContent.indexOf ("[url]");
      int endTag = cleanedMessageContent.indexOf ("[/url]");
      String newString = cleanedMessageContent.substring (0, startTag) + "<a href='" ; 
      newString += cleanedMessageContent.substring (startTag + 5, endTag);
      newString += "'>";
      newString += cleanedMessageContent.substring (startTag + 5, endTag);
      newString += "</a>";
      newString += cleanedMessageContent.substring (endTag + 6);
      cleanedMessageContent = newString;
    }

    // hyperlinks for [url = website] 
    while (cleanedMessageContent.contains ("[url=")) {
      int startTag = cleanedMessageContent.indexOf ("[url=");
      int closingTag = cleanedMessageContent.indexOf ("]", startTag);
      int endTag = cleanedMessageContent.indexOf ("[/url]");
      String newString = cleanedMessageContent.substring (0, startTag) + "<a href='" ; 
      newString += cleanedMessageContent.substring (startTag + 5, closingTag);
      newString += "'>";
      newString += cleanedMessageContent.substring (closingTag + 1, endTag);
      newString += "</a>";
      newString += cleanedMessageContent.substring (endTag + 6);
      cleanedMessageContent = newString;
    }

    // images
    cleanedMessageContent = cleanedMessageContent.replace ("[img]", "<img src='");
    cleanedMessageContent = cleanedMessageContent.replace ("[/img]", "' alt=''>");

    // quotes
    while (cleanedMessageContent.contains ("[quote")) {
      int startTag = cleanedMessageContent.indexOf ("[quote");
      int closingTag = cleanedMessageContent.indexOf ("]", startTag);
      int endTag = cleanedMessageContent.indexOf ("[/quote]");
      String newString = cleanedMessageContent.substring (0, startTag) + "<blockquote";

      // if this is [quote = "author"]
      if (closingTag != startTag + 6) {
        newString += " cite" + cleanedMessageContent.substring (startTag + 6, closingTag);
      }

      newString += "><p>";
      newString += cleanedMessageContent.substring (closingTag + 1, endTag) + "</p></blockquote>";
      newString += cleanedMessageContent.substring (endTag + 8);
      cleanedMessageContent = newString;
    }

    // monospaced text
    cleanedMessageContent = cleanedMessageContent.replace ("[code]", "<pre>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/code]", "</pre>");

    // lists 
    cleanedMessageContent = cleanedMessageContent.replace ("[ul]", "<ul>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/ul]", "</ul>");
    cleanedMessageContent = cleanedMessageContent.replace ("[ol]", "<ol>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/ol]", "</ol>");
    cleanedMessageContent = cleanedMessageContent.replace ("[li]", "<li>");
    cleanedMessageContent = cleanedMessageContent.replace ("[/li]", "</li>");

    // styling font 
    while (cleanedMessageContent.contains ("[style")) {
      int startTag = cleanedMessageContent.indexOf ("[style");
      int closingTag = cleanedMessageContent.indexOf ("]", startTag);
      int endTag = cleanedMessageContent.indexOf ("[/style]");
      int dividerColon = cleanedMessageContent.indexOf (";", startTag);
      String newString = cleanedMessageContent.substring (0, startTag) + "<span style='";

      if (cleanedMessageContent.substring (startTag + 6, closingTag).contains ("size")) {
        int sizeLocation = cleanedMessageContent.indexOf ("size", startTag);
        int equalSign = cleanedMessageContent.indexOf ("=", sizeLocation);
        String fontSize = cleanedMessageContent.substring (equalSign + 1, closingTag);
        if (dividerColon != -1 && dividerColon < closingTag && sizeLocation < dividerColon) {
          fontSize = cleanedMessageContent.substring (equalSign + 1, dividerColon);
        }
        fontSize = fontSize.trim ();
        fontSize = fontSize.substring (1, fontSize.length () - 1);
        newString += "font-size:" + fontSize + ";";
      }

      if (cleanedMessageContent.substring (startTag + 6, closingTag).contains ("color")){
        int colorLocation = cleanedMessageContent.indexOf ("color", startTag);
        int equalSign = cleanedMessageContent.indexOf ("=", colorLocation);
        String colorName = cleanedMessageContent.substring (equalSign + 1, closingTag);
        if (dividerColon != -1 && dividerColon < closingTag && colorLocation < dividerColon) {
          colorName = cleanedMessageContent.substring (equalSign + 1, dividerColon);
        }
        newString += "color:";
        colorName = colorName.trim ();

        // Hex form
        if (colorName.contains ("#")) {
          newString += colorName;
        }

        // Word form
        else {
          colorName = colorName.substring (1, colorName.length () - 1);
          newString += colorName;
        }
        newString += ";";
      }

      newString += "'>" + cleanedMessageContent.substring (closingTag + 1, endTag) + "</span>";
      newString += cleanedMessageContent.substring (endTag + 8);
      cleanedMessageContent = newString;
    }

    // styling size

    Message message =
        new Message(
            UUID.randomUUID(),
            conversation.getId(),
            user.getId(),
            cleanedMessageContent,
            Instant.now());

    messageStore.addMessage(message);

    // redirect to a GET request
    response.sendRedirect("/chat/" + conversationTitle);
  }
}
