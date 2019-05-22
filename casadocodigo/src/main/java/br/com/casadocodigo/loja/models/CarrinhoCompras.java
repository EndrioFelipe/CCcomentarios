package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component//
@Scope(value=WebApplicationContext.SCOPE_SESSION)
/*
*ao marcarmos a classe com a anotação @Component transformando nossa classe em um Bean do Spring estamos 
*também configurando que este objeto será Singleton. Para corrigir o problema, devemos especificar o escopo do 
*componente através da anotação @Scope. Ela recebe um parâmetro chamado value que pode receber valores das constantes da 
*interface WebApplicationContext.
* Quando se faz necessário que um recurso seja individual, ou seja, único para cada usuário, definimos os recursos com 
* o escopo de sessão
* ---> Ir para Carrinho Controller
*/

public class CarrinhoCompras implements Serializable{ //o servidor ao verificar que um objeto está em escopo de sessão, 
	//ele tenta salvar este objeto em arquivo. Para que ele possa sempre salvar a sessão e recuperar este objeto. 
	//Para resolvermos isto, basta fazer com que a classe CarrinhoCompras implemente a interface Serializable.
	
	private static final long serialVersionUID = 1L;
	
	private Map<CarrinhoItem, Integer> itens= new LinkedHashMap<CarrinhoItem, Integer>();
	

	public BigDecimal getTotal() {//aqui é o total de itens
		
		BigDecimal total = BigDecimal.ZERO;
		
		System.out.println("total: "+total);
		for (CarrinhoItem item : itens.keySet()) {
			System.out.println("getTotal: "+getTotal(item, 0));
			total = total.add(getTotal(item, 0));
			
		}
		return total;
	}
	
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();//esse keySet() serve para pegar a chave CarrinhoItem de itens q originalmente é um map.
		//o retorno do método captura as chaves do Map e as retorna. Se você se lembrar, vai 
		//perceber que as chaves desse Map são objetos da classe CarrinhoItem que possuem as informações 
		//sobre os produtos adicionados ao carrinho.
	}
	
	public BigDecimal getTotal(CarrinhoItem item, int valor){//esse método fará o seguinte cálculo: Quantidade de vezes que aquele item 
				System.out.println("total de itens: "+item.getTotal(getQuantidade(item, valor))); //foi adicionado no carrinho vezes o seu preço
	    return item.getTotal(getQuantidade(item, valor));
	}
	
	
	public void add(CarrinhoItem item, Integer valor) {
		System.out.println("[5]Método add");
		System.out.println("!!!!!!!!!!!!keyset() "+itens.containsValue(53));
		
		itens.put(item, getQuantidade(item, valor) + 1); //o getQuantidade é a quantidade de itens q ele já tem, então coloca +1 se ele for add mais um item
	}

	public int getQuantidade(CarrinhoItem item, Integer valor) {//esse método deve retornar o número de vezes em que o produto foi 
												  //encontrado na lista e somar 1 a essa quantidade
		if (!itens.containsKey(item)) {
			System.out.println("[6]A quantidade do "+item+" é: "+itens.get(item) );
			itens.put(item, 0);//Caso o item não exista na lista, colocamos o item e retornamos o valor 0, que será
							   //incrementado pelo metódo put
			System.out.println("[7]A quantidade do "+item+" é: "+itens.get(item) );
		}
		return itens.get(item); //caso o item já exista, retornamos apenas o valor que representa a quantidade de vezes 
							    //que o produto foi adicionado na lista.
	}
	
	public int getQuantidade(){ //Este código percorre toda a lista de itens e soma o valor de cada item a um aculumador
		
		return itens.values().stream().reduce(0, (proximo, acumulador) -> (proximo + acumulador)); //isso é um lâmbda, oq
																	//está nos parênteses é como se fossem os parâmetros do
																	//método, e o que está depois da seta é a implementação
																	//do método.
		/*detalhes: o método values pega os valores de itens e chama o stream (que é uma interface novos do java 8 com
		  vários métodos) e chama o reduce (do método do Stream). O primeiro parâmetro do reduce é o valor inicial,
		  o segundo é o acumulador, q soma os valores que existem no itens */
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
	    Produto produto = new Produto();
	    produto.setId(produtoId);
	    itens.remove(new CarrinhoItem(produto, tipoPreco));
	    
	    
	   
	}
	
	//vá até a classe CarrinhoItem na parte de HashCode

}
