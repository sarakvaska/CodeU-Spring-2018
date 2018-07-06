// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.persistence;

import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.data.Conversation;
import codeu.model.data.Friendship;
import codeu.model.data.Friendship.Status;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentDataStoreException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new of modified objects back to the Datastore.
 */
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;

  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {

    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String userName = (String) entity.getProperty("username");
        String passwordHash = (String) entity.getProperty("password_hash");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String aboutMe = (String) entity.getProperty("about_me");
        User user = new User(uuid, userName, passwordHash, creationTime, aboutMe);
        users.add(user);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {

    List<Conversation> conversations = new ArrayList<>();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime);
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }

  /**
   * Loads all Activity objects from the Datastore service and returns them in a List, sorted in
   * descending order by creation time.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Activity> loadActivities() throws PersistentDataStoreException {

    List<Activity> activities = new LinkedList<>();

    // Retrieve all activities from the datastore.
    Query query = new Query("chat-activities").addSort("creation_time", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        // Since the index of the activity type is stored, I use it to access the type.
        Long indexLong = (Long) entity.getProperty("activity_type");
        int index = indexLong.intValue();
        ActivityType type = ActivityType.values()[index];

        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Activity activity = new Activity(type, uuid, creationTime);
        activities.add(activity);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return activities;
  }

  /**
   * Loads all Friendship objects from the Datastore service and returns them in
   * a HashMap.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public Map<UUID, List<Friendship>> loadFriendships() throws PersistentDataStoreException {

    Map<UUID, List<Friendship>> friendships = new HashMap<>();

    // Retrieve all friendships from the datastore.
    Query query = new Query("chat-friendships");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID userId = UUID.fromString((String) entity.getProperty("user_id"));
        UUID friendId = UUID.fromString((String) entity.getProperty("friend_id"));
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));

        // Since the index of the status type is stored, I use it to access the type.
        Long indexLong = (Long) entity.getProperty("status");
        int index = indexLong.intValue();
        Status status = Status.values()[index];

        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));

        Friendship friendship = new Friendship(userId, friendId, uuid, status, creationTime);

        if (!friendships.containsKey(userId)) {
          List<Friendship> friends = new ArrayList<>();
          friends.add(friendship);
          friendships.put(userId, friends);
        } else {
          friendships.get(userId).add(friendship);
        }

      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return friendships;
  }

  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users", user.getId().toString());
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("password_hash", user.getPasswordHash());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    userEntity.setProperty("about_me", user.getAboutMe());
    datastore.put(userEntity);
  }

  /** Write a Message object to the Datastore service. */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages", message.getId().toString());
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /** Write a Conversation object to the Datastore service. */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations", conversation.getId().toString());
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    datastore.put(conversationEntity);
  }

  /** Write an Activity object to the Datastore service. */
  public void writeThrough(Activity activity) {
    Entity activityEntity = new Entity("chat-activities", activity.getId().toString());

    /* Using the index representation of the enum ActivityType as a way to store
    what the activity type is. */
    activityEntity.setProperty("activity_type", activity.getType().ordinal());

    activityEntity.setProperty("uuid", activity.getId().toString());
    activityEntity.setProperty("creation_time", activity.getCreationTime().toString());
    datastore.put(activityEntity);
  }

  /** Write a Friendship object to the Datastore service. */
  public void writeThrough(Friendship friendship) {
    Entity friendshipEntity = new Entity("chat-friendships", friendship.getId().toString());

    /* Storing both user IDs as properties. */
    friendshipEntity.setProperty("user_id", friendship.getUserId().toString());
    friendshipEntity.setProperty("friend_id", friendship.getFriendId().toString());

    friendshipEntity.setProperty("uuid", friendship.getId().toString());

    /* Using the index representation of the enum Status as a way to store
    what the friendship status is. */
    friendshipEntity.setProperty("status", friendship.getStatus().ordinal());

    friendshipEntity.setProperty("creation_time", friendship.getCreationTime().toString());
    datastore.put(friendshipEntity);
  }
}
