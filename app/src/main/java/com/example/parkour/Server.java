package com.example.parkour;

import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.IOException;
import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;

public class Server extends NanoHTTPD {
    private AssetManager assetManager;

    public Server(AssetManager assetManager) throws IOException {
        super(8080);
        this.assetManager = assetManager;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            String uri = session.getUri();
            uri = uri.substring(1);
            InputStream inputStream = assetManager.open(uri);
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri);
            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            return newChunkedResponse(Response.Status.OK, mimeType, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/html", "INTERNAL_ERROR");
    }
}