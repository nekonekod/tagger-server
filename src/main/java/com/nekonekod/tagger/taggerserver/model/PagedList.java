package com.nekonekod.tagger.taggerserver.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PagedList<T> implements Serializable {
    private List<T> pageList;

    private Paging page;

    public PagedList() {
    }

    public PagedList(List<T> pageList, Paging page) {
        this.pageList = pageList;
        this.page = page;
    }
}
