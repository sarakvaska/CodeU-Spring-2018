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

import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.data.Conversation;
import codeu.model.data.User;
import codeu.model.store.basic.ActivityStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ConversationServletTest {

  private ConversationServlet conversationServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private UserStore mockUserStore;
  private ActivityStore mockActivityStore;

  @Before
  public void setup() {
    conversationServlet = new ConversationServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/conversations.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    conversationServlet.setConversationStore(mockConversationStore);

    mockUserStore = Mockito.mock(UserStore.class);
    conversationServlet.setUserStore(mockUserStore);

    mockActivityStore = Mockito.mock(ActivityStore.class);
    conversationServlet.setActivityStore(mockActivityStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    List<Conversation> fakeConversationList = new ArrayList<>();
    fakeConversationList.add(
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "test_conversation", Instant.now()));
    Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversationList);

    conversationServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("conversations", fakeConversationList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_InvalidUser() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(null);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockResponse).sendRedirect("/conversations");
  }

  @Test
  public void testDoPost_BadConversationName() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("conversationTitle")).thenReturn("bad !@#$% name");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6",
            Instant.now(),
            "test_aboutMe");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockRequest).setAttribute("error", "Please enter only letters and numbers.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_ConversationNameTaken() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("conversationTitle")).thenReturn("test_conversation");

    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6",
            Instant.now(),
            "test_aboutMe");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    Mockito.when(mockConversationStore.isTitleTaken("test_conversation")).thenReturn(true);

    conversationServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockConversationStore, Mockito.never())
        .addConversation(Mockito.any(Conversation.class));
    Mockito.verify(mockResponse).sendRedirect("/chat/test_conversation");
  }

  @Test
  public void testDoPost_NewConversation() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("conversationTitle")).thenReturn("test_conversation");

    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6",
            Instant.now(),
            "test_aboutMe");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    Mockito.when(mockConversationStore.isTitleTaken("test_conversation")).thenReturn(false);

    conversationServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<Conversation> conversationArgumentCaptor =
        ArgumentCaptor.forClass(Conversation.class);
    Mockito.verify(mockConversationStore).addConversation(conversationArgumentCaptor.capture());
    Assert.assertEquals(conversationArgumentCaptor.getValue().getTitle(), "test_conversation");

    ArgumentCaptor<Activity> activityArgumentCaptor =
        ArgumentCaptor.forClass(Activity.class);
    Mockito.verify(mockActivityStore).addActivity(activityArgumentCaptor.capture());
    Assert.assertEquals(ActivityType.NEW_CONVERSATION, activityArgumentCaptor.getValue().getType());

    Mockito.verify(mockResponse).sendRedirect("/chat/test_conversation");
  }
}
