package tech.mogami.commons.header.payment.schemes;

import lombok.Builder;

/**
 * A scheme is a logical way of moving money.
 * Each payment scheme may have different operational functionality depending on what actions are necessary to fulfill the payment.
 *
 * @param name scheme name (ex: "exact)
 */
@Builder
@SuppressWarnings("unused")
public record Scheme(
        String name
) {
}
