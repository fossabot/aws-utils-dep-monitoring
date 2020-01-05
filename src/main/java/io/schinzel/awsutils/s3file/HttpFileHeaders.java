package io.schinzel.awsutils.s3file;


import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import io.schinzel.basicutils.Checker;
import io.schinzel.basicutils.thrower.Thrower;

import java.util.Map;

/**
 * The purpose of this class is return HTTP file headers for different file types.
 *
 * @author schinzel
 */
class HttpFileHeaders {
    /** Holds the mapping between file extension and the HTTP header. */
    private static final Map<String, String> FILE_HEADERS = ImmutableMap.<String, String>builder()
            //Text files
            .put("htm", "text/html; charset=UTF-8")
            .put("html", "text/html; charset=UTF-8")
            .put("css", "text/css; charset=UTF-8")
            .put("js", "application/javascript; charset=UTF-8")
            .put("json", "application/json; charset=UTF-8")
            .put("map", "text/plain; charset=UTF-8")
            .put("txt", "text/plain; charset=UTF-8")
            //Misc
            .put("pdf", "application/pdf; charset=UTF-8")
            //Images
            .put("svg", "image/svg+xml")
            .put("ico", "image/ico")
            .put("png", "image/png")
            .put("jpg", "image/jpg")
            .put("jpeg", "image/jpeg")
            .put("gif", "image/gif")
            .build();


    /**
     * @param fileName The name of the file
     * @return The http header for the argument file based on the file extension.
     */
    static String getFileHeader(String fileName) {
        Thrower.throwIfVarEmpty(fileName, "fileName");
        String fileExtension = Files.getFileExtension(fileName);
        Thrower.throwIfTrue(Checker.isEmpty(fileExtension))
                .message("File extension missing in file name '" + fileName + "'.");
        Thrower.throwIfFalse(FILE_HEADERS.containsKey(fileExtension))
                .message("No file header exists for extension '" + fileExtension + "'.");
        return FILE_HEADERS.get(fileExtension);
    }

}
