package com.develop.saas.documentation;

import com.develop.saas.dto.CategoryRequest;
import com.develop.saas.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
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

    @Operation(
            summary = "Get category by id",
            description = "Get category by id",
            parameters = {@Parameter(name = "id", description = "Unique category id", required = true, example = "1")},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))
                        })
            })
    CompletableFuture<ResponseEntity<CategoryResponse>> getCategoryById(Long id);

    @Operation(
            summary = "Get all categories",
            description = "Get all categories",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))
                        })
            })
    CompletableFuture<ResponseEntity<List<CategoryResponse>>> getAllCategories();

    @Operation(
            summary = "Update category",
            description = "Update category",
            parameters = {
                @Parameter(name = "id", description = "Unique category id", required = true, example = "1"),
                @Parameter(
                        name = "categoryRequest",
                        description = "Unique category name",
                        required = true,
                        example = "Artificial Intelligence")
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class))
                        })
            })
    CompletableFuture<ResponseEntity<CategoryResponse>> updateCategory(Long id, CategoryRequest categoryRequest);

    @Operation(
            summary = "Delete category",
            description = "Delete category",
            parameters = {@Parameter(name = "id", description = "Unique category id", required = true, example = "1")},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful operation",
                        content = {
                            @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(implementation = CategoryResponse.class))
                        })
            })
    CompletableFuture<ResponseEntity<String>> softDeleteCategory(Long id);
}
