package com.dk.core.common.utils.mail;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dk.core.common.utils.CommonUtils;

import freemarker.template.Template;

/**
 * @ClassName: MailUtils
 * @Description: 邮件发送的工具类
 */
public class MailUtils {
	private static final Logger logger = Logger.getLogger(MailUtils.class);
	
	/**
	 * @Title: sendMailBySynchronizationMode
	 * @Description: 同步发送 模板文件由freemarker生成
	 * @param map
	 * @param mailSender
	 * @param freeMarkerConfigurer
	 * @Return void
	 */
	public static void sendMailBySynchronizationMode(Map<String, Object> map, JavaMailSender mailSender, Template template) {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		Email email = (Email) map.get("email");
		map.remove("email");
		try {
			helper = new MimeMessageHelper(mime, true, "utf-8");
			helper.setFrom(email.getSender());//发件人
			helper.setTo(email.getAddress());//收件人 
			if (CommonUtils.isNotEmpty(email.getBcc())) {
				helper.setBcc(email.getBcc());// 暗送
			}
			if (CommonUtils.isNotEmpty(email.getCc())) {
				helper.setCc(email.getCc());// 抄送
			}
			if (CommonUtils.isNotEmpty(email.getReplyTo())) {
				helper.setReplyTo(email.getReplyTo());// 回复到
			}
			helper.setSubject(email.getSubject());//邮件主题
			String htmlText = getMailText(map, template); //使用模板生成html邮件内容 
			helper.setText(htmlText, true);//true表示设定html格式    

			//内嵌资源，这种功能很少用，因为大部分资源都在网上，只需在邮件正文中给个URL就足够了.  
			//helper.addInline("logo", new ClassPathResource("logo.gif"));    

			//处理附件
			for (MultipartFile file : email.getAttachment()) {
				if (file == null || file.isEmpty()) {
					continue;
				}
				String fileName = file.getOriginalFilename();
				try {
					fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
				} catch (Exception e) {
				}
				helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));
			}
			mailSender.send(mime);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @Title: sendMailByAsynchronousMode
	 * @Description: 异步发送 模板文件由freemarker生成
	 * @param map
	 * @param mailSender
	 * @param taskExecutor
	 * @param template
	 * @Return void
	 */
	public static void sendMailByAsynchronousMode(final Map<String, Object> map, final JavaMailSender mailSender,final Template template) {
		new Thread(new Runnable() {
			public void run() {
				try {
					sendMailBySynchronizationMode(map, mailSender, template);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}).start();
	}
	
	/**
	 * @Title: getMailText
	 * @Description: 通过模板构造邮件内容，参数map将替换模板文件中的${key}标签中相应的值
	 * @param map
	 * @param freeMarkerConfigurer
	 * @Return String
	 */
	private static String getMailText(Map<String, Object> map, Template template) {
		String htmlText = "";
		try {
			//解析模板并替换动态数据，最终map将替换模板文件中的${key}标签。       
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return htmlText;
	}

	
	

	/**
	 * @Title: sendMailBySynchronizationMode
	 * @Description: 同步发送 解析html模板文件，生成邮件内容
	 * @param map
	 * @param mailSender
	 * @Return void
	 */
	public static void sendMailBySynchronizationMode(Map<String, Object> map, JavaMailSender mailSender) {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper;

		/** 获取邮件信息 */
		Email email = (Email) map.get("email");
		/** 将邮件信息从map中清除* */
		map.remove("email");
		String content = email.getContent();
		
		String htmlTemplate = null;
		//如果邮件内容不存在,即根据模板生成，则获取模板信息
		if(CommonUtils.isEmpty(content)){
			/** 获取邮件模板信息* */
			htmlTemplate = (String) map.get("htmlTemplate");
			content = getMailText(map, htmlTemplate);
			if(CommonUtils.isNotEmpty(htmlTemplate)){
				/** 将模板信息从map中清除* */
				map.remove("htmlTemplate");
			}
		}

		try {
			helper = new MimeMessageHelper(mime, true, "utf-8");
			helper.setFrom(email.getSender());//发件人
			helper.setTo(email.getAddress());//收件人 
			if (CommonUtils.isNotEmpty(email.getCc())) {
				helper.setCc(email.getCc());// 抄送
			}
			if (CommonUtils.isNotEmpty(email.getBcc())) {
				helper.setBcc(email.getBcc());// 暗送
			}
			if (CommonUtils.isNotEmpty(email.getReplyTo())) {
				helper.setReplyTo(email.getReplyTo());// 回复到
			}
			helper.setSubject(email.getSubject());//邮件主题
			helper.setText(content, true);//true表示设定html格式

			//内嵌资源，这种功能很少用，因为大部分资源都在网上，只需在邮件正文中给个URL就足够了.  
			//helper.addInline("logo", new ClassPathResource("logo.gif"));    

			//处理附件
			for (MultipartFile file : email.getAttachment()) {
				if (file == null || file.isEmpty()) {
					continue;
				}
				String fileName = file.getOriginalFilename();
				try {
					fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
				} catch (Exception e) {
				}
				helper.addAttachment(fileName, new ByteArrayResource(file.getBytes()));
			}
			mailSender.send(mime);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	  * @Title: sendMailByAsynchronousMode
	  * @Description: 异步发送  解析html模板文件，生成邮件内容
	  * @param map
	  * @param mailSender
	  * @Return void
	 */
	public static void sendMailByAsynchronousMode(final Map<String, Object> map, final JavaMailSender mailSender) {
		new Thread(new Runnable() {
			public void run() {
				try {
					sendMailBySynchronizationMode(map, mailSender);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}).start();
	}

	
	//通过模板构造邮件内容，将模板中的变量替换成map中的值,注:模板中的变量与map中的key保持一致。       
	public static String getMailText(Map<String, Object> map, String htmlTemplate) {
		String htmlText = htmlTemplate;
		//遍历Map中的所有Key，将得到的value值替换模板字符串中的变量值
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			htmlText = htmlText.replace("${" + key + "}", (String) map.get(key));
		}
		return htmlText;
	}
}
