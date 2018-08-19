package authaddusertodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;

import org.mindrot.jbcrypt.BCrypt;

public class Main {

	// delete from app_user where login='donald@duck.dd';

	public static Connection getConnection() throws SQLException {

		String url = "jdbc:postgresql://localhost/databas";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "postgres");
		props.setProperty("ssl", "false");
		Connection conn = DriverManager.getConnection(url, props);
		return conn;
	}

	public static void main(String[] args) {

		try {
			Connection conn = getConnection();
			if (conn != null) {

				System.out.println("connected");

				String uid = "donald@duck.dd";
				String pwd = "donald@duck.dd";
				String role = "ADMIN";

				LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

				String sql1 = "insert into app_user (login,blocked,confirmed,lastlogin, password) values (?,?,?,?,?)";
				String sql2 = "insert into app_user_role (login,role,description) values (?,?,?)";

				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, uid);
				pstmt1.setBoolean(2, false);
				pstmt1.setBoolean(3, false);
				pstmt1.setObject(4, now);
				pstmt1.setString(5, BCrypt.hashpw(pwd, BCrypt.gensalt()));
				pstmt1.executeUpdate();

				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, uid);
				pstmt2.setString(2, role);
				pstmt2.setString(3, "description");
				pstmt2.executeUpdate();

				pstmt2.close();
				pstmt1.close();
				conn.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
