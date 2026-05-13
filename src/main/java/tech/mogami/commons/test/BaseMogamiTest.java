package tech.mogami.commons.test;

import tech.mogami.commons.api.facilitator.settle.SettlementResponse;
import tech.mogami.commons.api.payment.PaymentPayload;
import tech.mogami.commons.api.payment.PaymentRequirements;
import tech.mogami.commons.api.payment.PaymentResource;
import tech.mogami.commons.api.payment.schemes.exact.ExactSchemePayload;

import java.util.Map;

/**
 * Base test class providing shared wallet constants and encoded fixture helpers for Mogami Commons tests.
 *
 * <p>All wallet addresses and private keys are derived from {@link #TEST_MNEMONIC}, which is a
 * well-known test mnemonic with no real funds. They must never be used on mainnet.</p>
 *
 * <p>Production addresses used as reference data in cross-repository tests are available
 * in {@link KnownProductionAddresses}.</p>
 */
@SuppressWarnings({"unused", "magicnumber", "SameReturnValue", "SpellCheckingInspection"})
public class BaseMogamiTest {

    // ==================== Wallet constants ====================

    /** Test mnemonic phrase (no real funds — for test use only). */
    public static final String TEST_MNEMONIC = "slice joke drink glove ice brown erosion frown census talk topic entire size pair safe";

    /** Client (buyer) address number 1 - BIP-44 derivation index 0. */
    public static final String TEST_CLIENT_WALLET_ADDRESS_1 = "0xf6b42050A71Ca13f842eDa53C7d31B7C1BD94F6E";

    /** Client (buyer) address number 1 private key - BIP-44 derivation index 0. */
    public static final String TEST_CLIENT_WALLET_ADDRESS_1_PRIVATE_KEY = "0x9348777b9d20188fe21139591877048c76ffc91bdd445e708f2a5b91ea75fda8";

    /** Client (buyer) address number 2 - BIP-44 derivation index 1. */
    public static final String TEST_CLIENT_WALLET_ADDRESS_2 = "0xCC6f005718945b59cfC5aF1981BF93904A813601";

    /** Client (buyer) address number 2 private key - BIP-44 derivation index 1. */
    public static final String TEST_CLIENT_WALLET_ADDRESS_2_PRIVATE_KEY = "0x138874b2df71c4ecfd6dab78139f821e780c274ca4a3610dc7246be01a31294f";

    /** Server (seller) address number 1 - BIP-44 derivation index 5. */
    public static final String TEST_SERVER_WALLET_ADDRESS_1 = "0xB02166b97d37551cb8154c657d4c01B835404fc4";

    /** Server (seller) address number 1 private key - BIP-44 derivation index 5. */
    public static final String TEST_SERVER_WALLET_ADDRESS_1_PRIVATE_KEY = "0xc1f97668293dcaecb72bfc6fba31a39d34a1a5d1d3d36e30f237a9cbcb3077e9";

    /** Server (seller) address number 2 - BIP-44 derivation index 6. */
    public static final String TEST_SERVER_WALLET_ADDRESS_2 = "0x9E9F32D248d0f3E093310044847Ad1e8B6EF50a3";

    /** Server (seller) address number 2 private key - BIP-44 derivation index 6. */
    public static final String TEST_SERVER_WALLET_ADDRESS_2_PRIVATE_KEY = "0x37b8c69095c9247fce67b1b9bdfd30a5d364216a2f84a8a02a37725359ca9d9b";

    /** Client 1 client ID. */
    public static final String CLIENT_1_CLIENT_ID = "11111111-1111-1111-1111-111111111111";

    /** Client 1 tenant ID. */
    public static final String CLIENT_1_TENANT_ID = "11111111-1111-1111-1111-111111111111";

    /** Client 1 tenant API key. */
    public static final String CLIENT_1_TENANT_API_KEY = "cJNKTeIMnUUqj6qhK2c8sg2iggZ4FbBSnUCcJ4UgxWcdEaeO3KbXk4hz0s6jZIC1";

    /** Wallet with no funds. */
    public static final String EMPTY_WALLET_ADDRESS = "0x7b66Eb4d76c80C53D683059923B7D213F25C2627";

    /** Wallet with no funds private key. */
    public static final String EMPTY_WALLET_ADDRESS_PRIVATE_KEY = "0xbe35b3b0a1f47d963d9f5d35ca0b94e397add9385fb6fa742b737589cf3ccce0";

    // ==================== Spring test helpers ====================

    /**
     * Get default properties.
     *
     * @param configurationFile the config location
     * @return the default properties
     */
    protected Map<String, Object> getTestProperties(final String configurationFile) {
        return Map.of(
                "spring.config.location", "classpath:parameters/" + configurationFile + ".properties"
        );
    }

    // ==================== Encoded fixture helpers ====================

    /**
     * Get empty JSON.
     *
     * <p>Decodes to: {@code {}}</p>
     *
     * @return Base64 encoding of an empty JSON object
     */
    protected String getEmptyJson() {
        return "ewoKfQ==";
    }

    /**
     * Get sample encoded payment required (x402Version=2).
     *
     * <p>Decodes to a {@code PaymentRequired} with:
     * <ul>
     *   <li>x402Version: 2</li>
     *   <li>error: "PAYMENT-SIGNATURE header is required"</li>
     *   <li>resource.url: https://api.example.com/premium-data</li>
     *   <li>accepts[0]: scheme=exact, network=eip155:84532, amount=10000, asset=BASE_SEPOLIA_USDC, payTo=0x2096…287C, maxTimeoutSeconds=60, extra={name=USDC, version=2}</li>
     * </ul>
     * </p>
     *
     * @return Base64-encoded PaymentRequired (v2)
     */
    protected String getSampleEncodedPaymentRequired() {
        return "eyJ4NDAyVmVyc2lvbiI6MiwiZXJyb3IiOiJQQVlNRU5ULVNJR05BVFVSRSBoZWFkZXIgaXMgcmVxdWlyZWQiLCJyZXNvdXJjZSI6eyJ1cmwiOiJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLCJkZXNjcmlwdGlvbiI6IkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwibWltZVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0sImFjY2VwdHMiOlt7InNjaGVtZSI6ImV4YWN0IiwibmV0d29yayI6ImVpcDE1NTo4NDUzMiIsImFtb3VudCI6IjEwMDAwIiwiYXNzZXQiOiIweDAzNkNiRDUzODQyYzU0MjY2MzRlNzkyOTU0MWVDMjMxOGYzZENGN2UiLCJwYXlUbyI6IjB4MjA5NjkzQmM2YWZjMEM1MzI4YkEzNkZhRjAzQzUxNEVGMzEyMjg3QyIsIm1heFRpbWVvdXRTZWNvbmRzIjo2MCwiZXh0cmEiOnsibmFtZSI6IlVTREMiLCJ2ZXJzaW9uIjoiMiJ9fV19";
    }

    /**
     * Get sample encoded payment required (x402Version=1).
     *
     * <p>Identical {@code PaymentRequired} structure as {@link #getSampleEncodedPaymentRequired()}
     * except {@code x402Version=1} instead of 2. Used to test backward-compatibility with the v1 protocol.</p>
     *
     * @return Base64-encoded PaymentRequired (v1)
     */
    protected String getSampleEncodedPaymentRequiredV1() {
        return "eyJ4NDAyVmVyc2lvbiI6MSwiZXJyb3IiOiJQQVlNRU5ULVNJR05BVFVSRSBoZWFkZXIgaXMgcmVxdWlyZWQiLCJyZXNvdXJjZSI6eyJ1cmwiOiJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLCJkZXNjcmlwdGlvbiI6IkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwibWltZVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0sImFjY2VwdHMiOlt7InNjaGVtZSI6ImV4YWN0IiwibmV0d29yayI6ImVpcDE1NTo4NDUzMiIsImFtb3VudCI6IjEwMDAwIiwiYXNzZXQiOiIweDAzNkNiRDUzODQyYzU0MjY2MzRlNzkyOTU0MWVDMjMxOGYzZENGN2UiLCJwYXlUbyI6IjB4MjA5NjkzQmM2YWZjMEM1MzI4YkEzNkZhRjAzQzUxNEVGMzEyMjg3QyIsIm1heFRpbWVvdXRTZWNvbmRzIjo2MCwiZXh0cmEiOnsibmFtZSI6IlVTREMiLCJ2ZXJzaW9uIjoiMiJ9fV19";
    }

    /**
     * Get sample encoded payment payload.
     *
     * <p>Decodes to a {@code PaymentPayload} with:
     * <ul>
     *   <li>x402Version: 2</li>
     *   <li>resource.url: https://api.example.com/premium-data</li>
     *   <li>accepted: scheme=exact, network=eip155:84532, amount=10000, asset=BASE_SEPOLIA_USDC, payTo=0x2096…287C, maxTimeoutSeconds=60</li>
     *   <li>payload.signature: 0x2d6a…571c</li>
     *   <li>payload.authorization: from=0x857b…b66, to=0x2096…287C, value=10000, validAfter=1740672089, validBefore=1740672154</li>
     * </ul>
     * </p>
     *
     * @return Base64-encoded PaymentPayload (exact scheme, Base Sepolia USDC)
     */
    protected String getSampleEncodedPaymentPayload() {
        return "eyJ4NDAyVmVyc2lvbiI6MiwicmVzb3VyY2UiOnsidXJsIjoiaHR0cHM6Ly9hcGkuZXhhbXBsZS5jb20vcHJlbWl1bS1kYXRhIiwiZGVzY3JpcHRpb24iOiJBY2Nlc3MgdG8gcHJlbWl1bSBtYXJrZXQgZGF0YSIsIm1pbWVUeXBlIjoiYXBwbGljYXRpb24vanNvbiJ9LCJhY2NlcHRlZCI6eyJzY2hlbWUiOiJleGFjdCIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJhbW91bnQiOiIxMDAwMCIsImFzc2V0IjoiMHgwMzZDYkQ1Mzg0MmM1NDI2NjM0ZTc5Mjk1NDFlQzIzMThmM2RDRjdlIiwicGF5VG8iOiIweDIwOTY5M0JjNmFmYzBDNTMyOGJBMzZGYUYwM0M1MTRFRjMxMjI4N0MiLCJtYXhUaW1lb3V0U2Vjb25kcyI6NjAsImV4dHJhIjp7Im5hbWUiOiJVU0RDIiwidmVyc2lvbiI6IjIifX0sInBheWxvYWQiOnsic2lnbmF0dXJlIjoiMHgyZDZhNzU4OGQ2YWNjYTUwNWNiZjBkOWE0YTIyN2UwYzUyYzZjMzQwMDhjOGU4OTg2YTEyODMyNTk3NjQxNzM2MDhhMmNlNjQ5NjY0MmUzNzdkNmRhOGRiYmY1ODM2ZTliZDE1MDkyZjllY2FiMDVkZWQzZDYyOTNhZjE0OGI1NzFjIiwiYXV0aG9yaXphdGlvbiI6eyJmcm9tIjoiMHg4NTdiMDY1MTlFOTFlM0E1NDUzODc5MWJEYmIwRTIyMzczZTM2YjY2IiwidG8iOiIweDIwOTY5M0JjNmFmYzBDNTMyOGJBMzZGYUYwM0M1MTRFRjMxMjI4N0MiLCJ2YWx1ZSI6IjEwMDAwIiwidmFsaWRBZnRlciI6IjE3NDA2NzIwODkiLCJ2YWxpZEJlZm9yZSI6IjE3NDA2NzIxNTQiLCJub25jZSI6IjB4ZjM3NDY2MTNjMmQ5MjBiNWZkYWJjMDg1NmYyYWViMmQ0Zjg4ZWU2MDM3YjhjYzVkMDRhNzFhNDQ2MmYxMzQ4MCJ9fX0";
    }

    /**
     * Get sample payment payload.
     *
     * <p>Returns the same data as {@link #getSampleEncodedPaymentPayload()} as a built Java object,
     * useful for encode/decode round-trip tests.</p>
     *
     * @return the sample payment payload
     */
    protected PaymentPayload getSamplePaymentPayload() {
        return PaymentPayload.builder()
                .x402Version(2)
                .resource(
                        PaymentResource.builder()
                                .url("https://api.example.com/premium-data")
                                .description("Access to premium market data")
                                .mimeType("application/json")
                                .build()
                )
                .accepted(
                        PaymentRequirements.builder()
                                .scheme("exact")
                                .network("eip155:84532")
                                .amount("10000")
                                .asset("0x036CbD53842c5426634e7929541eC2318f3dCF7e")
                                .payTo("0x209693Bc6afc0C5328bA36FaF03C514EF312287C")
                                .maxTimeoutSeconds(60)
                                .extra(Map.of(
                                        "name", "USDC",
                                        "version", "2"
                                ))
                                .build()
                )
                .payload(
                        ExactSchemePayload.builder()
                                .signature("0x2d6a7588d6acca505cbf0d9a4a227e0c52c6c34008c8e8986a1283259764173608a2ce6496642e377d6da8dbbf5836e9bd15092f9ecab05ded3d6293af148b571c")
                                .authorization(
                                        ExactSchemePayload.Authorization.builder()
                                                .from("0x857b06519E91e3A54538791bDbb0E22373e36b66")
                                                .to("0x209693Bc6afc0C5328bA36FaF03C514EF312287C")
                                                .value("10000")
                                                .validAfter("1740672089")
                                                .validBefore("1740672154")
                                                .nonce("0xf3746613c2d920b5fdabc0856f2aeb2d4f88ee6037b8cc5d04a71a4462f13480")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    /**
     * Get sample encoded payment payload without the {@code accepts} field.
     *
     * <p>Decodes to a partial {@code PaymentRequired} containing only:
     * <ul>
     *   <li>x402Version: 2</li>
     *   <li>error: "PAYMENT-SIGNATURE header is required"</li>
     *   <li>resource.url: https://api.example.com/premium-data</li>
     * </ul>
     * Used to test validation failure when the required {@code accepts} array is absent.</p>
     *
     * @return Base64-encoded PaymentRequired without accepts
     */
    protected String getSampleEncodedPaymentPayloadWithoutAccepts() {
        return "ewogICJ4NDAyVmVyc2lvbiI6IDIsCiAgImVycm9yIjogIlBBWU1FTlQtU0lHTkFUVVJFIGhlYWRlciBpcyByZXF1aXJlZCIsCiAgInJlc291cmNlIjogewogICAgInVybCI6ICJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLAogICAgImRlc2NyaXB0aW9uIjogIkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwKICAgICJtaW1lVHlwZSI6ICJhcHBsaWNhdGlvbi9qc29uIgogIH0KfQ==";
    }

    /**
     * Get sample encoded payment response (failed).
     *
     * <p>Decodes to a {@code SettlementResponse} with:
     * <ul>
     *   <li>success: false</li>
     *   <li>errorReason: "insufficient_funds"</li>
     *   <li>transaction: "" (empty)</li>
     *   <li>network: eip155:84532</li>
     *   <li>payer: 0x857b…b66</li>
     * </ul>
     * </p>
     *
     * @return Base64-encoded failed payment response
     */
    protected String getSampleEncodedPaymentResponse() {
        return "eyJzdWNjZXNzIjpmYWxzZSwiZXJyb3JSZWFzb24iOiJpbnN1ZmZpY2llbnRfZnVuZHMiLCJ0cmFuc2FjdGlvbiI6IiIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJwYXllciI6IjB4ODU3YjA2NTE5RTkxZTNBNTQ1Mzg3OTFiRGJiMEUyMjM3M2UzNmI2NiJ9";
    }

    /**
     * Get sample encoded settlement response (successful).
     *
     * <p>Decodes to a {@code SettlementResponse} with:
     * <ul>
     *   <li>success: true</li>
     *   <li>transaction: 0x1234…cdef</li>
     *   <li>network: eip155:84532</li>
     *   <li>payer: 0x857b…b66</li>
     * </ul>
     * </p>
     *
     * @return Base64-encoded successful SettlementResponse
     */
    protected String getSampleEncodedSettlementResponse() {
        return "eyJzdWNjZXNzIjp0cnVlLCJ0cmFuc2FjdGlvbiI6IjB4MTIzNDU2Nzg5MGFiY2RlZjEyMzQ1Njc4OTBhYmNkZWYxMjM0NTY3ODkwYWJjZGVmMTIzNDU2Nzg5MGFiY2RlZiIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJwYXllciI6IjB4ODU3YjA2NTE5RTkxZTNBNTQ1Mzg3OTFiRGJiMEUyMjM3M2UzNmI2NiJ9";
    }

    /**
     * Get sample settlement response.
     *
     * <p>Returns the same data as {@link #getSampleEncodedSettlementResponse()} as a built Java object,
     * useful for encode/decode round-trip tests.</p>
     *
     * @return the sample settlement response
     */
    protected SettlementResponse getSampleSettlementResponse() {
        return SettlementResponse.builder()
                .success(true)
                .transaction("0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef")
                .network("eip155:84532")
                .payer("0x857b06519E91e3A54538791bDbb0E22373e36b66")
                .build();
    }

    /**
     * Get the payment-required header captured from <a href="https://www.x402.org/protected">x402.org/protected</a>.
     *
     * <p>Decodes to a {@code PaymentRequired} with:
     * <ul>
     *   <li>x402Version: 2</li>
     *   <li>error: "Payment required"</li>
     *   <li>resource.url: https://www.x402.org/protected</li>
     *   <li>accepts[0]: scheme=exact, network=eip155:84532 (Base Sepolia USDC), maxTimeoutSeconds=300</li>
     *   <li>accepts[1]: scheme=exact, network=solana:EtWTRABZaYq6iMfeYKouRu166VU2xqa1 (Solana devnet, CAIP-2 genesis hash), maxTimeoutSeconds=300</li>
     * </ul>
     * </p>
     *
     * @return Base64-encoded PaymentRequired from x402.org (two accepted schemes: EVM + Solana)
     */
    protected String getX402ProtectedPaymentRequiredHeader() {
        return "eyJ4NDAyVmVyc2lvbiI6MiwiZXJyb3IiOiJQYXltZW50IHJlcXVpcmVkIiwicmVzb3VyY2UiOnsidXJsIjoiaHR0cHM6Ly93d3cueDQwMi5vcmcvcHJvdGVjdGVkIiwiZGVzY3JpcHRpb24iOiJBY2Nlc3MgdG8gcHJvdGVjdGVkIGNvbnRlbnQiLCJtaW1lVHlwZSI6IiJ9LCJhY2NlcHRzIjpbeyJzY2hlbWUiOiJleGFjdCIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJhbW91bnQiOiIxMDAwMCIsImFzc2V0IjoiMHgwMzZDYkQ1Mzg0MmM1NDI2NjM0ZTc5Mjk1NDFlQzIzMThmM2RDRjdlIiwicGF5VG8iOiIweDIwOTY5M0JjNmFmYzBDNTMyOGJBMzZGYUYwM0M1MTRFRjMxMjI4N0MiLCJtYXhUaW1lb3V0U2Vjb25kcyI6MzAwLCJleHRyYSI6eyJuYW1lIjoiVVNEQyIsInZlcnNpb24iOiIyIn19LHsic2NoZW1lIjoiZXhhY3QiLCJuZXR3b3JrIjoic29sYW5hOkV0V1RSQUJaYVlxNmlNZmVZS291UnUxNjZWVTJ4cWExIiwiYW1vdW50IjoiMTAwMDAiLCJhc3NldCI6IjR6TU1DOXNydDVSaTVYMTRHQWdYaGFIaWkzR25QQUVFUllQSmdaSkRuY0RVIiwicGF5VG8iOiJDS1BLSldOZEpFcWE4MXg3Q2taMTRCVlBpWTZ5MTZTeHM3b3d6bnF0V1lwNSIsIm1heFRpbWVvdXRTZWNvbmRzIjozMDAsImV4dHJhIjp7ImZlZVBheWVyIjoiQ0tQS0pXTmRKRXFhODF4N0NrWjE0QlZQaVk2eTE2U3hzN293em5xdFdZcDUifX1dfQ==";
    }

}
