package com.crowley.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtil {
	
	public static void paintQRCode(Graphics g, String message, int x, int y) {
        
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, Constants.QR_CODE_WIDTH, Constants.QR_CODE_HEIGHT, null);
            BufferedImage image = new BufferedImage(Constants.QR_CODE_WIDTH, Constants.QR_CODE_HEIGHT , BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, Constants.QR_CODE_WIDTH, Constants.QR_CODE_HEIGHT);
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < Constants.QR_CODE_WIDTH; i++) {
                for (int j = 0; j < Constants.QR_CODE_HEIGHT; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            g.drawImage(image, x, y, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
		
	}
	
}
