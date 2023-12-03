package com.airnz.tpx.commserv.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holder Object servers as a intermediary between request Object or Dao
 */
public class EmailMessageDTO {

    private long timestamp;

    private String mailId;

    private List<String> toIds = new ArrayList<>();

    private List<String> ccIds = new ArrayList<>();

    private List<String> bccIds = new ArrayList<>();

    private String subject;

    private Map<String, List<String>> content = new HashMap<>();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public List<String> getToIds() {
        return toIds;
    }

    public void setToIds(List<String> toIds) {
        this.toIds = toIds;
    }

    public List<String> getCcIds() {
        return ccIds;
    }

    public void setCcIds(List<String> ccIds) {
        this.ccIds = ccIds;
    }

    public List<String> getBccIds() {
        return bccIds;
    }

    public void setBccIds(List<String> bccIds) {
        this.bccIds = bccIds;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, List<String>> getContent() {
        return content;
    }

    public void setContent(Map<String, List<String>> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Mail {").append("Id=").append(timestamp)
                .append(", from='").append(mailId).append('\'').append(", to=").append(toIds)
                .append(", cc=").append(ccIds).append(", bcc=").append(bccIds)
                .append(", subject='").append(subject).append('\'').append(", content=")
                .append(content).append('}').toString();
    }
}
