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
import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private HttpSession mockSession;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    adminServlet = new AdminServlet();
    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
  }

  @Test
  public void testDoGetAdmin() throws IOException, ServletException {
    User adminUser = new User(UUID.randomUUID(), 
                              "cindy",
                              "$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                              Instant.now(),
                              "test_aboutMe",
                              true);
    mockUserStore.addUser(adminUser);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("cindy");
    Mockito.when(mockUserStore.getUser("cindy")).thenReturn(adminUser);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGetAdminOther() throws IOException, ServletException {
    User adminUser = new User(UUID.randomUUID(), 
                              "aljon",
                              "$2a$10$bBiKUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                              Instant.now(),
                              "test_aboutMe",
                              true);
    mockUserStore.addUser(adminUser);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("aljon");
    Mockito.when(mockUserStore.getUser("aljon")).thenReturn(adminUser);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGetAdminAnother() throws IOException, ServletException {
    User adminUser = new User(UUID.randomUUID(), 
                              "sara",
                              "$2a$10$bBiNUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                              Instant.now(),
                              "test_aboutMe",
                              true);
    mockUserStore.addUser(adminUser);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("sara");
    Mockito.when(mockUserStore.getUser("sara")).thenReturn(adminUser);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGetAdminLast() throws IOException, ServletException {
    User adminUser = new User(UUID.randomUUID(), 
                              "esme",
                              "$2a$10$bBiMUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                              Instant.now(),
                              "test_aboutMe",
                              true);
    mockUserStore.addUser(adminUser);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("esme");
    Mockito.when(mockUserStore.getUser("esme")).thenReturn(adminUser);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGetNotAdmin() throws IOException, ServletException {
    User notadminUser = new User(UUID.randomUUID(), 
                              "test_user",
                              "$2a$10$bBiLUAVmUFK6Iwg5rmpBUOIBW6rIMhU1eKfi3KR60V9UXaYTwPfHy",
                              Instant.now(),
                              "test_aboutMe",
                              false);
    mockUserStore.addUser(notadminUser);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_user");
    Mockito.when(mockUserStore.getUser("test_user")).thenReturn(notadminUser);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockResponse).sendRedirect("/");
  }

  @Test
  public void testDoGetNotAdminNullUser() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

  @Test
  public void testDoGetNotRegisteredUser() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_user");
    Mockito.when(mockUserStore.getUser("test_user")).thenReturn(null);
    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockResponse).sendRedirect("/login");
  }
}
