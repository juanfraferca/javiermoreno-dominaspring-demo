package com.javiermoreno.dominaspring.services;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.javiermoreno.dominaspring.domain.CuentaCorriente;
import com.javiermoreno.dominaspring.framework.BusinessException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(/*classes={Log4jConfiguration.class, JPAConfiguration.class},*/
                      locations={"/META-INF/applicationContextTest.xml"})
@TransactionConfiguration(defaultRollback=true)
public class OperacionesFinancierasServImplTest {
	private static final Logger log = LoggerFactory.getLogger(OperacionesFinancierasServImplTest.class);
	
	@PersistenceUnit
	private EntityManagerFactory emFactory;
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
    private GestionProductosServ servicio;
	
	@Before
	public void inicializarDatabase() {
		log.info("Inicializando database.");
		EntityManager em = emFactory.createEntityManager();
		em.getTransaction().begin();
		CuentaCorriente cc = new CuentaCorriente("1111", new BigDecimal("1000.0"), new BigDecimal("0.03"));
		em.persist(cc);
		em.getTransaction().commit();
		em.close();
		
	}

	@After
	public void borrarDatabase() {
		log.info("Borrando database.");
		EntityManager em = emFactory.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.getReference(CuentaCorriente.class, "1111"));		
		em.getTransaction().commit();
		em.close();
	}

	/*
	@Test
	public void checkIngresarCorrecto() {
		servicio.ingresar("1111", new BigDecimal("1000.0"));
		
		CuentaCorriente cc = em.find(CuentaCorriente.class, "1111");		
		Assert.assertEquals("Saldo incorrectamente actualizado", 2000.0, cc.getSaldo().doubleValue(), 0.001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkIngresarImporteNegativo() {
		servicio.ingresar("1111", new BigDecimal("-1000.0"));
		
		CuentaCorriente cc = em.find(CuentaCorriente.class, "1111");		
		Assert.assertEquals("Saldo incorrectamente actualizado", 0.0, cc.getSaldo().doubleValue(), 0.001);
	}
	*/

	@Test(expected=BusinessException.class)
	public void checkTransferirPorEncimaDelMaximo() {
		servicio.transferir("2000", "1000", GestionProductosServImplJPA.MAXIMO_PERMITIDO.add(BigDecimal.ONE));
	}

	@Test(expected=javax.persistence.EntityNotFoundException.class)
	public void checkTransferirDestinoNoEncontrado() {
		servicio.transferir("2000", "9999", BigDecimal.ONE);
	}
	
	
	
	
}
