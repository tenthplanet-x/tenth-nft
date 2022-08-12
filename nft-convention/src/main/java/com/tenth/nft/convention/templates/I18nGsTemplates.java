package com.tenth.nft.convention.templates;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftHeaders;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.client.GsConfigTemplateFactory;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class I18nGsTemplates {

    @Value("${spring.application.language:en}")
    private String defaultLanguage;

    @Autowired
    private GsConfigTemplateFactory gsConfigTemplateFactory;


    public <T> T get(TemplateType templateType) {

        String language = getLanguage();
        String templateName = concatWithI18n(templateType.getName(), language);
        return (T)gsConfigTemplateFactory.getFromRegeng(templateName);

    }

    private String getLanguage() {
        String language = GameUserContext.get().get(NftHeaders.LANGUAGE);
        if(Strings.isNullOrEmpty(language)){
            language = defaultLanguage;
        }
        return language;
    }

    private String concatWithI18n(String name, String language) {
        return String.format("%s_%s", name, language);
    }
}
