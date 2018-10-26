package com.william.controller.qrcode;


import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.william.exception.FrameworkException;
import com.william.utils.QRCodeUtils;



@Controller
@RequestMapping("/api/v1/qrcode")
public class QrCodeController {
	

	@RequestMapping(value = "/getQRCode", method = RequestMethod.GET)
	public void getShareQrcodeOS(HttpServletResponse response, 
			@RequestParam(value = "content", required = true) String conent) {

		OutputStream os = null;
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("image/jpeg");
		
		try {
			QRCodeUtils.getEncodeOS(conent, null, os, false);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
		
	}
  

}
