import java.util.Set;

/**
 * Utility class for moving through a cave one room at a time.
 * It keeps track of the current room but hides direct access
 * to the underlying {@link Room} objects.
 */
public class CaveNavigator {
    private final SerialLoader loader;
    private Room current;

    /**
     * Construct a navigator starting at the loader's start room.
     */
    public CaveNavigator(SerialLoader loader) {
        this.loader = loader;
        this.current = loader.getStart();
    }

    /**
     * Return the set of doors in the current room.
     */
    public Set<Door> getDoors() {
        return current.getDoors();
    }

    /**
     * Move through the provided door if possible.
     */
    public void move(Door d) {
        Room next = current.enter(d);
        if (next != null) {
            current = next;
        }
    }

    /**
     * Check if the navigator has reached the end room.
     */
    public boolean atEnd() {
        return current.equals(loader.getEnd());
    }

    Room getCurrentRoom() {
        return current;
    }
}
