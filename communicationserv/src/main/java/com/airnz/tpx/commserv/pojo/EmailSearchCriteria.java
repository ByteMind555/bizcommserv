package com.airnz.tpx.commserv.pojo;

/**
 * Custom search request pojo
 */
public class EmailSearchCriteria {
    private String authorization;
    private String mailbox;
    private String page;
    private String pageSize;
    private String sortOrder;
    private String totalRequired;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getTotalRequired() {
        return totalRequired;
    }

    public void setTotalRequired(String totalRequired) {
        this.totalRequired = totalRequired;
    }

    public EmailSearchCriteria(String authorization, String mailbox, String page, String pageSize, String sortOrder, String totalRequired) {
        this.authorization = authorization;
        this.mailbox = mailbox;
        this.page = page;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.totalRequired = totalRequired;
    }


}
