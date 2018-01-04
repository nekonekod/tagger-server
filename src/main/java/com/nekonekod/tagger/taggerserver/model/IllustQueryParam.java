package com.nekonekod.tagger.taggerserver.model;

import com.nekonekod.tagger.taggerserver.annotation.JxQuery;
import com.nekonekod.tagger.taggerserver.constant.JxQueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.JxQueryOperator;
import com.nekonekod.tagger.taggerserver.constant.JxQueryType;
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

    @JxQuery(matcher = JxQueryMatcher.Equals)
    private String source;
    @JxQuery(matcher = JxQueryMatcher.Equals)
    private String sourceId;
    @JxQuery(matcher = JxQueryMatcher.Contains)
    private String author;
    @JxQuery(matcher = JxQueryMatcher.Equals)
    private String authorId;
    @JxQuery(matcher = JxQueryMatcher.Contains, listOperator = JxQueryOperator.AND)
    private List<String> tags;
    @JxQuery(matcher = JxQueryMatcher.Contains)
    private String comment;
    @JxQuery(matcher = JxQueryMatcher.Contains)
    private String title;
    @JxQuery(type = JxQueryType.Number, listOperator = JxQueryOperator.OR, matcher = JxQueryMatcher.Equals)
    private List<Integer> fav;

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
