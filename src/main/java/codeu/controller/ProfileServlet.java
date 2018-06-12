package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class for the user profile */
public class ProfileServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/profile.jsp")
           .forward(request, response);
  }
  /**
   * This function fires when a user presses the logout button. It makes the username = null
   * and redirects to a new login screen.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        String username = (String) request.getSession().getAttribute("user");
        username = null;
    request.getSession().setAttribute("user", username);
    response.sendRedirect("/login");
  }
}
