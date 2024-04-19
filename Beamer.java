
/**
 * This class represents a beamer which is an object that instantly
 * transports the player to the room it was charged in.
 *
 * @author Nkechi Chukwuma 101230684
 * @version 17th March 2023, Assignment 2
 */
public class Beamer extends Item
{
    // checks if the beamer is charged
    private boolean charge;
    
    // the room where the beamer is charged
    private Room chargeRoom;
    
    /**
     * Constructor for objects of class Beamer
     */
    public Beamer()
    {
        // use variables from the super class
        super("Beamer", "A wonderful beamer", 1.0);
        charge = false;
        chargeRoom = null;
    }

    /**
     * Charging the beamer and saving the 
     * The room where the beamer is charged
     *
     * @param  the room where the beamer is charged in
     * 
     */
    public void charging(Room room)
    {
        if(charge == false){
            chargeRoom = room;
            charge = true;
            System.out.println("The beamer is charged!");
        }
        else{
            System.out.println("The beamer was already charged!");
        }
    }
    
    /**
     * It checks if the beamer is charged
     */
    public boolean charged(){
        if(charge == true){
           return true; 
        }
        else{
            return false;
        }
    }
    
    /**
     * Allows the beamer to fire and transport 
     * the player to the saved room if charged
     */
    public Room fire(){
        if(charge == true){
            System.out.println("The beamer has been fired!");
            System.out.println("You are in " + chargeRoom.getShortDescription());
            charge = false;
            return chargeRoom;
        }
        else{
            System.out.println("The beamer has not been charged!");
            return null;
        }
    }
}
