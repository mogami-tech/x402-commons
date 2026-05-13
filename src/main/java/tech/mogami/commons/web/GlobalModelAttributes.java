package tech.mogami.commons.web;

import lombok.experimental.UtilityClass;

/**
 * Global model attributes.
 */
@UtilityClass
@SuppressWarnings({"HideUtilityClassConstructor", "unused"})
public class GlobalModelAttributes {

    /** Error message. */
    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    /** Query attribute. */
    public static final String QUERY_ATTRIBUTE = "query";

    /** Result attribute. */
    public static final String RESULT_ATTRIBUTE = "result";

    /** Form attribute. */
    public static final String FORM_ATTRIBUTE = "form";

    /** Page title attribute. */
    public static final String PAGE_TITLE_ATTRIBUTE = "pageTitle";

    /** Page description attribute. */
    public static final String PAGE_DESCRIPTION_ATTRIBUTE = "pageDescription";

    /** Payment nonce attribute. */
    public static final String PAYMENT_NONCE_ATTRIBUTE = "nonce";

}
