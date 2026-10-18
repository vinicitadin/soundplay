package com.example.soundplay.doc;

import com.example.soundplay.dto.request.PlaylistRequest;
import com.example.soundplay.dto.response.PlaylistResponse;
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
                            array = @ArraySchema(schema = @Schema(implementation = PlaylistResponse.class))
                    )
            )
    })
    ResponseEntity<List<PlaylistResponse>> listarTodas();

    @Operation(
            summary = "Lista as playlists do usuário logado",
            description = "Retorna somente as playlists criadas pelo usuário autenticado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Playlists listadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlaylistResponse.class))
                    )
            )
    })
    ResponseEntity<List<PlaylistResponse>> listarMinhas();

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
                            schema = @Schema(implementation = PlaylistResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist não encontrada"
            )
    })
    ResponseEntity<PlaylistResponse> buscarPorId(@PathVariable Long id);

    @Operation(
            summary = "Cria uma nova playlist",
            description = "Cria uma nova playlist com os dados informados. O dono é o usuário autenticado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Playlist criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da playlist inválidos"
            )
    })
    ResponseEntity<PlaylistResponse> criar(@RequestBody PlaylistRequest playlist);

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
                            schema = @Schema(implementation = PlaylistResponse.class)
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
    ResponseEntity<PlaylistResponse> atualizar(@PathVariable Long id, @RequestBody PlaylistRequest playlist);

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

    @Operation(
            summary = "Adiciona uma música à playlist",
            description = "Adiciona a música informada à playlist. Apenas o dono da playlist pode alterá-la."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Música adicionada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "403", description = "A playlist pertence a outro usuário"),
            @ApiResponse(responseCode = "404", description = "Playlist ou música não encontrada"),
            @ApiResponse(responseCode = "409", description = "A música já está na playlist")
    })
    ResponseEntity<PlaylistResponse> adicionarMusica(@PathVariable Long id, @PathVariable Long musicaId);

    @Operation(
            summary = "Remove uma música da playlist",
            description = "Remove a música da playlist. Apenas o dono da playlist pode alterá-la."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Música removida com sucesso"),
            @ApiResponse(responseCode = "403", description = "A playlist pertence a outro usuário"),
            @ApiResponse(responseCode = "404", description = "Playlist, música ou vínculo não encontrado")
    })
    ResponseEntity<Void> removerMusica(@PathVariable Long id, @PathVariable Long musicaId);
}
