package br.com.app.web.controller.almoxarifado;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.app.entity.almoxarifado.CotacaoEmpresa;
import br.com.app.exceptions.NotFoundException;
import br.com.app.jasper.JasperReportsService;
import br.com.app.jasper.RelatorioUtil;
import br.com.app.service.almoxarifado.CotacaoEmpresaService;

@RestController
@RequestMapping("/rest/almoxarifado/cotacao/empresa")
public class CotacaoEmpresaRestController {

	@Autowired
	private CotacaoEmpresaService cotacaoEmpresaService;
	@Autowired
	private JasperReportsService jasperReportsService;
	@Autowired
	private RelatorioUtil relatorioUtil;
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void salvar(@RequestBody CotacaoEmpresa cotacaoEmpresa){
		cotacaoEmpresaService.salvarOuEditar(cotacaoEmpresa);
		 				
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping
	public void update(@RequestBody CotacaoEmpresa cotacaoEmpresa){
		cotacaoEmpresaService.update(cotacaoEmpresa);
		 				
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/lista")
	public Collection<CotacaoEmpresa> buscarTodos(){
		
		return  cotacaoEmpresaService.buscarTodos();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/{id}")
	public CotacaoEmpresa buscarPorId(@PathVariable Long id) {
		return cotacaoEmpresaService.findById(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/buscaGanhdores/{idCotacao}")
	public Collection<CotacaoEmpresa> buscarGanhadores(@PathVariable Long idCotacao) {

		return cotacaoEmpresaService.ganhadores(idCotacao);
	} 
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/concorrentes/{idCotacao}")
	public Collection<CotacaoEmpresa> buscarConcorrentes(@PathVariable Long idCotacao) {

		return  cotacaoEmpresaService.concorrentes(idCotacao);
	} 
	
	@GetMapping(value = "/imprimir/vencedores/{idCotacao}")
	@ResponseBody
	public byte[] imprimirVencedores(@PathVariable Long idCotacao, HttpServletResponse response) {
		
		response.setHeader("Content-Disposition", "inline; filename=file.pdf");
	    response.setContentType("application/pdf");
	    
	    List<CotacaoEmpresa> cotacoes = cotacaoEmpresaService.ganhadores(idCotacao);
	try {	
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("SUB_REPORT_DIR", relatorioUtil.caminhoArquivoCotacaoVencedoresItens());
		return jasperReportsService.generateReport(cotacoes, relatorioUtil.caminhoArquivoCotacaoVencedores() , relatorioUtil.caminhoMapaDeLogos(hashMap) );	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Erro ao gerar pdf: " + e.getMessage());
		}		
		
	}
	
	@GetMapping(value = "/imprimir/{idCotacaoEmpresa}")
	@ResponseBody
	public byte[] imprimir(@PathVariable Long idCotacaoEmpresa, HttpServletResponse response) {
		
		response.setHeader("Content-Disposition", "inline; filename=file.pdf");
	    response.setContentType("application/pdf");
	    
	    CotacaoEmpresa cotacoes = cotacaoEmpresaService.findById(idCotacaoEmpresa);
	try {	
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("SUB_REPORT_DIR", relatorioUtil.caminhoArquivoCotacaoEmpresaItens());
		return jasperReportsService.generateReport(Arrays.asList(cotacoes), relatorioUtil.caminhoArquivoCotacaoEmpresa() , relatorioUtil.caminhoMapaDeLogos(hashMap) );	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Erro ao gerar pdf: " + e.getMessage());
		}		
		
	}
}
