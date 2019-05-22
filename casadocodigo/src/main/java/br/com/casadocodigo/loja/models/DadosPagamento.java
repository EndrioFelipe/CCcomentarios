package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;


public class DadosPagamento {
	
	private BigDecimal value; 
	
	public DadosPagamento(BigDecimal value) {//nome do atributo deve ser o mesmo que o sistema de pagamentos espera receber (value).
										//Isto é necessário pois o Spring irá transformar o objeto desta classe em um objeto JSON
		this.value = value;
		
	}
	
	public DadosPagamento() {
		// cria-se este construtor default para caso de não receber nenhum valor
	}
	
	public BigDecimal getValue() {
		return value;
	}

}
