package storage;

/**
 * Represents a storage location for Stanley's Storage Spots
 *
 * @author      Bill Barry
 * @version     2017-11-20
 * @author      Viktoryia Simakova
 * @version     2020-07-07
 */
public class StorageLocation implements StorageLocationInterface  {

    //---------------------------------------------------------------------
    //          CONSTANTS
    //---------------------------------------------------------------------
    /** the number of rows of units in this location */
    public static final int NUM_ROWS      =  12;
    /** the initial number of customers */
    public static final int NUM_CUSTOMERS = 100;
    /** the row at which standard units begin */
    public static final int ROW_START_STD = 0;
    /** the row at which humidity units begin */
    public static final int ROW_START_HUM = 7;
    /** the row at which temp units begin */
    public static final int ROW_START_TMP = 10;
    /** the spaces (number of units) in standart units row */
    public static final int SPACES_IN_STD_ROW = 10;
    /** the spaces (number of units) in humidity units row */
    public static final int SPACES_IN_HUM_ROW = 8;
    /** the spaces (number of units) in temp units row */
    public static final int SPACES_IN_TEMP_ROW = 6;
    /** the discount for multi-unit renters*/
    public static final double DISCOUNT = 0.05;
    /** width of screen for unit map */
    public static final int SCREEN_WIDTH = 60;
    /** width of storage unit */
    public static final int WIDTH = 4;
    /** length of storage unit */
    public static final int LENGTH = 8;
    /** height of storage unit */
    public static final int HEIGHT = 8;
    /** the level of temperature of storage unit */
    public static final int LEVEL_TEMP = 50;
    /** the level of humidity of storage unit */
    public static final int LEVEL_HUMID = 30;

    //---------------------------------------------------------------------
    //          INSTANCE DATA
    //---------------------------------------------------------------------
    /** this unit's designation per company guidelines */
    private String locationDesignation;
    /** stores all units managed by this location */
    private StorageUnit[][] units;
    /** maintains the customers for this location */
    private Customer[] customers;
    /** the number of customers at this location (may be less than array size) */
    private int customerCount;
    /**the base price of storage unit */
    private double basePrice;

    //---------------------------------------------------------------------
    //          CONSTRUCTORS
    //---------------------------------------------------------------------
    /**
     * StorageLocation Constructor
     *
     * @param   locationDesignation     the company's designation (name) for this location;
     *                                  must not be null or empty; must match the required
     *                                  format, two upper-case letters followed by two digits,
     *                                  followed by an additional string representing city
     * @param basePrice                 the base price of storage unit
     */
    public StorageLocation(String locationDesignation, double basePrice) {
        if (locationDesignation == null || locationDesignation.isEmpty()) {
            throw new IllegalArgumentException("The location designation can't be empty or null");
        }
        if (!locationDesignation.matches("[A-Z]{2}[0-9]{2}[A-Za-z ]+")) {
            throw new IllegalArgumentException("Designation doesn't match required pattern");
        }
        this.locationDesignation = locationDesignation;
        setUnitBasePrice(basePrice);
        customers = new Customer[NUM_CUSTOMERS];
        customerCount = 0;

        units = new StorageUnit[NUM_ROWS][];        
        for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
            if (rowIdx >= ROW_START_TMP) {
                TempStorageUnit [] tempUnit = new TempStorageUnit[SPACES_IN_TEMP_ROW];
                units[rowIdx] = tempUnit;
                for (int idx = 0; idx < tempUnit.length; idx++) {
                    units[rowIdx][idx] = new TempStorageUnit(WIDTH, LENGTH, HEIGHT, LEVEL_TEMP, this);
                }
            } else if (rowIdx >= ROW_START_HUM) {
                HumidStorageUnit [] humidUnit = new HumidStorageUnit[SPACES_IN_HUM_ROW];
                units[rowIdx] = humidUnit;
                for (int idx = 0; idx < humidUnit.length; idx++) {
                    units[rowIdx][idx] = new HumidStorageUnit(WIDTH, LENGTH, HEIGHT, LEVEL_HUMID, this);
                }
            } else {
                StdStorageUnit [] stdUnit = new StdStorageUnit[SPACES_IN_STD_ROW];
                units[rowIdx] = stdUnit;
                for (int idx = 0; idx < stdUnit.length; idx++) {
                    units[rowIdx][idx] = new StdStorageUnit(WIDTH, LENGTH, HEIGHT, this);
                }
            }
        }
    }

    //---------------------------------------------------------------------
    //          ACCESSORS 
    //---------------------------------------------------------------------

    /**
     * Retrieves the storage location's designation
     * 
     * @return      the location's designation
     */
    public String getDesignation() {
        return locationDesignation;
    }

    /**
     * Retrieves the number of rows in this location
     * 
     * @return      the number of rows
     */
    public int getRowCount() {
        return units.length;
    }

    /**
     * Retrieves the number of units in each row
     * 
     * @param   rowIdx      the row on which method retrieves the number of units 
     * @return              the number of units in a row
     */
    public int getUnitsPerRowCount(int rowIdx) {
        if (rowIdx < 0 || rowIdx >= units.length) {
            throw new IllegalArgumentException("Index is out of bound.");
        }
        return units[rowIdx].length;
    }

    /**
     * Retrieves a specific storage unit from the unit array
     *
     * @param   rowIdx      the row on which the unit sits
     * @param   spaceIdx    the space the unit occupies within the row
     * @return              the requested unit
     */
    public StorageUnit getStorageUnit(int rowIdx, int spaceIdx) {
        if (rowIdx < 0 || rowIdx >= units.length || spaceIdx < 0 || spaceIdx >= units[rowIdx].length) {
            throw new IllegalArgumentException("Index is out of bound.");
        }
        return units[rowIdx][spaceIdx];
    }

    /**
     * Retrieves a specific customer from the customer array
     *
     * @param   custIdx     the index of the desired customer
     * @return              the specified customer
     */
    public Customer getCustomer(int custIdx) {
        if (custIdx < 0 || custIdx >= customerCount) {
            throw new IllegalArgumentException("Index is out of bound.");
        }
        return customers[custIdx];
    }

    /**
     * Counts the number of customers currently in the array
     *
     * @return      the current customer count
     */
    public int getCustomerCount() {
        return customerCount;
    }

    //---------------------------------------------------------------------
    //          OTHER METHODS
    //---------------------------------------------------------------------
    /**
     * Adds a customer to the customer array
     *
     * @param   customer    the customer to add; must not be null
     * @return              the index at which the customer was added
     */
    public int addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer reference must not be null");
        }
        customers[customerCount] = customer;
        return customerCount++;
    }

    /**
     * Searches for units that are rented by a specific customer, returning them in an array
     *
     * @param       customer    the customer whose units are of interest; must not be null
     * @return                  an array of storage units belonging to that customer
     */
    public StorageUnit[] getCustomerUnits(Customer customer) {
        if (customer == null) {
            return null;
        }

        // Count the units
        int unitCount = 0;
        for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
            for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                if (units[rowIdx][spaceIdx].getCustomer() != null && units[rowIdx][spaceIdx].getCustomer() == customer) {
                    unitCount++;
                }
            }
        }

        // Create the array
        StorageUnit[] custUnits = new StorageUnit[unitCount];
        if (unitCount > 0) {
            // Fill the array
            int unitIdx = 0;
            for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
                for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                    if (units[rowIdx][spaceIdx].getCustomer() != null && units[rowIdx][spaceIdx].getCustomer() == customer) {
                        custUnits[unitIdx] = units[rowIdx][spaceIdx];
                        unitIdx++;
                    }
                }
            }
        }
        return custUnits;
    }

    /**
     * Returns an array of all available storage units
     *
     * @return      an array of available storage units
     */
    public StorageUnit[] getEmptyUnits() {
        return getEmptyUnits(null);
    }

    /**
     * Returns an array of all available storage units of the specified type
     *
     * @param   unitType    the type of units for which to search; pass null for wildcard (any type of unit)
     * @return              an array of available storage units of the specified type
     */
    public StorageUnit[] getEmptyUnits(Class<? extends StorageUnit> soughtClass) {
        // count the units
        int unitCount = 0;
        for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
            for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                if (soughtClass != null) {
                    // looking for a specific type of unit
                    if (units[rowIdx][spaceIdx].getClass() == soughtClass && units[rowIdx][spaceIdx].getCustomer() == null) {
                        unitCount++;
                    }
                } else {
                    // no type of unit sought
                    if (units[rowIdx][spaceIdx].getCustomer() == null) {
                        unitCount++;
                    }
                }
            }
        }

        // create the array
        StorageUnit[] emptyUnits = new StorageUnit[unitCount];

        if (unitCount > 0) {
            int unitIdx = 0;
            for (int rowIdx = 0; rowIdx < units.length; rowIdx++) {
                for (int spaceIdx = 0; spaceIdx < units[rowIdx].length; spaceIdx++) {
                    if (soughtClass != null) {
                        // looking for a specific type of unit
                        if (units[rowIdx][spaceIdx].getClass() == soughtClass && units[rowIdx][spaceIdx].getCustomer() == null) {
                            emptyUnits[unitIdx] = units[rowIdx][spaceIdx];
                            unitIdx++;
                        }
                    } else {
                        // no type of unit sought
                        if (units[rowIdx][spaceIdx].getCustomer() == null) {
                            emptyUnits[unitIdx] = units[rowIdx][spaceIdx];
                            unitIdx++;
                        }
                    }
                }
            }
        }

        // return the array
        return emptyUnits;
    }

    /**
     * Charges all customers their monthly rent
     *
     * @return      the total amount of rent charged to all customers
     */
    public double chargeMonthlyRent() {
        double totalRentCharged = 0.00;
        for( int idxCust = 0; idxCust < customerCount; idxCust++) {
            StorageUnit [] storCustUnits = getCustomerUnits(customers[idxCust]);
            Double unitsRent = 0.0;
            for(int i = 0; i < storCustUnits.length; i++){
                unitsRent += storCustUnits[i].getPrice();
            }
            if (storCustUnits.length > 1) {
                unitsRent = unitsRent - (unitsRent * getMultiUnitDiscount());
                unitsRent = Math.round(unitsRent * 20) / 20.0;
            }
            getCustomer(idxCust).charge(unitsRent);
            totalRentCharged += unitsRent;
        }
        return totalRentCharged;
    }
    
    /**
     * Retrieves    base unit storage price per month
     *
     * @return      base unit storage price per month
     */
    public double getUnitBasePrice(){
        return basePrice;
    }
    
    /**
     * Changes base unit storage price per month 
     *
     * @return      changed base unit storage price per month
     */
    public void setUnitBasePrice(double basePrice){
        if (basePrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.basePrice = basePrice;
    }
    
    /**
     * Shows table of rented and not rented units, with symbols for Standart{"S"), 
     * Humidity("H"), Temperature("T"). 
     *
     * @return      changed base unit storage price per month
     */
    public String unitMap() {
        String str = "";
        //creating the header with title of the table
        for (int i = 0; i < SCREEN_WIDTH; i++) {
            str += "-";
        }
        str += "\n";
        String title = "Unit Map for Location " + locationDesignation;
        int lenOfTitle = title.length();
        //make title be presented centered in the header
        int space = (SCREEN_WIDTH - lenOfTitle) / 2;
        for (int i = 0; i < space; i++){
            str += " ";
        }
        str += title + "\n";
        for (int i = 0; i < SCREEN_WIDTH; i++) {
            str += "-";
        }
        str += "\n\n     ";
        for (int i = 0; i < SPACES_IN_STD_ROW; i++){
            str += i + "    ";
        }
        str += "\n\n";
        //creating the table for Standart{"S"), Humidity("H"), Temperature("T"). 
        for (int idxRow = 0; idxRow < units.length; idxRow++) {
            str += String.format("%02d:  ", idxRow);
            String sign = "";
            if (idxRow >= ROW_START_TMP) {
                sign = "T";
                for (int idxSpace = 0; idxSpace < units [idxRow].length; idxSpace++){
                    if (units [idxRow][idxSpace].isRented()) {
                        TempStorageUnit unit = (TempStorageUnit)units[idxRow][idxSpace];
                        str += sign + unit.getTempLevel() + "  ";
                    }else{
                        str += sign + "__  ";
                    }
                }
                str += "\n";
            } else if (idxRow >= ROW_START_HUM) {
                sign = "H";
                for (int idxSpace = 0; idxSpace < units [idxRow].length; idxSpace++){
                    if (units [idxRow][idxSpace].isRented()) {
                        HumidStorageUnit unit = (HumidStorageUnit)units[idxRow][idxSpace];
                        str += sign + unit.getHumLevel() + "  ";
                    }else{
                        str += sign + "__  ";
                    }
                }
                str += "\n";
            } else {
                sign = "S";
                for (int idxSpace = 0; idxSpace < units [idxRow].length; idxSpace++){
                    if (units [idxRow][idxSpace].isRented()) {
                        str += sign + "*   ";
                    }else{
                        str += sign + "__  ";
                    }
                }
                str += "\n";
            }
        }
        return str;
    }

    /**
     * Retrieves    5-% discount for multi-renters 
     *
     * @return      5-% discount for multi-renters
     */
    public double getMultiUnitDiscount(){
        return DISCOUNT;
    }
}