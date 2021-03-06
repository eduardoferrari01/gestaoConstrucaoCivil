package br.com.app.service.almoxarifado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.app.entity.almoxarifado.CotacaoEmpresa;
import br.com.app.entity.almoxarifado.CotacaoEmpresaItem;
import br.com.app.enuns.CotacaoEmpresaItemStatus;
import br.com.app.repository.almoxarifado.CotacaoEmpresaRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CotacaoEmpresaService {

	@Autowired
	private CotacaoEmpresaRepository cotacaoEmpresaRepository;

	@Transactional(readOnly = false)
	public void salvarOuEditar(CotacaoEmpresa cotacaoEmpresa) {

		for (CotacaoEmpresaItem item : cotacaoEmpresa.getItens()) {
			item.setId(null);
			item.setStatus(CotacaoEmpresaItemStatus.PENDENTE);
			item.setCotacaoEmpresa(cotacaoEmpresa);
		}
		cotacaoEmpresaRepository.save(cotacaoEmpresa);
	}
	
	@Transactional(readOnly = false)
	public void update(CotacaoEmpresa cotacaoEmpresa) {
		for (CotacaoEmpresaItem item : cotacaoEmpresa.getItens()) {
			item.setStatus(CotacaoEmpresaItemStatus.PENDENTE);
			item.setCotacaoEmpresa(cotacaoEmpresa);
		}
		cotacaoEmpresaRepository.save(cotacaoEmpresa);
	}

	public List<CotacaoEmpresa> buscarTodos() {

		return cotacaoEmpresaRepository.findAll();
	}

	public CotacaoEmpresa buscarPorId(Long id) {
		return cotacaoEmpresaRepository.findById(id).get();
	}

	public List<CotacaoEmpresa> ganhadores(Long idCotacao) {
		List<CotacaoEmpresa> cotacoes = cotacaoEmpresaRepository.buscarGanhadores(idCotacao);
		for (CotacaoEmpresa item : cotacoes) {
			item.removerItensPerdedores();
		}
		return cotacoes;

	}

	public List<CotacaoEmpresa> concorrentes(Long idCotacao) {
		return cotacaoEmpresaRepository.buscarPorCotacao(idCotacao);
	}

}
