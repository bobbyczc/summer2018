package utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dao.NewsDao;
import dao.UserActionDao;
import dao.UserDao;

public class MailUtils {
	
	private static MimeMessage createMail(Session session, String keyword, String to) {
		String text = "你关注的话题"+keyword+"已有新闻更新";
		String fromEmail = "15071238330@163.com";
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(fromEmail,"舆情中心","UTF-8"));
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to, to, "UTF-8"));
			message.setSubject("舆情提醒","UTF-8");
			message.setText(text, "UTF-8");
			message.setSentDate(new Date());
			message.saveChanges();
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
	}
	
	public static void sendEmail(String keyword, String to) {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.163.com");
		props.setProperty("mail.smtp.port", "465"); 
		props.setProperty("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.ssl.enable", "true");
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		MimeMessage message = createMail(session, keyword, to);
		Transport transport;
		try {
			transport = session.getTransport();
			transport.connect("smtp.163.com","15071238330@163.com","op123456");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void mialTimer() {
		UserDao userDao = new UserDao();
		UserActionDao actionDao = new UserActionDao();
		NewsDao newsDao = new NewsDao();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 30 );
		calendar.set(Calendar.SECOND, 0);
		Date firstTime = calendar.getTime();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
//				sendEmail();
				List<Integer> userids = actionDao.getUserIds();
				System.out.println(userids);
				for(int id:userids) {
					List<String> topics = TransUtil.strToList(actionDao.getKeyWordsStr(id));
					System.out.println(topics);
					for(String keyword:topics) {
						float currentvalue = newsDao.getKeywordHotByDate(keyword, dateFormat.format(new Date()));
						float preValue = newsDao.getKeywordHotByDate(keyword, dateFormat.format(getPreDate()));
						System.out.println(currentvalue+" "+preValue);
						if(currentvalue-preValue>=0.30) {
							System.out.println("send email");
							sendEmail(keyword,userDao.getUserById(id).getEmail());
						}
					}
				}
				
			}
		}, firstTime, 1000*60*60*24);
	}
	/**
	 * 获取当前日期的前一天日期
	 * @return
	 */
	public static Date getPreDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}
	
	public static void main(String[] atgs) {
		mialTimer();
	}
	
}
