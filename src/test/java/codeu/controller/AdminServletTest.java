package codeu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminServletTest {

  private AdminServlet AdminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    AdminServlet = new AdminServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    AdminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
