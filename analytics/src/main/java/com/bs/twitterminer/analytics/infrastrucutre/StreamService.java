package com.bs.twitterminer.analytics.infrastrucutre;

import java.util.List;

public interface StreamService {
    void sendStartStreamCommand(String streamId, List<String> keywords);
    void sendStopStreamCommand(String streamId);
}
