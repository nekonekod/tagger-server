package com.nekonekod.tagger.taggerserver.model;

import com.nekonekod.tagger.taggerserver.dto.TagDto;
import lombok.Data;

import java.util.Optional;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Data
public class TagParam {

    private String name;
    private String mapTo;

    public TagParam() {
    }

    public TagParam(String name, String mapTo) {
        this.name = name;
        this.mapTo = mapTo;
    }

    public static TagParam fromTagDto(TagDto tagDto) {
        return Optional.ofNullable(tagDto)
                .map(dto -> new TagParam(dto.getName(), dto.getMapTo()))
                .orElse(null);
    }
}
