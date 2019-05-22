package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;

@RequestMapping("/pagamento")
@Controller
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {
	
	@Autowired
    public CarrinhoCompras carrinho;
	
	@Autowired//apesar de estarmos injetando essa classe, o spring não sabe muito bem como criar esse obj, então é preciso crair
	//um bean em AppWebConfiguration pra ensiná-lo a criar esse obj
	private RestTemplate restTemplate;
			
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(RedirectAttributes model) {
		return () -> { //classe anônima callable
			System.out.println("------------------------------------------------------------------------------");
			for(int i=190000; i<200000; i++) {
			try {
				
				String uri = "http://net.metisa.com.br/desenhos/"+i+".pdf";
				
				
				ResponseEntity<String> response  = restTemplate.getForEntity(uri, String.class);
				//System.out.println(response.getStatusCode());
				
			
				
				//DadosPagamento dadosPagamento = new DadosPagamento(carrinho.getTotal());
				//String response = restTemplate.postForObject(uri, dadosPagamento, String.class);
				//System.out.println("Dados pagamento: "+dadosPagamento.getValue());
				model.addFlashAttribute("sucesso", response.getStatusCode());
				
				System.out.println(uri);
				
				
				
				
				
			} catch (HttpClientErrorException ignore) {
				
				//e.printStackTrace();
				//model.addFlashAttribute("falha", "Valor maior que o permitido");
				
			}
			}
			System.out.println("------------------------------------------------------------------------------");
			return new ModelAndView("redirect:/produtos");
			
		};
		
		
	}
	
	@RequestMapping("ok")
	@ResponseBody
	public DadosPagamento finalizarPag() {
		
				long ff = 122;
				DadosPagamento dadosPagamento = new DadosPagamento(BigDecimal.valueOf(ff));
				
				System.out.println("Dados pagamento: "+dadosPagamento.getValue());
				
				
				return dadosPagamento;
			
		
	}
}
