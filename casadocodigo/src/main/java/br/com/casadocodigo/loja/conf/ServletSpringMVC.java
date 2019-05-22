package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{//classe que carrega todas as configurações de nossa aplicação

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[]{SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class}; //Essa classe fica no root pq carrega configurações logo ao iniciar a aplicação. 
	}

	@Override
	protected Class<?>[] getServletConfigClasses() { 
		// TODO Auto-generated method stub
		//return new Class[] {AppWebConfiguration.class, JPAConfiguration.class}; -> tava assim, mas teve que passar pro método de cima pq as configurações de segurança estão iniciando no método 
		//estas requerem as configurações do DAO
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() { //só pra botar pra utf-8
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        return new Filter[] {encodingFilter};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement("")); //O MultipartConfigElement espera receber uma String que configure o arquivo.
																		 //Não usaremos nenhuma configuração para o arquivo, queremos receber este do jeito que vier. Passamos então uma String vazia.	
	}
}
