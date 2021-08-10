package storage;

/**
 * Requirements for the StorageLocation class
 *
 * @author      Bill Barry
 * @version     2020-04-30
 */
public interface StorageLocationInterface {
    
    public String getDesignation();
    public int getRowCount();
    public int getUnitsPerRowCount(int rowIdx);
    public StorageUnit getStorageUnit(int rowIdx, int spaceIdx);
    public Customer getCustomer(int custIdx);
    public int getCustomerCount();
    public int addCustomer(Customer customer);
    public StorageUnit[] getCustomerUnits(Customer customer);
    public StorageUnit[] getEmptyUnits();
    public StorageUnit[] getEmptyUnits(Class<? extends StorageUnit> soughtClass);
    public double chargeMonthlyRent();
    public double getUnitBasePrice();
    public double getMultiUnitDiscount();

}
