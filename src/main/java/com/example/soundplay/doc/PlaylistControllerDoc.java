package com.example.soundplay.doc;

import com.example.soundplay.entity.Playlist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(
        name = "Playlists",
        description = "Endpoints responsáveis pela gestão de playlists"
)
public interface PlaylistControllerDoc {

    @Operation(
            summary = "Lista todas as playlists",
            description = "Retorna todas as playlists cadastradas no sistema."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlists listadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Playlist.class))
                    )
            )
    })
    ResponseEntity<List<Playlist>> listarTodas();

    @Operation(
            summary = "Busca uma playlist por ID",
            description = "Retorna os dados de uma playlist específica pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlist encontrada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist não encontrada"
            )
    })
    ResponseEntity<Playlist> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Cria uma nova playlist",
            description = "Cria uma nova playlist com os dados informados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Playlist criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da playlist inválidos"
            )
    })
    ResponseEntity<Playlist> criar(@RequestBody Playlist playlist);

    @Operation(
            summary = "Atualiza uma playlist existente",
            description = "Atualiza os dados de uma playlist pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlist atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist não encontrada"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da playlist inválidos"
            )
    })
    ResponseEntity<Playlist> atualizar(@PathVariable Long id, @RequestBody Playlist playlist);

    @Operation(
            summary = "Exclui uma playlist",
            description = "Remove uma playlist do sistema pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Playlist excluída com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist não encontrada"
            )
    })
    ResponseEntity<Void> excluir(@PathVariable Long id);
}