package studentgradecalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Student {

	public int _std_no;
	public String _std_name;
	public String _std_surname;
	public Grades _grade;
	public String _nationality;
	
	public static ArrayList<Student> students = new ArrayList<>();
        public static ArrayList<Student> backupStudents = new ArrayList<>();

	public int getStd_no() {
		return _std_no;
	}
        
        
	public Grades getGrade() {
		return _grade;
	}

	public void setGrade(Grades grade) {
		_grade = grade;
	}

	public void setStd_no(int std_no) {
		_std_no = std_no;
	}

	public String getStd_name() {
		return _std_name;
	}

	public void setStd_name(String std_name) {
		_std_name = std_name;
	}

	public String getStd_surname() {
		return _std_surname;
	}

	public void setStd_surname(String std_surname) {
		_std_surname = std_surname;
	}
        @Override
public String toString() {
    return "Student [_std_no=" + _std_no + ", _std_name=" + _std_name + ", _std_surname=" + _std_surname +
             ", _nationality=" + _nationality + "]";
}

	public Student() {

		_grade = new Grades();

	}
        

	public Student(int std_no, String std_name, String std_surname, Grades grade, String nationality) {
		_std_no = std_no;
		_std_name = std_name;
		_std_surname = std_surname;
		_grade = grade;
		_nationality = nationality;
		

	}
        public Student(int std_no, String std_name, String std_surname, String nationality) {
		_std_no = std_no;
		_std_name = std_name;
		_std_surname = std_surname;
		
		_nationality = nationality;
		

	}

	public void addStudent() {

		Scanner sc = new Scanner(System.in);
		System.out.printf("\nStudent No:");
		_std_no = sc.nextInt();
		System.out.printf("\nStudent Name:");
		_std_name = sc.next();
		System.out.printf("\nStudent Surname:");
		_std_surname = sc.next();
		_grade.add_grade();
		System.out.printf("\nNationality:");
		_nationality = sc.next();

		Student st2 = new Student(_std_no, _std_name, _std_surname, _grade, _nationality);
		students.add(st2);

	}

	public void addStudentToList(Student std) {
		students.add(std);
	}

	public static void deleteStudent(int std_id) {
    Iterator<Student> itr = students.iterator();
    while (itr.hasNext()) {
        Student student = itr.next();
        if (student.getStd_no() == std_id) {
            itr.remove(); // Use iterator's remove method
            break;
        }
    }
}


	public String get_nationality() {
		return _nationality;
	}

	public void set_nationality(String _nationality) {
		this._nationality = _nationality;
	}

	public void listStudents() {
		for (Student st : students) {
			System.out.printf("\n\n\nStudent No: %5d", st.getStd_no());
			System.out.printf("\nName: %s    Surname: %s", st.getStd_name(), st.getStd_surname());
			System.out.println("\nHomework: " + st.getGrade().getGrd_hw() + "\nMidterm: " + st.getGrade().getGrd_mt()
					+ "\nFinal: " + st.getGrade().getGrd_final());
			System.out.printf("\nLetter Grade: %s", st.getGrade().getGrd_lgrade());
			System.out.println("\nNationality: " + st.get_nationality());
		}
	}

	// End of calculateLetterGrade(Student st)

	public Student getStudentById(int id) {
		Student st=null;
		for (Student student : students) {
			if(student._std_no==id) {
				st=student;
			}
		}
		return st;
		
	}
	public static void updateStudent(Student student, int sno, String sname, String ssname, Grades grade, String nationality) {
    student.setStd_no(sno);
    student.setStd_name(sname);
    student.setStd_surname(ssname);
    student.setGrade(grade);
    student.set_nationality(nationality);
    
}
        
        	public static void updateStudent(Student student, int sno, String sname, String ssname, String nationality) {
    student.setStd_no(sno);
    student.setStd_name(sname);
    student.setStd_surname(ssname);
   
    student.set_nationality(nationality);
    
}



	public void printStudent(Student st) {
		System.out.printf("\n\n\nStudent No: %5d", st.getStd_no());
		System.out.printf("\nName: %s    Surname: %s", st.getStd_name(), st.getStd_surname());
		System.out.println("\nHomework:%d " + _grade.getGrd_hw() + "\nMidterm: " + _grade.getGrd_mt() + "\nFinal: "
				+ _grade.getGrd_final());
		System.out.printf("\nLetter Grade: %s", _grade.getGrd_lgrade());
		System.out.println("\nNationality: " + st.get_nationality());

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
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM student");
             ResultSet resultSet = statement.executeQuery()) {

            backupStudents.clear();

            while (resultSet.next()) {
                int std_no = resultSet.getInt("std_no");
                String std_name = resultSet.getString("std_name");
                String std_surname = resultSet.getString("std_surname");
                String nationality = resultSet.getString("nationality");

                Student student = new Student(std_no, std_name, std_surname, nationality);
                backupStudents.add(student);
            }

            System.out.println("Students list backup successful");
        } catch (SQLException e) {
            System.out.println("Error backing up students: " + e.getMessage());
        }
    }

  public static void restore_students() {
    try (Connection connection = getConnection();
         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM student");
         PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO student (std_no, std_name, std_surname, nationality) VALUES (?, ?, ?, ?)")) {

        // Delete all existing students
        deleteStatement.executeUpdate();

        // Restore students from backup
        for (Student backupStudent : backupStudents) {
            insertStatement.setInt(1, backupStudent.getStd_no());
            insertStatement.setString(2, backupStudent.getStd_name());
            insertStatement.setString(3, backupStudent.getStd_surname());
            insertStatement.setString(4, backupStudent.get_nationality());
            insertStatement.executeUpdate();
        }

        System.out.println("Students restored successfully.");
    } catch (SQLException e) {
        System.out.println("Error restoring students: " + e.getMessage());
    }
}
        
        
}
