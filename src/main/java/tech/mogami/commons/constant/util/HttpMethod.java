package tech.mogami.commons.constant.util;

import java.util.Set;

/**
 * HTTP methods.
 */
@SuppressWarnings("unused")
public enum HttpMethod {

    /** HTTP GET method. */
    GET,

    /** HTTP POST method. */
    POST,

    /** HTTP PUT method. */
    PUT,

    /** HTTP DELETE method. */
    DELETE,

    /** HTTP PATCH method. */
    PATCH,

    /** HTTP HEAD method. */
    HEAD;

    /** Allowed HTTP methods. */
    public static final Set<HttpMethod> ALLOWED_METHODS = Set.of(GET, HEAD, DELETE, POST, PUT, PATCH);

}
