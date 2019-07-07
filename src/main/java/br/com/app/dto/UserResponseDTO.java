package br.com.app.dto;

import java.util.Set;

import br.com.app.entity.Permissao;
import br.com.app.enuns.StatusUsuarioEnum;
import lombok.Data;

@Data
public class UserResponseDTO extends UserDTO{

	private boolean ativo;

	private String caminhoFoto;

	private StatusUsuarioEnum status;

	private Set<Permissao> permissoes;

	private boolean isRoot = false;

}
