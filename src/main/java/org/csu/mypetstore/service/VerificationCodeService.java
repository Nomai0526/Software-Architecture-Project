package org.csu.mypetstore.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationCodeService {
    public void getVerify(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BufferedImage bfi = new BufferedImage(80,25,BufferedImage.TYPE_INT_RGB);
        Graphics g = bfi.getGraphics();
        g.fillRect(0, 0, 80, 25);

        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random r = new Random();
        int index;
        StringBuffer sb = new StringBuffer(); //保存字符串
        for(int i=0; i<4; i++){
            index = r.nextInt(ch.length);
            g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
            Font font = new Font("宋体", 30, 20);
            g.setFont(font);
            g.drawString(ch[index]+"", (i*20)+2, 23);
            sb.append(ch[index]);
        }

        session.setAttribute("verificationCode", sb.toString());
        System.out.println((String) session.getAttribute("verificationCode")+session+1);
        g.dispose();
        try {
            ImageIO.write(bfi, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color interLine(int Low, int High) {
        if (Low > 255)
            Low = 255;
        if (High > 255)
            High = 255;
        if (Low < 0)
            Low = 0;
        if (High < 0)
            High = 0;
        int interval = High - Low;
        int r = Low + (int) (Math.random() * interval);
        int g = Low + (int) (Math.random() * interval);
        int b = Low + (int) (Math.random() * interval);
        return new Color(r, g, b);
    }

    public boolean check(HttpSession session,String code)
    {
        String answer = (String) session.getAttribute("verificationCode");
        answer.equals("111");
        if (answer.equals(code))
        {
            return true;
        }
        else return false;
    }
}
