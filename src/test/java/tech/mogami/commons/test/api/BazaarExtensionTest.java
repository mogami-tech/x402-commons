package tech.mogami.commons.test.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarExtension;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarHttpInput;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarInfo;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarInput;
import tech.mogami.commons.api.payment.extensions.bazaar.BazaarMcpInput;
import tech.mogami.commons.constant.util.HttpMethod;
import tech.mogami.commons.util.JsonUtil;
import tech.mogami.commons.util.ValidationUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BazaarExtension tests")
public class BazaarExtensionTest {

    // =========================================================================
    // BazaarHttpInput — validation
    // =========================================================================

    @Test
    @DisplayName("Valid BazaarHttpInput should have no violations")
    void validHttpInput() {
        var input = BazaarHttpInput.builder()
                .method(HttpMethod.GET)
                .build();
        assertThat(ValidationUtil.findViolations(input)).isEmpty();
    }

    @Test
    @DisplayName("Null method in BazaarHttpInput should fail validation")
    void nullMethodInHttpInput() {
        var input = BazaarHttpInput.builder().build();
        assertThat(ValidationUtil.findViolations(input))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("method");
    }

    // =========================================================================
    // BazaarMcpInput — validation
    // =========================================================================

    @Test
    @DisplayName("Valid BazaarMcpInput should have no violations")
    void validMcpInput() {
        var inputSchema = JsonUtil.fromJson("{\"type\":\"object\",\"properties\":{\"ticker\":{\"type\":\"string\"}}}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarMcpInput.builder()
                .tool("financial_analysis")
                .inputSchema(inputSchema)
                .build();
        assertThat(ValidationUtil.findViolations(input)).isEmpty();
    }

    @Test
    @DisplayName("Blank tool in BazaarMcpInput should fail validation")
    void blankToolInMcpInput() {
        var inputSchema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarMcpInput.builder()
                .tool("   ")
                .inputSchema(inputSchema)
                .build();
        assertThat(ValidationUtil.findViolations(input))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("tool");
    }

    @Test
    @DisplayName("Null inputSchema in BazaarMcpInput should fail validation")
    void nullInputSchemaInMcpInput() {
        var input = BazaarMcpInput.builder()
                .tool("my_tool")
                .inputSchema(null)
                .build();
        assertThat(ValidationUtil.findViolations(input))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("inputSchema");
    }

    @Test
    @DisplayName("Invalid transport in BazaarMcpInput should fail validation")
    void invalidTransportInMcpInput() {
        var inputSchema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarMcpInput.builder()
                .tool("my_tool")
                .inputSchema(inputSchema)
                .transport("grpc")
                .build();
        assertThat(ValidationUtil.findViolations(input))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("transport");
    }

    @Test
    @DisplayName("Valid transport 'streamable-http' in BazaarMcpInput should pass validation")
    void validTransportStreamableHttp() {
        var inputSchema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarMcpInput.builder()
                .tool("my_tool")
                .inputSchema(inputSchema)
                .transport("streamable-http")
                .build();
        assertThat(ValidationUtil.findViolations(input)).isEmpty();
    }

    @Test
    @DisplayName("Valid transport 'sse' in BazaarMcpInput should pass validation")
    void validTransportSse() {
        var inputSchema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarMcpInput.builder()
                .tool("my_tool")
                .inputSchema(inputSchema)
                .transport("sse")
                .build();
        assertThat(ValidationUtil.findViolations(input)).isEmpty();
    }

    // =========================================================================
    // JSON serialization/deserialization — discriminated union
    // =========================================================================

    @Test
    @DisplayName("BazaarHttpInput should serialize with type 'http'")
    void httpInputSerializesWithType() {
        var input = BazaarHttpInput.builder().method(HttpMethod.GET).build();
        String json = JsonUtil.toJson(input);
        assertThat(json).contains("\"type\":\"http\"");
    }

    @Test
    @DisplayName("BazaarMcpInput should serialize with type 'mcp'")
    void mcpInputSerializesWithType() {
        var inputSchema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarMcpInput.builder().tool("my_tool").inputSchema(inputSchema).build();
        String json = JsonUtil.toJson(input);
        assertThat(json).contains("\"type\":\"mcp\"");
    }

    @Test
    @DisplayName("BazaarInput JSON with type 'http' should deserialize to BazaarHttpInput")
    void httpJsonDeserializesToHttpInput() {
        String json = "{\"type\":\"http\",\"method\":\"GET\"}";
        BazaarInput input = JsonUtil.fromJson(json, BazaarInput.class);
        assertThat(input).isInstanceOf(BazaarHttpInput.class);
        assertThat(((BazaarHttpInput) input).method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    @DisplayName("BazaarInput JSON with type 'mcp' should deserialize to BazaarMcpInput")
    void mcpJsonDeserializesToMcpInput() {
        String json = "{\"type\":\"mcp\",\"tool\":\"financial_analysis\",\"inputSchema\":{\"type\":\"object\"}}";
        BazaarInput input = JsonUtil.fromJson(json, BazaarInput.class);
        assertThat(input).isInstanceOf(BazaarMcpInput.class);
        assertThat(((BazaarMcpInput) input).tool()).isEqualTo("financial_analysis");
    }

    // =========================================================================
    // BazaarInfo — cascaded validation
    // =========================================================================

    @Test
    @DisplayName("Null input in BazaarInfo should fail validation")
    void nullInputInBazaarInfo() {
        var info = BazaarInfo.builder().input(null).build();
        assertThat(ValidationUtil.findViolations(info))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("input");
    }

    // =========================================================================
    // BazaarExtension — cascaded validation
    // =========================================================================

    @Test
    @DisplayName("Null info in BazaarExtension should fail validation")
    void nullInfoInBazaarExtension() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var extension = BazaarExtension.builder().info(null).schema(schema).build();
        assertThat(ValidationUtil.findViolations(extension))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("info");
    }

    @Test
    @DisplayName("Null schema in BazaarExtension should fail validation")
    void nullSchemaInBazaarExtension() {
        var input = BazaarHttpInput.builder().method(HttpMethod.POST).build();
        var info = BazaarInfo.builder().input(input).build();
        var extension = BazaarExtension.builder().info(info).schema(null).build();
        assertThat(ValidationUtil.findViolations(extension))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("schema");
    }

    // =========================================================================
    // BazaarExtension — routeTemplate validation
    // =========================================================================

    @Test
    @DisplayName("Null routeTemplate in BazaarExtension should pass validation")
    void nullRouteTemplateIsValid() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarHttpInput.builder().method(HttpMethod.GET).build();
        var info = BazaarInfo.builder().input(input).build();
        var extension = BazaarExtension.builder().info(info).schema(schema).routeTemplate(null).build();
        assertThat(ValidationUtil.findViolations(extension)).isEmpty();
    }

    @Test
    @DisplayName("Valid routeTemplate in BazaarExtension should pass validation")
    void validRouteTemplateIsValid() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarHttpInput.builder().method(HttpMethod.GET).build();
        var info = BazaarInfo.builder().input(input).build();
        var extension = BazaarExtension.builder().info(info).schema(schema).routeTemplate("/users/:userId").build();
        assertThat(ValidationUtil.findViolations(extension)).isEmpty();
    }

    @Test
    @DisplayName("routeTemplate not starting with / should fail validation")
    void routeTemplateWithoutLeadingSlashFails() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarHttpInput.builder().method(HttpMethod.GET).build();
        var info = BazaarInfo.builder().input(input).build();
        var extension = BazaarExtension.builder().info(info).schema(schema).routeTemplate("users/:userId").build();
        assertThat(ValidationUtil.findViolations(extension))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("routeTemplate");
    }

    @Test
    @DisplayName("routeTemplate with illegal characters should fail validation")
    void routeTemplateWithIllegalCharactersFails() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarHttpInput.builder().method(HttpMethod.GET).build();
        var info = BazaarInfo.builder().input(input).build();
        var extension = BazaarExtension.builder().info(info).schema(schema).routeTemplate("/users/{userId}").build();
        assertThat(ValidationUtil.findViolations(extension))
                .hasSize(1)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("routeTemplate");
    }

    @Test
    @DisplayName("routeTemplate is serialized and deserialized correctly")
    void routeTemplateSerializeDeserialize() {
        var schema = JsonUtil.fromJson("{\"type\":\"object\"}", com.fasterxml.jackson.databind.JsonNode.class);
        var input = BazaarHttpInput.builder().method(HttpMethod.GET).build();
        var info = BazaarInfo.builder().input(input).build();
        var extension = BazaarExtension.builder().info(info).schema(schema).routeTemplate("/users/:userId").build();
        String json = JsonUtil.toJson(extension);
        assertThat(json).contains("\"routeTemplate\":\"/users/:userId\"");
        var deserialized = JsonUtil.fromJson(json, BazaarExtension.class);
        assertThat(deserialized.routeTemplate()).isEqualTo("/users/:userId");
    }

}
