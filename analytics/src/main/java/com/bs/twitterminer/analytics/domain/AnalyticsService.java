package com.bs.twitterminer.analytics.domain;

import java.util.List;

public interface AnalyticsService {
    void sendStartStreamCommand(String streamId, List<String> keywords);
    void sendStopStreamCommand(String streamId);
}
