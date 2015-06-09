package org.bs.messaging;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Tweet implements Serializable {
    private final Long statusId;
    private final String userName;
    private final Date createdAt;
    private final String text;
    private final String clientId;
    private final String profileImageUrl;
    private final List<String> keywords;
}
