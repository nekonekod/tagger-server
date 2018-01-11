package com.nekonekod.tagger.taggerserver.dto;

import com.nekonekod.tagger.taggerserver.util.CollectionUtil;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.Data;

import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@Data
public class IllustQueryDto {

    private String source;
    private String sourceId;
    private String author;
    private String authorId;
    private List<String> tags;
    private String comment;
    private String title;
    private List<Integer> fav;
    private String queryOperator;


    public boolean isEmptyCondition() {
        return StringUtil.isNullOrEmpty(source) &&
                StringUtil.isNullOrEmpty(sourceId) &&
                StringUtil.isNullOrEmpty(author) &&
                StringUtil.isNullOrEmpty(authorId) &&
                StringUtil.isNullOrEmpty(comment) &&
                StringUtil.isNullOrEmpty(title) &&
                CollectionUtil.isEmpty(tags) &&
                CollectionUtil.isEmpty(fav);
    }
}
