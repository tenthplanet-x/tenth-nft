package com.tenth.nft.marketplace.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftUbtLogDao;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftUbtLogDTO;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftUbtLog;
import com.tenth.nft.marketplace.common.dto.NftUbtLogDTO;
import com.tenth.nft.marketplace.common.service.AbsNftUbtLogService;
import com.tenth.nft.marketplace.common.vo.NftUbtLogListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInNftUbtLogService extends AbsNftUbtLogService<BuildInNftUbtLog> {

    @Autowired
    private RouteClient routeClient;

    public BuildInNftUbtLogService(BuildInNftUbtLogDao nftUbtLogDao) {
        super(nftUbtLogDao);
    }

    @Override
    protected BuildInNftUbtLog newNftUbtLog() {
        return new BuildInNftUbtLog();
    }

//    @Override
//    protected NftUbtLogDTO toDTO(BuildInNftUbtLog nftActivity) {
//        BuildInNftUbtLogDTO dto = (BuildInNftUbtLogDTO) super.toDTO(nftActivity);
//
//    }

    @Override
    public Page<NftUbtLogDTO> list(NftUbtLogListRequest request) {

        Page<NftUbtLogDTO> dataPage = super.list(request);

        List<NftUbtLogDTO> dtos = dataPage.getData();
        if(!dtos.isEmpty()){
            //fill with userProfile
            Set<Long> fromUids = dtos.stream().map(dto -> dto.getFrom()).filter(Objects::nonNull).filter(from -> !NullAddress.TOKEN.equals(from)).map(Long::valueOf).collect(Collectors.toSet());
            Set<Long> toUids = dtos.stream().map(dto -> dto.getTo()).filter(Objects::nonNull).filter(from -> !NullAddress.TOKEN.equals(from)).map(Long::valueOf).collect(Collectors.toSet());
            fromUids.addAll(toUids);
            Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                    Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(fromUids).build(),
                    SearchUserProfileRouteRequest.class
            ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
            dtos.stream().forEach(dto -> {
                if(null != dto.getFrom()){
                    ((BuildInNftUbtLogDTO)dto).setFromProfile(userProfileDTOMap.get(Long.valueOf(dto.getFrom())));
                }
                if(null != dto.getTo()){
                    ((BuildInNftUbtLogDTO)dto).setToProfile(userProfileDTOMap.get(Long.valueOf(dto.getTo())));
                }
            });
        }

        return dataPage;

    }

    @Override
    protected NftUbtLogDTO newDTO() {
        return new BuildInNftUbtLogDTO();
    }
}
