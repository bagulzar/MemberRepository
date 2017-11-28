package com.fr.memberservice.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fr.memberservice.models.Member;

public class ValidationUtil {

	public static boolean isDOBValid(String dob) {
		boolean isValid = false;

		try {

			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date dobDate = dateFormat.parse(dob);

			String reportDate = df.format(dobDate);

			isValid = reportDate.equals(dob) && dobDate.before(Calendar.getInstance().getTime());
		} catch (Exception ignore) {
			isValid = false;
		}

		return isValid;

	}

	public static Map<String, String> validateMemberRequest(Member member) {
		boolean isValidRequest = true;
		
		Map<String, String> validationErros = new HashMap<String, String>(0);

		if (member == null) {
			isValidRequest = false;
			validationErros.put("Member", "Member is null or empty");
			
		}

		if (CommonUtils.isNullOrEmpty(member.getFirstName())) {
			isValidRequest = false;
			validationErros.put("FirstName", "FirstName is null or empty");
		}

		if (CommonUtils.isNullOrEmpty(member.getLastName())) {
			isValidRequest = false;
			validationErros.put("LastName", "LastName is null or empty");
		}

		if (CommonUtils.isNullOrEmpty(member.getDob())) {
			isValidRequest = false;
			validationErros.put("Date of Birth", "Date of Birth is null or empty");
		}

		if (CommonUtils.isNullOrEmpty(member.getPostalCode())) {
			isValidRequest = false;
			validationErros.put("Postal Code", "Postal Code is null or empty");
		}
		
		if(isValidRequest && !isDOBValid(member.getDob())) {
			isValidRequest = false;
			validationErros.put("Date of Birth", "Date of Birth is invalid. It must be in dd.MM.yyyy");
		}

		return validationErros;
	}

}
