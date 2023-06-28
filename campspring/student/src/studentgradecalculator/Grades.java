package studentgradecalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class Grades {
	public int _grd_id;
	public int _std_id;
	public int _crs_id;
	public float _grd_mt;
	public float _grd_hw;
	public float _grd_final;
	public static String _grd_lgrade;

	public static ArrayList<Grades> gradesList = new ArrayList<>();
	public static ArrayList<Grades> backUpGradesList = new ArrayList<>();

	// Constructors
	public Grades(int grd_id, int std_id, int crs_id, float grd_mt, float grd_hw, float grd_final) {
		_grd_id = grd_id;
		_std_id = std_id;
		_crs_id = crs_id;
		_grd_mt = grd_mt;
		_grd_hw = grd_hw;
		_grd_final = grd_final;
                
		

	}
 @Override
public String toString() {
    return "Grades [_grd_id=" + _grd_id + ", _std_id=" + _std_id + ", _crs_id=" + _crs_id +
            ", _grd_mt=" + _grd_mt + ", _grd_hw=" + _grd_hw + ", _grd_final=" + _grd_final +
            ", _grd_lgrade=" + _grd_lgrade + "]";
}


	public Grades() {
		gradesList = new ArrayList<>();
		// Default constructor
	}

	// Getter and Setter methods
	public int getGrd_id() {
		return _grd_id;
	}

	public void setGrd_id(int grd_id) {
		_grd_id = grd_id;
	}

	public int getStd_id() {
		return _std_id;
	}

	public void setStd_id(int std_id) {
		_std_id = std_id;
	}

	public int getCrs_id() {
		return _crs_id;
	}

	public void setCrs_id(int crs_id) {
		_crs_id = crs_id;
	}

	public float getGrd_mt() {
		return _grd_mt;
	}

	public void setGrd_mt(float grd_mt) {
		_grd_mt = grd_mt;
	}

	public float getGrd_hw() {
		return _grd_hw;
	}

	public void setGrd_hw(float grd_hw) {
		_grd_hw = grd_hw;
	}

	public float getGrd_final() {
		return _grd_final;
	}

	public void setGrd_final(float grd_final) {
		_grd_final = grd_final;
	}
        public static void setlGrade(String letter) {
		_grd_lgrade=letter;
	}
        
	public String getGrd_lgrade() {
		return _grd_lgrade;
	}

        
        
    public  static  void calculateLetterGrade(float hw,float mt,float finl) {
            
    double totsc = 0.3 * mt + 0.3 * hw +(0.4) * finl;
    if(totsc >= 90.0) _grd_lgrade="A";
    else if(totsc >= 85.0) _grd_lgrade="A-";
    else if(totsc >= 80.0) _grd_lgrade="B";
    else if(totsc >= 75.0) _grd_lgrade="B-";
    else if(totsc >= 70.0) _grd_lgrade="C";
    else if(totsc >= 65.0) _grd_lgrade="C-";
    else if(totsc >= 60.0) _grd_lgrade="D";
    else if(totsc >= 55.0) _grd_lgrade="D-";
    else _grd_lgrade="F";
    
    
} 

	public void addGradeToList(Grades grd) {
		gradesList.add(grd);
	}

	public void add_grade() {
		Scanner sc = new Scanner(System.in);
		System.out.printf("\nid:");
		_grd_id = sc.nextInt();
		System.out.printf("\nstudent id:");
		_std_id = sc.nextInt();
		System.out.printf("\ncourse id:");
		_crs_id = sc.nextInt();
		System.out.printf("\nmidterm grade:");
		_grd_mt = sc.nextInt();
		System.out.printf("\nhomework grade:");
		_grd_hw = sc.nextInt();

		System.out.printf("\nfinal grade:");
		_grd_final = sc.nextInt();

		System.out.printf("\nLetter grade:");
		_grd_lgrade = sc.next();
		Grades grade = new Grades(_grd_id, _std_id, _crs_id, _grd_mt, _grd_hw, _grd_final);
		gradesList.add(grade);
		System.out.println("Grade added successfully.");
	}

	public static void edit_grade(int id, int std_id, int crs_id, float grd_mt, float grd_hw, float grd_final) {
		for (Grades grade : gradesList) {
			if (grade.getGrd_id() == id) {
				grade.setStd_id(std_id);
				grade.setCrs_id(crs_id);
				grade.setGrd_mt(grd_mt);
				grade.setGrd_hw(grd_hw);
				grade.setGrd_final(grd_final);
                                grade.calculateLetterGrade(grd_hw,grd_mt,grd_final);
				System.out.println("Grade edited successfully.");
				return;
			}
		}
		System.out.println("Grade not found.");
	}

	public static void delete_grade(int id) {
		for (Grades grade : gradesList) {
			if (grade.getGrd_id() == id) {
				gradesList.remove(grade);
				System.out.println("Grade deleted successfully.");
				return;
			}
		}
		System.out.println("Grade not found.");
	}

	public void list_grade(int id) {
		for (Grades grade : gradesList) {
			if (grade.getGrd_id() == id) {
				System.out.println("Grade ID: " + grade.getGrd_id());
				System.out.println("Student ID: " + grade.getStd_id());
				System.out.println("Course ID: " + grade.getCrs_id());
				System.out.println("Midterm Grade: " + grade.getGrd_mt());
				System.out.println("Homework Grade: " + grade.getGrd_hw());
				System.out.println("Final Grade: " + grade.getGrd_final());
				System.out.println("Letter Grade: " + grade.getGrd_lgrade());
				return;
			}
		}
		System.out.println("Grade not found.");
	}

	public void list_all_grades() {
		for (Grades grade : gradesList) {
			System.out.println("Grade ID: " + grade.getGrd_id());
			System.out.println("Student ID: " + grade.getStd_id());
			System.out.println("Course ID: " + grade.getCrs_id());
			System.out.println("Midterm Grade: " + grade.getGrd_mt());
			System.out.println("Homework Grade: " + grade.getGrd_hw());
			System.out.println("Final Grade: " + grade.getGrd_final());
			System.out.println("Letter Grade: " + grade.getGrd_lgrade());
			System.out.println("---------------------------");
		}
	}

	public void delete_all_grades() {
		gradesList.clear();
		System.out.println("All grades deleted successfully.");
	}

	   private static Connection getConnection() throws SQLException {
     
          String url = "jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace "database_name" with your actual database name
    String username = "dbuser";
    String password = "dbuser";
    Connection connection = DriverManager.getConnection(url, username, password);
    return connection;
}
           
         public static void backup_grades() {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM grades");
         ResultSet resultSet = statement.executeQuery()) {

        backUpGradesList.clear();

        while (resultSet.next()) {
            int grd_id = resultSet.getInt("grd_id");
            int std_id = resultSet.getInt("std_no");
            int crs_id = resultSet.getInt("crs_id");
            float grd_mt = resultSet.getFloat("grd_mt");
            float grd_hw = resultSet.getFloat("grd_hw");
            float grd_final = resultSet.getFloat("grd_final");

            Grades grade = new Grades(grd_id, std_id, crs_id, grd_mt, grd_hw, grd_final);
            backUpGradesList.add(grade);
        }

        System.out.println("Grades backup created successfully.");
    } catch (SQLException e) {
        System.out.println("Error backing up grades: " + e.getMessage());
    }
}
public static void restore_grades() {
    try (Connection connection = getConnection();
         Statement statement = connection.createStatement();
         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM grades");
         PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO grades (grd_id, std_no, crs_id, grd_mt, grd_hw, grd_final) VALUES (?, ?, ?, ?, ?, ?)")) {

        // Delete all existing grades
        deleteStatement.executeUpdate();

        // Restore grades from backup
        for (Grades backupGrade : backUpGradesList) {
            insertStatement.setInt(1, backupGrade.getGrd_id());
            insertStatement.setInt(2, backupGrade.getStd_id());
            insertStatement.setInt(3, backupGrade.getCrs_id());
            insertStatement.setFloat(4, backupGrade.getGrd_mt());
            insertStatement.setFloat(5, backupGrade.getGrd_hw());
            insertStatement.setFloat(6, backupGrade.getGrd_final());
            insertStatement.executeUpdate();
        }

        System.out.println("Grades restored successfully.");
    } catch (SQLException e) {
        System.out.println("Error restoring grades: " + e.getMessage());
    }
}


}
