package codeu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Friendship;
import codeu.model.data.Friendship.Status;
import codeu.model.data.User;
import codeu.model.store.basic.FriendshipStore;
import codeu.model.store.basic.UserStore;

/** Servlet class for the friend list */
public class FriendsListServlet extends HttpServlet {

  private FriendshipStore friendshipStore;

  private UserStore userStore;

  @Override
  public void init() throws ServletException {
    super.init();
    setFriendshipStore(friendshipStore.getInstance());
    setUserStore(userStore.getInstance());
  }

  void setFriendshipStore(FriendshipStore friendshipStore) {
    this.friendshipStore = friendshipStore;
  }

  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    Map<UUID, List<Friendship>> friendships = friendshipStore.getFriendships();
    request.setAttribute("friendships", friendships);
    request.getRequestDispatcher("/WEB-INF/view/friendslist.jsp")
           .forward(request, response);
  }

  /**
   * This function fires when the user clicks on Accept or Reject next to a
   * pending friend request in the friends list.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    UUID userId = userStore.getUser(username).getId();
    UUID friendId = UUID.fromString(request.getParameter("friendId"));

    List<Friendship> friendsList = friendshipStore.getFriendships().get(userId);
    Friendship friendshipToUpdate = searchFriendship(friendsList, friendId);

    if (friendshipToUpdate != null) {
      // If the Accept button was pressed:
      if (request.getParameter("accept") != null) {
        friendshipStore.acceptFriendship(friendshipToUpdate);

      // If the Reject button was pressed:
      } else if (request.getParameter("reject") != null) {
        friendshipStore.rejectFriendship(friendshipToUpdate);
      }
    }

    // redirect to a GET request
    response.sendRedirect("/friendslist");
  }

  private Friendship searchFriendship(List<Friendship> friendsList, UUID friendId) {
    for (Friendship friendship : friendsList) {
      if (friendship.getUserId().equals(friendId)) {
        return friendship;
      }
    }

    return null;
  }
}
