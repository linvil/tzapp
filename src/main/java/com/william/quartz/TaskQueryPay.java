package com.william.quartz;


import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class TaskQueryPay extends QuartzJobBean {

//	@Autowired
//	private OrderService orderService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		
		
		JobDetail detail = jobExecutionContext.getJobDetail();	
		JobDataMap jobDataMap = detail.getJobDataMap();
		
//		OrderDTO orderDTO = new OrderDTO();
//		orderDTO.setId(Integer.valueOf(jobDataMap.getInt("orderId")));
		/*orderDTO.setBankMchtId(jobDataMap.getString("bankMchtId"));
		orderDTO.setMerPrivateKey(jobDataMap.getString("merPrivateKey"));
		orderDTO.setTransId(jobDataMap.getString("transId"));*/
		Integer times = jobDataMap.getIntegerFromString("times");
		/*QuartzManagerUtils.removeJob("querPay", orderDTO.getTransId(), "csb", orderDTO.getTransId());*/
		
		/*boolean goOn = orderService.procOrderQuery(orderDTO);
		System.out.println("times---: " + times.toString());
		if (goOn && 5 > times) {
			times = times + 1;
			jobDataMap.put("times", times.toString());
			QuartzManagerUtils.addJob("querPay", orderDTO.getTransId(), "csb", orderDTO.getTransId(), TaskQueryPay.class, "0/20 * * * * ?", jobDataMap);
		}*/
/*		JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		System.out.println("key---: " + str);
		for (Map.Entry entry : jobDataMap.entrySet()) {
			System.out.println("key---: " + entry.getKey() + "value---: " + entry.getValue());
		}*/

	}

}
