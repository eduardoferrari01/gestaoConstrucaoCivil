package br.com.app.entity.servicos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.app.entity.Imagens;
import br.com.app.entity.Usuario;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "ocorrencia_servico_id_seq", sequenceName = "ocorrencia_servico_id_seq",allocationSize = 1, schema = "servicos")
@Table(name = "ocorrencia_servico", schema = "servicos")
public class OcorrenciaServico implements Serializable  , Comparable<OcorrenciaServico>{

	private static final long serialVersionUID = 4115751645501990704L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ocorrencia_servico_id_seq")
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_servico_empresa")
	private ServicoEmpresa servicoEmpresa;

	@Column(name = "ocorrencia")
	private String ocorrencia;
	

	@Column(name = "data")
	private Date data;
	
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "arquivo", columnDefinition="text")
	private String arquivo;
	
	@OneToMany(mappedBy = "ocorrencia", cascade = CascadeType.ALL)
	private List<Imagens> imagens = new ArrayList<>();
	
	@PostPersist
	public void addStatusAberto() {
		this.status = true;
	}
	
	public Boolean isAberto() {
		return this.status == true;
	}

	@Override
	public int compareTo(OcorrenciaServico o) {
		return (int) (o.id - id);
	}

}
