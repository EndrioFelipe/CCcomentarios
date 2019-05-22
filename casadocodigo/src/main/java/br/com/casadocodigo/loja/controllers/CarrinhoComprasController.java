package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.DadosPagamento;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
/* -->Vindo de CarrinhoCompras
 * Controller no geral tem um papel bem definido, ele simplesmente trata a requisição. 
 * Ele recebe os dados, repassa para um outro objeto e devolve uma resposta. Pensando assim, podemos 
 * concluir que após a requisição ser atendida, não faz sentido que o controller ainda esteja "vivo". 
 * Geralmente, o escopo dos controllers é o de request.
 */
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	
	
	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco, Integer valor){ //aqui passa só o id pq apenas o id do produto está sendo enviado pelo formulário da página de detalhes e não o produto.
	
		System.out.println("[1]entrou no método carrinho/add quando clica em comprar");
		System.out.println(carrinho.toString());
	    ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
	    CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco); //esse método vai fazer a relação do produto com o tipo
	    carrinho.add(carrinhoItem, valor);
	    return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens(){
	    return new ModelAndView("/carrinho/itens");
	}

	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco) { //como essa classe recebe informações da view para o controller
																	   //ela só recebe o número do id, então precisamos associar
		System.out.println("[2]produtoId: "+produtoId);					 //esse número ao produto de novo com o find
		
		Produto produto = produtoDAO.find(produtoId);
		System.out.println("[4]O id q o produto recebe: "+produto.getId());
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);//a relação entre o produto e o tipo é feita passando 
																	//o produto e o tipo para o construtor dessa nova classe
		
		return carrinhoItem;
	}
	
	@RequestMapping("/remover")
	public Callable<ModelAndView> remover(Integer produtoId, TipoPreco tipoPreco) {
		return ()->{
			carrinho.remover(produtoId, tipoPreco);
		    return new ModelAndView("redirect:/carrinho");	
		};
		
		
	}
	
	@RequestMapping("/alterar")
	public ModelAndView alterar(Integer produtoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		 Produto produto = new Produto();
		 produto.setId(produtoId);
		 System.out.println("produtoId: "+produtoId+" tipoPreco: "+tipoPreco);
		 System.out.println("produto com id: "+produto.getId());
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		System.out.println("");
		carrinho.add(carrinhoItem, 0);
		
	    return modelAndView ;
	}
	
	
}
