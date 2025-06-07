package org.ms.gatewayservice.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Hashtable;
import java.util.Map;



	@RefreshScope
	@RestController
	public class gatewayRestController {
		 @Value("${globalParam}")
		 private int globalParam;
		 @Value("${monParam}")
		 private int monParam;
		 @Value("${email}")
		 private String email;

		 @GetMapping("config")
		 public Map<String,Object> config () {
		 Map<String,Object> params = new Hashtable<>();
		 params.put("globalParam", globalParam);
		 params.put("monParam", monParam);
		 params.put("email", email);
		 params.put("threadName", Thread.currentThread().toString());
		 return params;
		 }
		}
