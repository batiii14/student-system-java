package studentgradecalculator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Department {
	private int dept_id;
	private String dept_name;

	public static ArrayList<Department> departments = new ArrayList<>();
        public static ArrayList<Department> backupDepartments = new ArrayList<>();
        

	// Constructors
	public Department(int dept_id, String dept_name) {
		this.dept_id = dept_id;
		this.dept_name = dept_name;
	}

	public Department() {
		// Default constructor
	}
	
	public void addDeptToList(Department dpt) {
		departments.add(dpt);
	}

	// Getter and Setter methods
	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	
	public static void add_department(int id, String dept_name) {
		Department department = new Department(id, dept_name);
		departments.add(department);
		System.out.println("Department added successfully.");
	}

	public static void edit_department(int id, String dept_name) {
		for (Department department : departments) {
			if (department.getDept_id() == id) {
				department.setDept_name(dept_name);
				System.out.println("Department edited successfully.");
				return;
			}
		}
		System.out.println("Department not found.");
	}

	public static void delete_department(int id) {
		for (Department department : departments) {
			if (department.getDept_id() == id) {
				departments.remove(department);
				System.out.println("Department deleted successfully.");
				return;
			}
		}
		System.out.println("Department not found.");
	}

	public static void list_department(int id) {
		for (Department department : departments) {
			if (department.getDept_id() == id) {
				System.out.println("Department ID: " + department.getDept_id());
				System.out.println("Department Name: " + department.getDept_name());
				return;
			}
		}
		System.out.println("Department not found.");
	}

	public static void list_all_departments() {
		for (Department department : departments) {
			System.out.println("Department ID: " + department.getDept_id());
			System.out.println("Department Name: " + department.getDept_name());
			System.out.println("---------------------------");
		}
	}

	public static void delete_all_departments() {
		departments.clear();
		System.out.println("All departments deleted successfully.");
	}
        
         private static Connection getConnection() throws SQLException {
     
          String url = "jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace "database_name" with your actual database name
    String username = "dbuser";
    String password = "dbuser";
    Connection connection = DriverManager.getConnection(url, username, password);
    return connection;
}

	public static void backup_departments() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM department");
             ResultSet resultSet = statement.executeQuery()) {

            backupDepartments.clear();

            while (resultSet.next()) {
                int dept_id = resultSet.getInt("dept_id");
                String dept_name = resultSet.getString("dept_name");

                Department department = new Department(dept_id, dept_name);
                backupDepartments.add(department);
            }

            System.out.println("Department list backup successful");
        } catch (SQLException e) {
            System.out.println("Error backing up departments: " + e.getMessage());
        }
    }

    public static void restore_departments() {
        try (Connection connection = getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM department");
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO department (dept_id, dept_name) VALUES (?, ?)")) {

            deleteStatement.executeUpdate();

            for (Department backupDepartment : backupDepartments) {
                insertStatement.setInt(1, backupDepartment.getDept_id());
                insertStatement.setString(2, backupDepartment.getDept_name());
                insertStatement.executeUpdate();
            }

            System.out.println("Department list restore successful");
        } catch (SQLException e) {
            System.out.println("Error restoring departments: " + e.getMessage());
        }
    }
}
