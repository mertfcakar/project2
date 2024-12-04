import java.util.List;

public class ManagerEmployee extends Employee {
    public ManagerEmployee(int employeeId, String username, String name, String surname, String phoneNo,
                           String dateOfBirth, String dateOfStart, String email, String password) {
        super(employeeId, username, name, surname, phoneNo, dateOfBirth, dateOfStart, email, password);
    }

    @Override
    public void displayMenu() {
        System.out.println("Manager Menu");
        System.out.println("1. Update Profile");
        System.out.println("2. Display All Employees");
        System.out.println("3. Display Employees By Role");
        System.out.println("4. Display Employee By Username");
        System.out.println("5. Update Employee Fields");
        System.out.println("6. Hire Employee");
        System.out.println("7. Fire Employee");
        System.out.println("8. Algorithms");
        System.out.println("9. Logout");
    }

    public void updateEmployeeFields(Employee employee, String phoneNo, String email) {
        employee.setPhoneNo(phoneNo);
        employee.setEmail(email);
        System.out.println("Employee " + employee.getName() + "'s fields updated.");
    }

    public void hireEmployee(List<Employee> employees, Employee newEmployee) {
        employees.add(newEmployee);
        System.out.println("Employee " + newEmployee.getName() + " hired successfully.");
    }

    public void fireEmployee(List<Employee> employees, Employee employeeToRemove) {
        if(employeeToRemove instanceof ManagerEmployee) {
            System.out.println("Managers cannot fire themselves!");
            return;
        }
        employees.remove(employeeToRemove);
        System.out.println("Employee " + employeeToRemove.getName() + " fired successfully.");
    }
}
