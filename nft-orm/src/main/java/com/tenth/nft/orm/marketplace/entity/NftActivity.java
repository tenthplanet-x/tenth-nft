package com.tenth.nft.orm.marketplace.entity;

import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tpulse.gs.convention.dao.annotation.SimpleCache;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.nft_exchange_activity")
public class NftActivity {

    @Id
    private Long id;

    @Indexed
    private Long assetsId;

    private Long createdAt;

    private Long updatedAt;

    private NftActivityEventType type;

    private MintEvent mint;

    private TransferEvent transfer;

    private ListEvent list;

    private OfferEvent offer;

    private SaleEvent sale;

    private ListCancelEvent cancel;

    private Boolean freeze = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public NftActivityEventType getType() {
        return type;
    }

    public void setType(NftActivityEventType type) {
        this.type = type;
    }

    public MintEvent getMint() {
        return mint;
    }

    public void setMint(MintEvent mint) {
        this.mint = mint;
    }

    public TransferEvent getTransfer() {
        return transfer;
    }

    public void setTransfer(TransferEvent transfer) {
        this.transfer = transfer;
    }

    public ListEvent getList() {
        return list;
    }

    public void setList(ListEvent list) {
        this.list = list;
    }

    public OfferEvent getOffer() {
        return offer;
    }

    public void setOffer(OfferEvent offer) {
        this.offer = offer;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public SaleEvent getSale() {
        return sale;
    }

    public void setSale(SaleEvent sale) {
        this.sale = sale;
    }

    public ListCancelEvent getCancel() {
        return cancel;
    }

    public void setCancel(ListCancelEvent cancel) {
        this.cancel = cancel;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }
}
