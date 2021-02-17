package com.bank.mani.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ManiBankUtils {

	static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ManiBankConsonents.YYYYMMDD_HHMMSS);

	public static LocalDateTime convertToLocalDateTime(String date) throws DateTimeParseException {
		return LocalDateTime.parse(date, dateTimeFormatter);
	}

	public static String convertToString(LocalDateTime localDateTime) throws DateTimeParseException {
		return localDateTime.format(dateTimeFormatter);
	}

	public static LocalDateTime getFormattedLocalDateTime(LocalDateTime localDateTime) {
		return convertToLocalDateTime(convertToString(localDateTime));
	}

}
