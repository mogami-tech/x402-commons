package tech.mogami.commons.api.facilitator.settle;


import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import tech.mogami.commons.header.payment.PaymentPayload;
import tech.mogami.commons.header.payment.PaymentRequirements;

/**
 * Settle request is a request to the facilitator service.
 *
 * @param x402Version         x402 version
 * @param paymentPayload      paument payload
 * @param paymentRequirements payment requirements
 */
@Builder
@Jacksonized
@SuppressWarnings("unused")
public record SettleRequest(
        int x402Version,
        PaymentPayload paymentPayload,
        PaymentRequirements paymentRequirements) {
}
