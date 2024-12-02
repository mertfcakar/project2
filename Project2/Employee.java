import java.util.Scanner;

public abstract class Employee {
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
