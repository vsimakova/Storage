package storage;

/**
 * Represents a Standart type of unit storage.
 * 
 * @author  Viktoryia Simakova 
 * @version 2020-07-07
 */
public class StdStorageUnit extends StorageUnit
{

    
    //---------------------------------------------------------------------
    //          CONSTANTS
    //---------------------------------------------------------------------
    
    /** the price for Standart Storage Unit */ 
    public static final double PRICE_FOR_STAND_STORAGE_UNIT = 75.0;
    //---------------------------------------------------------------------
    //          CONSTRUCTORS
    //---------------------------------------------------------------------
    /**
     * StorageUnit Constructor
     *
     * @param   width                   the storage unit's width, in feet; must be over 0 and evenly divisible by 4
     * @param   length                  the storage unit's length, in feet; must be over 0 and evenly divisible by 4
     * @param   height                  the storage unit's height, in feet; must be over 0 evenly divisible by 2
     * @param   storageLocation         the location of this storage unit
     */
    public StdStorageUnit(int width, int length,int height, StorageLocation storageLocation)
    {
        super(width, length, height, storageLocation);
    }
    
    /**
     * Method calculates specific unit price of Standart Storage Unit. This method trears
     * like an abstract in StorageUnit Class.
     * 
     * @return     specific unit price of Standart Storage Unit
     */
    public double calcUnitSpecificPrice(){
        return PRICE_FOR_STAND_STORAGE_UNIT;
    }
}
