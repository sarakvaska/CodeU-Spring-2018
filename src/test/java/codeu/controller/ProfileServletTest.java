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

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.data.Message;
import codeu.model.store.basic.MessageStore;
import codeu.model.data.Friendship;
import codeu.model.data.Friendship.Status;
import codeu.model.store.basic.FriendshipStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class ProfileServletTest {

  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;
  private MessageStore mockMessageStore;
  private FriendshipStore mockFriendshipStore;
  private User mockUser;

  @Before
  public void setup() {
    profileServlet = new ProfileServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockMessageStore = Mockito.mock(MessageStore.class);
    profileServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    profileServlet.setUserStore(mockUserStore);

    mockFriendshipStore = Mockito.mock(FriendshipStore.class);
    profileServlet.setFriendshipStore(mockFriendshipStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/user/test_user");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$Ajfzp.s.7EFhdKL10QvRtwdDTRihl5U656Pyx87wterbBBMIplcgFL",
            Instant.now(),
            "test_aboutMe");

    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    UUID fakeUserId = fakeUser.getId();
    List<Friendship> fakeFriendsList = new ArrayList<>();
    fakeFriendsList.add(
      new Friendship(fakeUserId, UUID.randomUUID(), UUID.randomUUID(),
                     Status.PENDING, Instant.now())
    );

    Map<UUID, List<Friendship>> fakeFriendshipMap = new HashMap<>();
    fakeFriendshipMap.put(fakeUserId, fakeFriendsList);
    Mockito.when(mockFriendshipStore.getFriendships()).thenReturn(fakeFriendshipMap);

    UUID testAuthor = UUID.randomUUID();
    List<Message> fakeMessageList = new ArrayList<>();
    fakeMessageList.add(
        new Message(
            UUID.randomUUID(),
            UUID.randomUUID(),
            testAuthor,
            "test message",
            Instant.now()));
    Mockito.when(mockMessageStore.getMessagesByAuthor(testAuthor))
        .thenReturn(fakeMessageList);

    profileServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("friendships", fakeFriendshipMap);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

@Test
  public void testDoPost_notLoggedIn() throws IOException, ServletException {
    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockUserStore, Mockito.never()).updateUser(Mockito.any(User.class));
  }

  @Test
  public void testDoPost_InvalidUserProfile() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
    Mockito.when(mockUserStore.getUser("test_username"))
        .thenReturn(null);

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockUserStore, Mockito.never()).addUser(Mockito.any(User.class));

  }
  @Test
  public void testDoPost_userUrl() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/user/test_username");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$Ajfzp.s.7EFhdKL10QvRtwdDTRihl5U656Pyx87wterbBBMIplcgFL",
            Instant.now(),
            "test_aboutMe");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    Mockito.when(mockRequest.getParameter("user")).thenReturn("test_username");

    profileServlet.doPost(mockRequest, mockResponse);

    Assert.assertEquals("test_username", fakeUser.getName());
  }
}
