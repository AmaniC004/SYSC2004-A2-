import java.util.*;
/**
 * The class TransporterRoom allows the player to be transported to any random room
 * regardless of direction.
 *
 * @author Nkechi Chukwuma 101230684
 * @version 17th March 2023, Assignment 2
 */
public class TransporterRoom extends Room
{
    //intitializing a random variable
    private Random rand = new Random();
    
    /**
     * Constructor for objects of class TransporterRoom
     */
    public TransporterRoom()
    {
        //description of transporter room
        super("Telportation in progress!");
        
        setExit("Random", null);
        
    }

    /**
    * Returns a random room, independent of the direction parameter.
    *
    * @param direction Ignored.
    * @return A randomly selected room.
    */
    @Override
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }
    
    /**
    * Choose a random room.
    *
    * @return The room we end up in upon leaving this current one.
    */
       
    private Room findRandomRoom()
    {
        int numRooms = Room.getRoom().size();
        int numRand = rand.nextInt(numRooms);
        Room room = Room.getRoom().get(numRand);
        return room;
    }
}
