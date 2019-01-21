package com.waio.email.api;

import java.util.Map;

import com.waio.verification.model.OTPSystem;

public interface IEmailService {

	/**
	 * @param request
	 * @param model
	 * @param templateName
	 * @return
	 */
	MailResponse sendEmail(MailRequest request, Map<String, Object> model, String templateName);
	/**
	 * @param request
	 * @param model
	 * @param templateName
	 * @return
	 */
	MailResponse sendResetEmail(OTPSystem request, Map<String, Object> model, String templateName);
}
