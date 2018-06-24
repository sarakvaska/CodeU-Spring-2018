package codeu.model.data;

import codeu.model.data.Friendship.Status;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class FriendshipTest {

  @Test
  public void testCreate() {
    User user = new User(UUID.randomUUID(), "test_user", "user_password", Instant.now());
    User friend = new User(UUID.randomUUID(), "test_friend", "friend_password", Instant.now());
    UUID id = UUID.randomUUID();
    Status status = Status.PENDING;
    Instant creation = Instant.now();

    Friendship friendship = new Friendship(user, friend, id, status, creation);
    Assert.assertEquals(user, friendship.getUser());
    Assert.assertEquals(friend, friendship.getFriend());
    Assert.assertEquals(id, friendship.getId());
    Assert.assertEquals(status, friendship.getStatus());
    Assert.assertEquals(creation, friendship.getCreationTime());

    friendship.setStatus(Status.ACCEPTED);
    Assert.assertEquals(Status.ACCEPTED, friendship.getStatus());

    friendship.setStatus(Status.REJECTED);
    Assert.assertEquals(Status.REJECTED, friendship.getStatus());
  }
}
