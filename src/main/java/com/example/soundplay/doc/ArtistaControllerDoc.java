package com.example.soundplay.doc;

import com.example.soundplay.entity.Artista;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Artistas", description = "Gerencia os artistas da SoundPlay")
public interface ArtistaControllerDoc {

    @Operation(
            summary = "Listar artistas",
            description = "Retorna todos os artistas cadastrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Lista de artistas retornada com sucesso")
    })
    ResponseEntity<List<Artista>> listar();

    @Operation(
            summary = "Pesquisar artistas por nome",
            description = "Pesquisa artistas pelo nome"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Pesquisa realizada com sucesso")
    })
    ResponseEntity<List<Artista>> buscarPorNome(String nome);

    @Operation(
            summary = "Cadastrar artista",
            description = "Cadastra um novo artista na SoundPlay"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Artista cadastrado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos")
    })
    ResponseEntity<Artista> salvar(Artista artista);

    @Operation(
            summary = "Atualizar artista",
            description = "Atualiza os dados de um artista existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Artista atualizado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Artista não encontrado")
    })
    ResponseEntity<Artista> atualizar(Long id, Artista artistaAtualizado);

    @Operation(
            summary = "Excluir artista",
            description = "Exclui um artista pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Artista excluído com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Artista não encontrado")
    })
    ResponseEntity<Void> deletar(Long id);
}