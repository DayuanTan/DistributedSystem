package com.javahelps.serviceSubtract;

import java.util.concurrent.TimeUnit;

public class ServiceSubtract {
	public float getDiff(float a, float b) throws Exception{
		TimeUnit.SECONDS.sleep(2);
		return a - b;
	}
	
	public float active(){
		return 1;
	}
}
