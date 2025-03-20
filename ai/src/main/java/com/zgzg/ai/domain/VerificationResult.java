package com.zgzg.ai.domain;

import lombok.Getter;

@Getter
public class VerificationResult {

	private boolean valid;
	private String errors;
	private String suggesteMessage;

	public VerificationResult(boolean valid, String errors, String suggesteMessage) {
		this.valid = valid;
		this.errors = (errors != null) ? errors : "";
		this.suggesteMessage = suggesteMessage;
	}

}
