package com.example.soundplay.doc;

import com.example.soundplay.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Tag(name = "Usuários", description = "Gerencia os usuários da SoundPlay")
public interface UsuarioControllerDoc {

    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Lista de usuários retornada com sucesso")
    })
    List<Usuario> listar();

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404",
                    description = "Usuário não encontrado")
    })
    Optional<Usuario> buscarPorId(Long id);

    @Operation(
            summary = "Cadastrar usuário",
            description = "Cadastra um novo usuário na SoundPlay"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos")
    })
    Usuario cadastrar(Usuario usuario);

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Usuário não encontrado")
    })
    Usuario atualizar(Long id, Usuario usuario);

    @Operation(
            summary = "Excluir usuário",
            description = "Exclui um usuário pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Usuário não encontrado")
    })
    void excluir(Long id);
}
