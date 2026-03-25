import java.util.*;
import java.io.Serializable;
public class Room implements Serializable {
    private static final long serialVersionUID = -6038461298026736823L;
	public static int next_id = 0;
	private static int numMoves = 0;
	private int id;
	private String name;
	private String description;
	private HashMap<Door, Room> doors;

	public Room(String name, String description){
		this.name = name;
		this.description = description;
		this.id = next_id;
		next_id++;
		this.doors = new HashMap<Door, Room>();
	}
	public void addDoor(Door door, Room room){
		this.doors.put(door, room);
		room.doors.put(door,this);
	}
	public Set<Door> getDoors(){
		return doors.keySet();
	}
	public void removeDoor(Door door){
		Room linked = this.doors.remove(door);
		if(linked != null){
			linked.doors.remove(door);
		}
	}
        Room enter(Door d){
                numMoves++;
                return doors.get(d);
        }
	public static int getNumMoves(){
		return numMoves;
	}
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	public int getID(){
		return this.id;
	}
        @Override
        public boolean equals(Object obj){
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                Room other = (Room) obj;
                return this.id == other.id;
        }

        @Override
        public int hashCode(){
                return this.id;
        }

        @Override
        public String toString(){
                return this.id+" "+this.name;
        }

}