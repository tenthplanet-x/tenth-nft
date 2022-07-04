package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.CurrencySearchDTO;
import com.tenth.nft.search.service.CurrencySearchService;
import com.tenth.nft.search.vo.CurrenySearchRequest;
import com.tpulse.commons.validation.Validations;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shijie
 */
@RestController
public class CurrenySearchController {

    @Autowired
    private CurrencySearchService currencySearchService;

    @RequestMapping(NftSearchPaths.CURRENCY_LIST)
    public Response list(@RequestBody CurrenySearchRequest request){
        Validations.check(request);
        List<CurrencySearchDTO> currencies = currencySearchService.listByBlockchain(request);
        return Response.successBuilder().data(currencies).build();
    }
}
