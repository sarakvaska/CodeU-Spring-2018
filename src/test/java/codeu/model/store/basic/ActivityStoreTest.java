package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityStoreTest {

  private ActivityStore activityStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final Activity ACTIVITY_ONE =
      new Activity(ActivityType.NEW_USER, UUID.randomUUID(), Instant.ofEpochMilli(1000));
  private final Activity ACTIVITY_TWO =
      new Activity(ActivityType.NEW_CONVERSATION, UUID.randomUUID(), Instant.ofEpochMilli(2000));
  private final Activity ACTIVITY_THREE =
      new Activity(ActivityType.NEW_MESSAGE, UUID.randomUUID(), Instant.ofEpochMilli(3000));

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    activityStore = ActivityStore.getTestInstance(mockPersistentStorageAgent);

    final List<Activity> activityList = new LinkedList<>();
    activityList.add(ACTIVITY_THREE);
    activityList.add(ACTIVITY_TWO);
    activityList.add(ACTIVITY_ONE);
    activityStore.setActivities(activityList);
  }

  @Test
  public void testAddActivity() {
    Activity newUser =
        new Activity(ActivityType.NEW_USER, UUID.randomUUID(), Instant.now());
    Activity newConversation =
        new Activity(ActivityType.NEW_CONVERSATION, UUID.randomUUID(), Instant.now());
    Activity newMessage =
        new Activity(ActivityType.NEW_MESSAGE, UUID.randomUUID(), Instant.now());

    activityStore.addActivity(newUser);
    activityStore.addActivity(newConversation);
    activityStore.addActivity(newMessage);

    Activity resultNewUser = activityStore.getActivityById(newUser.getId());
    Activity resultNewConversation = activityStore.getActivityById(newConversation.getId());
    Activity resultNewMessage = activityStore.getActivityById(newMessage.getId());

    Assert.assertEquals(newUser, resultNewUser);
    Assert.assertEquals(newConversation, resultNewConversation);
    Assert.assertEquals(newMessage, resultNewMessage);

    Mockito.verify(mockPersistentStorageAgent).writeThrough(newUser);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(newConversation);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(newMessage);
  }

  @Test
  public void testDeleteActivity() {
    UUID messageId = UUID.randomUUID();
    Activity newMessage =
        new Activity(ActivityType.NEW_MESSAGE, messageId, Instant.now());
    activityStore.addActivity(newMessage);

    activityStore.deleteActivity(newMessage);
    Activity resultDeletedMessage = activityStore.getActivityById(messageId);

    Assert.assertEquals(null, resultDeletedMessage);
    Mockito.verify(mockPersistentStorageAgent).deleteActivityThrough(newMessage);
  }

  @Test
  public void testGetActivityById_found() {
    Activity resultActivityOne = activityStore.getActivityById(ACTIVITY_ONE.getId());

    Assert.assertEquals(ACTIVITY_ONE, resultActivityOne);
  }

  @Test
  public void testGetActivityById_notFound() {
    Activity randomActivity = activityStore.getActivityById(UUID.randomUUID());

    Assert.assertNull(randomActivity);
  }

  @Test
  public void testGetActivities() {
    List<Activity> resultActivityList = activityStore.getActivities();

    Assert.assertEquals(ACTIVITY_THREE, resultActivityList.get(0));
    Assert.assertEquals(ACTIVITY_TWO, resultActivityList.get(1));
    Assert.assertEquals(ACTIVITY_ONE, resultActivityList.get(2));
  }
}
