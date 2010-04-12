/* Generated by Together */

package Persistence;

/**
 * Provides functionality for adding, updating and deleting locations in the database.
 */
public class LocationBroker implements Broker {
    /**
     * Must be used to get a Broker, ensures that singleton pattern is enforced. 
     */
    public LocationBroker getBroker() {
    }

    /**
     * Static representation of the broker. Initialized the first time getBroker is called. 
     */
    private LocationBroker locationBroker;
}
