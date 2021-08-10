package storage;

/**
 * Represents a Temperature type of unit storage.
 * 
 * @author  Viktoryia Simakova 
 * @version 2020-07-07
 */
public class TempStorageUnit extends StorageUnit
{
   
    //---------------------------------------------------------------------
    //          CONSTANTS
    //---------------------------------------------------------------------
    
    /** the lower boundary of temperature level */
    public static final int LOWER = 45;
    /** the upper boundary of temperature level */
    public static final int UPPER = 70; 
    /** the upper boundary of temperature level which cause additions to price */
    public static final int UPPER_PAY = 49;
    /** the lower boundary of temperature level which cause additions to price */
    public static final int LOWER_PAY = 65;
    /** the price for unit per month if customer uses paid option of temperature level */
    public static final double PRICE_PAY_TEMP = 30.0;
    /** the price for unit per cubic foot */
    public static final double PRICE_PER_CUB_FT = 1.0;
    //---------------------------------------------------------------------
    //          INSTANCE DATA
    //---------------------------------------------------------------------
    
    private int tempLevel;

    //---------------------------------------------------------------------
    //          CONSTRUCTORS
    //---------------------------------------------------------------------
    /**
     * StorageUnit Constructor
     *
     * @param   tempLevel               the temperature level of specific Temperature Storage unit; must be <45 & >70
     * @param   width                   the storage unit's width, in feet; must be over 0 and evenly divisible by 4
     * @param   length                  the storage unit's length, in feet; must be over 0 and evenly divisible by 4
     * @param   height                  the storage unit's height, in feet; must be over 0 evenly divisible by 2
     * @param   storageLocation         the location of this storage unit
     */
    public TempStorageUnit(int width, int length, int height, int tempLevel, StorageLocation storageLocation)
    {
        super(width, length, height, storageLocation);
        setTempLevel(tempLevel);
    }
    
    /**
     * Method retrieves temperature level of specific Temperature Storage unit 
     * 
     * @return     temperature level of specific Temperature Storage unit
     */
    public int getTempLevel()
    {
        return tempLevel;
    }
    
    /**
     * Method retrieves temperature level of specific Temperature Storage unit 
     * @param  tempLevel is temperature level of specific Temperature Storage unit 
     * 
     */
    public void setTempLevel(int tempLevel)
    {
        if (tempLevel < LOWER || tempLevel > UPPER) {
            throw new IllegalArgumentException("Level temperature is out of bound.");
        }
        this.tempLevel = tempLevel;
    }
    
    /**
     * Method calculates specific unit price of Temperature Storage Unit. This method trears
     * like an abstract in StorageUnit Class.
     * 
     * @return     specific unit price of Temperature Storage Unit
     */
    public double calcUnitSpecificPrice(){
        double unitSpecificPrice = getLength() * getWidth() * getHeight() * PRICE_PER_CUB_FT;
        if (tempLevel >= LOWER && tempLevel <= UPPER_PAY || tempLevel >= LOWER_PAY && tempLevel <= UPPER) {
            unitSpecificPrice += PRICE_PAY_TEMP;
        }        
        return unitSpecificPrice;
    }
}
