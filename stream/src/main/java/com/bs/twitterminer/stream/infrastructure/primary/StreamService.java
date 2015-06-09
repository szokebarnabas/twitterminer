package com.bs.twitterminer.stream.infrastructure.primary;

import java.util.List;

public interface StreamService {

    void createStream(String clientId, List<String> keywords);

    void deleteStream(String clientId);
}
