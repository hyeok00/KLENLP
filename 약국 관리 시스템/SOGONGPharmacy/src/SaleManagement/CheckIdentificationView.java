/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			CheckIdentificationView
 * @author 					김태민
 */
package SaleManagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

import MemberManagement.Member;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CheckIdentificationView extends JDialog {
	private JTextField memberCode;
	private JTextField memberName;
	private JTextField memberPhone;
	private JCheckBox concernedStatus;
	private JTextPane concernedReason;
	private boolean csCheck = false;
	private JButton readBtn;
	private JPanel panel;
	private JButton cancelBtn;
	private JButton correctBtn;
	
	private Member member;

	public void setMember(Member member) { this.member = member; }
	public Member getMember() { return member; }
	
	public void initial() {
        setTitle("신분증 확인");
        setBounds(500, 200, 500, 350);
        setResizable(false);
        getContentPane().setLayout(null);
        
        memberCode = new JTextField();
        memberCode.setHorizontalAlignment(SwingConstants.CENTER);
        memberCode.setBounds(100, 9, 296, 24);
        getContentPane().add(memberCode);
        memberCode.setColumns(10);
        
        readBtn = new JButton("조회");
        readBtn.setBounds(410, 8, 70, 27);
        getContentPane().add(readBtn);
        
        panel = new JPanel();
        panel.setBackground(SystemColor.activeCaptionBorder);
        panel.setBounds(14, 42, 466, 223);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        memberName = new JTextField();
        memberName.setBackground(Color.WHITE);
        memberName.setHorizontalAlignment(SwingConstants.CENTER);
        memberName.setBounds(114, 12, 291, 24);
        panel.add(memberName);
        memberName.setColumns(10);
        memberName.setEditable(false);
        
        memberPhone = new JTextField();
        memberPhone.setBackground(Color.WHITE);
        memberPhone.setEditable(false);
        memberPhone.setHorizontalAlignment(SwingConstants.CENTER);
        memberPhone.setBounds(114, 48, 291, 24);
        panel.add(memberPhone);
        memberPhone.setColumns(10);
        
        concernedStatus = new JCheckBox("");    
        concernedStatus.setForeground(Color.BLACK);
        concernedStatus.setHorizontalAlignment(SwingConstants.LEFT);
        concernedStatus.setBackground(SystemColor.activeCaptionBorder);
        concernedStatus.setBounds(130, 81, 25, 27);
        panel.add(concernedStatus);
        
        concernedReason = new JTextPane();
        concernedReason.setEditable(false);
        concernedReason.setBounds(147, 117, 258, 61);
        panel.add(concernedReason);
        
        cancelBtn = new JButton("취소");
        cancelBtn.setBounds(375, 277, 105, 27);
        getContentPane().add(cancelBtn);
        
        correctBtn = new JButton("확인");
        correctBtn.setBounds(258, 277, 105, 27);
        getContentPane().add(correctBtn);
        
        JLabel memberCodeLb = new JLabel("고객 코드");
        memberCodeLb.setBounds(14, 12, 70, 18);
        getContentPane().add(memberCodeLb);
        
        JLabel memberNameLb = new JLabel("고객명");
        memberNameLb.setHorizontalAlignment(SwingConstants.CENTER);
        memberNameLb.setBounds(14, 15, 62, 18);
        panel.add(memberNameLb);
        
        JLabel memberPhoneLb = new JLabel("연락처");
        memberPhoneLb.setHorizontalAlignment(SwingConstants.CENTER);
        memberPhoneLb.setBounds(13, 53, 62, 18);
        panel.add(memberPhoneLb);
        
        JLabel concernedStatusLb = new JLabel("관심고객 여부");
        concernedStatusLb.setHorizontalAlignment(SwingConstants.CENTER);
        concernedStatusLb.setBounds(16, 86, 101, 18);
        panel.add(concernedStatusLb);
        
        JLabel concernedReasonLb = new JLabel("관심고객 지정사유");
        concernedReasonLb.setHorizontalAlignment(SwingConstants.CENTER);
        concernedReasonLb.setBounds(14, 112, 131, 35);
        panel.add(concernedReasonLb);
	}
	public void initialListener() {
        readBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (readMember() == false) {
        			JOptionPane.showMessageDialog(null, "등록된 고객이 아닙니다", "error", JOptionPane.ERROR_MESSAGE);
        		}
        		else {
        			memberName.setText(member.getMemberName());
        			memberPhone.setText(member.getMemberPhone());
        			csCheck=(member.getConcernedStatus() != 0);
        			concernedStatus.setSelected(csCheck);
        			if (csCheck)
        				concernedReason.setText(member.getConcernReason());
        			else
        				concernedReason.setText("");
        		}
        	}
        });
        
        concernedStatus.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent arg0) {
        		concernedStatus.setSelected(csCheck);
        	}
        });
        
        cancelBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        
        correctBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
	}
	
	CheckIdentificationView(JFrame frame, boolean modal) {
		super(frame, modal);
		
		initial();
		initialListener();
    }
	
	private boolean readMember() {
		SaleManager sManager = new SaleManager();
		member = sManager.identificationCheck(memberCode.getText());
		if (member == null) return false;
		return true;
	}
}
