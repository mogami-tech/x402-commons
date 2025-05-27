package tech.mogami.commons.api.facilitator.settle;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

/**
 * Settle result returned by the x402 facilitator for a settle attempt.
 *
 * @param success     true if the payment succeeded
 * @param network     identifier of the blockchain network, or null
 * @param transaction blockchain transaction hash of the settled payment, or null
 * @param errorReason error message from the facilitator, or null
 * @param payer       payer address, or null
 */
@Builder
@Jacksonized
@SuppressWarnings("unused")
public record SettleResponse(
        boolean success,
        String network,
        String transaction,
        String errorReason,
        String payer
) {
}
