package com.tenth.nft.crawler.controler;

import com.tenth.nft.crawler.NftBotPaths;
import com.tenth.nft.crawler.vo.*;
import com.tpulse.commons.validation.Validations;
import com.tenth.nft.crawler.dto.NftBotDTO;
import com.tenth.nft.crawler.service.NftBotService;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
@RestController
public class NftBotWebController {

    @Autowired
    private NftBotService nftBotService;

    @RequestMapping(NftBotPaths.NFTBOT_LIST)
    public Response list(@RequestBody NftBotListRequest request){
        Validations.check(request);
        Page<NftBotDTO> dataPage = nftBotService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(NftBotPaths.NFTBOT_CREATE)
    public Response create(@RequestBody NftBotCreateRequest request){
        Validations.check(request);
        nftBotService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(NftBotPaths.NFTBOT_EDIT)
    public Response edit(@RequestBody NftBotEditRequest request){
        Validations.check(request);
        nftBotService.edit(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(NftBotPaths.NFTBOT_DETAIL)
    public Response delete(@RequestBody NftBotDeleteRequest request){
        Validations.check(request);
        NftBotDTO dto = nftBotService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

    @RequestMapping(NftBotPaths.NFTBOT_TOGGLEOFFLINE)
    public Response toggleOffline(@RequestBody NftBotToggleOfflineRequest request){
        Validations.check(request);
        nftBotService.toggleOffline(request);
        return Response.successBuilder().build();
    }


}
