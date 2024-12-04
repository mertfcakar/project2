import java.util.*;

/**
 * An employee management and sorting performance system that provides 
 * employee profile management and comparative sorting algorithm analysis.
 */
public class EmployeeSortingSystem {
    
    /**
     * An abstract base class representing an employee with core personal and 
     * professional information and basic profile management capabilities.
     */
    public abstract static class Employee {
        private int employeeId;
        private String username;
        private String name;
        private String surname;
        private String phoneNo;
        private String dateOfBirth;
        private String dateOfStart;
        private String email;
        private String password;

        /**
         * Constructs a new Employee with comprehensive personal details.
         * 
         * @param employeeId Unique identifier for the employee
         * @param username Login username for the employee
         * @param name First name of the employee
         * @param surname Last name of the employee
         * @param phoneNo Contact phone number
         * @param dateOfBirth Employee's date of birth
         * @param dateOfStart Date when employee started working
         * @param email Employee's email address
         * @param password Employee's login password
         */
        public Employee(int employeeId, String username, String name, String surname, String phoneNo,
                        String dateOfBirth, String dateOfStart, String email, String password) {
            this.employeeId = employeeId;
            this.username = username;
            this.name = name;
            this.surname = surname;
            this.phoneNo = phoneNo;
            this.dateOfBirth = dateOfBirth;
            this.dateOfStart = dateOfStart;
            this.email = email;
            this.password = password;
        }

        /**
         * Abstract method to display a menu specific to the employee type.
         * Must be implemented by subclasses.
         */
        public abstract void displayMenu();

        // Getter methods with comprehensive documentation
        
        /**
         * Retrieves the employee's first name.
         * @return First name of the employee
         */
        public String getName() { return name; }

        /**
         * Retrieves the employee's last name.
         * @return Last name of the employee
         */
        public String getSurname() { return surname; }

        /**
         * Retrieves the employee's phone number.
         * @return Contact phone number
         */
        public String getPhoneNo() { return phoneNo; }

        /**
         * Retrieves the employee's email address.
         * @return Email address
         */
        public String getEmail() { return email; }

        /**
         * Retrieves the employee's login password.
         * @return Login password (Note: In a real-world scenario, 
         * password should be handled more securely)
         */
        public String getPassword() { return password; }

        /**
         * Retrieves the employee's unique identifier.
         * @return Unique employee ID
         */
        public int getEmployeeId() { return employeeId; }

        // Setter methods with documentation

        /**
         * Updates the employee's phone number.
         * @param phoneNo New phone number to set
         */
        public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

        /**
         * Updates the employee's email address.
         * @param email New email address to set
         */
        public void setEmail(String email) { this.email = email; }

        /**
         * Updates the employee's login password.
         * @param password New password to set
         */
        public void setPassword(String password) { this.password = password; }

        /**
         * Interactively updates the employee's profile information.
         * Prompts the user to enter new phone number, email, and password.
         */
        public void updateProfile() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter new phone number: ");
            String newPhoneNo = scanner.nextLine();
            System.out.print("Enter new email address: ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            setPhoneNo(newPhoneNo);
            setEmail(newEmail);
            setPassword(newPassword);

            System.out.println("Profile updated successfully!");
        }

        /**
         * Displays the employee's basic profile information.
         * Shows name, phone number, and email.
         */
        public void displayProfile() {
            System.out.println("Name: " + name + " " + surname);
            System.out.println("Phone number: " + phoneNo);
            System.out.println("Email: " + email);
        }

        /**
         * Displays non-profile related employee information.
         * Shows employee ID, date of birth, and start date.
         */
        public void displayNonProfile() {
            System.out.println("Employee ID: " + employeeId);
            System.out.println("Date of Birth: " + dateOfBirth);
            System.out.println("Date of Start: " + dateOfStart);
        }
    }

    /**
     * A utility class for comparing the performance of various sorting algorithms.
     * Implements multiple sorting techniques and provides performance measurement.
     */
    public static class SortingPerformance {
        private int[] originalArray;
        
        /**
         * Runs a comprehensive comparison of sorting algorithms.
         * 
         * @param datasetSize Number of elements to sort (must be between 1,000 and 10,000)
         * @throws IllegalArgumentException if dataset size is out of valid range
         */
        public void runSortingAlgorithmsComparison(int datasetSize) {
            // Validate input
            if (datasetSize < 1000 || datasetSize > 10000) {
                throw new IllegalArgumentException("Dataset size must be between 1,000 and 10,000");
            }
            
            // Generate random array
            originalArray = generateRandomArray(datasetSize);
            
            // Create copies for each sorting algorithm
            int[] radixArray = originalArray.clone();
            int[] shellArray = originalArray.clone();
            int[] heapArray = originalArray.clone();
            int[] insertionArray = originalArray.clone();
            int[] collectionsArray = originalArray.clone();
            
            // Measure and sort
            long radixTime = measureSortTime(radixArray, this::radixSort);
            long shellTime = measureSortTime(shellArray, this::shellSort);
            long heapTime = measureSortTime(heapArray, this::heapSort);
            long insertionTime = measureSortTime(insertionArray, this::insertionSort);
            long collectionsTime = measureSortTime(collectionsArray, this::collectionSort);
            
            // Verify correctness
            boolean radixCorrect = Arrays.equals(collectionsArray, radixArray);
            boolean shellCorrect = Arrays.equals(collectionsArray, shellArray);
            boolean heapCorrect = Arrays.equals(collectionsArray, heapArray);
            boolean insertionCorrect = Arrays.equals(collectionsArray, insertionArray);
            
            // Display results
            displayResults(radixTime, shellTime, heapTime, insertionTime, collectionsTime,
                           radixCorrect, shellCorrect, heapCorrect, insertionCorrect);
        }
        
        /**
         * Generates an array of random integers.
         * 
         * @param size Number of elements in the array
         * @return Array of random integers between -10000 and 10000
         */
        private int[] generateRandomArray(int size) {
            Random rand = new Random();
            return rand.ints(size, -10000, 10001).toArray();
        }
        
        /**
         * Measures the execution time of a sorting method.
         * 
         * @param array Array to be sorted
         * @param sortMethod Sorting method to execute
         * @return Execution time in nanoseconds
         */
        private long measureSortTime(int[] array, Runnable sortMethod) {
            long startTime = System.nanoTime();
            sortMethod.run();
            return System.nanoTime() - startTime;
        }
        
        /**
         * Implements Radix Sort algorithm for non-negative and negative integers.
         * 
         * @param arr Array to be sorted
         */
        private void radixSort(int[] arr) {
            if (arr == null || arr.length == 0) return;
            
            int max = Arrays.stream(arr).map(Math::abs).max().getAsInt();
            
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countingSort(arr, exp);
            }
        }
        
        /**
         * Helper method for Radix Sort implementing counting sort for a specific digit.
         * 
         * @param arr Array to be sorted
         * @param exp Current digit/place value being sorted
         */
        private void countingSort(int[] arr, int exp) {
            int n = arr.length;
            int[] output = new int[n];
            int[] count = new int[19];
            
            for (int i = 0; i < n; i++) {
                int bucket = (arr[i] / exp) % 10 + 9;
                count[bucket]++;
            }
            
            for (int i = 1; i < 19; i++) {
                count[i] += count[i - 1];
            }
            
            for (int i = n - 1; i >= 0; i--) {
                int bucket = (arr[i] / exp) % 10 + 9;
                output[count[bucket] - 1] = arr[i];
                count[bucket]--;
            }
            
            System.arraycopy(output, 0, arr, 0, n);
        }
        
        /**
         * Implements Shell Sort algorithm for sorting arrays.
         * 
         * @param arr Array to be sorted
         */
        private void shellSort(int[] arr) {
            int n = arr.length;
            for (int gap = n/2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    int temp = arr[i];
                    int j;
                    for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                        arr[j] = arr[j - gap];
                    }
                    arr[j] = temp;
                }
            }
        }
        
        /**
         * Implements Heap Sort algorithm for sorting arrays.
         * 
         * @param arr Array to be sorted
         */
        private void heapSort(int[] arr) {
            int n = arr.length;
            
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(arr, n, i);
            }
            
            for (int i = n - 1; i > 0; i--) {
                int temp = arr[0];
                arr[0] = arr[i];
                arr[i] = temp;
                
                heapify(arr, i, 0);
            }
        }
        
        /**
         * Heapifies a subtree rooted at index i.
         * 
         * @param arr Array containing the heap
         * @param n Size of the heap
         * @param i Root index of the subtree
         */
        private void heapify(int[] arr, int n, int i) {
            int largest = i;
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            
            if (l < n && arr[l] > arr[largest])
                largest = l;
            
            if (r < n && arr[r] > arr[largest])
                largest = r;
            
            if (largest != i) {
                int swap = arr[i];
                arr[i] = arr[largest];
                arr[largest] = swap;
                
                heapify(arr, n, largest);
            }
        }
        
        /**
         * Implements Insertion Sort algorithm for sorting arrays.
         * 
         * @param arr Array to be sorted
         */
        private void insertionSort(int[] arr) {
            int n = arr.length;
            for (int i = 1; i < n; ++i) {
                int key = arr[i];
                int j = i - 1;
                
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j = j - 1;
                }
                arr[j + 1] = key;
            }
        }
        
        /**
         * Uses Java's built-in Collections.sort() method for sorting.
         * 
         * @param arr Array to be sorted
         */
        private void collectionSort(int[] arr) {
            Integer[] boxedArray = Arrays.stream(arr).boxed().toArray(Integer[]::new);
            Arrays.sort(boxedArray);
            for (int i = 0; i < arr.length; i++) {
                arr[i] = boxedArray[i];
            }
        }
        
        /**
         * Displays the performance results of different sorting algorithms.
         * 
         * @param radixTime Execution time for Radix Sort
         * @param shellTime Execution time for Shell Sort
         * @param heapTime Execution time for Heap Sort
         * @param insertionTime Execution time for Insertion Sort
         * @param collectionsTime Execution time for Collections Sort
         * @param radixCorrect Indicates if Radix Sort produced correct results
         * @param shellCorrect Indicates if Shell Sort produced correct results
         * @param heapCorrect Indicates if Heap Sort produced correct results
         * @param insertionCorrect Indicates if Insertion Sort produced correct results
         */
        private void displayResults(long radixTime, long shellTime, long heapTime, 
                                    long insertionTime, long collectionsTime,
                                    boolean radixCorrect, boolean shellCorrect, 
                                    boolean heapCorrect, boolean insertionCorrect) {
            System.out.println("Sorting Algorithm Performance:");
            System.out.printf("Radix Sort: %d ns (Correct: %b)%n", radixTime, radixCorrect);
            System.out.printf("Shell Sort: %d ns (Correct: %b)%n", shellTime, shellCorrect);
            System.out.printf("Heap Sort: %d ns (Correct: %b)%n", heapTime, heapCorrect);
            System.out.printf("Insertion Sort: %d ns (Correct: %b)%n", insertionTime, insertionCorrect);
            System.out.printf("Collections Sort: %d ns%n", collectionsTime);
        }
    }
}
