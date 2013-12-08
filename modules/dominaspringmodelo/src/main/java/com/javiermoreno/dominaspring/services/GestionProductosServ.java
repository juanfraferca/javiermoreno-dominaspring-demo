package com.javiermoreno.dominaspring.services;

import java.math.BigDecimal;

import com.javiermoreno.dominaspring.domain.Movimiento;
import com.javiermoreno.dominaspring.domain.ProductoFinanciero;

public interface GestionProductosServ {

	ProductoFinanciero obtenerProducto(String codigo);
	
	Movimiento ingresar(String codigoDestino, BigDecimal importe);
	
	Movimiento reintegrar(String codigoOrigen, BigDecimal importe);
	
	Movimiento transferir(String codigoOrigen, String codigoDestino, BigDecimal importe);
	
}
