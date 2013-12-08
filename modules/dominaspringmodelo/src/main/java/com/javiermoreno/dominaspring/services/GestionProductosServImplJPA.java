package com.javiermoreno.dominaspring.services;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javiermoreno.dominaspring.domain.CuentaCorriente;
import com.javiermoreno.dominaspring.domain.Movimiento;
import com.javiermoreno.dominaspring.domain.ProductoFinanciero;
import com.javiermoreno.dominaspring.domain.Retry;
import com.javiermoreno.dominaspring.framework.BusinessException;

@Service
public class GestionProductosServImplJPA implements GestionProductosServ {

	static final BigDecimal MAXIMO_PERMITIDO = new BigDecimal("1500.00");
	static final BigDecimal IMPORTE_COMISION = new BigDecimal("3.00");
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Movimiento ingresar(String codigoDestino, BigDecimal importe) {
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Transactional
	public Movimiento reintegrar(String codigoOrigen, BigDecimal importe) {
		throw new UnsupportedOperationException("Not implemented.");
	}
	
	@Transactional
	public ProductoFinanciero obtenerProducto(String codigo) {
		ProductoFinanciero productoFinanciero = em.find(ProductoFinanciero.class, codigo);
		if (productoFinanciero == null) throw new javax.persistence.EntityNotFoundException(MessageFormat.format("Destino {0} no encontrado.", codigo));
		return productoFinanciero;
	}

	@Retry(4)
	@Transactional
	public Movimiento transferir(String codigoOrigen, String codigoDestino, BigDecimal importe) {
		BusinessException.comprobar(importe.compareTo(MAXIMO_PERMITIDO) > 0, 
				                    "Importe {0} supera el máximo {1} permitido.", importe, MAXIMO_PERMITIDO);
		
		CuentaCorriente origen = em.find(CuentaCorriente.class, codigoOrigen);
		BusinessException.comprobar(origen.getSaldo().compareTo(IMPORTE_COMISION) < 0, 
				                    "La cuenta corriente {0} no dispone de saldo para pagar la comisión {1}.", codigoOrigen, IMPORTE_COMISION);

		ProductoFinanciero destino = obtenerProducto(codigoDestino);
		
		origen.setSaldo(origen.getSaldo().subtract(IMPORTE_COMISION));
		Movimiento movimientoComision = new Movimiento(0, origen, new Date(), IMPORTE_COMISION);
		em.persist(movimientoComision);
		
		origen.transferir(destino, importe);
		
		Movimiento movimientoTransferencia = new Movimiento(0, origen, new Date(), importe);
		em.persist(movimientoTransferencia);
		
		
		return movimientoTransferencia;
	}

}
