package com.ridvancopur.web.rest.errors;

import static com.ridvancopur.web.rest.errors.ErrorConstants.EMAIL_ALREADY_USED_TYPE;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
