package br.com.app.dto;

import br.com.app.entity.Empreendimento;
import lombok.Data;

@Data
public class UserDTO {
	
	protected Long id;
	
	protected Empreendimento empreendimento;

	protected String nome;
	
	protected String login;
	
	protected String email;

}
