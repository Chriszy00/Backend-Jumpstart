package com.jumpstart.exception;

public class RoleNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7771803609526874041L;

	public RoleNotFoundException(String roleName){
		   super("Could not fetch role with name "+ roleName);
	    }
}
