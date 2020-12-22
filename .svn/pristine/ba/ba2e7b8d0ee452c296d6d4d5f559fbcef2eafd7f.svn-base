package com.rsaame.pas.b2c.controllers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rsaame.pas.b2c.cmn.base.BaseController;

@Controller
public class CaptchaLoader extends BaseController {

	@RequestMapping(value = "**/loadCaptcha.do", method = RequestMethod.POST)
	public   void loadCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//System.out.println("HHH - Inside the loadCaptcha nethod");
		
		int width = 150;
		int height = 50;
		HttpSession session = request.getSession(false);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		
		

		Graphics2D g2d = bufferedImage.createGraphics();
		
		Font font = new Font("Times New Roman", Font.BOLD, 25); 
																
		g2d.setFont(font);
		

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		

		GradientPaint gp = new GradientPaint(0, 0, new Color(211, 211, 211), 0,
				height / 2, new Color(211, 211, 211), true);
		

		g2d.setPaint(gp);
		
		g2d.fillRect(0, 0, width, height);
		
		g2d.setColor(new Color(255, 20, 147));
		

		Random r = new Random();
		int index = Math.abs(r.nextInt()) % 5;
		StringBuffer sb = new StringBuffer();
		int j = 0;
		while (j < 6) {
			char ch = (char) (int) (Math.random() * 79 + 23 + 7);
			if (Character.isDigit(ch)) { 
											
				sb.append(ch);
				++j;
			}
		}
		String captchaCodeDisplay = sb.toString();
		session.setAttribute("captchaCode", captchaCodeDisplay);
		
		//System.out.println("HHH - captchaCodeDisplay IS: " + captchaCodeDisplay);

		
		char data1[][] = { captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray() };
		int x = 0;
		int y = 30; 
					
		for (int i = 0; i < data1[index].length; i++) {
			
			x += 20;
			g2d.drawChars(data1[index], i, 1, x, y);
			
		}

		g2d.dispose();
		
		String imgFilePath = request.getSession().getServletContext().getRealPath("/")	+ "/static/img/capImg.PNG" ;
		//System.out.println("HHH - Captcha Image real path is : " + imgFilePath) ;

		ImageIO.write(bufferedImage, "png", new File(imgFilePath));
		
		String respString = "{\"code\":\"" + captchaCodeDisplay + "\"}" ;
		
		//System.out.println("HHH - Captcha Response JSON IS :" + respString);
		
		JSONObject jsonObj = new JSONObject(respString);
		
	    response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonObj.toString());
	}
	
	public   void renderCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//System.out.println("HHH - Inside the renderCaptcha method.");
		
		int width = 150;
		int height = 50;
		HttpSession session = request.getSession(false);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		
		

		Graphics2D g2d = bufferedImage.createGraphics();
		
		Font font = new Font("Times New Roman", Font.BOLD, 25); 
																
		g2d.setFont(font);
		

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		

		GradientPaint gp = new GradientPaint(0, 0, new Color(211, 211, 211), 0,
				height / 2, new Color(211, 211, 211), true);
		

		g2d.setPaint(gp);
		
		g2d.fillRect(0, 0, width, height);
		
		g2d.setColor(new Color(255, 20, 147));
		

		Random r = new Random();
		int index = Math.abs(r.nextInt()) % 5;
		StringBuffer sb = new StringBuffer();
		int j = 0;
		while (j < 6) {
			char ch = (char) (int) (Math.random() * 79 + 23 + 7);
			if (Character.isDigit(ch)) { 
											
				sb.append(ch);
				++j;
			}
		}
		String captchaCodeDisplay = sb.toString();
		session.setAttribute("captchaCode", captchaCodeDisplay);
		
		//System.out.println("HHH - captchaCodeDisplay IS: " + captchaCodeDisplay);

		
		char data1[][] = { captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray(),
				captchaCodeDisplay.toCharArray() };
		int x = 0;
		int y = 30; 
					
		for (int i = 0; i < data1[index].length; i++) {
			
			x += 20;
			g2d.drawChars(data1[index], i, 1, x, y);
			
		}

		g2d.dispose();
		
		String imgFilePath = request.getSession().getServletContext().getRealPath("/")	+ "/static/img/capImg.PNG" ;
		//System.out.println("HHH - Captcha Image real path is : " + imgFilePath) ;

		ImageIO.write(bufferedImage, "png", new File(imgFilePath));
		
	}


		
	}
	

