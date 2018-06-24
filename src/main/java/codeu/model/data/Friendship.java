package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a friendship between two Users. */
public class Friendship {
  private final UUID userId;
  private final UUID friendId;
  private final UUID id;
  private Status status;
  private final Instant creation;

  /** Enum representing the status of this Friendship. */
  public enum Status {
    PENDING, ACCEPTED, REJECTED;
  }

  /**
   * Constructs a new Friendship.
   *
   * @param userId the ID of the User this Friendship is based on
   * @param friendId the ID of the user's friend
   * @param id the ID of this Friendship
   * @param status the status of this Friendship
   * @param creation the creation time of this Friendship
   */
  public Friendship(UUID userId, UUID friendId, UUID id, Status status, Instant creation) {
    this.userId = userId;
    this.friendId = friendId;
    this.id = id;
    this.status = status;
    this.creation = creation;
  }

  /**
   * @return the ID of the User this Friendship is based on
   */
  public UUID getUserId() {
    return userId;
  }

  /**
   * @return the ID of the user's friend
   */
  public UUID getFriendId() {
    return friendId;
  }

  /**
   * @return the ID of this Friendship
   */
  public UUID getId() {
    return id;
  }

  /**
   * @return the status of this Friendship
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets this friendship's Status to the given status
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * @return the creation time of this Friendship
   */
  public Instant getCreationTime() {
    return creation;
  }
}
