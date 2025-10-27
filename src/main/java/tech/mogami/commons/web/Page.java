package tech.mogami.commons.web;

import lombok.Builder;

/**
 * Page configuration.
 *
 * @param name        The page name.
 * @param url         The URL.
 * @param view        The view.
 * @param menu        The menu.
 * @param submenu     The submenu.
 * @param title       The message code to look up for the title.
 * @param description The message code to look up for the description.
 */
@Builder
@SuppressWarnings("unused")
public record Page(
        String name,
        String url,
        String view,
        String menu,
        String submenu,
        String title,
        String description) {

    /** Page and fragment separator. */
    private static final String PAGE_AND_FRAGMENT_SEPARATOR = " :: ";

    /** Page fragment suffix. */
    private static final String PAGE_FRAGMENT_SUFFIX = "-fragment";

    /**
     * Returns the view fragment.
     *
     * @return view fragment
     */
    public String viewFragment() {
        return view
                + PAGE_AND_FRAGMENT_SEPARATOR
                + view.replace("/", "-")
                + PAGE_FRAGMENT_SUFFIX;
    }

}
