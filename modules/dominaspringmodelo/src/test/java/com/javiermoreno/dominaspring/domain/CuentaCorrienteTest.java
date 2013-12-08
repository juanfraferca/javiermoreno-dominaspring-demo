package com.javiermoreno.dominaspring.domain;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.javiermoreno.dominaspring.framework.BusinessException;

public class CuentaCorrienteTest {

	@Test(expected=BusinessException.class)
	public void checkTransferenciaSinSaldoSuficiente() {
		CuentaCorriente cc = new CuentaCorriente("1111", new BigDecimal("1000.0"), new BigDecimal("0.02"));
		ProductoFinanciero pf = new ProductoFinanciero("2222", new BigDecimal("0.0"), new BigDecimal("0.0"));
		
		cc.transferir(pf, new BigDecimal("5000.0"));
	}

	@Test
	public void checkTransferenciaCorrecta() {
		CuentaCorriente cc = new CuentaCorriente("1111", new BigDecimal("1000.0"), new BigDecimal("0.02"));
		ProductoFinanciero pf = new ProductoFinanciero("2222", new BigDecimal("0.0"), new BigDecimal("0.0"));
		
		cc.transferir(pf, new BigDecimal("500.0"));
		
		Assert.assertEquals("Cuenta corriente de origen no descontó correctamente el importe.", 
	            1000.0-500.0, cc.getSaldo().doubleValue(), 0.001);
		Assert.assertEquals("Cuenta corriente de destino no recibió correctamente el importe.", 
	            0.0+500.0, pf.getSaldo().doubleValue(), 0.001);
	}

	
}
