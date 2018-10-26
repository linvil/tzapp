package com.william.task;
import java.net.ConnectException;
import java.util.Date;

import org.springframework.stereotype.Component;





@Component
public class Task{
	public  void work() throws ConnectException {
		System.out.println(new Date());
	}
}
