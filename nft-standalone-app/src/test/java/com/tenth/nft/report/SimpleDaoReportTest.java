package com.tenth.nft.report;

import com.tpulse.gs.report.errorcode.ErrorCodeReportBuilder;
import com.tpulse.gs.report.orm.SimpleDaoReport;
import com.tpulse.gs.report.orm.SimpleDaoReportBuilder;
import org.junit.Test;

/**
 * @author shijie
 */
public class SimpleDaoReportTest {

    @Test
    public void generate() throws Exception{
        new SimpleDaoReportBuilder(
                "build/reports",
                "com.tenth.nft"
        ).build();
    }
}
