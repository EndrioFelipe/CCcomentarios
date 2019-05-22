package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String titulo;
	@Lob
	private String descricao;	
	private int paginas; 
	
	@ElementCollection //esse é diferente do onetomany, pois não precisa de id, mas cria uma tabela 
					   //extra q relaciona o preço ao produto
	private List<Preco> precos;
	
	@DateTimeFormat //não precisa colocar o pattern pq já tá configurado no AppWebConfiguration
	private Calendar dataLancamento;
	
	private String sumarioPath; //No BD será guardado apenas o caminho (path) do arquivo.
	
	
	public BigDecimal precoPara(TipoPreco tipoPreco) {
	    return precos.stream().filter(preco -> preco.getTipo().equals(tipoPreco)).findFirst().get().getValor();
	    //Neste método, precisaremos filtrar de todos os preços, o preco escolhido e logo após encontra-lo 
	    //retornar seu valor através do método getvalor(). Observe o código abaixo:
	}
	
	
	
	
	public Calendar getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public List<Preco> getPrecos() {
		return precos;
	}
	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
	
		
		this.paginas = paginas;
	}
	
	public String getSumarioPath() {
		return sumarioPath;
	}
	public void setSumarioPath(String sumarioPath) {
		this.sumarioPath = sumarioPath;
	}
	
	
	//HashCode e equals apenas para o atributo Id
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
}
