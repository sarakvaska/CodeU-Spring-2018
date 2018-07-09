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
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private FriendshipStore mockFriendshipStore;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    friendsListServlet = new FriendsListServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
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
}
