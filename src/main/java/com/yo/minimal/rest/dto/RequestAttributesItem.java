package com.yo.minimal.rest.dto;

import com.yo.minimal.rest.models.entity.AttributesItem;
import com.yo.minimal.rest.models.entity.Item;

public class RequestAttributesItem {
    private AttributesItem attributesItem;
    private Item item;

    public RequestAttributesItem() {
        super();
    }

    public AttributesItem getAttributesItem() {
        return attributesItem;
    }

    public void setAttributesItem(AttributesItem attributesItem) {
        this.attributesItem = attributesItem;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
