package org.restaurant.models;

import java.util.Scanner;
public class Table {

    private final int tableID;
    private final int partySize;
    private TableStatus status;

    public enum TableStatus {
        AVAILABLE("\u001B[32mAvailable\u001B[0m"),
        RESERVED("\u001B[31mReserved\u001B[0m"),
        OCCUPIED("\u001B[31mOccupied\u001B[0m");

        private final String displayValue;

        TableStatus(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public Table(int tableID, int partySize) {
        this.tableID = tableID;
        this.partySize = partySize;
        this.status = TableStatus.AVAILABLE;
    }

    public int getTableID() {
        return tableID;
    }

    public int getPartySize() {
        return partySize;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public void assignCustomer() {
        if (status == TableStatus.AVAILABLE) {
            status = TableStatus.OCCUPIED;
            System.out.println("Table " + tableID + " has been assigned to a customer and is now occupied.");
        } else {
            System.out.println("Table " + tableID + " is currently " + status + " and cannot be assigned.");
        }
    }

    public void reserveTable() {
        if (status == TableStatus.AVAILABLE) {
            status = TableStatus.RESERVED;
            System.out.println("Table " + tableID + " has been reserved.");
        } else {
            System.out.println("Table " + tableID + " is currently " + status + " and cannot be reserved.");
        }
    }

    public void makeAvailable() {
        if (status != TableStatus.AVAILABLE) {
            status = TableStatus.AVAILABLE;
            System.out.println("Table " + tableID + " is now available.");
        } else {
            System.out.println("Table " + tableID + " is already available.");
        }
    }

    @Override
    public String toString() {
        return "Table ID: " + tableID +
                "\nParty Size: " + partySize +
                "\nStatus: " + status.getDisplayValue() + "\n";
    }

    public static void main(String[] args) {
        Table[] tables = createTables(); // Create four tables with different party sizes

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----- MENU -----");
            System.out.println("1. Assign a table");
            System.out.println("2. Reserve a table");
            System.out.println("3. Make a table available");
            System.out.println("4. Print table list");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Exiting...");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter party size: ");
                    int partySize = scanner.nextInt();
                    assignTableByPartySize(tables, partySize);
                    break;
                case 2:
                    System.out.print("Enter party size for reservation: ");
                    int reservationSize = scanner.nextInt();
                    Table reservedTable = reserveTableByPartySize(tables, reservationSize);
                    if (reservedTable != null) {
                        System.out.println("Reserved Table: " + reservedTable.toString());
                    } else {
                        System.out.println("No available table for the specified reservation size.");
                    }
                    break;
                case 3:
                    System.out.print("Enter table ID to make available: ");
                    int availableTableID = scanner.nextInt();
                    makeTableAvailable(tables, availableTableID);
                    break;
                case 4:
                    printTableListWithColors(tables);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }
    }

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

    public static void makeTableAvailable(Table[] tables, int tableID) {
        for (Table table : tables) {
            if (table.getTableID() == tableID) {
                table.makeAvailable();
                return;
            }
        }
        System.out.println("Table " + tableID + " does not exist.");
    }

    public static void printTableListWithColors(Table[] tables) {
        for (Table table : tables) {
            System.out.println(table.toString());
        }
    }

    private static Table[] createTables() {
        Table[] tables = new Table[4];
        tables[0] = new Table(1, 2);
        tables[1] = new Table(2, 4);
        tables[2] = new Table(3, 5);
        tables[3] = new Table(4, 6);
        return tables;
    }
}


