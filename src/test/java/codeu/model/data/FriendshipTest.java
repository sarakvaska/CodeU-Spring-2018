package codeu.model.data;

import codeu.model.data.Friendship.Status;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class FriendshipTest {

  @Test
  public void testCreate() {
    User user = new User(UUID.randomUUID(), "test_user", "user_password", Instant.now(), "user_aboutMe", false);
    User friend = new User(UUID.randomUUID(), "test_friend", "friend_password", Instant.now(), "user_aboutMe", false);

    UUID userId = user.getId();
    UUID friendId = friend.getId();
    UUID id = UUID.randomUUID();
    Status status = Status.PENDING;
    Instant creation = Instant.now();

    Friendship friendship = new Friendship(userId, friendId, id, status, creation);
    Assert.assertEquals(userId, friendship.getUserId());
    Assert.assertEquals(friendId, friendship.getFriendId());
    Assert.assertEquals(id, friendship.getId());
    Assert.assertEquals(status, friendship.getStatus());
    Assert.assertEquals(creation, friendship.getCreationTime());

    friendship.setStatus(Status.ACCEPTED);
    Assert.assertEquals(Status.ACCEPTED, friendship.getStatus());

    friendship.setStatus(Status.REJECTED);
    Assert.assertEquals(Status.REJECTED, friendship.getStatus());
  }
}
