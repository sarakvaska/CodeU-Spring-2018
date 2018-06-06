package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/**
 * Class representing an activity, which include users registering, making new
 * conversations, or sending messages.
 */
public class Activity {
  private final ActivityType type;
  private final UUID id;
  private final Instant creation;

  /**
   * Enum representing the different types of activities.
   */
  public enum ActivityType {
    NEW_USER, NEW_CONVERSATION, NEW_MESSAGE;
  }

  /**
   * Constructs a new Activity.
   *
   * @param type the type of this activity
   * @param id the ID of this activity
   * @param creation the creation time of this activity
   */
  public Activity(ActivityType type, UUID id, Instant creation) {
    this.type = type;
    this.id = id;
    this.creation = creation;
  }

  /**
   * @return the type of this activity
   */
  public ActivityType getType() {
    return type;
  }

  /**
   * @return the ID of this activity
   */
  public UUID getId() {
    return id;
  }

  /**
   * @return the creation time of this activity
   */
  public Instant getCreationTime() {
    return creation;
  }
}
