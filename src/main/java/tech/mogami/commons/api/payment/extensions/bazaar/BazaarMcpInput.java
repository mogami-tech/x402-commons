package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jspecify.annotations.Nullable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * MCP tool input specification for a bazaar resource (type = "mcp").
 *
 * @param tool        MCP tool name, as passed to {@code tools/call}
 * @param description Human-readable description of the tool
 * @param inputSchema JSON Schema for the tool's {@code arguments}, following the MCP {@code Tool.inputSchema} format
 * @param transport   MCP transport protocol: {@code "streamable-http"} or {@code "sse"}. Defaults to {@code "streamable-http"} if absent.
 * @param example     Example {@code arguments} object
 */
@Builder
@Jacksonized
@Schema(description = "MCP tool input specification for a bazaar resource (type = \"mcp\")")
@SuppressWarnings("unused")
public record BazaarMcpInput(

        @JsonProperty(required = true)
        @NotBlank(message = "{validation.bazaar.mcp.input.tool.required}")
        @Schema(description = "MCP tool name, as passed to tools/call", example = "financial_analysis", requiredMode = REQUIRED)
        String tool,

        @Schema(description = "Human-readable description of the tool", nullable = true)
        @Nullable String description,

        @JsonProperty(required = true)
        @NotNull(message = "{validation.bazaar.mcp.input.inputSchema.required}")
        @Schema(description = "JSON Schema for the tool's arguments (MCP Tool.inputSchema format)", requiredMode = REQUIRED)
        JsonNode inputSchema,

        @Pattern(regexp = "streamable-http|sse", message = "{validation.bazaar.mcp.input.transport.invalid}")
        @Schema(description = "MCP transport protocol: 'streamable-http' or 'sse'. Defaults to 'streamable-http' if absent.", nullable = true)
        @Nullable String transport,

        @Schema(description = "Example arguments object", nullable = true)
        @Nullable JsonNode example

) implements BazaarInput {
}
