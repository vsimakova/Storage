package storage;

import java.time.LocalDate;

/**
 * Demo for Stanley's Storage 
 *
 * @author      Bill Barry
 * @version     2020-06-23
 * @author      Viktoryia Simakova
 * @version     2020-07-07
 */
public class StorageApplication {

    public static void main(String[] args) {
            
        // Set up location and some sample customers
        StorageLocation loc1 = new StorageLocation("WA23Issaquah", 100.0);
        loc1.addCustomer(new Customer("Pat Perkins", "425-555-1314"));
        loc1.addCustomer(new Customer("Chris Connoly", "425-555-3141"));
        //show state
        loc1.toString();
        
        // Display basic information about the location
        System.out.print('\u000c');
        System.out.printf("Storage Location : %s\n",  loc1.getDesignation());
        System.out.printf("Customer count   : %3d\n", loc1.getCustomerCount());
        System.out.printf("Empty unit count : %3d\n", loc1.getEmptyUnits().length);
        
        // Rent some units and display some unit info
        System.out.println("\nRenting three units to Pat Perkins");        
        Customer pat = loc1.getCustomer(0);
        loc1.getStorageUnit(1, 1).rent(pat, LocalDate.now());
        loc1.getStorageUnit(8, 2).rent(pat, LocalDate.now());
        loc1.getStorageUnit(11, 3).rent(pat, LocalDate.now());

        System.out.println("\nRenting three units to Chris Connoly");        
        Customer chris = loc1.getCustomer(1);
        loc1.getStorageUnit(11, 1).rent(chris, LocalDate.now());
        loc1.getStorageUnit(11, 2).rent(chris, LocalDate.now());
        loc1.getStorageUnit(11, 5).rent(chris, LocalDate.now());
        System.out.println();
        
        System.out.printf("Empty count                  : %3d\n", loc1.getEmptyUnits().length);
        System.out.printf("Pat's unit count             : %3d\n", loc1.getCustomerUnits(loc1.getCustomer(0)).length);
        System.out.printf("Chris's unit count           : %3d\n", loc1.getCustomerUnits(loc1.getCustomer(1)).length);
        System.out.printf("Empty standard unit count    : %3d\n", loc1.getEmptyUnits(StdStorageUnit.class).length);
        System.out.printf("Empty humidity unit count    : %3d\n", loc1.getEmptyUnits(HumidStorageUnit.class).length);
        System.out.printf("Empty temperature unit count : %3d\n", loc1.getEmptyUnits(TempStorageUnit.class).length);
        
        System.out.println("\nShowing storage units, rented and unrented");        
        System.out.println(loc1.getStorageUnit(1, 5));
        System.out.println(loc1.getStorageUnit(11, 5));
        System.out.println();
        
        // Demonstrate charging of rent
        System.out.printf("Pat's balance before charging monthly rent   :  $%,7.2f\n", pat.getBalance());
        System.out.printf("Chris's balance before charging monthly rent :  $%,7.2f\n", chris.getBalance());
        double total = loc1.chargeMonthlyRent();
        System.out.printf("Pat's balance after charging monthly rent    :  $%,7.2f\n", pat.getBalance()); 
        System.out.printf("Chris's balance after charging monthly rent  :  $%,7.2f\n", chris.getBalance());
        System.out.printf("Total rent charged for all units             :  $%,7.2f\n", total);
        //show unit map
        System.out.println(loc1.unitMap());
    }

}
