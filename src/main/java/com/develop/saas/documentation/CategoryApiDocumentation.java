package com.develop.saas.documentation;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;

public interface CategoryApiDocumentation {
    @Operation(
            summary = "Add new category",
            description = "Add new category",
            parameters = {
                @Parameter(
                        name = "categoryRequest",
                        description = "Unique category name",
                        required = true,
                        example = "Artificial Intelligence")
            },
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))
                        })
            })
    CompletableFuture<ResponseEntity<CategoryResponse>> addCategory(CategoryRequest categoryRequest);
}
