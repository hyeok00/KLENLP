package ReturnAndDiscardManagement;
/**
 * @systemName		SOGONG Pharmacy Management
 * @subSystemName	ReturnAndDiscard Management
 * @className 		ReturnAndDiscardManager
 * @author 			이성애
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ReturnAndDiscardManagement.DBControl;


//결과값 넘겨주는거 하나 더 만들기
public class ReturnAndDiscardManager {
	private boolean result=false;
	DBControl db=new DBControl();
	Connection conn=db.dbConn();
	PreparedStatement pstmt=DBControl.pst;
	PreparedStatement pstmt2=DBControl.pst2;
	ResultSet rs=DBControl.rs;
	
	Object[][] pdList = null;

	void register(String productCode, int quantity, String category, String reason, int state) {
		ReturnAndDiscard rad=new ReturnAndDiscard(productCode,quantity,category,reason,state);
			setResult(true);
			
	}
	void FinalizeRegisteration(String productCode,String date) {
		//update returnanddiscard set date="2019-06-03" where productCode='P20190006';
		ReturnAndDiscard rad= new ReturnAndDiscard();
		rad.setDate(productCode,date);
		setResult(true);
	}
	void cancelRegisteration(String productCode,int quantity) {
		String enteringcode="update productstock set quantity=\""+quantity+"\"where productCode=\""+productCode+"\"";
		String query="delete from returnanddiscard where productCode=\""+productCode+"\"";
		try {
			pstmt2=conn.prepareStatement(enteringcode);
			pstmt2.execute(enteringcode);
			pstmt=conn.prepareStatement(query);
			pstmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResult(true);
	}

	Object[][] read(int code) {
			String productCode=null;
			int i=0;
			String radQuery;
			String pdQuery;
			try {
				if(code==1) {
					pdList=new Object[50][6];
					//상품코드, 상품명, 수량, 유통기한,거래처
					pdQuery="select productCode, productstock.quantity, productstock.productName, productstock.expirationDate, supplier.name from  productstock inner join enteringlist on productstock.enteringCode=enteringlist.enteringCode left join supplier on supplier.supplierCode=enteringlist.supplierCode where productstock.quantity>0;";
					pstmt=conn.prepareStatement(pdQuery);
					rs=pstmt.executeQuery();
					while(rs.next()) {
						pdList[i][0]=i+1;
						pdList[i][1]=rs.getString("productCode");
						pdList[i][2]=rs.getString("productName");
						pdList[i][3]=rs.getString("expirationDate");
						pdList[i][4]=rs.getString("quantity");
						pdList[i][5]=rs.getString("name");
						i+=1;
				}
				}
				if(code==2) {
					ReturnAndDiscard rad=new ReturnAndDiscard();
					pdList=new Object[20][9];
					//상품코드, 상품명, 유통기한, 수량, 처리사유, 처리일, 처리상태
					radQuery="select returnanddiscard.productCode, returnanddiscard.quantity, reason,date,category,productName,expirationDate from returnanddiscard join productstock on returnanddiscard.productCode=productstock.productCode where state=0";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
					pstmt=conn.prepareStatement(radQuery);
					rs=pstmt.executeQuery();
					String[] productCode1=rad.getProductCode(1);
					String[] quantity=rad.getQuantity(1);
					String[] reason=rad.getReason(1);
					String[] date=rad.getDate(1);
					String[] category=rad.getCategory(1);
					while(rs.next()) {
						pdList[i][0]=i+1;
						pdList[i][1]=productCode1[i];//상품코드
						pdList[i][2]=rs.getString("productName");//상품명
						pdList[i][3]=rs.getString("expirationDate");//유통기한
						pdList[i][4]=quantity[i];//수량
						pdList[i][5]=reason[i];//처리사유
						pdList[i][6]=date[i];//처리일
						pdList[i][7]=category[i];//처리상태
						i+=1;
					}
				}
				if(code==3) {
					ReturnAndDiscard rad=new ReturnAndDiscard();
					i=0;
					pdList=new Object[50][9];
					//상품코드, 상품명, 유통기한, 수량, 처리사유, 처리일, 처리상태
					radQuery="select returnanddiscard.productCode, returnanddiscard.quantity, reason,date,category,productName,expirationDate from returnanddiscard join productstock on returnanddiscard.productCode=productstock.productCode";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
					pstmt=conn.prepareStatement(radQuery);
					rs=pstmt.executeQuery();
					String[] productCode1=rad.getProductCode(2);
					String[] quantity=rad.getQuantity(2);
					String[] reason=rad.getReason(2);
					String[] date=rad.getDate(2);
					String[] category=rad.getCategory(2);
					while(rs.next()) {
						pdList[i][0]=i+1;
						pdList[i][1]=productCode1[i];//상품코드
						pdList[i][2]=rs.getString("productName");//상품명
						pdList[i][3]=rs.getString("expirationDate");//유통기한
						pdList[i][4]=quantity[i];//수량
						pdList[i][5]=reason[i];//처리사유
						pdList[i][6]=date[i];//처리일
						pdList[i][7]=category[i];//처리상태
						i+=1;
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("실패");
			}
			return pdList;
	}
	void setResult(boolean result) {
		this.result=result;
	}
	boolean getResult() {
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		}


}


