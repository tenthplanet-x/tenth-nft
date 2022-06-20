package com.tenth.nft.convention;

import com.tenth.nft.convention.cmd.NftInnerCmdTypes;
import com.tpulse.gs.convention.cmd.CmdTypeRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.convention")
public class NftConventionConfiguration {

    @Bean
    public CmdTypeRegister nftCmdTypeRegister(){
        return CmdTypeRegister.newBuilder()
                .add(NftInnerCmdTypes.values())
                .build();
    }

}
