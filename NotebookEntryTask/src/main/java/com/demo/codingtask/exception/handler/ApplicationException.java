package com.demo.codingtask.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationException extends RuntimeException {
	private String code;
	private String errorCode;
	private String wsUuid;

	public ApplicationException(Throwable th) {
		super(th);
	}

	public ApplicationException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApplicationException(String code, String message, Throwable th) {
		super(message, th);
		this.code = code;
	}

}
