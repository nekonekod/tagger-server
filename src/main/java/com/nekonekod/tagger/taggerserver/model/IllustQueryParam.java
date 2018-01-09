package com.nekonekod.tagger.taggerserver.model;

import com.nekonekod.tagger.taggerserver.annotation.WhereField;
import com.nekonekod.tagger.taggerserver.constant.QueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Data
public class IllustQueryParam {

    @WhereField(matcher = QueryMatcher.Equals)
    private String source;
    @WhereField(matcher = QueryMatcher.Equals)
    private String sourceId;
    @WhereField(matcher = QueryMatcher.Contains)
    private String author;
    @WhereField(matcher = QueryMatcher.Equals)
    private String authorId;
    @WhereField(matcher = QueryMatcher.Contains, listOperator = QueryOperator.AND)
    private List<String> tags;
    @WhereField(matcher = QueryMatcher.Contains)
    private String comment;
    @WhereField(matcher = QueryMatcher.Contains)
    private String title;
    @WhereField(listOperator = QueryOperator.OR, matcher = QueryMatcher.Equals)
    private List<Integer> fav;

    private QueryOperator operator;

    public IllustQueryParam selfClean() {
        this.source = StringUtil.trimToNull(this.source);
        this.sourceId = StringUtil.trimToNull(this.sourceId);
        this.author = StringUtil.trimToNull(this.author);
        this.authorId = StringUtil.trimToNull(this.authorId);
        if (Objects.isNull(this.tags)) this.tags = Collections.emptyList();
        this.tags = Optional.ofNullable(this.tags.stream()
                .map(StringUtil::trimToNull)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
        this.comment = StringUtil.trimToNull(this.comment);
        this.title = StringUtil.trimToNull(this.title);
        if (Objects.isNull(this.fav)) this.fav = Collections.emptyList();
        return this;
    }
}
