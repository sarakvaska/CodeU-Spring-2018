package codeu.model.store.basic;

import codeu.model.data.Friendship;
import codeu.model.data.Friendship.Status;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;

import java.time.Instant;
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

  /**
   * In-memory map of Friendships. Each User maps to a List of Friendships,
   * and each Friendship has a variable that stores one of the User's friends.
   */
  private Map<User, List<Friendship>> friendships;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private FriendshipStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    friendships = new HashMap<>();
  }

  /**
   * Adds a friendship by adding the user's friend to a list that a user maps
   * to in the HashMap.
   */
  public void addFriendship(Friendship friendship) {
    User user = friendship.getUser();

    if (!friendships.containsKey(user)) {
      List<Friendship> friends = new ArrayList<>();
      friends.add(friendship);
      friendships.put(user, friends);
    } else {
      friendships.get(user).add(friendship);
    }

    persistentStorageAgent.writeThrough(friendship);
  }

  /**
   * Updates an existing friendship. This method is called when a friend request
   * is either accepted or rejected.
   */
  public void updateFriendship(Friendship friendship) {
    if (friendship.getStatus() == Status.ACCEPTED) {
      acceptFriendship(friendship);
    } else if (friendship.getStatus() == Status.REJECTED) {
      rejectFriendship(friendship);
    }
  }

  /**
   * Updates the status of the existing one-way friendship from PENDING to ACCEPTED
   * by first looking through the list of friends to find the friend that accepted
   * the request.
   *
   * Then, a new Friendship object is made to represent the now two-way friendship
   * both users have with each other.
   */
  public void acceptFriendship(Friendship friendship) {
    for (Friendship friend : friendships.get(friendship.getUser())) {
      if (friend.getFriend() == friendship.getFriend()) {
        friend.setStatus(Status.ACCEPTED);
        break;
      }
    }

    Friendship mutualFriendship = new Friendship(
        friendship.getFriend(), friendship.getUser(), UUID.randomUUID(),
        Status.ACCEPTED, Instant.now()
    );

    addFriendship(mutualFriendship);
  }

  /**
   * Removes the rejected friendship from the HashMap, then updates the friendship
   * in Datastore.
   */
  public void rejectFriendship(Friendship friendship) {
    friendships.get(friendship.getUser()).remove(friendship);

    persistentStorageAgent.writeThrough(friendship);
  }

  /** Access the current set of friendships known to the application. */
  public Map<User, List<Friendship>> getFriendships() {
    return friendships;
  }

  /**
   * Sets the List of Friendships stored by this FriendshipStore.
   * This should only be called once, when the data is loaded from Datastore.
   */
  public void setFriendships(Map<User, List<Friendship>> friendships) {
    this.friendships = friendships;
  }
}
