package com.BernielDE.blackwizard.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Progress {
	
	boolean inProgress();
	
	String TODO();

}
