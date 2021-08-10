package storage;

import java.time.LocalDate;

/**
 * Represents a single storage unit for Stanley's Storage Spots
 *
 * @author      Bill Barry
 * @version     2017-11-20
 * @author      Viktoryia Simakova
 * @version     2020-07-07
 */
public abstract class StorageUnit implements StorageUnitInterface  { 

    //---------------------------------------------------------------------
    //          CONSTANTS
    //---------------------------------------------------------------------
    /** width and length must be a multiple of this number */
    public static final int MULT_FOR_WIDTH_LENGTH = 4;
    /** height must be a multiple of this number */
    public static final int MULT_FOR_HEIGHT = 2;
    //----------------------------------------------------------------
    //          INSTANCE DATA
    //----------------------------------------------------------------
    /** the width of the unit */
    private int width;
    /** the length of the unit */    
    private int length;
    /** the height of the unit */
    private int height;
    /** the price for this unit */
    private double price;
    /** the customer renting this unit */
    private Customer customer;
    /** the rental start date for this unit */
    private LocalDate rentalStart;
    /** the storage location for this unit */
    private StorageLocation storageLocation;

    //----------------------------------------------------------------
    //          CONSTRUCTORS
    //----------------------------------------------------------------
    /**
     * StorageUnit Constructor
     *
     * @param   width                   the storage unit's width, in feet; must be over 0 and evenly divisible by 4
     * @param   length                  the storage unit's length, in feet; must be over 0 and evenly divisible by 4
     * @param   height                  the storage unit's height, in feet; must be over 0 evenly divisible by 2
     * @param   storageLocation         the location of this storage unit
     */
    public StorageUnit(int width, int length, int height, StorageLocation storageLocation) {
        if (width <= 0 || length <= 0 || height <= 0) {
            throw new IllegalArgumentException("All dimensions must be > 0");
        }
        if (width % MULT_FOR_WIDTH_LENGTH != 0 || length % MULT_FOR_WIDTH_LENGTH != 0) {
            throw new IllegalArgumentException("width and length must be a multiple of 4");
        }
        if (height % MULT_FOR_HEIGHT != 0) {
            throw new IllegalArgumentException("height must be a multiple of 2");
        }
        if (storageLocation == null) {
            throw new IllegalArgumentException("type must be non-null");
        }

        this.width           = width;
        this.length          = length;
        this.height          = height;
        this.storageLocation = storageLocation;

        this.customer    = null;
        this.rentalStart = null;
        this.price       = 0.00;
    }

    //----------------------------------------------------------------
    //          ACCESSORS
    //----------------------------------------------------------------
    /**
     * Retrieves the unit's width, in feet
     *
     * @return  the unit's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the unit's length, in feet
     *
     * @return  the unit's length
     */
    public int getLength() {
        return length;
    }

    /**
     * Retrieves the unit's height, in feet
     *
     * @return  the unit's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves the unit's full price for storage unit rent per month
     *
     * @return  the unit's full price for storage unit rent per month
     */
    public double getPrice() {
        //check if there is no customer in order to not calculate specific price if customer is null
        if(this.customer == null) {
            price = 0;
        }else{
            price = storageLocation.getUnitBasePrice() + calcUnitSpecificPrice();
        }
        return price;
    }

    /**
     * Retrieves the customer associated with this unit
     *
     * @return  the unit's customer, or null if not rented
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Retrieves the date at which the current rental started
     *
     * @return  the unit's rental start date
     */
    public LocalDate getRentalStart() {
        return rentalStart;
    }

    //----------------------------------------------------------------
    //          OTHER METHODS
    //----------------------------------------------------------------
    /**
     * Rents the unit to the specified customer
     *
     * @param   customer        the customer to whom the unit has been rented
     * @param   rentalStart     the start data of the rental; must not be null
     * @return                  true, if the unit was successfully rented; false, if already rented and not available
     */
    public boolean rent(Customer customer, LocalDate rentalStart) {
        if (rentalStart == null) {
            throw new IllegalArgumentException("rentalStart must not be null");
        }
        if (this.customer != null) {
            return false;
        }
        this.customer    = customer;
        this.rentalStart = rentalStart;
        return true;
    }

    /**
     * Releases ("un-rents") the unit, making it available for rent
     * 
     * @return  true, if release could be completed; false, if unit wasn't rented to begin with
     */
    public boolean release() {
        if (this.customer == null) {
            return false;
        }
        this.customer    = null;
        this.rentalStart = null;
        this.price       = 0.00;
        return true;
    }

    /**
     * Retrieves the unit's location
     *
     * @return  the unit's location
     */
    public StorageLocation getStorageLocation() {
        return storageLocation;
    }
    
    /**
     * Checks if this unit is rented.
     * 
     * @return   true, if unit is rented by a customer, and false - if not.
     */
    public boolean isRented(){
        boolean flag = false;
        if (this.customer != null){
            flag = true;
        }
        return flag;
    }
    
    /**
     * Sends "buck" to specific units(subclasses) and calculates specific price of certain unit
     * 
     * @return   specific price of certain unit
     */
    public double calcUnitSpecificPrice(){
        return price;
    }

    /**
     * Represents the current state of the unit in string format
     *
     * @return      basic information about the storage unit and its rental status
     */
    public String toString() {
        String info = getClass() + " unit, " + getWidth() + "'(w) x " + getLength() + "'(l) x " + getHeight() + "'(h), ";
        if (getCustomer() == null) {
            info += "available";
        } else {
            info += "rented to " + getCustomer().getName() + " for $" + String.format("%.2f", this.getPrice());
        }
        return info;
    }

}
