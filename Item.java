
/**
 * This class represents an item which may be put
 * in a room in the game of Zuul.
 * 
 * The base code was created by Professor Lynn Marshall 
 * as the sample solution for Assignment 1
 * 
 * @author Nkechi Chukwuma 101230684
 * @version 17th March 2023, Assignment 2
 * 
 * @author Lynn Marshall 
 * @version A1 Sample Solution
 */
public class Item
{
    // description of the item
    private String description;
    
    // weight of the item in kilgrams 
    private double weight;
    
    //name of the item
    private String name;

    /**
     * Constructor for objects of class Item.
     * 
     * @param description The description of the item
     * @param weight The weight of the item
     * @param name The name of the item
     */
    public Item(String name, String description, double weight)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
    
    /**
     * Returns the name of the item
     * 
     * @return item name
     */
    public String getItemName(){
        return name;
    }

    /**
     * Returns a description of the item, including its name,
     * description and weight.
     * 
     * @return  A description of the item
     */
    public String getDescription()
    {
        return name + ": " + description + " that weighs " + weight + "kg.";
    }
}
