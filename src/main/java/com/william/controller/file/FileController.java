package com.william.controller.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.william.model.ResponseBean;
import com.william.utils.FileUtils;


@Controller
@RequestMapping("/api/v1/files")
public class FileController {

	@ResponseBody
	@RequestMapping("/uploadFile")
	public Object upload(@RequestParam(value = "file", required = false) CommonsMultipartFile[] files) {
		List<String> list = new ArrayList<>();
		System.out.println("文件上传");
		if (files != null) {
			for (CommonsMultipartFile file : files) {
				String path = FileUtils.saveMultipartFile(file);
				list.add(path);
			}
		} 
		ResponseBean responseBean = new ResponseBean();
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		map.put("url", list);
		responseBean.setData(map);
		return responseBean;
	}

}
