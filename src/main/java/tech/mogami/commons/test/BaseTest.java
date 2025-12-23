package tech.mogami.commons.test;

import tech.mogami.commons.payment.PaymentPayload;
import tech.mogami.commons.payment.PaymentRequirements;
import tech.mogami.commons.payment.PaymentResource;
import tech.mogami.commons.payment.schemes.exact.ExactSchemePayload;

import java.util.Map;

/**
 * Base test.
 */
@SuppressWarnings({"unused", "magicnumber"})
public class BaseTest extends BaseTestData {

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

    /**
     * Get empty JSON.
     *
     * @return just {}
     */
    protected String getEmptyJson() {
        return "ewoKfQ==";
    }

    /**
     * Get sample encoded payment required.
     *
     * @return the sample encoded payment required
     */
    protected String getSampleEncodedPaymentRequired() {
        return "eyJ4NDAyVmVyc2lvbiI6MiwiZXJyb3IiOiJQQVlNRU5ULVNJR05BVFVSRSBoZWFkZXIgaXMgcmVxdWlyZWQiLCJyZXNvdXJjZSI6eyJ1cmwiOiJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLCJkZXNjcmlwdGlvbiI6IkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwibWltZVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0sImFjY2VwdHMiOlt7InNjaGVtZSI6ImV4YWN0IiwibmV0d29yayI6ImVpcDE1NTo4NDUzMiIsImFtb3VudCI6IjEwMDAwIiwiYXNzZXQiOiIweDAzNkNiRDUzODQyYzU0MjY2MzRlNzkyOTU0MWVDMjMxOGYzZENGN2UiLCJwYXlUbyI6IjB4MjA5NjkzQmM2YWZjMEM1MzI4YkEzNkZhRjAzQzUxNEVGMzEyMjg3QyIsIm1heFRpbWVvdXRTZWNvbmRzIjo2MCwiZXh0cmEiOnsibmFtZSI6IlVTREMiLCJ2ZXJzaW9uIjoiMiJ9fV19";
    }

    /**
     * Get sample encoded payment required (V1).
     *
     * @return the sample encoded payment required
     */
    protected String getSampleEncodedPaymentRequiredV1() {
        return "eyJ4NDAyVmVyc2lvbiI6MSwiZXJyb3IiOiJQQVlNRU5ULVNJR05BVFVSRSBoZWFkZXIgaXMgcmVxdWlyZWQiLCJyZXNvdXJjZSI6eyJ1cmwiOiJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLCJkZXNjcmlwdGlvbiI6IkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwibWltZVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0sImFjY2VwdHMiOlt7InNjaGVtZSI6ImV4YWN0IiwibmV0d29yayI6ImVpcDE1NTo4NDUzMiIsImFtb3VudCI6IjEwMDAwIiwiYXNzZXQiOiIweDAzNkNiRDUzODQyYzU0MjY2MzRlNzkyOTU0MWVDMjMxOGYzZENGN2UiLCJwYXlUbyI6IjB4MjA5NjkzQmM2YWZjMEM1MzI4YkEzNkZhRjAzQzUxNEVGMzEyMjg3QyIsIm1heFRpbWVvdXRTZWNvbmRzIjo2MCwiZXh0cmEiOnsibmFtZSI6IlVTREMiLCJ2ZXJzaW9uIjoiMiJ9fV19";
    }

    /**
     * Get sample encoded payment payload.
     *
     * @return the sample encoded payment payload
     */
    protected String getSampleEncodedPaymentPayload() {
        return "eyJ4NDAyVmVyc2lvbiI6MiwicmVzb3VyY2UiOnsidXJsIjoiaHR0cHM6Ly9hcGkuZXhhbXBsZS5jb20vcHJlbWl1bS1kYXRhIiwiZGVzY3JpcHRpb24iOiJBY2Nlc3MgdG8gcHJlbWl1bSBtYXJrZXQgZGF0YSIsIm1pbWVUeXBlIjoiYXBwbGljYXRpb24vanNvbiJ9LCJhY2NlcHRlZCI6eyJzY2hlbWUiOiJleGFjdCIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJhbW91bnQiOiIxMDAwMCIsImFzc2V0IjoiMHgwMzZDYkQ1Mzg0MmM1NDI2NjM0ZTc5Mjk1NDFlQzIzMThmM2RDRjdlIiwicGF5VG8iOiIweDIwOTY5M0JjNmFmYzBDNTMyOGJBMzZGYUYwM0M1MTRFRjMxMjI4N0MiLCJtYXhUaW1lb3V0U2Vjb25kcyI6NjAsImV4dHJhIjp7Im5hbWUiOiJVU0RDIiwidmVyc2lvbiI6IjIifX0sInBheWxvYWQiOnsic2lnbmF0dXJlIjoiMHgyZDZhNzU4OGQ2YWNjYTUwNWNiZjBkOWE0YTIyN2UwYzUyYzZjMzQwMDhjOGU4OTg2YTEyODMyNTk3NjQxNzM2MDhhMmNlNjQ5NjY0MmUzNzdkNmRhOGRiYmY1ODM2ZTliZDE1MDkyZjllY2FiMDVkZWQzZDYyOTNhZjE0OGI1NzFjIiwiYXV0aG9yaXphdGlvbiI6eyJmcm9tIjoiMHg4NTdiMDY1MTlFOTFlM0E1NDUzODc5MWJEYmIwRTIyMzczZTM2YjY2IiwidG8iOiIweDIwOTY5M0JjNmFmYzBDNTMyOGJBMzZGYUYwM0M1MTRFRjMxMjI4N0MiLCJ2YWx1ZSI6IjEwMDAwIiwidmFsaWRBZnRlciI6IjE3NDA2NzIwODkiLCJ2YWxpZEJlZm9yZSI6IjE3NDA2NzIxNTQiLCJub25jZSI6IjB4ZjM3NDY2MTNjMmQ5MjBiNWZkYWJjMDg1NmYyYWViMmQ0Zjg4ZWU2MDM3YjhjYzVkMDRhNzFhNDQ2MmYxMzQ4MCJ9fX0";
    }

    /**
     * Get sample payment payload.
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
     * Get sample encoded payment payload without accepts.
     *
     * @return the sample encoded payment payload without accepts
     */
    protected String getSampleEncodedPaymentPayloadWithoutAccepts() {
        return "ewogICJ4NDAyVmVyc2lvbiI6IDIsCiAgImVycm9yIjogIlBBWU1FTlQtU0lHTkFUVVJFIGhlYWRlciBpcyByZXF1aXJlZCIsCiAgInJlc291cmNlIjogewogICAgInVybCI6ICJodHRwczovL2FwaS5leGFtcGxlLmNvbS9wcmVtaXVtLWRhdGEiLAogICAgImRlc2NyaXB0aW9uIjogIkFjY2VzcyB0byBwcmVtaXVtIG1hcmtldCBkYXRhIiwKICAgICJtaW1lVHlwZSI6ICJhcHBsaWNhdGlvbi9qc29uIgogIH0KfQ==";
    }

    /**
     * Get sample encoded payment response.
     *
     * @return the sample encoded payment response
     */
    protected String getSampleEncodedPaymentResponse() {
        return "eyJzdWNjZXNzIjpmYWxzZSwiZXJyb3JSZWFzb24iOiJpbnN1ZmZpY2llbnRfZnVuZHMiLCJ0cmFuc2FjdGlvbiI6IiIsIm5ldHdvcmsiOiJlaXAxNTU6ODQ1MzIiLCJwYXllciI6IjB4ODU3YjA2NTE5RTkxZTNBNTQ1Mzg3OTFiRGJiMEUyMjM3M2UzNmI2NiJ9";
    }

}
