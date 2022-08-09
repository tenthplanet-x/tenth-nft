package com.tenth.nft.operation.controller.ops;

import com.tenth.nft.operation.NftCurrencyPaths;
import com.tenth.nft.operation.dto.NftCurrencyDTO;
import com.tenth.nft.operation.service.NftCurrencyService;
import com.tenth.nft.operation.vo.NftCurrencyCreateRequest;
import com.tenth.nft.operation.vo.NftCurrencyDeleteRequest;
import com.tenth.nft.operation.vo.NftCurrencyEditRequest;
import com.tenth.nft.operation.vo.NftCurrencyListRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@RestController
@HttpRoute(userAuth = true)
public class NftCurrencyOpsWebController {

    @Autowired
    private NftCurrencyService nftCurrencyService;

    @RequestMapping(NftCurrencyPaths.NFTCURRENCY_LIST)
    public Response list(@RequestBody NftCurrencyListRequest request){
        Validations.check(request);
        Page<NftCurrencyDTO> dataPage = nftCurrencyService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(NftCurrencyPaths.NFTCURRENCY_CREATE)
    public Response create(@RequestBody NftCurrencyCreateRequest request){
        Validations.check(request);
        nftCurrencyService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(NftCurrencyPaths.NFTCURRENCY_EDIT)
    public Response edit(@RequestBody NftCurrencyEditRequest request){
        Validations.check(request);
        nftCurrencyService.edit(request);
        return Response.successBuilder().build();
    }


    @RequestMapping(NftCurrencyPaths.NFTCURRENCY_DETAIL)
    public Response detail(@RequestBody NftCurrencyDeleteRequest request){
        Validations.check(request);
        NftCurrencyDTO dto = nftCurrencyService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

    @RequestMapping(NftCurrencyPaths.NFTCURRENCY_DELETE)
    public Response delete(@RequestBody NftCurrencyDeleteRequest request){
        Validations.check(request);
        nftCurrencyService.delete(request);
        return Response.successBuilder().build();
    }

}
