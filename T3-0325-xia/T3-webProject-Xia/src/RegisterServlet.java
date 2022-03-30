
/**
 * @file RegisterServlet.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RegisterServlet() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      String studentName = request.getParameter("studentName");
	      String studentAge = request.getParameter("studentAge");
	      String studentGrade = request.getParameter("studentGrade");
	      String parentName = request.getParameter("parentName");
	      String parentPhone = request.getParameter("parentPhone");
	      String parentEmail = request.getParameter("parentEmail");
	      String address = request.getParameter("address");

      Connection connection = null;
      String insertSql = " INSERT INTO myTableXia0325 (ID, studentName, studentAge, studentGrade,parentName,parentPhone,parentEmail,address) values (default, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, studentName);
         preparedStmt.setString(2, studentAge);
         preparedStmt.setString(3, studentGrade);
         preparedStmt.setString(4, parentName);
         preparedStmt.setString(5, parentPhone);
         preparedStmt.setString(6, parentEmail);
         preparedStmt.setString(7, address);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "OCCA online Registration";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>student Name</b>: " + studentName + "\n" + //
            "  <li><b>student Age</b>: " + studentAge + "\n" + //
            "  <li><b>student Grade</b>: " + studentGrade + "\n" + //
            "  <li><b>parent Name</b>: " + parentName + "\n" + //
            "  <li><b>parent Phone</b>: " + parentPhone + "\n" + //
            "  <li><b>parent Email</b>: " + parentEmail + "\n" + //
            "  <li><b>address</b>: " + address + "\n" + //

            "</ul>\n");

      out.println("<a href=/T3-webProject-Xia/index.html>Back to Home page</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
