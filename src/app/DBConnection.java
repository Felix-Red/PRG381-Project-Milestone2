/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author felix
 */
import java.sql.*;
import java.util.ArrayList;


public class DBConnection {
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    
    private static final String JDBC_URL = "jdbc:derby:libraryDBM2; create=true";
    
    Connection con;
    public DBConnection(){}
    
    public void connect() throws ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            this.con = DriverManager.getConnection(JDBC_URL);
            if (this.con != null) {
                System.out.println("Connected to database");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
      /*    public void createTableBooks() {
        try (Statement stmt = con.createStatement()) {
            String query = "CREATE TABLE Books(BookID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
                    + " Title VARCHAR(20), Author VARCHAR(20), YearPublished VARCHAR(20), Genre VARCHAR(20), Price VARCHAR(20))";
            stmt.executeUpdate(query);
            System.out.println("Table created");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Table creation failed");
        }
    }
 
    public void createTableBorrowers(){
    try (Statement stmt = con.createStatement()) {
        String query = "CREATE TABLE Borrowers ("
                + "StudentID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "BookID INT, "
                + "StudentName VARCHAR(20), "
                + "StudentSurname VARCHAR(20), "
                + "StudentCourse VARCHAR(20), "
                + "RentalPrice VARCHAR(20), "
                + "FOREIGN KEY (BookID) REFERENCES Books(BookID))";
        stmt.executeUpdate(query);
        System.out.println("Table created");
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("Table creation failed");
    }
}*/

 
    public void addBorrower(Borrower borrower) {
    try {
        String query = "INSERT INTO Borrowers (BookID, StudentName, StudentSurname, StudentCourse, RentalPrice) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, borrower.getBookID());
        pstmt.setString(2, borrower.getName());
        pstmt.setString(3, borrower.getSurname());
        pstmt.setString(4, borrower.getStudentCourse());
        pstmt.setString(5, borrower.getRentalPrice());
        pstmt.executeUpdate();
        System.out.println("Data Added");
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("Data not added");
    }
}

     
      public void addBook(Book book) {
    try {
        String query = "INSERT INTO Books (Title, Author, YearPublished, Genre, Price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, book.getTitle());
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getYearPublished());
        pstmt.setString(4, book.getGenre());
        pstmt.setString(5, book.getPrice());
        pstmt.executeUpdate();
        System.out.println("Book Added");
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("Book not added");
    }
}
      
      public ArrayList<String[]> viewBooks(){
          ArrayList<String[]> dataList = new ArrayList<>();
          try{
              String query = "SELECT * FROM Books";
              ResultSet table = this.con.createStatement().executeQuery(query);
              
              while(table.next()){
                  String id = table.getString("BookID");
                  String title = table.getString("Title");
                  String author = table.getString("Author");
                  String  year = table.getString("YearPublished");
                  String genre = table.getString("Genre");
                  String price = table.getString("Price");
                  
                  String[] row = {id, title, author, year, genre, price};
                  dataList.add(row);
              }
          }catch(SQLException ex){
              ex.printStackTrace();
          }
          return dataList;
      }
    
}
