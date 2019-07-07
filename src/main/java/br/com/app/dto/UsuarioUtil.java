package br.com.app.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.app.entity.Usuario;

public class UsuarioUtil {

	public static UserResponseDTO convertToUserResponseDTO(Usuario usuario) {
		
		return new ModelMapper().map(usuario, UserResponseDTO.class);
	}
	
	public static Page<UserResponseDTO> convertToUserResponseDTO(Page<Usuario> usuarios) {
		
		List<UserResponseDTO> usersResponseDTO = usuarios.stream().map(user -> map(user))
				.collect(Collectors.toList());
		return new PageImpl<>(usersResponseDTO);
	}
	
	private static UserResponseDTO map(Usuario usuario) {
	
	 return new ModelMapper().map(usuario, UserResponseDTO.class);
    }
	
	public static Usuario convertToUsuario(UserRequestDTO userRequestDTO) {
		
		return new ModelMapper().map(userRequestDTO, Usuario.class);
	}
	
}
