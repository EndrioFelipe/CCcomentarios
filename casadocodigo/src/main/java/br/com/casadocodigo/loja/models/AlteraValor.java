package br.com.casadocodigo.loja.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component//
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class AlteraValor {
	private int alteraValor;

	public int getAlteraValor() {
		return alteraValor;
	}

	public void setAlteraValor(int alteraValor) {
		System.out.println("SET altera valor: "+alteraValor);
		this.alteraValor = alteraValor;
	}

	
}
