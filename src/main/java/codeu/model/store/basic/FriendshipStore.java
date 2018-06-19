package codeu.model.store.basic;

import codeu.model.data.Friendship;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class FriendshipStore {

  /** Singleton instance of ActivityStore. */
  private static FriendshipStore instance;

  /**
   * Returns the singleton instance of FriendshipStore that should be shared
   * between all servlet classes. Do not call this function from a test;
   * use getTestInstance() instead.
   */
  public static FriendshipStore getInstance() {
    if (instance == null) {
      instance = new FriendshipStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static FriendshipStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new FriendshipStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Friendships from and
   * saving Friendships to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** In-memory map of Friendships. Each User maps to a List of Users containing
  the User's friends. */
  private Map<User, List<User>> friendships;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private FriendshipStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    friendships = new HashMap<>();
  }

  /**
   * Adds a friendship by adding the user's friend to a list that the user maps
   * to in the HashMap.
   */
  public void addFriendship(Friendship friendship) {
    User user = friendship.getUser();
    User friend = friendship.getFriend();

    if (!friendships.containsKey(user)) {
      List<User> friends = new ArrayList<>();
      friends.add(friend);
      friendships.put(user, friends);
    } else {
      friendships.get(user).add(friend);
    }

    persistentStorageAgent.writeThrough(friendship);
  }

  /** Access the current set of friendships known to the application. */
  public Map<User, List<User>> getFriendships() {
    return friendships;
  }

  /**
   * Sets the List of Friendships stored by this FriendshipStore.
   * This should only be called once, when the data is loaded from Datastore.
   */
  public void setFriendships(Map<User, List<User>> friendships) {
    this.friendships = friendships;
  }
}
