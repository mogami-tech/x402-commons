package tech.mogami.commons.api.payment.extensions.bazaar;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Input specification for a bazaar resource.
 * Discriminated by the {@code type} field: {@code "http"} for HTTP endpoints, {@code "mcp"} for MCP tools.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BazaarHttpInput.class, name = "http"),
        @JsonSubTypes.Type(value = BazaarMcpInput.class, name = "mcp")
})
@Schema(description = "Input specification for a bazaar resource, discriminated by 'type' (\"http\" or \"mcp\")")
public sealed interface BazaarInput permits BazaarHttpInput, BazaarMcpInput {
}
