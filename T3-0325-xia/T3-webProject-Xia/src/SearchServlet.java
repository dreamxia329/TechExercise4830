import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Search Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword!=null && keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM myTableXia0325";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {       	
        	// to support both upper case and lower case
        	String selectSQL = "SELECT * FROM myTableXia0325 WHERE UPPER(parentEmail) LIKE ?";
        	preparedStatement = connection.prepareStatement(selectSQL);
        	String theEmail = '%' + (keyword != null? keyword.toUpperCase():keyword) + '%';
             preparedStatement.setString(1, theEmail);
         }
         
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String studentName = rs.getString("studentName").trim();
            String studentAge = rs.getString("studentAge").trim();
            String studentGrade = rs.getString("studentGrade").trim();
            String parentName = rs.getString("parentName").trim();
            String parentPhone = rs.getString("parentPhone").trim();
            String parentEmail = rs.getString("parentEmail").trim();
            String address = rs.getString("address").trim();

            if (keyword.isEmpty() || (keyword != null && parentEmail.toUpperCase().contains(keyword.toUpperCase()))) {
               out.println("ID: " + id + ", ");
               out.println("Student Name: " + studentName + ", ");
               out.println("Student Age: " + studentAge + ", ");
               out.println("Student Grade: " + studentGrade + ", ");
               out.println("Parent Name: " + parentName + ", ");
               out.println("Parent Phone: " + parentPhone + ", ");
               out.println("Parent Email: " + parentEmail + ", ");
               out.println("Address: " + address + "<br>");
            }
         }
         out.println("<a href=/T3-webProject-Xia/index.html>Back to Home page</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
