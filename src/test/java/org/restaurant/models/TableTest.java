package org.restaurant.models;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.restaurant.models.Table.assignTableByPartySize;
import static org.restaurant.models.Table.reserveTableByPartySize;

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

    @Test
    public void testAssignTableByPartySize() {
        // Create an array of tables
        Table[] tables = new Table[3];
        tables[0] = new Table(1, 2);
        tables[1] = new Table(2, 4);
        tables[2] = new Table(3, 6);

        // Assign a table with party size 3
        assignTableByPartySize(tables, 3);

        // Check if a table has been assigned
        boolean tableAssigned = false;
        for (Table table : tables) {
            if (table.getStatus() == Table.TableStatus.OCCUPIED) {
                tableAssigned = true;
                break;
            }
        }

        assertTrue(tableAssigned, "A table should have been assigned.");
    }

    @Test
    public void testReserveTableByPartySize() {
        // Create an array of tables
        Table[] tables = new Table[3];
        tables[0] = new Table(1, 2);
        tables[1] = new Table(2, 4);
        tables[2] = new Table(3, 6);

        // Reserve a table with party size 3
        Table reservedTable = reserveTableByPartySize(tables, 3);

        // Check if a table has been reserved
        assertNotNull(reservedTable, "A table should have been reserved.");
        assertEquals(Table.TableStatus.RESERVED, reservedTable.getStatus(), "The reserved table should have status 'RESERVED'.");
    }

    @Test
    public void testMakeAvailable() {
        Table table = new Table(1, 4);

        // Initially, the table is available
        assertEquals(Table.TableStatus.AVAILABLE, table.getStatus());

        // Making the table available again should not change its status
        String output1 = getConsoleOutput(table::makeAvailable);
        assertEquals(Table.TableStatus.AVAILABLE, table.getStatus());
        assertEquals("Table 1 is already available.", output1);

        // Occupying the table
        table.assignCustomer();
        assertEquals(Table.TableStatus.OCCUPIED, table.getStatus());

        // Making the table available should change its status
        String output2 = getConsoleOutput(table::makeAvailable);
        assertEquals(Table.TableStatus.AVAILABLE, table.getStatus());
        assertEquals("Table 1 is now available.", output2);
    }

    // Helper method to capture console output
    private String getConsoleOutput(Runnable action) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);

        // Call the method that prints to the console
        action.run();

        System.setOut(originalOut);
        return outputStream.toString().trim();
    }



}

