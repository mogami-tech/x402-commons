package tech.mogami.commons.payment;

/**
 * Payment status.
 */
@SuppressWarnings("unused")
public enum PaymentStatus {

    /** Payment getStatus indicating the payment is pending, completed, or failed. */
    PENDING,

    /** Payment getStatus indicating the payment has been successfully completed. */
    COMPLETED,

    /** Payment getStatus indicating the payment has failed. */
    FAILED

}
