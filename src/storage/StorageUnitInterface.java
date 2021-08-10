package storage;

import java.time.LocalDate;

/**
* Requirements for the StorageUnit class
*
* @author      Bill Barry
* @version     2020-04-30
*/
public interface StorageUnitInterface {
   
   public int getWidth();
   public int getLength();
   public int getHeight();
   public double getPrice();
   public Customer getCustomer();
   public boolean isRented();
   public LocalDate getRentalStart();
   public StorageLocation getStorageLocation();
   public boolean rent(Customer customer, LocalDate rentalStart);
   public boolean release();
   public String toString();
   public double calcUnitSpecificPrice();

}
