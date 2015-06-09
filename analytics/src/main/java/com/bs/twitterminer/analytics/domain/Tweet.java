package com.bs.twitterminer.analytics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tweet implements Serializable {
    private Long statusId;
    private String userName;
    private Date createdAt;
    private String text;
    private String clientId;
    private String profileImageUrl;
    private List<String> keywords;
}
