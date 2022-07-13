package com.tenth.nft.convention;

import com.ruixi.tpulse.convention.TpulseConventionConfiguration;
import com.tenth.nft.convention.cmd.NftInnerCmdTypes;
import com.tpulse.gs.convention.cmd.CmdTypeRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.convention")
@Import(TpulseConventionConfiguration.class)
public class NftConventionConfiguration {

    @Bean
    public CmdTypeRegister nftCmdTypeRegister(){
        return CmdTypeRegister.newBuilder()
                .add(NftInnerCmdTypes.values())
                .build();
    }

}
