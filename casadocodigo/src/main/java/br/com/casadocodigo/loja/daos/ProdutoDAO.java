package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

@Transactional
@Repository
public class ProdutoDAO {
	@PersistenceContext
    private EntityManager manager;

    public void gravar(Produto produto){
        manager.persist(produto);
    }
    
    public List<Object> listar(){
    	System.out.println("aqui");
    	TypedQuery<Object> query = (TypedQuery<Object>) manager.createQuery("select distinct(p.titulo) from Produto p");
    	return query.getResultList();
    	
    	
    }

	public Produto find(int id) {
		// TODO Auto-generated method stub
		System.out.println("[3]Aqui é o método find!");
		
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id", 
				Produto.class).setParameter("id", id).getSingleResult(); //distinct é pq vc quer o preço de um produto
		//o join fetch faz a ligação das tabelas, o join apenas faz a junção de duas pesquisaas na mesma tabela
		//getSingleResulta já devolve o produto relacionado com os preços
	}
}
