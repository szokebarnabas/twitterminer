package com.bs.twitterminer.analytics.infrastrucutre;

import java.util.List;

public interface StreamService {
    void sendStartStreamCommand(String clientId, List<String> keywords);
    void sendStopStreamCommand(String clientId);
}
