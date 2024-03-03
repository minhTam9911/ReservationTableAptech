package com.bookingtable.servicies;

public interface IMailService {
	boolean send(String from, String to, String subject, String content);
}
