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
  private Map<UUID, List<Friendship>> friendships;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private FriendshipStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    friendships = new HashMap<>();
  }

  /**
   * Adds a friendship by adding the user's friend to a list that a user maps
   * to in the HashMap.
   *
   * This method actually adds the same friendship to both lists mapped to by
   * userId and friendId, only if the status is PENDING.
   *
   * This is to distinguish whether the pending friendship was made by the user
   * who started it. For example, if a friendship appears in a list,
   * whose userId is different from the ID that it is mapped in,
   * then this means that the friendship originated from another user.
   */
  public void addFriendship(Friendship friendship) {
    UUID userId = friendship.getUserId();
    UUID friendId = friendship.getFriendId();

    if (!friendships.containsKey(userId)) {
      List<Friendship> friends = new ArrayList<>();
      friends.add(friendship);
      friendships.put(userId, friends);
    } else {
      friendships.get(userId).add(friendship);
    }

    if (friendship.getStatus() == Status.PENDING) {
      if (!friendships.containsKey(friendId)) {
        List<Friendship> friends = new ArrayList<>();
        friends.add(friendship);
        friendships.put(friendId, friends);
      } else {
        friendships.get(friendId).add(friendship);
      }
    }

    persistentStorageAgent.writeThrough(friendship);
  }

  /**
   * Updates the status of the existing friendship from PENDING to ACCEPTED
   * by first looking through the list of friends to find the friend that accepted
   * the request.
   *
   * The extra friendship object that was added to the list of friends under the
   * friendId key is removed.
   *
   * Then, a new Friendship object is made to represent the now two-way friendship
   * both users have with each other.
   */
  public void acceptFriendship(Friendship friendship) {
    // Search for the friendship that was accepted
    for (Friendship friend : friendships.get(friendship.getUserId())) {
      if (friend.getFriendId().equals(friendship.getFriendId())) {
        friend.setStatus(Status.ACCEPTED);
        break;
      }
    }

    // Remove extra friendship
    friendships.get(friendship.getFriendId()).remove(friendship);

    // Add the mutual friendship to make it two-way
    Friendship mutualFriendship = new Friendship(
        friendship.getFriendId(), friendship.getUserId(), UUID.randomUUID(),
        Status.ACCEPTED, Instant.now()
    );

    addFriendship(mutualFriendship);
  }

  /**
   * Removes the rejected friendship and the extra pending friendship from the HashMap,
   * then updates the friendship in Datastore.
   */
  public void rejectFriendship(Friendship friendship) {
    friendships.get(friendship.getUserId()).remove(friendship);
    friendships.get(friendship.getFriendId()).remove(friendship);

    friendship.setStatus(Status.REJECTED);
    persistentStorageAgent.writeThrough(friendship);
  }

  /** Access the current set of friendships known to the application. */
  public Map<UUID, List<Friendship>> getFriendships() {
    return friendships;
  }

  /**
   * Sets the List of Friendships stored by this FriendshipStore.
   * This should only be called once, when the data is loaded from Datastore.
   */
  public void setFriendships(Map<UUID, List<Friendship>> friendships) {
    this.friendships = friendships;
  }
}
