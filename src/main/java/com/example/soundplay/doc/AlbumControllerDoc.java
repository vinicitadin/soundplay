package com.example.soundplay.doc;

import com.example.soundplay.entity.Album;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Álbuns", description = "Gerencia os álbuns da SoundPlay")
public interface AlbumControllerDoc {

    @Operation(
            summary = "Listar álbuns",
            description = "Retorna todos os álbuns cadastrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Lista de álbuns retornada com sucesso")
    })
    ResponseEntity<List<Album>> listar();

    @Operation(
            summary = "Pesquisar álbuns por título",
            description = "Pesquisa álbuns pelo título"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Pesquisa realizada com sucesso")
    })
    ResponseEntity<List<Album>> buscarPorTitulo(String titulo);

    @Operation(
            summary = "Cadastrar álbum",
            description = "Cadastra um novo álbum na SoundPlay"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Álbum cadastrado com sucesso"),
            @ApiResponse(responseCode = "400",
                    description = "Dados inválidos")
    })
    ResponseEntity<Album> salvar(Album album);

    @Operation(
            summary = "Atualizar álbum",
            description = "Atualiza os dados de um álbum existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Álbum atualizado com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Álbum não encontrado")
    })
    ResponseEntity<Album> atualizar(Long id, Album albumAtualizado);

    @Operation(
            summary = "Excluir álbum",
            description = "Exclui um álbum pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Álbum excluído com sucesso"),
            @ApiResponse(responseCode = "404",
                    description = "Álbum não encontrado")
    })
    ResponseEntity<Void> deletar(Long id);
}