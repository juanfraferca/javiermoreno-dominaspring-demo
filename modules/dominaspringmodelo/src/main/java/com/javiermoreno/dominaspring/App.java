package com.javiermoreno.dominaspring;

import java.math.BigDecimal;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.javiermoreno.dominaspring.services.GestionProductosServ;

public class App {

	public static void main(String[] args) {
		System.out.println("Inicio.");
		
		BasicConfigurator.configure();
		
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");
		
		GestionProductosServ serv = ctx.getBean(GestionProductosServ.class);
		serv.transferir("2000", "1000", new BigDecimal("100"));
		
		System.out.println("Fin.");
	}
	
}
