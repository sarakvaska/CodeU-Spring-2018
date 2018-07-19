package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import java.util.List;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.data.Message;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.Date;

/** Servlet class for the user profile */
public class ProfileServlet extends HttpServlet {

  /** Store class that gives access to Users.*/
  private UserStore userStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  @Override
  public void init() throws ServletException {
    super.init();
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String userProfile = requestUrl.substring("/user/".length());
    User getProfile = userStore.getUser(userProfile);
    String username = (String) request.getSession().getAttribute("user");
    if (request.getSession().getAttribute("user") != null) {
    List<Message> messages = messageStore.getMessagesByAuthor(userStore.getUser(username).getId());
    request.setAttribute("messages", messages);
    }
    request.setAttribute("getProfile", getProfile);
    request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
    }
  /**
   * This function fires when a user presses the logout button. It makes the username equal to null
   * and redirects to a new login screen.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");
    User user = userStore.getUser(username);
    // if user presses logout button
    if(request.getParameter("logout") != null) {
        username = null;
    request.getSession().setAttribute("user", username);
    response.sendRedirect("/login");
  }
    // if user wants to presses submit on their aboutMe description
    else if (request.getParameter("about") != null) {
    /**
     * This function should fire  when a user submits the About Me form on the profile page. It gets the logged-in
     * username from the session and the About Me message from the
     * submitted form data. It adds what the user input into the model and then
     * redirects back to the profile page.
     */
      String aboutMeContent = request.getParameter("aboutMe");

      // this removes any HTML from the message content
      String cleanedAboutContent = Jsoup.clean(aboutMeContent, Whitelist.none());

      user.setAboutMe(cleanedAboutContent);
      userStore.getInstance().updateUser(user);
      // redirect to a GET request
      response.sendRedirect("/user/" + username);
    }
 }
}
