package br.com.app.entity.almoxarifado;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import br.com.app.entity.Categoria;
import br.com.app.enuns.UnidadeMedidaEnum;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "produto_id_seq", sequenceName = "produto_id_seq", allocationSize = 1, schema = "almoxarifado")
@NamedEntityGraph(name = "Produto.detail", attributeNodes = { @NamedAttributeNode("categoria"),
		@NamedAttributeNode("fabricante"), @NamedAttributeNode("tipoProduto") })

@Table(name = "produto", schema = "almoxarifado")
@JsonIgnoreType
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_seq")
	private Long id;

	@Column(nullable = true, unique = true)
	private Integer codigo;

	@Column(nullable = false, length = 50, unique = true)
	private String codigoBarra;

	@Column(nullable = false, length = 50)
	private String descricao;
	@Column(nullable = true)
	private boolean ativo;

	@Enumerated(EnumType.STRING)
	private UnidadeMedidaEnum unidadeMedida;

	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = true)
	private Categoria categoria;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "produtos_fornecedores", schema = "almoxarifado", joinColumns = @JoinColumn(name = "id_produto"), inverseJoinColumns = @JoinColumn(name = "id_fornecedor"))
	@Fetch(FetchMode.SUBSELECT)
	private List<Fornecedor> fornecedores;

	@ManyToOne
	@JoinColumn(name = "id_fabricante", nullable = true)
	private Fabricante fabricante;

	@ManyToOne
	@JoinColumn(name = "id_tipo", nullable = true)
	private TipoProduto tipoProduto;

	@Transient
	private boolean geraCodigoBarra = true;

}