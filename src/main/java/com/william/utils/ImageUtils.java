package com.william.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ImageUtils {

	private static Random random = new Random();
	private static Font mFont = new Font("Times New Roman", Font.PLAIN, 17);

	public static void generateCaptcha(String captcha, OutputStream os) {
		int width = 100, height = 18;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, width - 1, height - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setFont(mFont);

		g.setColor(getRandColor(160, 200));

		// 画随机线
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 从另一方向画随机线
		for (int i = 0; i < 70; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}

		// 生成随机数,并将随机数字转换为字母
		int length = captcha.length();
		for (int i = 0; i < length; i++) {
			char c = captcha.charAt(i);
			captcha += c;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

			String text = String.valueOf(c);
			if (g instanceof Graphics2D) {
				// Graphics2D g2d = (Graphics2D) g;
				Graphics2D g2d = (Graphics2D) g.create();
				Rectangle2D r = g2d.getFontMetrics().getStringBounds(text, g2d);
				g2d.translate(15 * i + 10 - r.getWidth() / 2, 16 - r.getHeight() / 2);
				// g2d.translate(r.getX(), r.getY());
				// g2d.translate(-r.getCenterX(), -r.getCenterY());
				// g2d.rotate(Math.PI);
				// g2d.translate(-r.getCenterX(), -r.getCenterY());
				// g2d.drawString(text, 0, 0);
				g2d.rotate(-random.nextDouble() * Math.PI);
				// g2d.translate(-(15 * i + 10 + r.getCenterX()), -(16 +
				// r.getCenterY()));
				// g2d.translate(r.getCenterX(), r.getCenterY());
				g2d.translate(-r.getCenterX(), -r.getCenterY());
				g2d.drawString(text, 0, 0);
			} else {
				g.drawString(text, 15 * i + 10, 16);
			}
		}

		g.dispose();
		try {
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Color getRandColor(int fc, int bc) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static void generateQR(String website, OutputStream output) throws WriterException, IOException {
		int width = 300;
		int height = 300;
		String format = "png";
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bm = new MultiFormatWriter().encode(website, BarcodeFormat.QR_CODE, width, height, hints);

		BufferedImage image = toImage(bm);
		ImageIO.write(image, format, output); // 把二维码写到response的输出流
	}

	private static BufferedImage toImage(BitMatrix bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bm.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}
		return image;
	}

}
