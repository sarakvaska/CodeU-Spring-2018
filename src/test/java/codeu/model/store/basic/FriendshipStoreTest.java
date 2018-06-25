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
          USER_ONE.getId(),
          USER_TWO.getId(),
          UUID.randomUUID(),
          Status.PENDING,
          Instant.ofEpochMilli(5000));
  private final Friendship FRIENDSHIP_TWO =
      new Friendship(
          USER_THREE.getId(),
          USER_FOUR.getId(),
          UUID.randomUUID(),
          Status.PENDING,
          Instant.ofEpochMilli(6000));

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    friendshipStore = FriendshipStore.getTestInstance(mockPersistentStorageAgent);

    List<Friendship> userOneFriends = new ArrayList<>();
    userOneFriends.add(FRIENDSHIP_ONE);

    List<Friendship> userTwoFriends = new ArrayList<>();
    userTwoFriends.add(FRIENDSHIP_ONE);

    List<Friendship> userThreeFriends = new ArrayList<>();
    userThreeFriends.add(FRIENDSHIP_TWO);

    List<Friendship> userFourFriends = new ArrayList<>();
    userFourFriends.add(FRIENDSHIP_TWO);

    Map<UUID, List<Friendship>> friendshipMap = new HashMap<>();
    friendshipMap.put(USER_ONE.getId(), userOneFriends);
    friendshipMap.put(USER_TWO.getId(), userTwoFriends);
    friendshipMap.put(USER_THREE.getId(), userThreeFriends);
    friendshipMap.put(USER_FOUR.getId(), userFourFriends);

    friendshipStore.setFriendships(friendshipMap);
  }

  @Test
  public void testGetFriendships() {
    Map<UUID, List<Friendship>> resultFriendshipMap = friendshipStore.getFriendships();

    assertEquals(FRIENDSHIP_ONE, resultFriendshipMap.get(USER_ONE.getId()).get(0));
    assertEquals(FRIENDSHIP_ONE, resultFriendshipMap.get(USER_TWO.getId()).get(0));
    assertEquals(FRIENDSHIP_TWO, resultFriendshipMap.get(USER_THREE.getId()).get(0));
    assertEquals(FRIENDSHIP_TWO, resultFriendshipMap.get(USER_FOUR.getId()).get(0));
  }

  @Test
  public void testAddFriendship() {
    Friendship newFriendship =
        new Friendship(
            USER_ONE.getId(),
            USER_THREE.getId(),
            UUID.randomUUID(),
            Status.PENDING,
            Instant.now()
        );

    friendshipStore.addFriendship(newFriendship);
    Friendship resultFriendshipOne = friendshipStore.getFriendships().get(USER_ONE.getId()).get(1);
    Friendship resultFriendshipTwo = friendshipStore.getFriendships().get(USER_THREE.getId()).get(1);

    assertEquals(newFriendship, resultFriendshipOne);
    assertEquals(newFriendship, resultFriendshipTwo);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(newFriendship);
  }

  @Test
  public void testAcceptFriendship() {
    friendshipStore.acceptFriendship(FRIENDSHIP_ONE);
    Status resultStatus =
        friendshipStore.getFriendships().get(USER_ONE.getId()).get(0).getStatus();
    Assert.assertEquals(Status.ACCEPTED, resultStatus);

    Friendship resultFriendship =
        friendshipStore.getFriendships().get(USER_TWO.getId()).get(0);
    Assert.assertEquals(USER_TWO.getId(), resultFriendship.getUserId());
    Assert.assertEquals(USER_ONE.getId(), resultFriendship.getFriendId());
    Assert.assertEquals(Status.ACCEPTED, resultFriendship.getStatus());

    Mockito.verify(mockPersistentStorageAgent).writeThrough(resultFriendship);
  }

  @Test
  public void testRejectFriendship() {
    friendshipStore.rejectFriendship(FRIENDSHIP_TWO);
    Assert.assertTrue(friendshipStore.getFriendships().containsKey(USER_THREE.getId()));
    Assert.assertTrue(friendshipStore.getFriendships().get(USER_THREE.getId()).isEmpty());
    Assert.assertTrue(friendshipStore.getFriendships().containsKey(USER_FOUR.getId()));
    Assert.assertTrue(friendshipStore.getFriendships().get(USER_FOUR.getId()).isEmpty());

    Assert.assertEquals(Status.REJECTED, FRIENDSHIP_TWO.getStatus());
    Mockito.verify(mockPersistentStorageAgent).writeThrough(FRIENDSHIP_TWO);
  }

  private void assertEquals(Friendship expectedFriendship, Friendship actualFriendship) {
    Assert.assertEquals(expectedFriendship.getUserId(), actualFriendship.getUserId());
    Assert.assertEquals(expectedFriendship.getFriendId(), actualFriendship.getFriendId());
    Assert.assertEquals(expectedFriendship.getId(), actualFriendship.getId());
    Assert.assertEquals(expectedFriendship.getStatus(), actualFriendship.getStatus());
    Assert.assertEquals(expectedFriendship.getCreationTime(), actualFriendship.getCreationTime());
  }
}
