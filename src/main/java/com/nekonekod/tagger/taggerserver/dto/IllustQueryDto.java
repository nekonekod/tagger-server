package com.nekonekod.tagger.taggerserver.dto;

import com.nekonekod.tagger.taggerserver.util.CollectionUtil;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        boolean flag = StringUtil.isNullOrEmpty(source) &&
                StringUtil.isNullOrEmpty(sourceId) &&
                StringUtil.isNullOrEmpty(author) &&
                StringUtil.isNullOrEmpty(authorId) &&
                StringUtil.isNullOrEmpty(comment) &&
                StringUtil.isNullOrEmpty(title);
        if (!flag) return false;
        if (Objects.nonNull(tags))
            tags = tags.stream().filter(StringUtil::notNullOrEmpty).collect(Collectors.toList());
        if (Objects.nonNull(fav))
            fav = fav.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return CollectionUtil.isEmpty(tags) && CollectionUtil.isEmpty(fav);
    }
}
