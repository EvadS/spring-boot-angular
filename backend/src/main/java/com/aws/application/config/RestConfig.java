package com.aws.application.config;

import javax.print.DocFlavor;

public class RestConfig {
    public static final String IMAGE_CONTROLLER_API = "api/v1/image";
    public static final String TITLE_REQUEST_PARAM = "title";
    public static final String FILE_REQUEST_PARAM = "file";
    public static final String SEARCH_API = "/search";
    public static final String DOWNLOAD_IMAGE_API = "/download/{id}";


    private RestConfig() {
    }
}
