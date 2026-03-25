import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
public class CaveData implements Serializable{
        private static final long serialVersionUID = 6575458930476063253L;
	private Room start, end;
	private List<Room> doors;

	private Set<Room> rooms;

	public CaveData(){

		rooms = new HashSet<Room>();
		doors = new ArrayList<Room>();

	}

	public void addRoom(Room r){
		doors.add(r);
		rooms.add(r);
	}

	public void setStart(Room r){
		start = r;
	}

	public void setEnd(Room r){
		end = r;
	}

	public List<Room> getOrder(){
		return doors;
	}

	public Room getStart(){
		return start;
	}

	public Room getEnd(){
		return end;
	}

}