package com.nekonekod.tagger.taggerserver.model;

import lombok.Data;

/**
 * Created by HzQ on 16/8/29.
 */
@Data
public class Paging {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPageCount;

    public Paging() {
        this.pageNo = 1;
        this.pageSize = 20;
        this.totalCount = 0;
        this.totalPageCount = 0;
    }

    public Paging(long pageNo, long pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public Paging(long pageNo, long pageSize, long totalCount) {
        setPageNo(pageNo);
        setPageSize(pageSize);
        resetTotalCount(totalCount);
    }

    public Paging resetTotalCount(long count) {
        this.totalCount = count;
        this.totalPageCount = ((long) Math.ceil((double) count / pageSize));
        return this;
    }

    public long getOffset() {
        return (pageNo - 1) * pageSize;
    }
}



