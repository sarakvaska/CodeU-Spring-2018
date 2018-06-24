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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FriendshipStoreTest {

  private FriendshipStore friendshipStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final User USER_ONE =
      new User(
          UUID.randomUUID(),
          "test_username_one",
          "$2a$10$/zf4WlT2Z6tB5sULB9Wec.QQdawmF0f1SbqBw5EeJg5uoVpKFFXAa",
          Instant.ofEpochMilli(1000));
  private final User USER_TWO =
      new User(
          UUID.randomUUID(),
          "test_username_two",
          "$2a$10$lgZSbmcYyyC7bETcMo/O1uUltWYDK3DW1lrEjCumOE1u8QPMlzNVy",
          Instant.ofEpochMilli(2000));
  private final User USER_THREE =
      new User(
          UUID.randomUUID(),
          "test_username_three",
          "$2a$10$htXz4E48iPprTexGsEeBFurXyCwW6F6aoiSBqotL4m0NBg/VSkB9.",
          Instant.ofEpochMilli(3000));
  private final User USER_FOUR =
      new User(
          UUID.randomUUID(),
          "test_username_four",
          "random_password",
          Instant.ofEpochMilli(4000));

  private final Friendship FRIENDSHIP_ONE =
      new Friendship(
          USER_ONE,
          USER_TWO,
          UUID.randomUUID(),
          Status.PENDING,
          Instant.ofEpochMilli(5000));
  private final Friendship FRIENDSHIP_TWO =
      new Friendship(
          USER_THREE,
          USER_FOUR,
          UUID.randomUUID(),
          Status.PENDING,
          Instant.ofEpochMilli(6000));

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    friendshipStore = FriendshipStore.getTestInstance(mockPersistentStorageAgent);

    List<Friendship> userOneFriends = new ArrayList<>();
    userOneFriends.add(FRIENDSHIP_ONE);

    List<Friendship> userThreeFriends = new ArrayList<>();
    userThreeFriends.add(FRIENDSHIP_TWO);

    Map<User, List<Friendship>> friendshipMap = new HashMap<>();
    friendshipMap.put(USER_ONE, userOneFriends);
    friendshipMap.put(USER_THREE, userThreeFriends);

    friendshipStore.setFriendships(friendshipMap);
  }

  @Test
  public void testGetFriendships() {
    Map<User, List<Friendship>> resultFriendshipMap = friendshipStore.getFriendships();

    assertEquals(FRIENDSHIP_ONE, resultFriendshipMap.get(USER_ONE).get(0));
    assertEquals(FRIENDSHIP_TWO, resultFriendshipMap.get(USER_THREE).get(0));
  }

  @Test
  public void testAddFriendship() {
    Friendship newFriendship =
        new Friendship(
            USER_ONE,
            USER_THREE,
            UUID.randomUUID(),
            Status.PENDING,
            Instant.now()
        );

    friendshipStore.addFriendship(newFriendship);
    Friendship resultFriendship = friendshipStore.getFriendships().get(USER_ONE).get(1);

    assertEquals(newFriendship, resultFriendship);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(newFriendship);
  }

  @Test
  public void testAcceptFriendship() {
    friendshipStore.acceptFriendship(FRIENDSHIP_ONE);
    Status resultStatus = friendshipStore.getFriendships().get(USER_ONE).get(0).getStatus();
    Assert.assertEquals(Status.ACCEPTED, resultStatus);

    Friendship resultFriendship = friendshipStore.getFriendships().get(USER_TWO).get(0);
    Assert.assertEquals(USER_TWO, resultFriendship.getUser());
    Assert.assertEquals(USER_ONE, resultFriendship.getFriend());
    Assert.assertEquals(Status.ACCEPTED, resultFriendship.getStatus());

    Mockito.verify(mockPersistentStorageAgent).writeThrough(resultFriendship);
  }

  @Test
  public void testRejectFriendship() {
    friendshipStore.rejectFriendship(FRIENDSHIP_TWO);
    Assert.assertTrue(friendshipStore.getFriendships().containsKey(USER_THREE));
    Assert.assertTrue(friendshipStore.getFriendships().get(USER_THREE).isEmpty());

    Assert.assertEquals(Status.REJECTED, FRIENDSHIP_TWO.getStatus());
    Mockito.verify(mockPersistentStorageAgent).writeThrough(FRIENDSHIP_TWO);
  }

  private void assertEquals(Friendship expectedFriendship, Friendship actualFriendship) {
    Assert.assertEquals(expectedFriendship.getUser(), actualFriendship.getUser());
    Assert.assertEquals(expectedFriendship.getFriend(), actualFriendship.getFriend());
    Assert.assertEquals(expectedFriendship.getId(), actualFriendship.getId());
    Assert.assertEquals(expectedFriendship.getStatus(), actualFriendship.getStatus());
    Assert.assertEquals(expectedFriendship.getCreationTime(), actualFriendship.getCreationTime());
  }
}
