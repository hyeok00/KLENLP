/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	MemberManagement
 * @className 		MemberManager
 * @author 			정윤서
 */
package MemberManagement;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import SystemManagement.DBControl;

public class MemberManager extends Member {
	private JPanel contentPane;

//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		// TODO Auto-generated method stub
//		MemberManager m = new MemberManager();
//		m.registerMember("20170002", "정윤서", "010-0000-0010");
//		m.changeMember("20170002", "me", "010-0000-0000");
//		System.out.println(m.readMember("20160001"));
//		m.setConcernedMember("20150001", 1, "과다복용");
//	}

	// 고객 등록
	public void registerMember(String memberCode, String memberName, String memberPhone)
			throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement reservationTable = null;
		String driver = DBControl.FOR_NAME;
        String dbURL = DBControl.URL;
        String user = DBControl.ID; 
        String pwd = DBControl.PASSWORD;

		int defInterest = 0;
		String defReason = null;
		String dbsql = "insert into Member (memberID, name, phone, interest, reason) VALUES (\'" + memberCode + "\',\'"
				+ memberName + "\',\'" + memberPhone + "\',\'" + defInterest + "\',\'" + defReason + "\')";

		ResultSet rss = null;
		int check = 0;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbURL, user, pwd);

			// 중복된 고객번호 있는지 확인하는 쿼리문
			reservationTable = con
					.prepareStatement("select memberID from Member where memberID=\'" + memberCode + "\'");
			rss = reservationTable.executeQuery();

			// 중복된 고객 번호 있으면 check = 1, 없으면 check = 0
			if (rss.next()) {
				check = 1;
			} else {
				check = 0;
			}

			// check = 1이면 중복 메시지, 0이면 등록
			if (check == 1) {
				JOptionPane.showMessageDialog(contentPane, "고객코드 중복");
			} else if (check == 0) {
				reservationTable = con.prepareStatement(dbsql);
				reservationTable.executeUpdate();
				JOptionPane.showMessageDialog(contentPane, memberName + "님 등록 완료");
			}
		} catch (SQLException e) {
			System.out.println("SQL Error : " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
		} finally { // 사용순서와 반대로 close 함
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 고객 수정
	public void changeMember(String memberCode, String memberName, String memberPhone)
			throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement reservationTable = null;
		String driver = DBControl.FOR_NAME;
        String dburl = DBControl.URL;
        String user = DBControl.ID; 
        String pwd = DBControl.PASSWORD;
		String dbsql = "update Member set name=" + "\'" + memberName + "\'" + ",phone=" + "\'" + memberPhone + "\'"
				+ " where memberID=" + "\'" + memberCode + "\'";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dburl, user, pwd);
			reservationTable = con.prepareStatement(dbsql);
			reservationTable.executeUpdate();
			JOptionPane.showMessageDialog(contentPane, memberName + "님 정보 수정 완료");
		} catch (SQLException e) {
			System.out.println("SQL Error : " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
		} finally { // 사용순서와 반대로 close함
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 고객 조회
	public String readMember(String memberCode) throws ClassNotFoundException, SQLException {
		Connection con = null; // 데이터 베이스와 연결을 위한 객체
		Statement stmt = null; // SQL 문을 데이터베이스에 보내기위한 객체
		ResultSet rss = null; // SQL 질의에 의해 생성된 테이블을 저장하는 객체

		// 1. JDBC Driver Class - com.mysql.jdbc.Driver
		String driver = DBControl.FOR_NAME;
		// 2. 데이터베이스에 연결하기 위한 정보
        String dburl = DBControl.URL; // 연결문자열
        String user = DBControl.ID;   // 데이터베이스 ID
        String pwd = DBControl.PASSWORD; // 데이터베이스 PW
		String dbsql = "select * from Member where memberID=\'" + memberCode + "\'";
		String result = null;
		int check = 0;

		try {
			// 1. JDBC 드라이버 로딩
			Class.forName(driver);

			// 2. Connection 객체 생성
			con = DriverManager.getConnection(dburl, user, pwd); // DB 연결

			// 3. Statement 객체 생성
			stmt = con.createStatement();

			// 해당 고객번호 있는지 확인하는 쿼리문
			rss = stmt.executeQuery("select memberID from Member where memberID=\'" + memberCode + "\'");

			// 해당 고객 번호 있으면 check = 1, 없으면 check = 0
			if (rss.next()) {
				check = 1;
			} else {
				check = 0;
			}

			// check = 1이면 조회, 0이면 오류 메시지
			if (check == 1) {
				// 4. SQL 문장을 실행하고 결과를 리턴
				// stmt.excuteQuery(SQL) : select
				// stmt.excuteUpdate(SQL) : insert, update, delete ..
				rss = stmt.executeQuery(dbsql);

				// 5. ResultSet에 저장된 데이터 string으로 만들기
				while (rss.next()) {
					String memberID = rss.getString("memberID");
					String name = rss.getString("name");
					String phone = rss.getString("phone");
					String interest = rss.getString("interest");
					String reason = rss.getString(5); // rs.getString("reason");
					result = memberID + "," + name + "," + phone + "," + interest + "," + reason;
				}
			} else if (check == 0) {
				result = "해당 고객 없음";
			}

		} catch (SQLException e) {
			System.out.println("SQL Error : " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
		} finally { // 사용순서와 반대로 close 함
			if (rss != null) {
				try {
					rss.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	// 관심 고객 설정
	public void setConcernedMember(String memberCode, int concernedStatus, String concernedReason) {
		Connection con = null;
		PreparedStatement reservationTable = null;
		String driver = DBControl.FOR_NAME;
        String dburl = DBControl.URL;
        String user = DBControl.ID; 
        String pwd = DBControl.PASSWORD;
		String dbsql = "update Member set interest=" + "\'" + concernedStatus + "\'" + ",reason=" + "\'"
				+ concernedReason + "\'" + " where memberID=" + "\'" + memberCode + "\'";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dburl, user, pwd);
			reservationTable = con.prepareStatement(dbsql);
			reservationTable.executeUpdate();
			JOptionPane.showMessageDialog(contentPane, "관심고객 설정 완료");
		} catch (SQLException e) {
			System.out.println("SQL Error : " + e.getMessage());
		} catch (ClassNotFoundException e1) {
			System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
		} finally { // 사용순서와 반대로 close 함
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 고객번호 중복 확인
//	public String duplicateCheck(String memberCode) {
//		Connection con = null;
//		PreparedStatement reservationTable = null;
//		String driver = "com.mysql.cj.jdbc.Driver";
//		String dburl = "jdbc:mysql://localhost/pharmacy?serverTimezone=UTC";
//		String user = "root";
//		String pwd = "990102";
//		ResultSet rss = null;
//		// String str1 = "20170001";
//		// String dbsql = "update Member set interest=" + "\'" + concernedStatus + "\'"
//		// + ",reason=" + "\'"
//		// + concernedReason + "\'" + " where memberID=" + "\'" + memberCode + "\'";
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(dburl, user, pwd);
//			reservationTable = con
//					.prepareStatement("select memberID from Member where memberID=\'" + memberCode + "\'");
//			rss = reservationTable.executeQuery();
//		} catch (SQLException e) {
//			System.out.println("SQL Error : " + e.getMessage());
//		} catch (ClassNotFoundException e1) {
//			System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
//		} finally { // 사용순서와 반대로 close 함
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return rss.toString();
//	}
}
