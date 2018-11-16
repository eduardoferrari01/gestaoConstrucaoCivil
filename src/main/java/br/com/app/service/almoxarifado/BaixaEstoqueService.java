package br.com.app.service.almoxarifado;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.app.entity.almoxarifado.EstoqueEmpreendimento;
import br.com.app.entity.almoxarifado.interfaces.EntradaOuBaixa;
import br.com.app.entity.almoxarifado.interfaces.IItem;
import br.com.app.repository.almoxarifado.EstoqueEmpreendimentoRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class BaixaEstoqueService {

	@Autowired
	private EstoqueEmpreendimentoRepository estoqueRepository;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = false)
	public void baixar(EntradaOuBaixa baixa) {
		Collection<IItem> t = baixa.getItens();
		
		for(IItem item : t)
		{
			EstoqueEmpreendimento estoque = estoqueRepository.estoque(baixa.empreendimentoSaida().getId(),item.getProduto().getId());
			if(estoque != null)
			{
			 estoque.setQuantidade(estoque.getQuantidade() - item.getQuantidade());
		    
			if(estoque.isNegativo())
		    {
			   estoqueRepository.save(estoque);
			   
		    }else
		    {
		    	throw new EstoqueEmpreendimentoException("Estoque negativo");
		    }
			
			}else
			{
				throw new EstoqueEmpreendimentoException("Produto não encontrado no estoque do empreendimento");
				
			}
				
		}
		
	}
	
}
