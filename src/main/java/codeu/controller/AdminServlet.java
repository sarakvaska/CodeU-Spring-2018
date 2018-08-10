package codeu.controller;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.FriendshipStore;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/** Servlet class for admin */
public class AdminServlet extends HttpServlet {	
	/** Store class that stores the info needed*/
	private UserStore userStore;
	private MessageStore messageStore;
	private ConversationStore conversationStore;
	private FriendshipStore friendshipStore;


	/**sets up the current state of the stores*/
	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
		setMessageStore(MessageStore.getInstance());
		setConversationStore(ConversationStore.getInstance());
	}

	/**Sets up the stores*/
	void setUserStore(UserStore userStore) { 
		this.userStore = userStore; 
	}

	void setMessageStore(MessageStore messageStore) { 
		this.messageStore = messageStore; 
	}

	void setConversationStore(ConversationStore conversationStore) { 
		this.conversationStore = conversationStore; 
	}

	void setFriendshipStore(FriendshipStore friendshipStore) {
    	this.friendshipStore = friendshipStore;
  	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	  throws IOException, ServletException {
		String username = (String) request.getSession().getAttribute("user");
		if (username == null) {
			// user is not logged in, don't give them access
			response.sendRedirect("/login");
			return;
		}
		User user = userStore.getUser(username);
		if (user == null) {
			// user was not found, don't let them access admin
			response.sendRedirect("/login");
			return;
		}
		if(user.isAdmin()){
			// user is in admin list, give access to admin site, and send map as attribute
			Map<String, String> adminStatsMap = new HashMap<>();
			addStats(adminStatsMap);
			request.setAttribute("adminStatsMap", adminStatsMap);
			request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
		}
	    else {
	    	response.sendRedirect("/");
	    }
	}

	/** This method will gets the correct stats from datastore and store them in a map */
	public void addStats(Map<String, String> map) {
		// Retrieve sizes of each Datastore
		int userSize = userStore.countTotalUsers();
		int messageSize = messageStore.countTotalMessages();
		int convSize = conversationStore.countTotalConversations();
		// Adds them to the map
        map.put("userSize", Integer.toString(userSize));
        map.put("messageSize", Integer.toString(messageSize));
        map.put("convSize", Integer.toString(convSize));

        // Instant converter to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yy hh:mm a")
                       .withZone(ZoneId.systemDefault());

         // Adds latest Message
        Message lastMessage = messageStore.getLastMessageIndex();

        if(lastMessage == null) {
          //If there's no latestMessage send empty attributes
          map.put("lastMessageContent", "");
          map.put("lastMessageTime", "");
          map.put("lastMessageUser", "");
        } 
        else {
			//else set body and time of creation
			String lastMessageContent = lastMessage.getContent();
			Instant lastMessageTime = lastMessage.getCreationTime();
			String lastMessageTimeString = formatter.format(lastMessageTime);
			String lastMessageUser = userStore.getUser(lastMessage.getAuthorId()).getName();
			map.put("lastMessageContent", lastMessageContent);
			map.put("lastMessageTime", lastMessageTimeString);
			map.put("lastMessageUser", lastMessageUser);
		}

		// Adds latest Conversation
        Conversation lastConversation = conversationStore.getLastConversationIndex();
        if(lastConversation == null) {
			//If there's no lastConversation send empty attributes
			map.put("lastConversationName", "");
			map.put("lastConversationTime", "");
        } 
        else { 
			//else set body and time of creation
			String lastConversationTitle = lastConversation.getTitle();
			Instant lastConversationTime = lastConversation.getCreationTime();
			String lastConversationTimeString = formatter.format(lastConversationTime);
			map.put("lastConversationName", lastConversationTitle);
			map.put("lastConversationTime", lastConversationTimeString);
        }

         // Adds latest User
        User lastUser = userStore.getLastUserIndex();
        if(lastUser == null) {
			//If there's no latestUser send empty attributes
			map.put("lastUserName", "");
			map.put("lastUserTime", "");
        } 
        else {
			//else set name and time of creation
			String lastUserName = lastUser.getName();

			// convert creation time to string
			Instant lastUserTime = lastUser.getCreationTime();
            String lastUserTimeString = formatter.format(lastUserTime);

			map.put("lastUserName", lastUserName);
			map.put("lastUserTime", lastUserTimeString);
        }
	}
}
