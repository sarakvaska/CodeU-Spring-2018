package codeu.model.data;

import codeu.model.data.Activity.ActivityType;

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {

  @Test
  public void testCreate() {
    ActivityType type = ActivityType.NEW_USER;
    UUID id = UUID.randomUUID();
    Instant creation = Instant.now();

    Activity activity = new Activity(type, id, creation);

    Assert.assertEquals(type, activity.getType());
    Assert.assertEquals(id, activity.getId());
    Assert.assertEquals(creation, activity.getCreationTime());
  }
}
