import java.util.*;

public class EmployeeSortingSystem {
    // Abstract Employee class remains the same as in the original Employee.java
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

        public abstract void displayMenu();

        public String getName() { return name; }
        public String getSurname() { return surname; }
        public String getPhoneNo() { return phoneNo; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public int getEmployeeId() { return employeeId; }

        public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }

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

        public void displayProfile() {
            System.out.println("Name: " + name + " " + surname);
            System.out.println("Phone number: " + phoneNo);
            System.out.println("Email: " + email);
        }

        public void displayNonProfile() {
            System.out.println("Employee ID: " + employeeId);
            System.out.println("Date of Birth: " + dateOfBirth);
            System.out.println("Date of Start: " + dateOfStart);
        }
    }

    // Sorting Performance class with all original sorting methods
    public static class SortingPerformance {
        private int[] originalArray;
        
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
        
        private int[] generateRandomArray(int size) {
            Random rand = new Random();
            return rand.ints(size, -10000, 10001).toArray();
        }
        
        private long measureSortTime(int[] array, Runnable sortMethod) {
            long startTime = System.nanoTime();
            sortMethod.run();
            return System.nanoTime() - startTime;
        }
        
        private void radixSort(int[] arr) {
            if (arr == null || arr.length == 0) return;
            
            int max = Arrays.stream(arr).map(Math::abs).max().getAsInt();
            
            for (int exp = 1; max / exp > 0; exp *= 10) {
                countingSort(arr, exp);
            }
        }
        
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
        
        private void collectionSort(int[] arr) {
            Integer[] boxedArray = Arrays.stream(arr).boxed().toArray(Integer[]::new);
            Arrays.sort(boxedArray);
            for (int i = 0; i < arr.length; i++) {
                arr[i] = boxedArray[i];
            }
        }
        
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
