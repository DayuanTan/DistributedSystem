package com.javahelps.servicesum;

public class Client {
	public static void main(String[] args) throws Exception{
		//Create the stub object
		ServiceSumStub stub = new ServiceSumStub();
		
		//Create the request
		ServiceSumStub.GetSum request = new ServiceSumStub.GetSum();
		
		for (float i = 1; i < 10; i++) {
			//Set the parameters
			float a = i;
			float b = i + 1;
			request.setA(a);
			request.setB(b);
			
			//Invoke the service
			ServiceSumStub.GetSumResponse response = stub.getSum(request);
			float res = response.get_return();
			System.out.println("The sum of " + a +" and " + b + " is :" + res);	
		}
	}
}
