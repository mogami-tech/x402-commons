package tech.mogami.commons.constant.x402;

/**
 * The x402 protocol involves three primary components.
 */
@SuppressWarnings("unused")
public enum X402Actor {

    /** Any application or agent that requests access to protected resources. */
    X402_CLIENT,

    /** A service that requires payment for access to protected resources (APIs, content, data, etc.). */
    X402_RESOURCE_SERVER,

    /** A service that handles payment verification and blockchain settlement. */
    X402_FACILITATOR

}
