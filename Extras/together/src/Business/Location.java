/* Generated by Together */

package Business;

/**
 * Locations are used to keep track of specific real-world places.  An employee may have a preferred location to work in, used by the schedule and report generators.
 */
public class Location implements BusinessObject {
    /**
     * The name of the location which is to be used by other components of WebAgenda. Non-unique location names will cause conflicts so only unique names can be used.
     */
    private String locName;

    /**
     * A description of the location being used. If location names are similar, a description of what goes on at that location or who should/does work at that location. This is optional.
     */
    private String locDescription;
}
