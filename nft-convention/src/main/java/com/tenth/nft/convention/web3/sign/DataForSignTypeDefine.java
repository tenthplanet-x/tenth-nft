package com.tenth.nft.convention.web3.sign;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
public class DataForSignTypeDefine {

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Item{

        private String name;

        private String type;

        public Item(){}

        public Item(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Builder{

        List<Item> output = new ArrayList<>();

        public Builder add(String name, String type) {
            output.add(new Item(name, type));
            return this;
        }

        public Object build() {
            return output;
        }
    }
}
