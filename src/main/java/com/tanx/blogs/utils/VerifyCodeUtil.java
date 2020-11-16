package com.tanx.blogs.utils;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * @author tan
 */
public class VerifyCodeUtil {
    private static ByteArrayOutputStream bo;
    private static final String NUMBER_CHAR = "ABCDEFJHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String getRandomChar(int count, String code) {
        StringBuilder builder = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            builder.append(code.charAt(RANDOM.nextInt(code.length())));
        }
        return builder.toString();
    }

    public static String getRandomChar() {

        return getRandomChar(4, NUMBER_CHAR);
    }

    public static String getRandomCharCount(int count) {
        return getRandomChar(count, NUMBER_CHAR);
    }

    public static String getRandomNumber() {
        String numberChar = "0123456789";
        return getRandomChar(4, numberChar);
    }

    public static String drawImage(int width, int height) {
        bo = new ByteArrayOutputStream();
        char[] data = getRandomChar().toCharArray();
        BufferedImage bi = new BufferedImage(width, height, 5);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();

        graphics.setBackground(new Color(255, 255, 255));
        graphics.fillRect(0, 0, width, height);

        drawLine(graphics, RANDOM.nextInt(8) + 1, width, height);

        fontColor(graphics, data);
        graphics.dispose();
        try {
            ImageIO.write(bi, "png", bo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(data);
    }

    private static void fontColor(Graphics2D graphics, char[] data) {
        graphics.setFont(new Font("\u5B8B\u4F53", Font.BOLD, 28));

        for (int i = 0; i < data.length; i++) {
            graphics.setColor(new Color(50 + RANDOM
                    .nextInt(100), 50 + RANDOM
                    .nextInt(100), 50 + RANDOM
                    .nextInt(100)));
            graphics.drawString(String.valueOf(data[i]), 30 * i + 15, 25 + RANDOM.nextInt(8));
        }
    }

    private static void drawLine(Graphics2D graphics, int line, int width, int height) {
        for (int i = 0; i < line; i++) {
            graphics.setColor(new Color(40 + RANDOM
                    .nextInt(150), 40 + RANDOM
                    .nextInt(150), 40 + RANDOM
                    .nextInt(150)));
            int x1 = RANDOM.nextInt(width);
            int y1 = RANDOM.nextInt(height);
            int x2 = RANDOM.nextInt(width);
            int y2 = RANDOM.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    public static byte[] getByteArrayOutputStream() {
        return bo.toByteArray();
    }
}
