package com.example.e_commerce.Listener;

import com.example.e_commerce.Model.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> responses, String message);
    void didError(String message);
}
