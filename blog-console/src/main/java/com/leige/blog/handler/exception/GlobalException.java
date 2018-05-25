package com.leige.blog.handler.exception;


import com.leige.blog.common.enums.ResultEnum;

public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private ResultEnum cm;
	
	public GlobalException(ResultEnum cm) {
		super(cm.toString());
		this.cm = cm;
	}

	public ResultEnum getCm() {
		return cm;
	}

}
