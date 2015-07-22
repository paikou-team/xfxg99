package com.xfxg99.test;
import java.rmi.RemoteException;   

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
public class WebServiceTest {
	 public String invokeRemoteFuc() { 
		 /*  try {
	        	final String endpoint = "http://61.143.53.250:1388/Egean.asmx";
	        	   String opName = "method1";

	        	   String param="xxx";
	        	   Object[] opArgs = new Object[] { param };

	        	   Class<?>[] opReturnType = new Class[] { String[].class };

	        	   RPCServiceClient serviceClient = new RPCServiceClient();//此处RPCServiceClient 对象实例建议定义成类中的static变量，否则多次调用会出现连接超时的错误。
	        	   Options options = serviceClient.getOptions();
	        	   EndpointReference targetEPR = new EndpointReference(endpoint);
	        	   options.setTo(targetEPR);

	        	   QName opQName = new QName("http://service.ws.sms.ipcc.ydtf.com",
	        	     opName);
	        	   Object[] ret = serviceClient.invokeBlocking(opQName, opArgs,
	        	     opReturnType);
	        	   System.out.println(((String[]) ret[0])[0]);

	        	  } catch (AxisFault e) {
	        	     e.printStackTrace();
	        	  }*/
	        return null;
	    }
	 
	    public static void main(String[] args) {
	        WebServiceTest t = new WebServiceTest();
	        String result = t.invokeRemoteFuc();
	        System.out.println(result);
	    }
}
