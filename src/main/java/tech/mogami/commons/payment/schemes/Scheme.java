package tech.mogami.commons.payment.schemes;

import lombok.Builder;

/**
 * A scheme is a logical way of moving money.
 * Each payment scheme may have different operational functionality depending on what actions are necessary to fulfill the payment.
 *
 * @param name         scheme name (ex: "exact")
 * @param payloadClass class of the scheme payload
 */
@Builder
@SuppressWarnings("unused")
public record Scheme(
        String name,
        Class<?> payloadClass
) {
}
