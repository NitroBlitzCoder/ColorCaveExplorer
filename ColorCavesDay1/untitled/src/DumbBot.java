import java.util.*;
/**
 * A very simple cave exploring bot.
 *
 * DumbBot walks through the cave by randomly selecting one of the
 * available doors in the current room until it eventually reaches the end
 * room.  The complete sequence of moves is stored in a string which is
 * printed when the exploration finishes.
 */
public class DumbBot{

	private static boolean showPath = true;
	private static boolean showRoom = true;
	private static int totalLengthOfPaths = 0;
	private SerialLoader loader;
	private CaveNavigator nav;
	private ArrayList<Door> path; // consider other data structures here
	private int lastMazeSteps;
	private ArrayList<Room> rNum=new ArrayList();




	/**
	 * Initialize the bot by loading the cave from the default file and
	 * positioning it at the start room.
	 */
	public DumbBot(String fileName){
		path = new ArrayList<Door>();
		loader = new SerialLoader();
		loader.deserialize("src/CaveData/" + fileName + ".ser");
		nav = new CaveNavigator(loader);
		lastMazeSteps = Room.getNumMoves(); // get number of total steps at start
	}


	/**
	 * Continuously pick a random door and move until the end room is
	 * reached.  The sequence of rooms visited is appended a List
	 * of doors which constitute a path
	 */
	public void run() {
		somewhatSmartBot();
		optimizePath();
		report();
	}


	private void optimizePath(){

		ArrayList<Door> temp = new ArrayList<>();
		ArrayList<Room> temp2 = new ArrayList<>();

		for(int i = 0; i < rNum.size(); i++){

			Room current = rNum.get(i);

			// if room not seen before keep it
			if(!temp2.contains(current)){
				temp2.add(current);
				temp.add(path.get(i));
			}
			else{
				// remove everything after first occurrence
				int index = temp2.indexOf(current);

				while(temp2.size() > index + 1){
					temp2.remove(temp2.size() - 1);
					temp.remove(temp.size() - 1);
				}
			}
		}
		path = temp;
		rNum = temp2;
	}

	/**
	 * Trace path backwards to ensure you get back to start
	 */
	private boolean validatePath(){
		for (int i = path.size()-1; i >= 0; i--)
			nav.move(path.get(i));
		totalLengthOfPaths += path.size(); // keep track of # moves used for validation

		// return true if actually back at the start
		return loader.getStart().equals(nav.getCurrentRoom());


	}


	private void report(){
		int steps = Room.getNumMoves() - lastMazeSteps;
		System.out.println("Found the End!\nsteps =" + steps + " |  path length = "+path.size());

		if (showPath){
			System.out.print("\nPATH: START -> ");
			for (Door d : path)
				System.out.print(d.toString().charAt(0)+" -> ");
			System.out.println("END");
		}
		if (showRoom){
			System.out.print("\nRoom: START -> ");
			for (Room d : rNum)
				System.out.print(d.toString().charAt(0)+" -> ");
			System.out.println("END");
		}
		System.out.println("valid = "+validatePath());
		System.out.println("__________________________________________________________________\n");

	}


	/**
	 * Select a random door from the current room.
	 *
	 * @return a door chosen uniformly at random or {@code null} if there are
	 *         no doors in the current room
	 */
	private Door pickRandomDoor() {
		int i = 0;
		int rand = (int) (Math.random() * nav.getDoors().size());
		for (Door d : nav.getDoors()) {
			if (i == rand)
				return d;
			i++;
		}
		return null; // Should never occur unless the room has no doors
	}
	/**
	 * breath first search
	*/
	private void somewhatSmartBot() {
					Queue<ArrayList<Door>> q = new LinkedList<>();
					Set<Room> set = new HashSet<>();

					q.add(new ArrayList<>());
					set.add(nav.getCurrentRoom());

					while (!q.isEmpty()) {
						ArrayList<Door> currPath = q.poll();

						nav = new CaveNavigator(loader);
						for (Door d : currPath)
							nav.move(d);

						if (nav.atEnd()) {
							// Populate path and rNum so run() can use them normally
							path = currPath;
							rNum = new ArrayList<>();
							nav = new CaveNavigator(loader);
							for (Door d : currPath) {
					nav.move(d);
					rNum.add(nav.getCurrentRoom());
				}
				return;
			}
			for (Door d : nav.getDoors()) {
				nav.move(d);
				Room sibling = nav.getCurrentRoom();

				if (!set.contains(sibling)) {
					set.add(sibling);
					ArrayList<Door> temp = new ArrayList<>(currPath);
					temp.add(d);
					q.add(temp);
				}

				nav = new CaveNavigator(loader);
				for (Door d2 : currPath)
					nav.move(d2);
			}
		}
	}

	/**
	 * Launch DumbBot as a standalone program.
	 */
	public static void main(String[] args) {


		// run one
		 DumbBot bot = new DumbBot("C10");
		 bot.run();

		// Run a series
		//String[] mazeFileNames = {"M1","M2","M3","M4","M5","M6","M7","M8","M9"};
		String[] mazeFileNames = {"C1"};
		int numTrials = 1;/*
		for (int i = 0; i < numTrials; i++)
			for (String fileName : mazeFileNames){
				DumbBot bot = new DumbBot(fileName);
				bot.run();
			}
*/
		int pathStepMultiplier = 100;
		int averageTotalPathSteps = totalLengthOfPaths/numTrials;
		int averageTotalExploreSteps = (Room.getNumMoves() - totalLengthOfPaths)/numTrials;
		System.out.println("Done! --> Below are averages of "+numTrials+" trials");
		System.out.println("Average Total moves between Rooms = "+Room.getNumMoves());
		System.out.println("Average Total path steps          = "+averageTotalPathSteps);
		System.out.println("Average Total explore steps       = "+averageTotalExploreSteps);
		System.out.println("Weighted Score            = "+(averageTotalPathSteps*pathStepMultiplier+averageTotalExploreSteps));

	}
}
