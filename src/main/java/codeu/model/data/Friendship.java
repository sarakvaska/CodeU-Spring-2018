package codeu.model.data;

import java.time.Instant;
import java.util.UUID;

/** Class representing a friendship between two Users. */
public class Friendship {
  private final User user;
  private final User friend;
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
   * @param user the User this Friendship is based on
   * @param friend the other User that user is friends with
   * @param id the ID of this Friendship
   * @param status the status of this Friendship
   * @param creation the creation time of this Friendship
   */
  public Friendship(User user, User friend, UUID id, Status status, Instant creation) {
    this.user = user;
    this.friend = friend;
    this.id = id;
    this.status = status;
    this.creation = creation;
  }

  /**
   * @return the User this Friendship is based on
   */
  public User getUser() {
    return user;
  }

  /**
   * @return the other User that user is friends with
   */
  public User getFriend() {
    return friend;
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
