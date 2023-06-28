package studentgradecalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;



public class Attendance {
	private int att_id;
	private int std_id;
	private int crs_id;
	private LocalDate att_date;
	public static ArrayList<Attendance> attendanceList = new ArrayList<>();
        public static ArrayList<Attendance> backup = new ArrayList<>();
//Constructors
	public Attendance(int att_id, int std_id, int crs_id, LocalDate att_date2) {
		this.att_id = att_id;
		this.std_id = std_id;
		this.crs_id = crs_id;
		this.att_date = att_date2;
	}

	public Attendance() {
		// Default constructor
	}

//Getter and Setter methods
	public int getAtt_id() {
		return att_id;
	}

	public void setAtt_id(int att_id) {
		this.att_id = att_id;
	}

	public int getStd_id() {
		return std_id;
	}

	public void setStd_id(int std_id) {
		this.std_id = std_id;
	}

	public int getCrs_id() {
		return crs_id;
	}

	public void setCrs_id(int crs_id) {
		this.crs_id = crs_id;
	}

	public LocalDate getAtt_date() {
		return att_date;
	}
	public void addAttToList(Attendance at) {
		attendanceList.add(at);
	}

	public void setAtt_date(LocalDate att_date) {
		this.att_date = att_date;
	}

//Other methods
	public static void add_attendance(int id, int std_id, int crs_id, LocalDate att_date) {
		Attendance attendance = new Attendance(id, std_id, crs_id, att_date);
		attendanceList.add(attendance);
		System.out.println("Attendance added successfully.");
	}

	public static void edit_attendance(int id, int std_id, int crs_id, LocalDate att_date) {
		for (Attendance attendance : attendanceList) {
			if (attendance.getAtt_id() == id) {
				attendance.setStd_id(std_id);
				attendance.setCrs_id(crs_id);
				attendance.setAtt_date(att_date);
				System.out.println("Attendance edited successfully.");
				return;
			}
		}
		System.out.println("Attendance not found.");
	}

	public static void delete_attendance(int id) {
		for (Attendance attendance : attendanceList) {
			if (attendance.getAtt_id() == id) {
				attendanceList.remove(attendance);
				System.out.println("Attendance deleted successfully.");
				return;
			}
		}
		System.out.println("Attendance not found.");
	}

	public static void list_attendance(int id) {
		for (Attendance attendance : attendanceList) {
			if (attendance.getAtt_id() == id) {
				System.out.println("Attendance ID: " + attendance.getAtt_id());
				System.out.println("Student ID: " + attendance.getStd_id());
				System.out.println("Course ID: " + attendance.getCrs_id());
				System.out.println("Attendance Date: " + attendance.getAtt_date());
				return;
			}
		}
		System.out.println("Attendance not found.");
	}

	public static void list_all_attendances() {
		for (Attendance attendance : attendanceList) {
			System.out.println("Attendance ID: " + attendance.getAtt_id());
			System.out.println("Student ID: " + attendance.getStd_id());
			System.out.println("Course ID: " + attendance.getCrs_id());
			System.out.println("Attendance Date: " + attendance.getAtt_date());
			System.out.println("---------------------------");
		}
	}

	public static void delete_all_attendances() {
		attendanceList.clear();
		System.out.println("All attendances deleted successfully.");
	}

	  private static Connection getConnection() throws SQLException {
     
          String url = "jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=CONVERT_TO_NULL"; // Replace "database_name" with your actual database name
    String username = "dbuser";
    String password = "dbuser";
    Connection connection = DriverManager.getConnection(url, username, password);
    return connection;
}
          
 public static void backup_attendances() {
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM attendance");
         ResultSet resultSet = statement.executeQuery()) {

        backup.clear();

        while (resultSet.next()) {
            int att_id = resultSet.getInt("att_id");
            int std_id = resultSet.getInt("std_id");
            int crs_id = resultSet.getInt("crs_id");
            LocalDate att_date = resultSet.getDate("att_date").toLocalDate();

            Attendance attendance = new Attendance(att_id, std_id, crs_id, att_date);
            backup.add(attendance);
        }

        System.out.println("Attendances backed up successfully.");
    } catch (SQLException e) {
        System.out.println("Error backing up attendances: " + e.getMessage());
    }
}

public static void restore_attendances() {
    try (Connection connection = getConnection();
         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM attendance");
         PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO attendance (att_id, std_id, crs_id, att_date) VALUES (?, ?, ?, ?)")) {

        deleteStatement.executeUpdate();

        for (Attendance backupAttendance : backup) {
            insertStatement.setInt(1, backupAttendance.getAtt_id());
            insertStatement.setInt(2, backupAttendance.getStd_id());
            insertStatement.setInt(3, backupAttendance.getCrs_id());
            insertStatement.setDate(4, java.sql.Date.valueOf(backupAttendance.getAtt_date()));
            insertStatement.executeUpdate();
        }

        System.out.println("Attendances restored successfully.");
    } catch (SQLException e) {
        System.out.println("Error restoring attendances: " + e.getMessage());
    }
}


}