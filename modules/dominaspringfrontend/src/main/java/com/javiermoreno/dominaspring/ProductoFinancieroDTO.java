package com.javiermoreno.dominaspring;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.javiermoreno.dominaspring.domain.ProductoFinanciero;

@XmlRootElement
public class ProductoFinancieroDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private BigDecimal saldo;

	
	
	public ProductoFinancieroDTO() {
		super();
	}

	public ProductoFinancieroDTO(ProductoFinanciero producto) {
		this.codigo = producto.getCodigo();
		this.saldo = producto.getSaldo();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	
	
}
