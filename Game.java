import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * The base code was created by Professor Lynn Marshall 
 * as the sample solution for Assignment 1
 *
 * @author Nkechi Chukwuma 101230684
 * @version 17th March 2023, Assignment 2
 * 
 * @author  Michael Kolling and David J. Barnes 
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version A1 Sample Solution
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Item heldItem;
    private Stack<Room> previousRoomStack; 
    //it limits the amount of items that can be picked up
    private int strength;
    private TransporterRoom transportRoom;
    
    /**
     * Create the game and initialise its internal map, as well
     * as the previous room (none) and previous room stack (empty).
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        strength = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Item chair1, chair2, chair3, chair4, bar, computer1, computer2, computer3, tree1, tree2, cookie1, cookie2, cookie3, cookie4, cookie5, cookie6, energy1, energy2, energy3;
        Beamer beamer;
        // create some items
        chair1 = new Item("chair","a wooden chair",5);
        chair2 = new Item("chair","a wooden chair",5);
        chair3 = new Item("chair","a wooden chair",5);
        chair4 = new Item("chair","a wooden chair",5);
        bar = new Item("bar","a long bar with stools",95.67);
        computer1 = new Item("PC","a PC",10);
        computer2 = new Item("Mac","a Mac",5);
        computer3 = new Item("PC","a PC",10);
        tree1 = new Item("Tree","a fir tree",500.5);
        tree2 = new Item("Tree","a fir tree",500.5);
        cookie1 = new Item("Cookie", "a choco chip cookie", 0.25);
        cookie2 = new Item("Cookie", "a choco chip cookie", 0.25);
        cookie3 = new Item("Cookie", "a choco chip cookie", 0.25);
        cookie4 = new Item("Cookie", "a choco chip cookie", 0.25);
        cookie5 = new Item("Cookie", "a choco chip cookie", 0.25);
        cookie6 = new Item("Cookie", "a choco chip cookie", 0.25);
        beamer = new Beamer();
        energy1 = new Item("Booster", "a temporary strength booster", 0.5);
        energy2 = new Item("Booster", "a temporary strength booster", 0.5);
        energy3 = new Item("Booster", "a temporary strength booster", 0.5);
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transportRoom= new TransporterRoom();
        
        // put items in the rooms
        outside.addItem(tree1);
        outside.addItem(tree2);
        outside.addItem(beamer);
        outside.addItem(energy1);
        outside.addItem(cookie5);
        theatre.addItem(chair1);
        theatre.addItem(cookie4);
        pub.addItem(bar);
        pub.addItem(cookie2);
        pub.addItem(cookie3);
        lab.addItem(chair2);
        lab.addItem(computer1);
        lab.addItem(chair3);
        lab.addItem(computer2);
        lab.addItem(beamer);
        lab.addItem(energy2);
        theatre.addItem(cookie6);
        office.addItem(chair4);
        office.addItem(computer3);
        office.addItem(cookie1);
        transportRoom.addItem(beamer);
        transportRoom.addItem(energy3);
        // initialise room exits
        outside.setExit("east", theatre); 
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", transportRoom);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
             drop(command);
        }
        else if (commandWord.equals("energy")){
            energy(command);
        }
        else if (commandWord.equals("charge")){
            charge(command);
        }
        else if (commandWord.equals("fire")){
            fire(command);
        }
        else if(commandWord.equals("boost")){
            boost(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * It allows you to view the item your carryin whenever you enter a new room
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom; // store the previous room
            previousRoomStack.push(currentRoom); // and add to previous room stack
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if(heldItem != null){
                System.out.println("You are holding " + heldItem.getItemName());
            }
            else{
                System.out.println("Your hand is empty!");
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            // output the long description of this room
            System.out.println(currentRoom.getLongDescription());
        }
        if(heldItem != null){
            System.out.println("You're holding "+ heldItem.getItemName());
        }
        else{
            System.out.println("You aren't holding anything!");
        }
    }
    
    /** 
     * "Eat" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Eat what?");
        }
        //to confirm item to be eaten is cookie
        
        if(heldItem != null){
            if (heldItem.getItemName().toLowerCase().equals("cookie"))
            {
                // output that we have eaten
                System.out.println("You have eaten and are no longer hungry."); 
                heldItem = null;
                strength = 5;
            }
            else{
               System.out.println("Item is not edible!");
            }
        }
        else{
            System.out.println("You have no food."); 
        }
        
        
    }
    
    /** 
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /** 
     * "StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /** 
     * "Take" was entered. Check the rest of the command to see
     * whether we really want to take.
     * 
     * @param command The command to be processed
     */
    private void take(Command command)
    {
       if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        } 
        
        String itemName = command.getSecondWord();
        //allows player to pick a cookie for strength
       if(itemName.toLowerCase().equals("cookie")){
           heldItem = currentRoom.pickItem(itemName);
           System.out.println("You've picked up " + itemName);
       }
       
       //checks if you're holding anything and if you have enough strength 
       
       if(heldItem == null && strength > 0 && !itemName.toLowerCase().equals("tree") && !itemName.toLowerCase().equals("booster")){
            heldItem = currentRoom.pickItem(itemName);
            System.out.println("You've picked up " + itemName);
            strength -= 1;
            
        }
        //confirms if you have a enough strength to carry a tree
        else if(itemName.toLowerCase().equals("tree")){
            if(strength < 10){
            System.out.println("You do not have enough strength to pick up this item.");
        }
            else{
                heldItem = currentRoom.pickItem(itemName);
                System.out.println("You've picked up " + itemName);
                strength -= 5;
            }
        }
        else if(strength <= 0){
            System.out.println("You have no energy, eat  a cookie!");
        }
        else{
            System.out.println("You're already holding something, please drop it first");
        }
        
        
    }
    
     /** 
     * "Drop" was entered. Check the rest of the command to see
     * whether we really want to drop.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command){
        if(command.hasSecondWord()) {
            System.out.println("drop what?");
        }
        
        //checks if an item is held
        if(heldItem != null){
            currentRoom.dropItem(heldItem);
            System.out.println(heldItem.getItemName() + " has been dropped!");
            heldItem = null;
        }
        else{
            System.out.println("You have nothing to drop!");
        }
    }
    
     /** 
     * "Energy" was entered. Check the rest of the command to see
     * whether we really want to check stength level.
     * 
     * @param command The command to be processed
     */
    private void energy(Command command){
        System.out.println("Your current hp is: " + strength);
    }
    
    /** 
     * "Charge" was entered. Check the rest of the command to see
     * whether we really want to charge.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command){
        if(command.hasSecondWord()) {
            System.out.println("charge what?");
        }
        
        if(heldItem != null) {
            if(heldItem.getItemName().toLowerCase().equals("Beamer".toLowerCase())){
               ((Beamer)heldItem).charging(currentRoom); 
            }
            else{
                System.out.println("You are not holding a beamer!");
            }
        }
        else{
                System.out.println("A beamer hasn't been picked up!");
            }
        
        
    }
    
    /** 
     * "Fire" was entered. Check the rest of the command to see
     * whether we really want to fire.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command){
        if(command.hasSecondWord()) {
            System.out.println("fire what?");
        }
        
        if(heldItem.getItemName().toLowerCase().equals("Beamer".toLowerCase())){
             if(((Beamer)heldItem).charged() == true){
                 currentRoom = ((Beamer)heldItem).fire();
             }
             else{
                 System.out.println("The beamer has not been charged!");
             }
        }
        else{
            System.out.println("You are not holding a beamer!");
        }
    }
    
    /** 
     * "Boost" was entered. Check the rest of the command to see
     * whether we really want to boost.
     * 
     * @param command The command to be processed
     */    
    private void boost(Command command){
        if(command.hasSecondWord()) {
            System.out.println("boost what?");
        }
        
        if(strength > 0){
            if(heldItem == null) {
                heldItem = currentRoom.pickItem("booster");
                if(heldItem.getItemName().toLowerCase().equals("Booster".toLowerCase())){
                    System.out.println("Your strength has been boosted!"); 
                    heldItem = null;
                    strength = 10;  
                }
            }
            else{
                System.out.println("You haven't picked up a booster!"); 
 
            }
        
        }
        else{
            System.out.println("You have no energy, please eat a cookie!");
        }
}
}
