package storage;

/**
 * Represents a Humidity type of unit storage.
 * 
 * @author  Viktoryia Simakova 
 * @version 2020-07-07
 */
public class HumidStorageUnit extends StorageUnit
{
    
    //---------------------------------------------------------------------
    //          CONSTANTS
    //---------------------------------------------------------------------
    
    /** the lower boundary of humidity level */
    public static final int LOWER = 20;
    /** the upper boundary of humidity level */
    public static final int UPPER = 60; 
    /** the upper boundary of humidity level which cause additions to price */
    public static final int UPPER_PAY = 29;
    /** the price for unit per month if customer uses paid option of humidity level */
    public static final double PRICE_PAY_HUMID = 20.0;
    /** the price for unit per square foot */
    public static final double PRICE_PER_SQ_FT = 5.0;
    //---------------------------------------------------------------------
    //          INSTANCE DATA
    //---------------------------------------------------------------------
    /** humidity level of unit's in this class */
    private int humLevel;

    //---------------------------------------------------------------------
    //          CONSTRUCTORS
    //---------------------------------------------------------------------
    /**
     * StorageUnit Constructor
     *
     * @param   humLevel                the humidity level of specific Humidity Storage unit; must be <20 & >60
     * @param   width                   the storage unit's width, in feet; must be over 0 and evenly divisible by 4
     * @param   length                  the storage unit's length, in feet; must be over 0 and evenly divisible by 4
     * @param   height                  the storage unit's height, in feet; must be over 0 evenly divisible by 2
     * @param   storageLocation         the location of this storage unit
     */
    public HumidStorageUnit(int width, int length, int height, int humLevel,StorageLocation storageLocation)
    {
        super(width, length, height, storageLocation);
        this.humLevel = humLevel;
    }
    
    /**
     * Method retrieves humidity level of specific Humidity Storage unit 
     * 
     * @return     humidity level of specific Humidity Storage unit
     */
    public int getHumLevel()
    {
        return humLevel;
    }
    
    /**
     * Method retrieves humidity level of specific Humidity Storage unit 
     * @param  humLevel is humidity level of specific Humidity Storage unit 
     * 
     */
    public void setHumLevel(int humLevel)
    {
        if (humLevel < LOWER || humLevel > UPPER) {
            throw new IllegalArgumentException("Level is out of bound.");
        }
        this.humLevel = humLevel;
    }
    
    /**
     * Method calculates specific unit price of Humidity Storage Unit. This method trears
     * like an abstract in StorageUnit Class.
     * 
     * @return     specific unit price of Humidity Storage Unit
     */
    public double calcUnitSpecificPrice(){
        double unitSpecificPrice = getLength() * getWidth() * PRICE_PER_SQ_FT;
        if (humLevel >= LOWER && humLevel <= UPPER_PAY) {
            unitSpecificPrice += PRICE_PAY_HUMID;
        }
        return unitSpecificPrice;
    }
}
