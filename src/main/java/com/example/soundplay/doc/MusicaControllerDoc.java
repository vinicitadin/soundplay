 package com.example.soundplay.doc;

import com.example.soundplay.entity.Musica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Músicas", description = "Gerencia as músicas da SoundPlay")
public interface MusicaControllerDoc {

    @Operation(
            summary = "Listar músicas",
            description = "Retorna todas as músicas cadastradas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Lista de músicas retornada com sucesso")
    })
    List<Musica> listar();

    @Operation(
            summary = "Buscar música por ID",
            description = "Retorna uma música específica pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Música encontrada"),
            @ApiResponse(responseCode = "404",
                    description = "Música não encontrada")
    })
    Musica buscarPorId(Long id);

    @Operation(
            summary = "Pesquisar músicas por título",
            description = "Pesquisa músicas pelo título, ignorando diferenças entre maiúsculas e minúsculas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Pesquisa realizada com sucesso")
    })
    List<Musica> buscarPorTitulo(String titulo);

    @Operation(
            summary = "Cadastrar música",
            description = "Cadastra uma nova música na SoundPlay"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Música cadastrada com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos")
    })
    Musica cadastrar(Musica musica);

    @Operation(
            summary = "Atualizar música",
            description = "Atualiza os dados de uma música existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Música atualizada com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Música não encontrada")
    })
    Musica atualizar(Long id, Musica musica);

    @Operation(
            summary = "Excluir música",
            description = "Exclui uma música pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Música excluída com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Música não encontrada")
    })
    void excluir(Long id);
}
