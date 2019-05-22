package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarrinhoItem implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private TipoPreco tipoPreco;
	private int valor;
	
	

	

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public BigDecimal getPreco(){
	    return produto.precoPara(tipoPreco);
	}
	
	public BigDecimal getTotal(int quantidade) {
		System.out.println("Recebeu a quantidade de: "+quantidade);
		System.out.println("Resultado da multiplicação: "+this.getPreco().multiply(new BigDecimal(quantidade)));
	    return this.getPreco().multiply(new BigDecimal(quantidade));
	}
	
	public CarrinhoItem(Produto produto, TipoPreco tipoPreco) {
		this.produto = produto;
		this.tipoPreco = tipoPreco;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoPreco getTipoPreco() {
		return tipoPreco;
	}

	public void setTipo(TipoPreco tipoPreco) {
		this.tipoPreco = tipoPreco;
	}

	
	/* <---------Hash Code--------->
	Apesar de usar o método containsKey (do método getQuantidade de CarrinhoCompras) não é o suficiente. Ele usa o método equals 
	disponível na classe Object. Para que o método containsKey consiga comparar corretamente os itens da lista, 
	devemos sobrescrever dois métodos na classe CarrinhoItem e depois, na classe Produto. Use os geradores do
	Eclipse e selecione a opção Generate HashCode and Equals. Na classe CarrinhoItem, teremos algo parecido 
	 */
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tipoPreco == null) ? 0 : tipoPreco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrinhoItem other = (CarrinhoItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (tipoPreco != other.tipoPreco)
			return false;
		return true;
	}

	/*
	 Lembre-se que estes métodos são gerados a partir dos atributos da classe. No caso da classe CarrinhoItem, 
	 deixamos todos os atributos selecionados, mas no caso da classe Produto, usaremos apenas o atributo id que é 
	 gerado pelo banco de dados e nos garante que o produto é único. 
	 */

}
