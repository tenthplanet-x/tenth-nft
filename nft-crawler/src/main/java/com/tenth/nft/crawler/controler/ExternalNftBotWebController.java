package com.tenth.nft.crawler.controler;

import com.tenth.nft.crawler.ExternalNftBotPaths;
import com.tenth.nft.crawler.vo.*;
import com.tpulse.commons.validation.Validations;
import com.tenth.nft.crawler.dto.ExternalNftBotDTO;
import com.tenth.nft.crawler.service.ExternalNftBotService;
import com.tpulse.gs.convention.dao.dto.Page;
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
public class ExternalNftBotWebController {

    @Autowired
    private ExternalNftBotService nftBotService;

    @RequestMapping(ExternalNftBotPaths.NFTBOT_LIST)
    public Response list(@RequestBody ExternalNftBotListRequest request){
        Validations.check(request);
        Page<ExternalNftBotDTO> dataPage = nftBotService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(ExternalNftBotPaths.NFTBOT_CREATE)
    public Response create(@RequestBody ExternalNftBotCreateRequest request){
        Validations.check(request);
        nftBotService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExternalNftBotPaths.NFTBOT_EDIT)
    public Response edit(@RequestBody ExternalNftBotEditRequest request){
        Validations.check(request);
        nftBotService.edit(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExternalNftBotPaths.NFTBOT_DETAIL)
    public Response delete(@RequestBody ExternalNftBotDeleteRequest request){
        Validations.check(request);
        ExternalNftBotDTO dto = nftBotService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

    @RequestMapping(ExternalNftBotPaths.NFTBOT_TOGGLEOFFLINE)
    public Response toggleOffline(@RequestBody ExternalNftBotToggleOfflineRequest request){
        Validations.check(request);
        nftBotService.toggleOffline(request);
        return Response.successBuilder().build();
    }


}
