package knu.cse.listenthis.PhysicalArchitecture;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import knu.cse.listenthis.ProblemDomain.User;

public class MailManager {

	public MailManager(User user){
		String host = "smtp.naver.com";
		final String id = "wnn156";
		final String password = "8702DKSwns!";
		
		String to = user.getId();
		
		Properties p = new Properties();
		
		p.put("mail.smtp.host", host);
		p.put("mail.smtp.auth",  "true");
		
		// session 생성 및 MimeMessage생성
		Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(id,password);
			}
		});

		try {

			MimeMessage msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(id));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// 이메일 제목
			msg.setSubject("이거나 들어 비밀번호.", "UTF-8");

			// 이메일 내용

			msg.setText("안녕하세요 " + user.getId() 
			+ "님!\n회원님의 비밀번호는 : " + user.getPw() + " 입니다.\n감사합니다.", "UTF-8");
			


			// 메일보내기
			Transport.send(msg);
			System.out.println("message sent successfully...");

		} catch (MessagingException msg_e) {
			msg_e.printStackTrace();
		}
	}

}
