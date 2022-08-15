package com.tenth.nft.convention.templates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.ruixi.tpulse.convention.protobuf.app.AppChat;

/**
 * @author shijie
 */
@JsonDeserialize(builder = WalletActivityConfig.Builder.class)
public class WalletActivityConfig {

    private Integer id;

    private String description;

    private String name;

    private String type;

    private String icon;

    private String incomeExpense;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public String getIncomeExpense() {
        return incomeExpense;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder{

        private WalletActivityConfig config = new WalletActivityConfig();

        public void setId(Integer id) {
            config.id = id;
        }

        public void setDescription(String description) {
            config.description = description;
        }

        public void setName(String name) {
            config.name = name;
        }

        public void setType(String type) {
            config.type = type;
        }

        public void setIcon(String icon) {
            config.icon = icon;
        }

        public void setIncomeExpense(String incomeExpense) {
            config.incomeExpense = incomeExpense;
        }

        public WalletActivityConfig build(){
            return config;
        }

    }
}
