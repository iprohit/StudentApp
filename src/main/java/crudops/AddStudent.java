package crudops;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add")
public class AddStudent extends HttpServlet {
	Connection con;

	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/1eja7", "root", "Mysql@123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// fetch data from HTMl page
		String id = req.getParameter("studentid");
		String name = req.getParameter("studentname");
		String stream = req.getParameter("studentstream");
		String dob=req.getParameter("dob");

		// parsing
		int sid = Integer.parseInt(id);
		
		//convert string into date
		Date d=Date.valueOf(dob);
		int count=0;
		PreparedStatement pstmt = null;

		String query = "insert into student_app values(?,?,?,?)";

		try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, sid);
			pstmt.setString(2, name);
			pstmt.setString(3, stream);
			pstmt.setDate(4, d);
			count = pstmt.executeUpdate();
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PrintWriter pw=resp.getWriter();
		pw.print("<h1>"+count+" Record Inserted Successfully"+"</h1>");
	}
}
