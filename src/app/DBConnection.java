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
 
    public void createTableBorrowers() {
    try (Statement stmt = con.createStatement()) {
        String query = "CREATE TABLE Borrowers ("
                + "StudentID INT PRIMARY KEY, "
                + "StudentName VARCHAR(20), "
                + "StudentSurname VARCHAR(20), "
                + "StudentCourse VARCHAR(20), "
                + "RentalPrice VARCHAR(20), "
                + "BookID INT, " // Add this line to define BookID
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
        String query = "INSERT INTO Borrowers (StudentID, StudentName, StudentSurname, StudentCourse, RentalPrice, BookID) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, borrower.id);
        pstmt.setString(2, borrower.getName());
        pstmt.setString(3, borrower.getSurname());
        pstmt.setString(4, borrower.getStudentCourse());
        pstmt.setString(5, borrower.getRentalPrice());
        pstmt.setString(6, borrower.getBookID());
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
      
      public ArrayList<String[]> viewBorrowers(){
           ArrayList<String[]> dataList = new ArrayList<>();
           
           try{
              String query = "SELECT * FROM Borrowers";
              ResultSet table = this.con.createStatement().executeQuery(query);
              while(table.next()){
                  String sid = table.getString("StudentID");
                  String name = table.getString("StudentName");
                  String sname = table.getString("StudentSurname");
                  String course = table.getString("StudentCourse");
                  String price = table.getString("RentalPrice");
                  String fkey = table.getString("BookID");
                  
                  String[] row = {sid,name, sname,course, price, fkey};
                  dataList.add(row);
              }
           }catch(SQLException ex){
               ex.printStackTrace();
           }
               return dataList;
      }
       public void deleteBook(int id) {
        String sql = "DELETE FROM Books WHERE BookID = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            //System.out.println("Book deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       public void updateBook(int id, String title, String author, String year, String genre, String price) {
    String sql = "UPDATE Books SET Title = ?, Author = ?, YearPublished = ?, Genre = ?, Price = ? WHERE BookID = ?";
    
    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        //pstmt.setInt(1, id);
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, year);
        pstmt.setString(4, genre);
        pstmt.setString(5, price);
        pstmt.setInt(6, id);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   public void updateBorrowers(String studentid, String name, String sname, String course, String price){
       String query = "UPDATE Borrowers SET StudentID = ?, StudentName = ?, StudentSurname = ?, StudentCourse = ?, RentalPrice = ? WHERE StudentID = ?";
       
       try(PreparedStatement pstmt = con.prepareStatement(query)){
           pstmt.setString(1, studentid);
           pstmt.setString(2, name);
           pstmt.setString(3, sname);
           pstmt.setString(4, course);
           pstmt.setString(5, price);
           pstmt.setString(6, studentid);
           pstmt.executeUpdate();
           
       }catch(SQLException ex){
           ex.printStackTrace();
       }
   }
   public void deleteBorrower(String id) {
        String sql = "DELETE FROM Borrowers WHERE StudentID = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            //System.out.println("Book deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String[]> displayBorrowers(String id) {
    String query = "SELECT b.StudentName, b.StudentSurname, b.StudentCourse, bo.Title, bo.Genre "
                 + "FROM Borrowers b "
                 + "INNER JOIN Books bo ON b.BookID = bo.BookID "
                 + "WHERE b.StudentID = ?";
    
    ArrayList<String[]> dataList = new ArrayList<>();
                 
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        pstmt.setString(1, id); // Set the parameter for StudentID
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String studentName = rs.getString("StudentName");
                String studentSurname = rs.getString("StudentSurname");
                String studentCourse = rs.getString("StudentCourse");
                String title = rs.getString("Title");
                String genre = rs.getString("Genre");
                
                String[] row = {studentName, studentSurname, studentCourse, title, genre};
                dataList.add(row); 
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return dataList;
}

}
