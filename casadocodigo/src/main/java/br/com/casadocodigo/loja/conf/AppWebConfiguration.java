package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

@EnableWebMvc
@ComponentScan(basePackageClasses= {HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class}) //aqui o spring vai atrás do pacote de controller automaticamente
@EnableCaching//habilita o caching
public class AppWebConfiguration extends WebMvcConfigurerAdapter{ //tem que estender essa classe pra habilitar os js e css do bootstrap
	//basicamente toda classe q tá marcada com @Component tá nesse basePackageClasses, o erro q aparece é aquele dizendo que não
	//tem um bean qualificado
	
	@Bean //diz q o retorne desse método gere uma classe gerenciada pelo spring
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		System.out.println("prefixo e sufixo: "+resolver);
		resolver.setExposedContextBeanNames("carrinhoCompras");//como a classe CarrinhoCompras esta marcada como @Component
													//significa que ela está sendo gerenciada pelo spring, bem como esse
													//carrinhoCompras (q está sendo requisitado pelo pela página
													//detalhe) seja possível ser invocado por uma expression language
		/*
		 * usaremos o método setExposedContextBeanNames deste mesmo objeto. Este método nos permite dizer 
		 * qual *Bean estará disponível para a view. Os nomes dos Beans seguem um padrão bem simples. O 
		 * padrão é o nome da classe com sua primeira em minúsculo, ou seja, a classe CarrinhoCompras fica 
		 * carrinhoCompras. Com esta mudança o método InternalResourceViewResolver da classe AppWebConfiguration fica assim do jeito q acabamos de deixar.


		 */
		return resolver;
	}
	
	@Bean
	public MessageSource messageSource() { //método que carregará nossos arquivos de mensagens.
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8"); //só pra evitar problemas de caracteres
	    messageSource.setCacheSeconds(1);
	    return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() { //essa configuração é só pra não ter que ficar digitando 
																//pattern em toda vez que colocarmos @DateTimeFormat
																//nos atributos de data dos modelos
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
	    DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
	    formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
	    formatterRegistrar.registerFormatters(conversionService);

	    return conversionService;
	} 
	
	@Bean
	public MultipartResolver multipartResolver() { //configuração do trem de carregar arquivo
												   //tem uma outra configuração no servletsrpingmvc a ser feita
		return new StandardServletMultipartResolver();
	}
	
	@Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
        //agora é preciso colocar as dependências do jackson para converter o bagulho pro tipo de mensagem q o 
        //servido espera receber
    }
	
	@Bean//esse método fornece um gerenciador de cache para que o Spring o use
    public CacheManager cacheManager(){
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES);//aqui oideria ser DAYS
		//100 para o tamanho máximo de elementos que serão guardados ; 5 e TimeUnit.MINUTES para o segundo método, 
		//respectivamente, indicando que o cache será expirado a cada cinco minutos
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);//passa o builder de cache que criamos anteriormente
        return manager;
    }
	
	@Bean //Content Negotiation é um padrão de projeto q torna possível que uma mesma URL retorne as informações em formatos diferentes (como jsp, xml ou json)
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {//O manager que estamos recebendo por parâmetro será o responsável pela opção de qual view será utilizada
		List<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(internalResourceViewResolver()); //A primeira opção é em HTML promovida pelo método internalResourceViewResolver
		viewResolvers.add(new JsonViewResolver());//essa é a segunda opção a de json
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}
	
	@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
			// esse método irá configurar o servlet padrão para que este atenda as requisições de arquivos como css e js.
		configurer.enable();//o método enable() deste objeto habilita o servlet padrão do servidor de aplicação
		}
	
}
