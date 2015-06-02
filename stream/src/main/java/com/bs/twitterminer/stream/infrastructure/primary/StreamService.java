package com.bs.twitterminer.stream.infrastructure.primary;

import java.util.List;

public interface StreamService {

    void createStream(String streamId, List<String> keywords);

    void deleteStream(String streamId);
}
