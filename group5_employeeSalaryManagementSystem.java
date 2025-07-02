import java.util.Scanner;

public class group5_employeeSalaryManagementSystem {

    static String[] names = new String[3]; // The static here can be accessed by any methods. This is the same concepts as the global keyword for variable in Python
    static int[] ids = new int[10];
    static double[] salaries = new double[3];
    static double[] bonuses = new double[3];
    static double[] salariesAfterBonuses = new double[3];
    static int count = 0;

    static Scanner input = new Scanner(System.in);
    
    public static void title(String text) {
            System.out.println();
            System.out.printf("========================= %s =========================\n", text);
    }

    public static boolean isValidName(String name) {
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c!= ' ') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int choice = 0;

        while(choice != 5){
            System.out.println();
            System.out.println("===============================================================");
            System.out.printf("%60s\n", "Welcome to the Main Menu. Select One of the Options Below");
            System.out.println("===============================================================\n");
        
            System.out.println("""
                1. Add Employee Details
                2. Calculate Salary After Bonus
                3. Display Employee Records
                4. Search For An Employee By ID
                5. Exit
                """);
    
        /* Prompt for user to pick option */
            System.out.print("Enter Option (1-5) >> ");
            choice = input.nextInt();
            input.nextLine(); // Clears leftover newline before any nextLine()
            
            switch (choice) {
                case 1:
                    addEmployeeDetails();
                    break;
                case 2:
                    calculateSalary();
                    break;
                case 3:
                    showEmployeeDetails();
                    break;
                case 4:
                    searchEmployee();
                    break;
                case 5:
                    System.out.println("Exiting Program . . .");
                    System.exit(1);
                default:
                    System.out.println("Invalid Option. Please Choose Options Between 1 to 5.");
            }
        }

    }

    public static void addEmployeeDetails() {
        title("Add Employee Details");
        
        // Check if Employee Slot are Full
        if(count >= 3) {
            System.out.println("No more space for more Employees.");
            return;
        }

        /* Get Employee Name */
        String name = "";
        while (true) {
            System.out.print("Enter the Employee's Name >> ");
            name = input.nextLine();

            // Checking for empty input
            if (name.isEmpty()) {
                System.out.println("Name cannot be Empty. Please Try Again.");
                continue;
            }

            // Checking if Name is purely characters using another method isValidName
            if (isValidName(name)) {
                break;
            } else {
                System.out.println("Invalid Name. Can only Contain letters and spaces. Please try again.");
            }
        }

        System.out.println("* Name is entered into the record.");
        

        /* Get Employee ID */
        int id;
        while (true) {
            System.out.print("Enter the Employee's ID >> ");

            // Get ID as string
            String line = input.nextLine().trim(); // read line and get rid of spaces

            // Check if line is empty
            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a valid Integer.");
                continue; // back to start of while loop
            }

            /* Validate Integer Input */
            try {
                // Convert String ID to Integer ID
                id = Integer.parseInt(line);

                // Check if id < 0
                if (id < 0) {
                    System.out.println("ID cannot be negative. Please try again.");
                    continue; // back to start of while loop
                }

                // check if ID already exists
                boolean exists = false;
                for (int i = 0; i < count; i++) {
                    if (ids[i] == id) {
                        exists = true;
                        break;
                    }  
                }

                if (exists) {
                    System.out.println("This ID already exists. Please try again.");
                    continue;
                } 
            
                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid integer.");
            }
        }
        
        /* Get Employee Salary */
        double salary = 0;
        while (true) {
            System.out.print("Enter the Employee's Salary >> ");
            
            // Get ID as string
            String line = input.nextLine().trim(); // read line and get rid of spaces

            // Check if line is empty
            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a valid Integer.");
                continue; // back to start of while loop
            }

            try {
                // Convert String salary to Double salary
                salary = Double.parseDouble(line);

                // Check if id < 0
                if (salary < 0) {
                    System.out.println("ID cannot be negative. Please try again.");
                    continue; // back to start of while loop
                }
                break; //exit loop because valid salary
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid number.");
            }
        }
        

        // Find the correct position to insert based on ID
        int insertPos = count;
        for (int i = 0; i < count; i++) {
            if (id < ids[i]) {
                insertPos = i;
                break;
            }
        }

        // Shift records to make space for the new employee
        for (int i = count; i > insertPos; i--) {
            ids[i] = ids[i - 1];
            names[i] = names[i - 1];
            salaries[i] = salaries[i - 1];
            bonuses[i] = bonuses[i - 1];
            salariesAfterBonuses[i] = salariesAfterBonuses[i - 1];
        }

        // Insert new employee at the correct position
        ids[insertPos] = id;
        names[insertPos] = name;
        salaries[insertPos] = salary;
        bonuses[insertPos] = 0;
        salariesAfterBonuses[insertPos] = salary;

        count++;
    }


    public static void showEmployeeDetails() {
        title("Employee Records");
        if (count == 0) {
            System.out.println("No Employees are added yet . . .");
            return;
        }

        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "ID", "Names", "Salaries", "Bonuses", "Salaries After Bonus");

        /*
        %-10s means
        - means string left-aligned
        10s = 10 space
         */

        // Printing records
        for(int i=0;i<count;i++){    // This is to iterate the array and print the entire table
            System.out.printf("%-10s %-10s %-10.2f %-10.2f %-10.2f\n",ids[i], names[i],salaries[i], bonuses[i],salariesAfterBonuses[i]);
        }   
    }

    public static void calculateSalary() {
        title("Apply Bonus");

        if(count == 0){
            System.out.println("No Employees are added yet...");
            return;
        }

        int bonusId;
        while (true) {
            System.out.print("Enter Search ID to give bonus >> ");
            
            // Get ID as string
            String line = input.nextLine().trim(); // read line and get rid of spaces

            // Check if line is empty
            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a valid Integer.");
                continue; // back to start of while loop
            }

            /* Validate Integer Input */
            try {
                // Convert String ID to Integer ID
                bonusId = Integer.parseInt(line);

                // Check if id < 0
                if (bonusId < 0) {
                    System.out.println("ID cannot be negative. Please try again.");
                    continue; // back to start of while loop
                }
                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid integer.");
            }
        }

        boolean found = false;

        for(int z = 0; z < count; z++){
            if(ids[z] == bonusId){
                found = true;
                System.out.println("===========================================");
                System.out.print("What is the performance of employee ID: "+ bonusId+" on a scale of 1-5: ");
                int bonusPerformanceScale = input.nextInt();
                input.nextLine(); //Clear buffer for the if statement

                double bonusPercentage = 0;
                if(bonusPerformanceScale == 5){
                    bonusPercentage = 20;
                }
                else if(bonusPerformanceScale == 4){
                    bonusPercentage = 15;
                }
                else if(bonusPerformanceScale == 3){
                    bonusPercentage = 10;
                }
                else{
                    bonusPercentage = 0;
                }
                double oldSalary = salaries[z];
                double bonusAmountInCash = oldSalary * (bonusPercentage / 100);
                salariesAfterBonuses[z] = oldSalary + bonusAmountInCash; // Update salaries after the bonus
                bonuses[z] = bonusAmountInCash; // Update bonus

                System.out.println("Bonus Applied to Employee ID: "+bonusId+" Successfully.");
                System.out.println("Bonus Received "+bonusPercentage+"%");
                System.out.printf("Old Salary: $%.2f%n", oldSalary);
                System.out.printf("New Salary: $%.2f%n", salariesAfterBonuses[z]);
            }
            
        }
        
        if (!found) {
            System.out.println("Employee Not Found. Please Enter a Valid ID");
        }
    }

    public static void searchEmployee() {
        title("Search Employee");
        if (count == 0) {
            System.out.println("No employees in the system.");
            return;
        }

        System.out.print("Enter employee ID to search: ");
        int searchId = input.nextInt();
        input.nextLine(); // consume newline

        for (int i = 0; i < count; i++) {
            if (ids[i] == searchId) {
                System.out.println("Employee Found:");
                System.out.printf("ID: %d\nName: %s\nSalary: %.2f\nBonus: %.2f\nSalary After Bonus: %.2f\n",
                    ids[i], names[i], salaries[i], bonuses[i], salariesAfterBonuses[i]);
                return;
            }
        }
        System.out.println("Employee with ID " + searchId + " not found.");
    }


}