package br.com.app.service;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.app.entity.Empreendimento;
import br.com.app.entity.almoxarifado.NotaFiscalProduto;
import br.com.app.repository.almoxarifado.NotaFiscalProdutoRepository;

@RunWith(MockitoJUnitRunner.class)
class CalculaEstoqueServiceTest {

	@Mock
	private final NotaFiscalProdutoRepository notaFiscalProdutoRepository = null;
	
	@Test
	void test() {
		
		Long empreendimento_id = 100L;
		
		Empreendimento empreendimento = new Empreendimento();
		
		//List<NotaFiscalProduto> notas = notaFiscalProdutoRepository.buscarNotaPorEmpreendimento(empreendimento_id);
		
		Mockito.when(notaFiscalProdutoRepository.buscarNotaPorEmpreendimento(empreendimento_id)).thenReturn(null);
		
		//when(notaFiscalProdutoRepository.buscarNotaPorEmpreendimento(1L)).thenReturn(null);
	}

}
