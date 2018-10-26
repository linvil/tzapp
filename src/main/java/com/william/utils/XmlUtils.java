package com.william.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.william.exception.BeanException;

public class XmlUtils {

	public static String toXMLString(Object obj) {
		try {
			JAXBContext jc = JAXBContext.newInstance(obj.getClass());
			Marshaller mar = jc.createMarshaller();
			mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的XML串
			mar.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略XML头声明信息

			StringWriter sw = new StringWriter();

			mar.marshal(obj, sw);

			return sw.toString();
		} catch (JAXBException e) {
			throw new BeanException("输出XML报文出错", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T parseXML(String xml, Class<T> clazz) {
		try {
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller unmar = jc.createUnmarshaller();

			return (T) unmar.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new BeanException("解析报文出错", e);
		}
	}

	public static Map<String, String> parseXML(String xml) {
		Map<String, String> map = new HashMap<String, String>();

		SAXReader saxReader = new SAXReader();

		Document document;
		try {
			document = saxReader.read(new StringReader(xml));
			Element root = document.getRootElement();

			for (Iterator<?> i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();

				map.put(element.getName(), element.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return map;
	}

}
