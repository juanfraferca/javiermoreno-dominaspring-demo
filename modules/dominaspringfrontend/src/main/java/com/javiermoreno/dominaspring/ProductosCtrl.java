package com.javiermoreno.dominaspring;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.javiermoreno.dominaspring.domain.ProductoFinanciero;
import com.javiermoreno.dominaspring.services.GestionProductosServ;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/productos")
public class ProductosCtrl {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductosCtrl.class);
	
	@Inject
	private GestionProductosServ servicio;

	@RequestMapping(value = "/{codigo}", 
			method = RequestMethod.GET, produces = {"application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ProductoFinancieroDTO getProductoFinancieroPorCodigo(@PathVariable String codigo) {
		ProductoFinanciero productoFinanciero = servicio.obtenerProducto(codigo);
		return new ProductoFinancieroDTO(productoFinanciero);
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)  
    public String verProductoFinancieroPorCodigo(Model model, @PathVariable String codigo)  {  
           
     model.addAttribute("pf", getProductoFinancieroPorCodigo(codigo));  
  
     return "productofinanciero";  
    }  	


}
