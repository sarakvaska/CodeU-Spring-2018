package codeu.controller;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet class for admin */
public class AdminServlet extends HttpServlet {
	
	/** Store class that stores the info needed*/
	  private UserStore userStore;
	  private MessageStore messageStore;
	  private ConversationStore conversationStore;
	  
	  /**sets up the current state of the stores*/
	  @Override
	  public void init() throws ServletException {
	    super.init();
	    setUserStore(UserStore.getInstance());
	    setMessageStore(MessageStore.getInstance());
	    setConversationStore(ConversationStore.getInstance());
	  }
	  
	/**Sets up the stores*/
	  void setUserStore(UserStore userStore) { this.userStore = userStore; }
	  void setMessageStore(MessageStore messageStore) { this.messageStore = messageStore; }
	  void setConversationStore(ConversationStore conversationStore) { this.conversationStore = conversationStore; }

	  /**The first function called to set up admin page*/
	  @Override
  	public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
      Map<String, String> adminStatsMap = new HashMap<>();

      String username = (String) request.getSession().getAttribute("user");


      if (username == null) {
	  // user is not logged in, don't give them access
	  response.sendRedirect("/login");
	  return;
      }

      User user = userStore.getUser(username);
      if (user == null) {
	  // user was not found, don't let them access admin
	  System.out.println("User not found: " + username);
	  response.sendRedirect("/login");
	  return;
      }

      if(user.isAdmin()){
	  // user is in admin list, give access to admin site, and send map as attribute
	  addStats(adminStatsMap);
	  request.setAttribute("adminStatsMap", adminStatsMap);
	  adminStatsMap.forEach((key,value) -> System.out.println(key + " = " + value));
	  request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
	  return;
      }
      
      /** This method will gets the correct stats from datastore and store them in a map */
      public void addStats(Map<String, String> map) {
        // Retrieve sizes of each Datastore
        Integer userSize = userStore.countTotalUsers();
        Integer messageSize = messageStore.countTotalMessages();
        Integer convSize = conversationStore.countTotalConversations();


        // Adds them to the map
        map.put("userSize", userSize.toString());
        map.put("messageSize", messageSize.toString());
        map.put("convSize", convSize.toString());

        // Adds latest Message
        Message lastMessage = messageStore.getLastMessageIndex();
        if(lastMessage == null) {
          //If there's no latestMessage send empty attributes
          map.put("lastMessageContent", "");
          map.put("lastMessageTime", "");
          map.put("lastMessageUser", "");
        } else {
          //else set body and time of creation
          String lastMessageContent = lastMessage.getContent();
          String lastMessageTime = lastMessage.getTime();
          String lastMessageUser = userStore.getUser(lastMessage.getAuthorId()).getName();

          map.put("lastMessageContent", lastMessageContent);
          map.put("lastMessageTime", lastMessageTime);
          map.put("lastMessageUser", lastMessageUser);

          System.out.println(lastMessageContent);
          System.out.println(lastMessageTime);
          System.out.println(lastMessageUser);
        }

        // Adds latest Conversation
        Conversation lastConversation = conversationStore.getLastConversationIndex();
        if(lastConversation == null) {
          //If there's no lastConversation send empty attributes
          map.put("lastConversationName", "");
          map.put("lastConversationTime", "");
        } else {
          //else set body and time of creation
          String lastConversationTitle= lastConversation.getTitle();
          String lastConversationTime = lastConversation.getTime();

          map.put("lastConversationName", lastConversationTitle);
          map.put("lastConversationTime", lastConversationTime);

          System.out.println(lastConversationTitle);
          System.out.println(lastConversationTime);
        }

        // Adds latest User
        User lastUser = userStore.getLastUserIndex();
        if(lastUser == null) {
          //If there's no latestUser send empty attributes
          map.put("lastUserName", "");
          map.put("lastUserTime", "");
        } else {
          //else set name and time of creation
          String lastUserName = lastUser.getName();
          String lastUserTime = lastUser.getTime();

          map.put("lastUserName", lastUserName);
          map.put("lastUserTime", lastUserTime);

          System.out.println(lastUserName);
          System.out.println(lastUserTime);
        }

      }
      // user isn't in admin list, redirect him to root
      response.sendRedirect("/");

  }

}
