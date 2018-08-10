package codeu.model.store.persistence;

import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.data.Conversation;
import codeu.model.data.Friendship;
import codeu.model.data.Friendship.Status;
import codeu.model.data.Message;
import codeu.model.data.User;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for PersistentDataStore. The PersistentDataStore class relies on DatastoreService,
 * which in turn relies on being deployed in an AppEngine context. Since this test doesn't run in
 * AppEngine, we use LocalServiceTestHelper to do all of the AppEngine setup so we can test. More
 * info: https://cloud.google.com/appengine/docs/standard/java/tools/localunittesting
 */
public class PersistentDataStoreTest {

  private PersistentDataStore persistentDataStore;
  private final LocalServiceTestHelper appEngineTestHelper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

  @Before
  public void setup() {
    appEngineTestHelper.setUp();
    persistentDataStore = new PersistentDataStore();
  }

  @After
  public void tearDown() {
    appEngineTestHelper.tearDown();
  }

  @Test
  public void testSaveAndLoadUsers() throws PersistentDataStoreException {
    UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
    String nameOne = "test_username_one";
    String passwordHashOne = "$2a$10$BNte6sC.qoL4AVjO3Rk8ouY6uFaMnsW8B9NjtHWaDNe8GlQRPRT1S";
    Instant creationOne = Instant.ofEpochMilli(1000);
    String aboutMeOne = "test_aboutMe_one";
    Boolean adminOne = false;
    User inputUserOne = new User(idOne, nameOne, passwordHashOne, creationOne, aboutMeOne, adminOne);

    UUID idTwo = UUID.fromString("10000001-2222-3333-4444-555555555555");
    String nameTwo = "test_username_two";
    String passwordHashTwo = "$2a$10$ttaMOMMGLKxBBuTN06VPvu.jVKif.IczxZcXfLcqEcFi1lq.sLb6i";
    Instant creationTwo = Instant.ofEpochMilli(2000);
    String aboutMeTwo = "test_aboutMe_two";
    Boolean adminTwo = false;
    User inputUserTwo = new User(idTwo, nameTwo, passwordHashTwo, creationTwo, aboutMeTwo, adminTwo);

    // save
    persistentDataStore.writeThrough(inputUserOne);
    persistentDataStore.writeThrough(inputUserTwo);

    // load
    List<User> resultUsers = persistentDataStore.loadUsers();

    // confirm that what we saved matches what we loaded
    User resultUserOne = resultUsers.get(0);
    Assert.assertEquals(idOne, resultUserOne.getId());
    Assert.assertEquals(nameOne, resultUserOne.getName());
    Assert.assertEquals(passwordHashOne, resultUserOne.getPasswordHash());
    Assert.assertEquals(creationOne, resultUserOne.getCreationTime());
    Assert.assertEquals(aboutMeOne, resultUserOne.getAboutMe());
    Assert.assertEquals(adminOne, resultUserOne.isAdmin());

    User resultUserTwo = resultUsers.get(1);
    Assert.assertEquals(idTwo, resultUserTwo.getId());
    Assert.assertEquals(nameTwo, resultUserTwo.getName());
    Assert.assertEquals(passwordHashTwo, resultUserTwo.getPasswordHash());
    Assert.assertEquals(creationTwo, resultUserTwo.getCreationTime());
    Assert.assertEquals(aboutMeTwo, resultUserTwo.getAboutMe());
    Assert.assertEquals(adminTwo, resultUserTwo.isAdmin());
  }

  @Test
  public void testSaveAndLoadConversations() throws PersistentDataStoreException {
    UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
    UUID ownerOne = UUID.fromString("10000001-2222-3333-4444-555555555555");
    String titleOne = "Test_Title";
    Instant creationOne = Instant.ofEpochMilli(1000);
    Conversation inputConversationOne = new Conversation(idOne, ownerOne, titleOne, creationOne);

    UUID idTwo = UUID.fromString("10000002-2222-3333-4444-555555555555");
    UUID ownerTwo = UUID.fromString("10000003-2222-3333-4444-555555555555");
    String titleTwo = "Test_Title_Two";
    Instant creationTwo = Instant.ofEpochMilli(2000);
    Conversation inputConversationTwo = new Conversation(idTwo, ownerTwo, titleTwo, creationTwo);

    // save
    persistentDataStore.writeThrough(inputConversationOne);
    persistentDataStore.writeThrough(inputConversationTwo);

    // load
    List<Conversation> resultConversations = persistentDataStore.loadConversations();

    // confirm that what we saved matches what we loaded
    Conversation resultConversationOne = resultConversations.get(0);
    Assert.assertEquals(idOne, resultConversationOne.getId());
    Assert.assertEquals(ownerOne, resultConversationOne.getOwnerId());
    Assert.assertEquals(titleOne, resultConversationOne.getTitle());
    Assert.assertEquals(creationOne, resultConversationOne.getCreationTime());

    Conversation resultConversationTwo = resultConversations.get(1);
    Assert.assertEquals(idTwo, resultConversationTwo.getId());
    Assert.assertEquals(ownerTwo, resultConversationTwo.getOwnerId());
    Assert.assertEquals(titleTwo, resultConversationTwo.getTitle());
    Assert.assertEquals(creationTwo, resultConversationTwo.getCreationTime());
  }

  @Test
  public void testSaveAndLoadMessages() throws PersistentDataStoreException {
    UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
    UUID conversationOne = UUID.fromString("10000001-2222-3333-4444-555555555555");
    UUID authorOne = UUID.fromString("10000002-2222-3333-4444-555555555555");
    String contentOne = "test content one";
    Instant creationOne = Instant.ofEpochMilli(1000);
    Message inputMessageOne =
        new Message(idOne, conversationOne, authorOne, contentOne, creationOne);

    UUID idTwo = UUID.fromString("10000003-2222-3333-4444-555555555555");
    UUID conversationTwo = UUID.fromString("10000004-2222-3333-4444-555555555555");
    UUID authorTwo = UUID.fromString("10000005-2222-3333-4444-555555555555");
    String contentTwo = "test content one";
    Instant creationTwo = Instant.ofEpochMilli(2000);
    Message inputMessageTwo =
        new Message(idTwo, conversationTwo, authorTwo, contentTwo, creationTwo);

    // save
    persistentDataStore.writeThrough(inputMessageOne);
    persistentDataStore.writeThrough(inputMessageTwo);

    // load
    List<Message> resultMessages = persistentDataStore.loadMessages();

    // confirm that what we saved matches what we loaded
    Message resultMessageOne = resultMessages.get(0);
    Assert.assertEquals(idOne, resultMessageOne.getId());
    Assert.assertEquals(conversationOne, resultMessageOne.getConversationId());
    Assert.assertEquals(authorOne, resultMessageOne.getAuthorId());
    Assert.assertEquals(contentOne, resultMessageOne.getContent());
    Assert.assertEquals(creationOne, resultMessageOne.getCreationTime());

    Message resultMessageTwo = resultMessages.get(1);
    Assert.assertEquals(idTwo, resultMessageTwo.getId());
    Assert.assertEquals(conversationTwo, resultMessageTwo.getConversationId());
    Assert.assertEquals(authorTwo, resultMessageTwo.getAuthorId());
    Assert.assertEquals(contentTwo, resultMessageTwo.getContent());
    Assert.assertEquals(creationTwo, resultMessageTwo.getCreationTime());
  }

  @Test
  public void testSaveAndLoadActivities() throws PersistentDataStoreException {
    ActivityType newUser = ActivityType.NEW_USER;
    UUID idOne = UUID.fromString("10000000-2222-3333-4444-555555555555");
    Instant creationOne = Instant.ofEpochMilli(1000);
    Activity activityOne = new Activity(newUser, idOne, creationOne);

    ActivityType newConversation = ActivityType.NEW_CONVERSATION;
    UUID idTwo = UUID.fromString("10000001-2222-3333-4444-555555555555");
    Instant creationTwo = Instant.ofEpochMilli(2000);
    Activity activityTwo = new Activity(newConversation, idTwo, creationTwo);

    ActivityType newMessage = ActivityType.NEW_MESSAGE;
    UUID idThree = UUID.fromString("10000002-2222-3333-4444-555555555555");
    Instant creationThree = Instant.ofEpochMilli(3000);
    Activity activityThree = new Activity(newMessage, idThree, creationThree);

    // save
    persistentDataStore.writeThrough(activityOne);
    persistentDataStore.writeThrough(activityTwo);
    persistentDataStore.writeThrough(activityThree);

    // load
    List<Activity> resultActivities = persistentDataStore.loadActivities();

    // confirm that what we saved matches what we loaded
    Activity resultActivityThree = resultActivities.get(0);
    Assert.assertEquals(newMessage, resultActivityThree.getType());
    Assert.assertEquals(idThree, resultActivityThree.getId());
    Assert.assertEquals(creationThree, resultActivityThree.getCreationTime());

    Activity resultActivityTwo = resultActivities.get(1);
    Assert.assertEquals(newConversation, resultActivityTwo.getType());
    Assert.assertEquals(idTwo, resultActivityTwo.getId());
    Assert.assertEquals(creationTwo, resultActivityTwo.getCreationTime());

    Activity resultActivityOne = resultActivities.get(2);
    Assert.assertEquals(newUser, resultActivityOne.getType());
    Assert.assertEquals(idOne, resultActivityOne.getId());
    Assert.assertEquals(creationOne, resultActivityOne.getCreationTime());
  }

  @Test
  public void testSaveAndLoadFriendships() throws PersistentDataStoreException {
    UUID userId = UUID.fromString("10000000-2222-3333-4444-555555555555");
    String userName = "test_user";
    String userPasswordHash = "$2a$10$BNte6sC.qoL4AVjO3Rk8ouY6uFaMnsW8B9NjtHWaDNe8GlQRPRT1S";
    Instant userCreation = Instant.ofEpochMilli(1000);
    String userAboutMe = "test_aboutMe";
    Boolean userAdmin = false;
    User inputUser = new User(userId, userName, userPasswordHash, userCreation, userAboutMe, userAdmin);

    UUID friendId = UUID.fromString("10000001-2222-3333-4444-555555555555");
    String friendName = "test_friend";
    String friendPasswordHash = "$2a$10$ttaMOMMGLKxBBuTN06VPvu.jVKif.IczxZcXfLcqEcFi1lq.sLb6i";
    Instant friendCreation = Instant.ofEpochMilli(2000);
    String friendAboutMe = "test_aboutMe";
    Boolean friendAdmin = false;
    User inputFriend = new User(friendId, friendName, friendPasswordHash, friendCreation, friendAboutMe, friendAdmin);

    UUID friendshipId = UUID.fromString("10000002-2222-3333-4444-555555555555");
    Status inputStatus = Status.PENDING;
    Instant inputCreation = Instant.ofEpochMilli(3000);
    Friendship inputFriendship =
        new Friendship(userId, friendId, friendshipId, inputStatus, inputCreation);

    // save
    persistentDataStore.writeThrough(inputFriendship);

    // load
    Map<UUID, List<Friendship>> resultFriendships = persistentDataStore.loadFriendships();

    // confirm that what we saved matches what we loaded
    Friendship resultFriendship = resultFriendships.get(userId).get(0);
    Assert.assertEquals(userId, resultFriendship.getUserId());
    Assert.assertEquals(friendId, resultFriendship.getFriendId());
    Assert.assertEquals(friendshipId, resultFriendship.getId());
    Assert.assertEquals(inputStatus, resultFriendship.getStatus());
    Assert.assertEquals(inputCreation, resultFriendship.getCreationTime());

    // update status
    inputFriendship.setStatus(Status.REJECTED);

    // update friendship in datastore
    persistentDataStore.writeThrough(inputFriendship);

    // load
    resultFriendships = persistentDataStore.loadFriendships();

    // confirm that what we saved matches what we loaded
    resultFriendship = resultFriendships.get(userId).get(0);
    Assert.assertEquals(userId, resultFriendship.getUserId());
    Assert.assertEquals(friendId, resultFriendship.getFriendId());
    Assert.assertEquals(friendshipId, resultFriendship.getId());
    Assert.assertEquals(Status.REJECTED, resultFriendship.getStatus());
    Assert.assertEquals(inputCreation, resultFriendship.getCreationTime());
  }
}
