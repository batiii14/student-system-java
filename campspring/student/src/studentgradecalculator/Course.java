package studentgradecalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class Course {
	private int crs_id;
	private int dept_id;
	private String crs_code;
	private String crs_name;

	public static ArrayList<Course> courses = new ArrayList<>();
        public static ArrayList<Course> backup = new ArrayList<>();
	// Constructors
	public Course(int crs_id, int dept_id, String crs_code, String crs_name) {
		this.crs_id = crs_id;
		this.dept_id = dept_id;
		this.crs_code = crs_code;
		this.crs_name = crs_name;
	}

	public Course() {
		// Default constructor
	}

	// Getter and Setter methods
	public int getCrs_id() {
		return crs_id;
	}

	public void setCrs_id(int crs_id) {
		this.crs_id = crs_id;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public String getCrs_code() {
		return crs_code;
	}

	public void setCrs_code(String crs_code) {
		this.crs_code = crs_code;
	}

	public String getCrs_name() {
		return crs_name;
	}
        public String getCrsFullName() {
		return crs_name+crs_code;
	}

	public void setCrs_name(String crs_name) {
		this.crs_name = crs_name;
	}

	public static void add_course(int id, int dept_id, String crs_code, String crs_name) {
		Course course = new Course(id, dept_id, crs_code, crs_name);
		courses.add(course);
		System.out.println("Course added successfully.");
	}

	public static void edit_course(int id, int dept_id, String crs_code, String crs_name) {
		for (Course course : courses) {
			if (course.getCrs_id() == id) {
				course.setDept_id(dept_id);
				course.setCrs_code(crs_code);
				course.setCrs_name(crs_name);
				System.out.println("Course edited successfully.");
				return;
			}
		}
		System.out.println("Course not found.");
	}

	public static void delete_course(int id) {
		for (Course course : courses) {
			if (course.getCrs_id() == id) {
				courses.remove(course);
				System.out.println("Course deleted successfully.");
				return;
			}
		}
		System.out.println("Course not found.");
	}

	public static void list_course(int id) {
		for (Course course : courses) {
			if (course.getCrs_id() == id) {
				System.out.println("Course ID: " + course.getCrs_id());
				System.out.println("Department ID: " + course.getDept_id());
				System.out.println("Course Code: " + course.getCrs_code());
				System.out.println("Course Name: " + course.getCrs_name());
				return;
			}
		}
		System.out.println("Course not found.");
	}

	public void addCourseToList(Course crs) {
		courses.add(crs);
	}

	public static void list_all_courses() {
		for (Course course : courses) {
			System.out.println("Course ID: " + course.getCrs_id());
			System.out.println("Department ID: " + course.getDept_id());
			System.out.println("Course Code: " + course.getCrs_code());
			System.out.println("Course Name: " + course.getCrs_name());
			System.out.println("---------------------------");
		}
	}

	public static void delete_all_courses() {
		courses.clear();
		System.out.println("All Courses deleted successfully.");
	}
        
     private static Connection getConnection() throws SQLException {
     
          String url = "jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace "database_name" with your actual database name
    String username = "dbuser";
    String password = "dbuser";
    Connection connection = DriverManager.getConnection(url, username, password);
    return connection;
}
     public static void backup() {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM course");
         ResultSet resultSet = statement.executeQuery()) {

        backup.clear();

        while (resultSet.next()) {
            int crs_id = resultSet.getInt("crs_id");
            int dept_id = resultSet.getInt("dept_id");
            String crs_code = resultSet.getString("crs_code");
            String crs_name = resultSet.getString("crs_name");

            Course course = new Course(crs_id, dept_id, crs_code, crs_name);
            backup.add(course);
        }

        System.out.println("Course list backup successful");
    } catch (SQLException e) {
        System.out.println("Error backing up courses: " + e.getMessage());
    }
}

public static void restore() {
    try (Connection connection = getConnection();
         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM course");
         PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO course (crs_id, dept_id, crs_code, crs_name) VALUES (?, ?, ?, ?)")) {

        deleteStatement.executeUpdate();

        for (Course backupCourse : backup) {
            insertStatement.setInt(1, backupCourse.getCrs_id());
            insertStatement.setInt(2, backupCourse.getDept_id());
            insertStatement.setString(3, backupCourse.getCrs_code());
            insertStatement.setString(4, backupCourse.getCrs_name());
            insertStatement.executeUpdate();
        }

        System.out.println("Course list restore successful");
    } catch (SQLException e) {
        System.out.println("Error restoring courses: " + e.getMessage());
    }
}

}