package com.tenth.nft.crawler.sdk.alchemy.dto;

/**
 * @author shijie
 * @createdAt 2022/6/14 19:29
 */
public class AlchemyNftDTO {

    private String title;

    private AlchemyNftMetadataDTO metadata;

    private AlchemyNftIdDTO id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AlchemyNftMetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(AlchemyNftMetadataDTO metadata) {
        this.metadata = metadata;
    }

    public AlchemyNftIdDTO getId() {
        return id;
    }

    public void setId(AlchemyNftIdDTO id) {
        this.id = id;
    }
}
