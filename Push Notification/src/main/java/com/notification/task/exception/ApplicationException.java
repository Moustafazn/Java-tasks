package com.notification.task.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApplicationException extends Exception {

  private static final long serialVersionUID = 1L;

  public ApplicationException(ErrorCode errorCode) {
    super(errorCode.getDescription());
  }

}
