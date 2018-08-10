package codeu.model.store.basic;

import codeu.model.data.Activity;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ActivityStore {

  /** Singleton instance of ActivityStore. */
  private static ActivityStore instance;

  /**
   * Returns the singleton instance of ActivityStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static ActivityStore getInstance() {
    if (instance == null) {
      instance = new ActivityStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static ActivityStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new ActivityStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Activities from and
   * saving Activities to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Activities. */
  private List<Activity> activities;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ActivityStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    activities = new LinkedList<>();
  }

  /**
   * Add a new activity to the current set of activities known to the application.
   */
  public void addActivity(Activity activity) {
    // Add to the front of the list
    activities.add(0, activity);
    persistentStorageAgent.writeThrough(activity);
  }

  /**
   * Delete an activity from the current set of activities known to the application.
   */
  public void deleteActivity(Activity activity) {
    activities.remove(activity);
    persistentStorageAgent.deleteActivityThrough(activity);
  }

  /**
   * Access the Activity object with the given UUID.
   *
   * @return null if the UUID does not match any existing Activity.
   */
  public Activity getActivityById(UUID id) {
    for (Activity activity : activities) {
      if (activity.getId().equals(id)) {
        return activity;
      }
    }
    return null;
  }

  /** Access the current set of activities known to the application. */
  public List<Activity> getActivities() {
    return activities;
  }

  /**
   * Sets the List of Activities stored by this ActivityStore. This should only
   * be called once, when the data is loaded from Datastore.
   */
  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }
}
