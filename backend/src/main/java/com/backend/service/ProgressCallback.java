package com.backend.service;


@FunctionalInterface
public interface ProgressCallback {
    /**
     * progress 0..100, note is optional textual message
     */
    void onProgress(int progress, String note);
}

