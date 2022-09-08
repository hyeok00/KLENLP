/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			VanIO
 * @author 					김태민
 */
package SaleManagement;

import java.io.IOException;
import java.math.BigDecimal;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.CardInfo;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

//입력형태 : 9446-0380-0292-8003,2022-06,960827,비번앞2자리
public class VanIO {
	private final String key = "2238586563855205";
	private final String keySecret = "6oL2HHSYWeRu2x2IUQgu7h2ScPnkoRvvfgxXYqVUENoGlrofhEJoWLg8AEtZvZvLf2IetE87rxNMYc2f";
	private IamportClient client;
	
	public VanIO() {
		client = new IamportClient(key, keySecret);
	}
	public boolean requestPayment(String creditCardInfo, int amount, String saleHistoryCode, SaleMessage message) {
		String[] infoArr = creditCardInfo.split(",");
		if (infoArr.length != 4) {
			message.setMessage("잘못된 입력");
			return false;
		}
		String cardNum = infoArr[0];
		String cardExpirationDate = infoArr[1];
		String dateOfBirth = infoArr[2];
		String cardPw = infoArr[3];
		
		try {
			CardInfo card = new CardInfo(cardNum, cardExpirationDate, dateOfBirth, cardPw);
			BigDecimal bigDecimal = new BigDecimal(Integer.toString(amount));
			IamportResponse<Payment> response = client.onetimePayment(new OnetimePaymentData(saleHistoryCode, bigDecimal , card));
			Payment payment = response.getResponse();

			if (response.getMessage() != null) {
				message.setMessage(response.getMessage());
				return false;
			}
			else if (payment.getFailReason() != null)  {
				message.setMessage(payment.getFailReason());
				return false;
			}
			else {
				message.setMessage(payment.getApplyNum());
				System.out.println(payment.getApplyNum());
				System.out.println(message);
			}
		} catch (IamportResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean requestRefund(String saleHistoryCode, SaleMessage message) {
		
		try {
			IamportResponse<Payment> response = client.cancelPaymentByImpUid(new CancelData(saleHistoryCode, false));
			Payment payment = response.getResponse();

			if (response.getMessage() != null) {
				message.setMessage(response.getMessage());
				return false;
			}
			else if (payment.getFailReason() != null)  {
				message.setMessage(payment.getFailReason());
				return false;
			}
			else {
				message.setMessage(payment.getApplyNum());
			}
		} catch (IamportResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
