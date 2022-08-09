package com.tenth.nft.operation.controller.ops;

import com.tenth.nft.operation.vo.*;
import com.tpulse.commons.validation.Validations;
import com.tenth.nft.operation.CurrencyRatePaths;
import com.tenth.nft.operation.dto.CurrencyRateDTO;
import com.tenth.nft.operation.service.CurrencyRateService;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
@RestController
@HttpRoute(userAuth = true)
public class CurrencyRateWebController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @RequestMapping(CurrencyRatePaths.CURRENCYRATE_LIST)
    public Response list(@RequestBody CurrencyRateListRequest request){
        Validations.check(request);
        Page<CurrencyRateDTO> dataPage = currencyRateService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(CurrencyRatePaths.CURRENCYRATE_CREATE)
    public Response create(@RequestBody CurrencyRateCreateRequest request){
        Validations.check(request);
        currencyRateService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(CurrencyRatePaths.CURRENCYRATE_EDIT)
    public Response edit(@RequestBody CurrencyRateEditRequest request){
        Validations.check(request);
        currencyRateService.edit(request);
        return Response.successBuilder().build();
    }


    @RequestMapping(CurrencyRatePaths.CURRENCYRATE_DETAIL)
    public Response detail(@RequestBody CurrencyRateDetailRequest request){
        Validations.check(request);
        CurrencyRateDTO dto = currencyRateService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

    @RequestMapping(CurrencyRatePaths.CURRENCYRATE_DELETE)
    public Response delete(@RequestBody CurrencyRateDeleteRequest request){
        Validations.check(request);
        currencyRateService.delete(request);
        return Response.successBuilder().build();
    }



}
