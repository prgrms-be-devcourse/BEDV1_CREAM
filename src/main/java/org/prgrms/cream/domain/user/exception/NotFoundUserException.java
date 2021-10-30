package org.prgrms.cream.domain.user.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.NotFoundException;

public class NotFoundUserException extends NotFoundException {

	public NotFoundUserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
