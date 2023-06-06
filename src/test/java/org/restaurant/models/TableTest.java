package org.restaurant.models;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {


    @Test
    public void testAssignCustomerToAvailableTable() {
        // Create a table
        Table table = new Table(1, 4);

        // Initially, the table should be available
        assertEquals(Table.TableStatus.AVAILABLE, table.getStatus());

        // Assign a customer to the table
        table.assignCustomer();

        // After assigning a customer, the status should be OCCUPIED
        assertEquals(Table.TableStatus.OCCUPIED, table.getStatus());
    }

    @Test
    public void testAssignCustomerToOccupiedTable() {
        // Create a table
        Table table = new Table(1, 4);

        // Assign the table to an initial customer
        table.setStatus(Table.TableStatus.OCCUPIED);

        // Try to assign another customer to the same table
        table.assignCustomer();

        // The status should still be OCCUPIED and cannot be assigned again
        assertEquals(Table.TableStatus.OCCUPIED, table.getStatus());
    }
}

