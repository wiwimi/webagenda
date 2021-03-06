/**
 * business.schedule - Location.java
 */
package business.schedule;

import business.BusinessObject;

/**
 * Represents the locations where employees can work; is used mainly by
 * scheduling and job-related tasks.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Location extends BusinessObject
    {
    /**
     * The name of the location which is to be used by other components of
     * WebAgenda. Non-unique location names will cause conflicts so only unique
     * names can be used.
     */
    private String name = null;
    
    /**
     * A description of the location being used. If location names are similar,
     * a description of what goes on at that location or who should/does work at
     * that location. This is optional.
     */
    private String desc = null;
    
    /** Produces a blank template of an location */
    public Location()
        {
        }
    
    /**
     * Constructor that sets the Location name
     * 
     * @param name String
     */
    public Location(String name)
        {
        this.name = name;
        }
    
    /**
     * Constructor that sets the location name and description. Description can
     * be null.
     * 
     * @param name String
     * @param desc String
     */
    public Location(String name, String desc)
        {
        this.name = name;
        this.desc = desc;
        }
    
    /**
     * Gets the location name
     * 
     * @return String
     */
    public String getName()
        {
        return name;
        }
    
    /**
     * Sets the location name
     * 
     * @param String
     */
    public void setName(String name)
        {
        this.name = name;
        }
    
    /**
     * Gets the description of the location
     * 
     * @return String
     */
    public String getDesc()
        {
        return desc;
        }
    
    /**
     * Sets the description for the location
     * 
     * @param desc the desc to set
     */
    public void setDesc(String desc)
        {
        this.desc = desc;
        }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return name;
        }
    
    }
