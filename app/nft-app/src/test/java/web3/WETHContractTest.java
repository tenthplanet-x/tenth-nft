package web3;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.solidity.TpulseContractHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class WETHContractTest {

    @Autowired
    private TpulseContractHelper tpulseContractHelper;

    @Test
    public void deposit(){

    }

    @Test
    public void withDraw(){

    }

}
