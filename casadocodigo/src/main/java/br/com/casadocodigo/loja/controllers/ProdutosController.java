package br.com.casadocodigo.loja.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired	
    private ProdutoDAO produtoDao;
	
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
	    binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping("form")
	public ModelAndView form(Produto produto) {//Para que o objeto do tipo Produto fique disponível em nosso formulário
											   //assim o produto já fica disponível no formulário
											   //oq torna possível o lance de não apagar os outros campos
											   //quando ocorre um erro em um campo
		ModelAndView modelandview = new ModelAndView("produtos/form");
		modelandview.addObject("tipos", TipoPreco.values());//values é um método que puxa todos os valores do enum
		return modelandview;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@CacheEvict(value="produtosHome", allEntries=true)//limpa o cache quando grava no bd
		public ModelAndView gravar(MultipartFile sumario ,@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		//o BindingResult tuem que vir depois do produto pq ele valida o produto e por isso tem q vir logo depois
		
		new FileSaver();
		
		//System.out.println(sumario.getOriginalFilename()); // o método getOriginalFilename(). Este será o teste básico 
														   //para sabermos se o arquivo está sendo enviado corretamente.
														   //tem que fazer um método em appwebconf e servletsrping pra funcionar
		
		if(result.hasErrors()) {
			return form(produto);
		}
		
		
		
		String path = fileSaver.write("arquivos-sumario", sumario);//arquivos-sumario é o nome da pasta e o sumario é o arquivo
	    produto.setSumarioPath(path); //aqui é pra gravar no bd a localizção do arquivo
	    
		produtoDao.gravar(produto);
		 redirectAttributes.addFlashAttribute("sucesso", "produto cadastrado com sucesso!");
		 return new ModelAndView("redirect:produtos");//o redirect devolve um código 302 que é via get e não tem o problema
		 											  //de quando vc atualiza e fica criando o produto de novo
		}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
	  List<Object> produtos = produtoDao.listar();
	    ModelAndView modelAndView = new ModelAndView("produtos/lista");
	    List<String> arr = new ArrayList<>();
	    for(int i=0; i<produtos.size(); i++) {
	    	System.out.println(produtos.get(i));
	    	//arr.add(produtos.get(i));
	    	//System.out.println(arr.get(i));
	    }
	    modelAndView.addObject("produtos", produtos);
	    return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		
		
		
	    ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
	    Produto produto = produtoDao.find(id);
	    modelAndView.addObject("produto", produto);
	    return modelAndView;
	}
	
	
	
	
	
}
