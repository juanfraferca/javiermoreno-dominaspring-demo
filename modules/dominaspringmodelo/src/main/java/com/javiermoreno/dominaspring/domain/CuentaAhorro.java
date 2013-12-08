package com.javiermoreno.dominaspring.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CA")
public class CuentaAhorro extends ProductoFinanciero implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean penalizado;

    public CuentaAhorro() {
    }
    
    

    public CuentaAhorro(String codigo, BigDecimal saldo, BigDecimal interesAnual, boolean penalizado) {
		super(codigo, saldo, interesAnual);
		this.penalizado = penalizado;
	}



	@Override
    public void reintegrar(BigDecimal importe) {
        super.reintegrar(importe);
        this.setPenalizado(true);
    }

    @Override
    public void actualizarInteresMensual() {
    	if (penalizado == false) {
    		super.actualizarInteresMensual();
    	} else {
        	penalizado = false;
    	}
    }
    
    
    public boolean isPenalizado() {
        return penalizado;
    }

    public void setPenalizado(boolean penalizado) {
        this.penalizado = penalizado;
    }
    

}



