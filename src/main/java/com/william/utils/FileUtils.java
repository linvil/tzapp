package com.william.utils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.william.exception.FrameworkException;

@Component
public class FileUtils {
	
	
	public static final String IMG_TYPE_WXMEDIAID = "2";						// 微信图片MEDIAID
	public static final String IMG_TYPE_FILE = "1";								// 文件流

	public static String saveMultipartFile(CommonsMultipartFile multipartFile) {
		return saveMultipartFile2Path(multipartFile, 0);
	}

	public static String saveMultipartFile2Path(CommonsMultipartFile multipartFile, Integer businessType) {
		
		String virtualUrl = PropertyUtils.getProperty("head_virtualUrl");
		String physicalPath = PropertyUtils.getProperty("head_physicalPath");
		
		InputStream is;
		try {
			is = multipartFile.getInputStream();
		} catch (IOException e) {
			throw new FrameworkException(e);
		}

		String originalFilename = multipartFile.getOriginalFilename();
		int index = originalFilename.lastIndexOf(".");
		String extension = originalFilename.substring(index);

		String md5Str = HashUtils.getMd5(is);

		String filePath = md5Str + extension;
		String currentTime = FormatUtils.format(new Date(System.currentTimeMillis()), FormatUtils.DATE_FORMAT_YYYY_MM_DD);
		File file = new File(physicalPath + currentTime);
		if(!file.exists()){
			file.mkdirs();
		}
		File dest = new File(physicalPath + md5Str + extension);
		if (!dest.exists()) {
			try {
				multipartFile.transferTo(dest);
			} catch (Exception e) {
				throw new FrameworkException(e);
			}

			try {
				is.close();
			} catch (IOException e) {
				throw new FrameworkException(e);
			}
		}

		return virtualUrl + filePath;
	}

	public static String saveMultipartFile2ContextPath(CommonsMultipartFile multipartFile, String rootPath) {
		InputStream is;
		try {
			is = multipartFile.getInputStream();
		} catch (IOException e) {
			throw new FrameworkException(e);
		}

		String originalFilename = multipartFile.getOriginalFilename();
		int index = originalFilename.lastIndexOf(".");
		String extension = originalFilename.substring(index);

		String md5Str = HashUtils.getMd5(is);

		String realPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(rootPath);

		String filePath = rootPath + "/" + md5Str + extension;

		File dest = new File(realPath + "/" + md5Str + extension);
		try {
			multipartFile.transferTo(dest);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}

		try {
			is.close();
		} catch (IOException e) {
			throw new FrameworkException(e);
		}

		return filePath;
	}

//	public static String saveFile2ContextPath(File src, Integer businessType) {
//		InputStream is;
//		try {
//			is = new FileInputStream(src);
//		} catch (IOException e) {
//			throw new FrameworkException(e);
//		}
//		
//		String virtualUrl = "";
//		String physicalPath = "";
//		if (businessType.equals(0)) {
//			virtualUrl = ContextUtils.getParameter(null, "upload.virtual.address", true).getParamValue();
//			physicalPath = ContextUtils.getParameter(null, "upload.physical.address", true).getParamValue();
//		} else {
//			virtualUrl = ContextUtils.getParameter(null, "upload.virtual.address", true).getParamValue();
//			physicalPath = ContextUtils.getParameter(null, "upload.physical.address", true).getParamValue();
//		}
//
//		String originalFilename = src.getName();
//		int index = originalFilename.lastIndexOf(".");
//		String extension = originalFilename.substring(index);
//
//		String md5Str = HashUtils.getMd5(is);
//
//		String filePath = md5Str + extension;
//
//		File dest = new File(physicalPath + md5Str + extension);
//		try {
//			FileCopyUtils.copy(src, dest);
//		} catch (Exception e) {
//			throw new FrameworkException(e);
//		}
//
//		try {
//			is.close();
//		} catch (IOException e) {
//			throw new FrameworkException(e);
//		}
//		
//		return virtualUrl + filePath;
//	}
//
//	public static String saveFile2ContextPath(CommonsMultipartFile multipartFile, String rootPath) {
//		InputStream is;
//		try {
//			is = multipartFile.getInputStream();
//		} catch (IOException e) {
//			throw new FrameworkException(e);
//		}
//
//		String originalFilename = multipartFile.getOriginalFilename();
//		int index = originalFilename.lastIndexOf(".");
//		String extension = originalFilename.substring(index);
//
//		String md5Str = HashUtils.getMd5(is);
//
//		String realPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(rootPath);
//
//		String filePath = rootPath + "/" + md5Str + extension;
//
//		File dest = new File(realPath + "/" + md5Str + extension);
//		try {
//			multipartFile.transferTo(dest);
//		} catch (Exception e) {
//			throw new FrameworkException(e);
//		}
//
//		try {
//			is.close();
//		} catch (IOException e) {
//			throw new FrameworkException(e);
//		}
//
//		return filePath;
//	}
//	
//	public static String createThumb(String url, Double scale, Integer width, Integer height, Float quality) {
//		String physicalPath = ContextUtils.getParameter(null, "upload.physical.address", true).getParamValue();
//		String virtualUrl = ContextUtils.getParameter(null, "upload.virtual.address", true).getParamValue();
//		
//		int index = url.lastIndexOf("/");
//		String fileName = "";
//		if (-1 == index) {
//			fileName = url;
//		} else {
//			fileName = url.substring(index+1);
//		}
//		String realPath = physicalPath.concat(fileName);
//
//		String thumbPath = physicalPath.concat("/thumb/").concat(fileName);
//		
//		// 判断有没有，有就不用写入
////		System.out.println("====thumbPath====" + thumbPath);
//		File dest = new File(thumbPath);
//		if (!dest.exists()) {
//			try {
////				System.out.println("====write====" + thumbPath);
//				if ( null == scale) {
//					Thumbnails.of(realPath) // 原图文件的路径
//							.size(width, height)
//							.outputQuality(quality.floatValue()) // 图片质量，值是在0到1，越接近于1质量越好，越接近于0质量越差。
//							.toFile(thumbPath); // 压缩后文件的路径
//				} else {
//					Thumbnails.of(realPath) // 原图文件的路径
//							.scale(scale.doubleValue()) // 指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽,.size(200, 300) 宽200高300
//							.outputQuality(quality.floatValue()) // 图片质量，值是在0到1，越接近于1质量越好，越接近于0质量越差。
//							.toFile(thumbPath); // 压缩后文件的路径
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		return virtualUrl.concat("/thumb/").concat(fileName);
//	}
//
//	public static String saveWeixinFile(String mediaId, Integer businessType) {
//		File temp = weixinDao.downloadMediaFile(mediaId);
//
//		String filePath = FileUtils.saveFile2ContextPath(temp, businessType);
//
//		temp.delete();
//
//		return filePath;
//	}
//
//	public static String getImgUrl(String imgUrl, String imgType, Integer businessType){
//		if (FileUtils.IMG_TYPE_FILE.equals(imgType)) {
//			return imgUrl.concat(";").concat(FileUtils.createThumb(imgUrl, Double.valueOf(0.25f), null, null, Float.valueOf(0.5f)));
//		}else if (FileUtils.IMG_TYPE_WXMEDIAID.equals(imgType)) {
//			imgUrl = FileUtils.saveWeixinFile(imgUrl, businessType);
//			return imgUrl.concat(";").concat(FileUtils.createThumb(imgUrl, Double.valueOf(0.25f), null, null, Float.valueOf(0.5f)));
//		}else{
//			return null;
//		}
//	}
}
