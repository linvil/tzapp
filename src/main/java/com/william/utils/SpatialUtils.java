package com.william.utils;

import java.math.BigDecimal;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class SpatialUtils {

	public static Point toPoint(Double double1, Double double2) {
		String wktPoint = "POINT(" + double1 + " " + double2 + ")";
		WKTReader fromText = new WKTReader();
		Geometry geom = null;
		try {
			geom = fromText.read(wktPoint);
		} catch (ParseException e) {
			throw new RuntimeException("Not a WKT string:" + wktPoint);
		}
		return (Point) geom;
	}

	public static void main(String[] args) {
		System.out.println(toPoint(200.00,199.01));
	}

}
