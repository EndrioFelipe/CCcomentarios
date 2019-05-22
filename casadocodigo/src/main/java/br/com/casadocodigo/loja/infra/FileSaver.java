package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component //@Bean é a msma coisa, transforma em um componente do string, só q é pra métodos, component é pra classe
public class FileSaver {
	
	@Autowired
    private HttpServletRequest request; //A partir deste objeto, conseguimos extrair o contexto atual em que o usuário se 
										//encontra e então conseguir o caminho absoluto desse diretório em nosso servidor
	
	public String write(String baseFolder, MultipartFile file) {
		try { //try/catch foi adicionado por causa que operações I/O podem conter erros na hora de salvar
			System.out.println("arquivo= "+file);
			
			String realPath = request.getServletContext().getRealPath("/"+baseFolder);
			
			
			String path = realPath + "/"+ file.getOriginalFilename();
			String emptyPath = realPath + "/"+ "none";
			
			if(file.isEmpty()) {
				file.transferTo(new File(emptyPath));
			} else {
				file.transferTo(new File(path));
			}
			
			
			
			return baseFolder + "/" + file.getOriginalFilename(); //vamos retornar isso aí ao invés de retornar path. pq
															      //queremos só o caminho relativo
		} catch (IllegalStateException |  IOException e) {
			
			throw new RuntimeException(e);
		}
	
		
	}
}
