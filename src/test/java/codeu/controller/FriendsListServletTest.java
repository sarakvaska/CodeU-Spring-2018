package codeu.controller;

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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import codeu.model.data.Friendship;
import codeu.model.data.Friendship.Status;
import codeu.model.data.User;
import codeu.model.store.basic.FriendshipStore;
import codeu.model.store.basic.UserStore;

public class FriendsListServletTest {

  private FriendsListServlet friendsListServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private FriendshipStore mockFriendshipStore;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    friendsListServlet = new FriendsListServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/friendslist.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockFriendshipStore = Mockito.mock(FriendshipStore.class);
    friendsListServlet.setFriendshipStore(mockFriendshipStore);

    mockUserStore = Mockito.mock(UserStore.class);
    friendsListServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    UUID fakeUserId = UUID.randomUUID();
    List<Friendship> fakeFriendsList = new ArrayList<>();
    fakeFriendsList.add(
      new Friendship(fakeUserId, UUID.randomUUID(), UUID.randomUUID(),
                     Status.PENDING, Instant.now())
    );

    Map<UUID, List<Friendship>> fakeFriendshipMap = new HashMap<>();
    fakeFriendshipMap.put(fakeUserId, fakeFriendsList);
    Mockito.when(mockFriendshipStore.getFriendships()).thenReturn(fakeFriendshipMap);

    friendsListServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("friendships", fakeFriendshipMap);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_Accept() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6",
            Instant.now(),
            "test_aboutMe");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    User fakeFriend =
        new User(
            UUID.randomUUID(),
            "test_friend",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg7",
            Instant.now(),
            "test_aboutMe");
    UUID fakeFriendId = fakeFriend.getId();
    Mockito.when(mockRequest.getParameter("friendId")).thenReturn(fakeFriendId.toString());

    UUID fakeUserId = fakeUser.getId();

    /* This is the other way around since the fakeFriend is the one who added
    fakeUser, and we are looking through fakeUser's perspective who will either
    accept or reject the friend request.*/
    Friendship fakeFriendship = new Friendship(
        fakeFriendId, fakeUserId, UUID.randomUUID(), Status.PENDING, Instant.now()
    );

    Map<UUID, List<Friendship>> fakeFriendshipMap = new HashMap<>();

    List<Friendship> fakeUser_friendsList = new ArrayList<>();
    fakeUser_friendsList.add(fakeFriendship);
    fakeFriendshipMap.put(fakeUserId, fakeUser_friendsList);

    List<Friendship> fakeFriend_friendsList = new ArrayList<>();
    fakeFriend_friendsList.add(fakeFriendship);
    fakeFriendshipMap.put(fakeFriendId, fakeFriend_friendsList);

    Mockito.when(mockFriendshipStore.getFriendships()).thenReturn(fakeFriendshipMap);

    Mockito.when(mockRequest.getParameter("accept")).thenReturn("true");
    Mockito.when(mockRequest.getParameter("reject")).thenReturn(null);

    friendsListServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockFriendshipStore).acceptFriendship(fakeFriendship);
    Mockito.verify(mockResponse).sendRedirect("/friendslist");
  }

  @Test
  public void testDoPost_Reject() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    User fakeUser =
        new User(
            UUID.randomUUID(),
            "test_username",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg6",
            Instant.now(),
            "test_aboutMe");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);

    User fakeFriend =
        new User(
            UUID.randomUUID(),
            "test_friend",
            "$2a$10$eDhncK/4cNH2KE.Y51AWpeL8/5znNBQLuAFlyJpSYNODR/SJQ/Fg7",
            Instant.now(),
            "test_aboutMe");
    UUID fakeFriendId = fakeFriend.getId();
    Mockito.when(mockRequest.getParameter("friendId")).thenReturn(fakeFriendId.toString());

    UUID fakeUserId = fakeUser.getId();

    /* This is the other way around since the fakeFriend is the one who added
    fakeUser, and we are looking through fakeUser's perspective who will either
    accept or reject the friend request.*/
    Friendship fakeFriendship = new Friendship(
        fakeFriendId, fakeUserId, UUID.randomUUID(), Status.PENDING, Instant.now()
    );

    Map<UUID, List<Friendship>> fakeFriendshipMap = new HashMap<>();

    List<Friendship> fakeUser_friendsList = new ArrayList<>();
    fakeUser_friendsList.add(fakeFriendship);
    fakeFriendshipMap.put(fakeUserId, fakeUser_friendsList);

    List<Friendship> fakeFriend_friendsList = new ArrayList<>();
    fakeFriend_friendsList.add(fakeFriendship);
    fakeFriendshipMap.put(fakeFriendId, fakeFriend_friendsList);

    Mockito.when(mockFriendshipStore.getFriendships()).thenReturn(fakeFriendshipMap);

    Mockito.when(mockRequest.getParameter("accept")).thenReturn(null);
    Mockito.when(mockRequest.getParameter("reject")).thenReturn("true");

    friendsListServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockFriendshipStore).rejectFriendship(fakeFriendship);
    Mockito.verify(mockResponse).sendRedirect("/friendslist");
  }
}
