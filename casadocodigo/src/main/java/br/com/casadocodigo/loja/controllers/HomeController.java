package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@RequestMapping("/")
	@Cacheable(value="produtoHome")//A subtituição por um acesso em memória, onde o cache é guardado, agiliza o processo. Como os livros não
	//são atualizados com muita frequencia, vale a pena usar o cache, assim o hibernate vai fazer um select no BD só de vez em quando, 
	//já que a lista não muda com muita frequencia. Mas antes devemos configurar isso no AppWebConf.
	//value dá o nome para o cache
	public ModelAndView index() {
		//List<Produto> produtos = produtoDao.listar();		
		ModelAndView modelAndView = new ModelAndView("Home");
		//modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
}
