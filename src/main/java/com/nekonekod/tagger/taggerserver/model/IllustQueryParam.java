package com.nekonekod.tagger.taggerserver.model;

import com.nekonekod.tagger.taggerserver.annotation.WhereField;
import com.nekonekod.tagger.taggerserver.constant.QueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.dto.IllustQueryDto;
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

    public static IllustQueryParam fromIllustQueryDto(IllustQueryDto dto) {
        if (Objects.isNull(dto) || dto.isEmptyCondition()) return null;
        IllustQueryParam param = new IllustQueryParam();
        param.source = StringUtil.trimToNull(dto.getSource());
        param.sourceId = StringUtil.trimToNull(dto.getSourceId());
        param.author = StringUtil.trimToNull(dto.getAuthor());
        param.authorId = StringUtil.trimToNull(dto.getAuthorId());
        if (Objects.isNull(dto.getTags())) param.tags = Collections.emptyList();
        else param.tags = dto.getTags();
        param.tags = Optional.ofNullable(param.tags.stream()
                .map(StringUtil::trimToNull)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
        param.comment = StringUtil.trimToNull(dto.getComment());
        param.title = StringUtil.trimToNull(dto.getTitle());
        if (Objects.isNull(dto.getFav())) param.fav = Collections.emptyList();
        else param.fav = dto.getFav();
        param.operator = Optional.ofNullable(dto.getQueryOperator())
                .map(QueryOperator::valueOf)
                .orElse(QueryOperator.AND);
        return param;
    }
}
