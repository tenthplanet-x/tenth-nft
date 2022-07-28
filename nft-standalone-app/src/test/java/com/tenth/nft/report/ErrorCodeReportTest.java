package com.tenth.nft.report;

import com.tpulse.gs.report.errorcode.ErrorCodeReportBuilder;
import org.junit.Test;

/**
 * @author shijie
 */
public class ErrorCodeReportTest {

    @Test
    public void generate() throws Exception{
        new ErrorCodeReportBuilder(
                "build/reports",
                "com.tenth.nft"
        ).build();
    }

}
