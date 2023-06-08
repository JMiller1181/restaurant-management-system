package org.restaurant.models;


import java.util.Scanner;

public class Table {

    // Table attributes
    private final int tableID;
    private final int partySize;
    private TableStatus status;


    // Enum for table status
    public enum TableStatus {
        AVAILABLE("\u001B[32mAvailable\u001B[0m"),//color added
        RESERVED("\u001B[31mReserved\u001B[0m"),//color added
        OCCUPIED("\u001B[31mOccupied\u001B[0m");//color added


        private final String displayValue;


        TableStatus(String displayValue) {
            this.displayValue = displayValue;
        }


        public String getDisplayValue() {
            return displayValue;
        }
    }


    // Constructor for Table class
    public Table(int tableID, int partySize) {
        this.tableID = tableID;
        this.partySize = partySize;
        this.status = TableStatus.AVAILABLE;
    }


    // Getter methods
    public int getTableID() {
        return tableID;
    }


    public int getPartySize() {
        return partySize;
    }


    public TableStatus getStatus() {
        return status;
    }


    // Setter method for status
    public void setStatus(TableStatus status) {
        this.status = status;
    }


    // Method to assign a customer to the table
    public void assignCustomer() {
        if (status == TableStatus.AVAILABLE) {
            status = TableStatus.OCCUPIED;
            System.out.println("Table " + tableID + " has been assigned to a customer and is now occupied.");
        } else {
            System.out.println("Table " + tableID + " is currently " + status + " and cannot be assigned.");
        }
    }


    // Method to reserve the table
    public void reserveTable() {
        if (status == TableStatus.AVAILABLE) {
            status = TableStatus.RESERVED;
            System.out.println("Table " + tableID + " has been reserved.");
        } else {
            System.out.println("Table " + tableID + " is currently " + status + " and cannot be reserved.");
        }
    }


    // Method to make the table available
    public void makeAvailable() {
        if (status != TableStatus.AVAILABLE) {
            status = TableStatus.AVAILABLE;
            System.out.println("Table " + tableID + " is now available.");
        } else {
            System.out.println("Table " + tableID + " is already available.");
        }
    }


    // Override toString() method to provide a string representation of the table
    @Override
    public String toString() {
        return "Table ID: " + tableID +
                "\nParty Size: " + partySize +
                "\nStatus: " + status.getDisplayValue() + "\n";
    }

    // Method to assign a table based on party size
    public static void assignTableByPartySize(Table[] tables, int partySize) {
        Table assignedTable = null;
        int closestSizeDifference = Integer.MAX_VALUE;


        for (Table table : tables) {
            if (table.getStatus() == TableStatus.AVAILABLE && table.getPartySize() >= partySize) {
                int sizeDifference = table.getPartySize() - partySize;
                if (sizeDifference < closestSizeDifference) {
                    closestSizeDifference = sizeDifference;
                    assignedTable = table;
                }
            }
        }


        if (assignedTable != null) {
            assignedTable.assignCustomer();
            System.out.println("Please seat the customers at table " + assignedTable.getTableID() +
                    " with a party size of " + assignedTable.getPartySize() + ".");
        } else {
            System.out.println("No available table for the specified party size.");
        }
    }


    // Method to reserve a table based on reservation size
    public static Table reserveTableByPartySize(Table[] tables, int reservationSize) {
        Table reservedTable = null;
        int closestSizeDifference = Integer.MAX_VALUE;


        for (Table table : tables) {
            if (table.getStatus() == TableStatus.AVAILABLE && table.getPartySize() >= reservationSize) {
                int sizeDifference = table.getPartySize() - reservationSize;
                if (sizeDifference < closestSizeDifference) {
                    closestSizeDifference = sizeDifference;
                    reservedTable = table;
                }
            }
        }


        if (reservedTable != null) {
            reservedTable.reserveTable();
        }


        return reservedTable;
    }


    // Method to make a table available based on table ID
    public static void makeTableAvailable(Table[] tables, int tableID) {
        for (Table table : tables) {
            if (table.getTableID() == tableID) {
                table.makeAvailable();
                return;
            }
        }
        System.out.println("Table " + tableID + " does not exist.");
    }


    // Method to print the table list with colored status
    public static void printTableListWithColors(Table[] tables) {
        for (Table table : tables) {
            System.out.println(table.toString());
        }
    }


    // Method to create an array of tables
    public static Table[] createTables() {
        Table[] tables = new Table[4];
        tables[0] = new Table(1, 2);
        tables[1] = new Table(2, 4);
        tables[2] = new Table(3, 5);
        tables[3] = new Table(4, 6);
        return tables;
    }


    // Menu method to interact with the tables
    public static void handleTableMenu(Scanner scanner, Table[] tables) {

        while (true) {
// Print menu options
            System.out.println("----- TABLE MENU -----");
            System.out.println("1. Assign a table");
            System.out.println("2. Reserve a table");
            System.out.println("3. Make a table available");
            System.out.println("4. Print table list");
            System.out.println("0. Go back\n");


            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();


            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }

            switch (choice) {
                case 1://Option "1. Assign a table"
                    System.out.print("Enter party size: ");
                    int partySize = scanner.nextInt();
                    assignTableByPartySize(tables, partySize);
                    break;
                case 2://Option "2. Reserve a table"
                    System.out.print("Enter party size for reservation: ");
                    int reservationSize = scanner.nextInt();
                    Table reservedTable = reserveTableByPartySize(tables, reservationSize);
                    if (reservedTable != null) {
                        System.out.println("Reserved Table: " + reservedTable);
                    } else {
                        System.out.println("No available table for the specified reservation size.");
                    }
                    break;
                case 3: //Option "3. Make a table available"
                    System.out.print("Enter table ID to make available: ");
                    int availableTableID = scanner.nextInt();
                    makeTableAvailable(tables, availableTableID);
                    break;
                case 4: //Option "4. Print table list"
                    printTableListWithColors(tables);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }


            System.out.println();
        }
    }

}








