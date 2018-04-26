package org.codechimp.appstatus.enums;

public enum AppStatusError {
    /**
     * No Internet connection available
     */
    NETWORK_NOT_AVAILABLE,

    /**
     * URL for the file is not valid
     */
    URL_MALFORMED,

    /**
     * XML file is invalid or is down
     */
    XML_ERROR,


    /**
     * JSON file is invalid or is down
     */
    JSON_ERROR
}
