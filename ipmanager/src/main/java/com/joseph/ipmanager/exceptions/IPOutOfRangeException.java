package com.joseph.ipmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "IP Address of Pool Range")
public class IPOutOfRangeException extends Exception {
}
