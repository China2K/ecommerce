package com.dk.core.common.utils.mail;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: Email
 * @Description: 邮件相关的基础信息，但不包括邮件内容
 */
public class Email implements Serializable
{

	private static final long	serialVersionUID	= 1L;
	/** 发件人 **/
	private String				sender;
	/** 收件人* */
	private String				address;
	/** 抄送给* */
	private String				cc;
	/** 暗送给* */
	private String				bcc;
	/** 恢复给* */
	private String				replyTo;
	/** 邮件主题* */
	private String				subject;
	/** 邮件模板 **/
	private String              template;
	/** 邮件内容*/
	private String              content;
	/** 附件* */
	private MultipartFile[]		attachment			= new MultipartFile[0];

	//////////////////////////解析邮件地址//////////////////////////////  
	public String[] getAddress()
	{
		return address.split(";");
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String[] getCc()
	{
		return cc != null ? cc.split(";") : null;
	}

	public void setCc(String cc)
	{
		this.cc = cc;
	}

	public String[] getBcc()
	{
		return bcc != null ? bcc.split(";") : null;
	}

	public void setBcc(String bcc)
	{
		this.bcc = bcc;
	}

	public String getReplyTo()
	{
		return replyTo;
	}

	public void setReplyTo(String replyTo)
	{
		this.replyTo = replyTo;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public MultipartFile[] getAttachment()
	{
		return attachment;
	}

	public void setAttachment(MultipartFile[] attachment)
	{
		this.attachment = attachment;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

}
