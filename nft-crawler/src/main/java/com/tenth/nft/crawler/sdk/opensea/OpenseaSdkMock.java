package com.tenth.nft.crawler.sdk.opensea;

import com.google.common.collect.Lists;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaCollectionDTO;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaConstractDTO;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaGetCollectionResponse;
import com.tenth.nft.crawler.sdk.opensea.dto.OpenseaItemDTO;
import com.wallan.json.JsonUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shijie
 * @createdAt 2022/6/14 14:22
 */
@Service
@Primary
public class OpenseaSdkMock extends OpenseaSdk {

    @Override
    public OpenseaCollectionDTO getByMarketplaceId(String marketplaceId) {
        String json = """
                
                {
                    "collection":
                    {
                        "payment_tokens":
                        [
                            {
                                "id": 13689077,
                                "symbol": "ETH",
                                "address": "0x0000000000000000000000000000000000000000",
                                "image_url": "https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg",
                                "name": "Ether",
                                "decimals": 18,
                                "eth_price": 1,
                                "usd_price": 1206.11
                            },
                            {
                                "id": 4645681,
                                "symbol": "WETH",
                                "address": "0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2",
                                "image_url": "https://openseauserdata.com/files/accae6b6fb3888cbff27a013729c22dc.svg",
                                "name": "Wrapped Ether",
                                "decimals": 18,
                                "eth_price": 1,
                                "usd_price": 1206.11
                            }
                        ],
                        "primary_asset_contracts":
                        [],
                        "traits":
                        {
                            "NFT":
                            {
                                "primate": 4189
                            }
                        },
                        "stats":
                        {
                            "one_day_volume": 191.91527093369584,
                            "one_day_change": 0,
                            "one_day_sales": 1975,
                            "one_day_average_price": 0.09717228908035232,
                            "seven_day_volume": 191.91527093369584,
                            "seven_day_change": 0,
                            "seven_day_sales": 1975,
                            "seven_day_average_price": 0.09717228908035232,
                            "thirty_day_volume": 191.91527093369584,
                            "thirty_day_change": 0,
                            "thirty_day_sales": 1975,
                            "thirty_day_average_price": 0.09717228908035232,
                            "total_volume": 191.91527093369584,
                            "total_sales": 1975,
                            "total_supply": 4189,
                            "count": 4189,
                            "num_owners": 2372,
                            "average_price": 0.09717228908035232,
                            "num_reports": 0,
                            "market_cap": 407.05471895759587,
                            "floor_price": 0.088778844
                        },
                        "banner_image_url": "https://openseauserdata.com/files/7e3d63f23009a2f1da8734caf6b0c288.jpg",
                        "chat_url": null,
                        "created_date": "2022-06-14T18:30:07.226116",
                        "default_to_fiat": false,
                        "description": "Primates is a project built for the community, by the community. The goal of Primates is to create a brand that facilitates a seamless adoption of the web3 space through our community-fueled ventures and collaborations.",
                        "dev_buyer_fee_basis_points": "0",
                        "dev_seller_fee_basis_points": "0",
                        "discord_url": "https://discord.gg/theprimates",
                        "display_data":
                        {
                            "card_display_style": "cover"
                        },
                        "external_url": null,
                        "featured": false,
                        "featured_image_url": "https://openseauserdata.com/files/654a0663de2e3456fdf4bb3c8070e504.jpg",
                        "hidden": false,
                        "safelist_request_status": "approved",
                        "image_url": "https://openseauserdata.com/files/89e468471b05a0d3f49b0b14def8879a.gif",
                        "is_subject_to_whitelist": false,
                        "large_image_url": "https://openseauserdata.com/files/654a0663de2e3456fdf4bb3c8070e504.jpg",
                        "medium_username": null,
                        "name": "Primates",
                        "only_proxied_transfers": false,
                        "opensea_buyer_fee_basis_points": "0",
                        "opensea_seller_fee_basis_points": "250",
                        "payout_address": null,
                        "require_email": false,
                        "short_description": null,
                        "slug": "primates-solana",
                        "telegram_url": null,
                        "twitter_username": null,
                        "instagram_username": null,
                        "wiki_url": null,
                        "is_nsfw": false
                    }
                }
                
                """;

        OpenseaGetCollectionResponse openseaGetCollectionResponse = JsonUtil.fromJson(json, OpenseaGetCollectionResponse.class);
        return openseaGetCollectionResponse.getCollection();
    }

    @Override
    public OpenseaConstractDTO getByConstract(String contractAddress) {
        String json = """
                {
                    "collection":
                    {
                        "banner_image_url": "https://openseauserdata.com/static/banners/cryptokitties-banner2.png",
                        "chat_url": null,
                        "created_date": "2019-04-26T22:13:04.207050",
                        "default_to_fiat": false,
                        "description": "CryptoKitties is a game centered around breedable, collectible, and oh-so-adorable creatures we call CryptoKitties! Each cat is one-of-a-kind and 100% owned by you; it cannot be replicated, taken away, or destroyed.",
                        "dev_buyer_fee_basis_points": "0",
                        "dev_seller_fee_basis_points": "0",
                        "discord_url": "https://discord.gg/cryptokitties",
                        "display_data":
                        {
                            "card_display_style": "padded"
                        },
                        "external_url": "https://www.cryptokitties.co/",
                        "featured": false,
                        "featured_image_url": "https://openseauserdata.com/0x06012c8cf97bead5deae237070f9587f8e7a266d-featured-1556589429.png",
                        "hidden": false,
                        "safelist_request_status": "verified",
                        "image_url": "https://lh3.googleusercontent.com/C272ZRW1RGGef9vKMePFSCeKc1Lw6U40wl9ofNVxzUxFdj84hH9xJRQNf-7wgs7W8qw8RWe-1ybKp-VKuU5D-tg=s60",
                        "is_subject_to_whitelist": false,
                        "large_image_url": "https://lh3.googleusercontent.com/C272ZRW1RGGef9vKMePFSCeKc1Lw6U40wl9ofNVxzUxFdj84hH9xJRQNf-7wgs7W8qw8RWe-1ybKp-VKuU5D-tg",
                        "medium_username": null,
                        "name": "CryptoKitties",
                        "only_proxied_transfers": false,
                        "opensea_buyer_fee_basis_points": "0",
                        "opensea_seller_fee_basis_points": "250",
                        "payout_address": null,
                        "require_email": false,
                        "short_description": null,
                        "slug": "cryptokitties",
                        "telegram_url": null,
                        "twitter_username": "CryptoKitties",
                        "instagram_username": null,
                        "wiki_url": "https://opensea.readme.io/page/cryptokitties",
                        "is_nsfw": false
                    },
                    "address": "0x06012c8cf97bead5deae237070f9587f8e7a266d",
                    "asset_contract_type": "non-fungible",
                    "created_date": "2018-01-23T04:51:38.832339",
                    "name": "CryptoKitties",
                    "nft_version": "1.0",
                    "opensea_version": null,
                    "owner": 463841,
                    "schema_name": "ERC721",
                    "symbol": "CKITTY",
                    "total_supply": null,
                    "description": "CryptoKitties is a game centered around breedable, collectible, and oh-so-adorable creatures we call CryptoKitties! Each cat is one-of-a-kind and 100% owned by you; it cannot be replicated, taken away, or destroyed.",
                    "external_link": "https://www.cryptokitties.co/",
                    "image_url": "https://lh3.googleusercontent.com/C272ZRW1RGGef9vKMePFSCeKc1Lw6U40wl9ofNVxzUxFdj84hH9xJRQNf-7wgs7W8qw8RWe-1ybKp-VKuU5D-tg=s60",
                    "default_to_fiat": false,
                    "dev_buyer_fee_basis_points": 0,
                    "dev_seller_fee_basis_points": 0,
                    "only_proxied_transfers": false,
                    "opensea_buyer_fee_basis_points": 0,
                    "opensea_seller_fee_basis_points": 250,
                    "buyer_fee_basis_points": 0,
                    "seller_fee_basis_points": 250,
                    "payout_address": null
                }
                
                """;
        return JsonUtil.fromJson(json, OpenseaConstractDTO.class);
    }

    @Override
    public List<OpenseaItemDTO> getItemsByConstract(String contractAddress, int fromTokenId, int limit) {
        //return super.getItemsByConstract(contractAddress, fromTokenId, limit);

        String json = """
                {
                    "id": 19719423,
                    "num_sales": 16,
                    "background_color": null,
                    "image_url": "https://lh3.googleusercontent.com/ucgZvUGtt4Fi57LXyMaoyyA2mLQMOmmxJyZOI9MXHzWkWD6StvxfQw23jxMK44Ag8-LlQ2zNgZXdO8O2LfCreeX8ESpqVtekkuqh",
                    "image_preview_url": "https://lh3.googleusercontent.com/ucgZvUGtt4Fi57LXyMaoyyA2mLQMOmmxJyZOI9MXHzWkWD6StvxfQw23jxMK44Ag8-LlQ2zNgZXdO8O2LfCreeX8ESpqVtekkuqh=s250",
                    "image_thumbnail_url": "https://lh3.googleusercontent.com/ucgZvUGtt4Fi57LXyMaoyyA2mLQMOmmxJyZOI9MXHzWkWD6StvxfQw23jxMK44Ag8-LlQ2zNgZXdO8O2LfCreeX8ESpqVtekkuqh=s128",
                    "image_original_url": "ipfs://ipfs/QmUFsPKHa623Dg9LsTGDRVJgK6SdJRkYT4dBDR7NzMYijB/image.gif",
                    "animation_url": "https://openseauserdata.com/files/b4569b518bf2f03b58cf1702f47c5737.mp4",
                    "animation_original_url": "ipfs://ipfs/QmUFsPKHa623Dg9LsTGDRVJgK6SdJRkYT4dBDR7NzMYijB/animation.mp4",
                    "name": "#LetsWalk - DeeKay #001",
                    "description": "His name is DeeKay and he's the animator behind the #LetsWalk project. His goal is to make walk cycle loops with new characters as much as possible.",
                    "external_link": "https://rarible.com/token/0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c:1",
                    "asset_contract":
                    {
                        "address": "0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c",
                        "asset_contract_type": "semi-fungible",
                        "created_date": "2021-03-16T03:28:25.093081",
                        "name": "#LetsWalk",
                        "nft_version": null,
                        "opensea_version": null,
                        "owner": 20976311,
                        "schema_name": "ERC1155",
                        "symbol": "#LetsWalk",
                        "total_supply": null,
                        "description": "LetsWalk guide: \\nhttps://deekaykwon.com/letswalk\\n\\n#LetsWalk is an ongoing project by animator DeeKay Kwon\\nHis goal is to make a total of 100 walk cycles.\\nJoin his journey and collect your favorite character walk! :)\\nEditions:  1~30",
                        "external_link": "https://app.rarible.com/collection/0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c",
                        "image_url": "https://lh3.googleusercontent.com/LS2AweBD09u_9ZJKCg0kygkkUwUEm48vdgFNYm7SoT-GvQ9iysUUCbSz5ILDKd3SfLFstKOOmKjeWdE7_SZwVhCSggHiLLwytEGN=s120",
                        "default_to_fiat": false,
                        "dev_buyer_fee_basis_points": 0,
                        "dev_seller_fee_basis_points": 1000,
                        "only_proxied_transfers": false,
                        "opensea_buyer_fee_basis_points": 0,
                        "opensea_seller_fee_basis_points": 250,
                        "buyer_fee_basis_points": 0,
                        "seller_fee_basis_points": 1250,
                        "payout_address": "0x7b640407513bc16167ef3450fd6339803982e976"
                    },
                    "permalink": "https://opensea.io/assets/ethereum/0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c/1",
                    "collection":
                    {
                        "payment_tokens":
                        [
                            {
                                "id": 13689077,
                                "symbol": "ETH",
                                "address": "0x0000000000000000000000000000000000000000",
                                "image_url": "https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg",
                                "name": "Ether",
                                "decimals": 18,
                                "eth_price": 1,
                                "usd_price": 1222.04
                            },
                            {
                                "id": 12182941,
                                "symbol": "DAI",
                                "address": "0x6b175474e89094c44da98b954eedeac495271d0f",
                                "image_url": "https://openseauserdata.com/files/dai-ethereum.svg",
                                "name": "Dai Stablecoin",
                                "decimals": 18,
                                "eth_price": 0.00082094,
                                "usd_price": 1.005
                            },
                            {
                                "id": 4645681,
                                "symbol": "WETH",
                                "address": "0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2",
                                "image_url": "https://openseauserdata.com/files/accae6b6fb3888cbff27a013729c22dc.svg",
                                "name": "Wrapped Ether",
                                "decimals": 18,
                                "eth_price": 1,
                                "usd_price": 1222.04
                            },
                            {
                                "id": 4403908,
                                "symbol": "USDC",
                                "address": "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48",
                                "image_url": "https://openseauserdata.com/files/749015f009a66abcb3bbb3502ae2f1ce.svg",
                                "name": "USD Coin",
                                "decimals": 6,
                                "eth_price": 0.00081889,
                                "usd_price": 1.003
                            }
                        ],
                        "primary_asset_contracts":
                        [
                            {
                                "address": "0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c",
                                "asset_contract_type": "semi-fungible",
                                "created_date": "2021-03-16T03:28:25.093081",
                                "name": "#LetsWalk",
                                "nft_version": null,
                                "opensea_version": null,
                                "owner": 20976311,
                                "schema_name": "ERC1155",
                                "symbol": "#LetsWalk",
                                "total_supply": null,
                                "description": "LetsWalk guide: \\nhttps://deekaykwon.com/letswalk\\n\\n#LetsWalk is an ongoing project by animator DeeKay Kwon\\nHis goal is to make a total of 100 walk cycles.\\nJoin his journey and collect your favorite character walk! :)\\nEditions:  1~30",
                                "external_link": "https://app.rarible.com/collection/0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c",
                                "image_url": "https://lh3.googleusercontent.com/LS2AweBD09u_9ZJKCg0kygkkUwUEm48vdgFNYm7SoT-GvQ9iysUUCbSz5ILDKd3SfLFstKOOmKjeWdE7_SZwVhCSggHiLLwytEGN=s120",
                                "default_to_fiat": false,
                                "dev_buyer_fee_basis_points": 0,
                                "dev_seller_fee_basis_points": 1000,
                                "only_proxied_transfers": false,
                                "opensea_buyer_fee_basis_points": 0,
                                "opensea_seller_fee_basis_points": 250,
                                "buyer_fee_basis_points": 0,
                                "seller_fee_basis_points": 1250,
                                "payout_address": "0x7b640407513bc16167ef3450fd6339803982e976"
                            }
                        ],
                        "traits":
                        {},
                        "stats":
                        {
                            "one_day_volume": 36.8609,
                            "one_day_change": 5.153739565943239,
                            "one_day_sales": 18,
                            "one_day_average_price": 2.047827777777778,
                            "seven_day_volume": 57.1149,
                            "seven_day_change": -0.8130599631437329,
                            "seven_day_sales": 27,
                            "seven_day_average_price": 2.1153666666666666,
                            "thirty_day_volume": 417.6997319999997,
                            "thirty_day_change": 0.9421581143665587,
                            "thirty_day_sales": 256,
                            "thirty_day_average_price": 1.6316395781249988,
                            "total_volume": 4097.414120889982,
                            "total_sales": 1798,
                            "total_supply": 81,
                            "count": 81,
                            "num_owners": 783,
                            "average_price": 2.2788732596718475,
                            "num_reports": 1,
                            "market_cap": 171.3447,
                            "floor_price": 0
                        },
                        "banner_image_url": "https://lh3.googleusercontent.com/cqSBuF7I_zTIZ_j57MHSUFR8a4ILad-kwFIECk2q386yRyGCtJpuS0TN0clV5VE_4v9oupK6eD9zJDNm4aUKQCwrMGPPkvzjtGqS=s2500",
                        "chat_url": null,
                        "created_date": "2021-03-16T04:10:29.128021",
                        "default_to_fiat": false,
                        "description": "LetsWalk guide: \\nhttps://deekaykwon.com/letswalk\\n\\n#LetsWalk is an ongoing project by animator DeeKay Kwon\\nHis goal is to make a total of 100 walk cycles.\\nJoin his journey and collect your favorite character walk! :)\\nEditions:  1~30",
                        "dev_buyer_fee_basis_points": "0",
                        "dev_seller_fee_basis_points": "1000",
                        "discord_url": "https://discord.gg/deekayverse",
                        "display_data":
                        {
                            "card_display_style": "padded"
                        },
                        "external_url": "https://app.rarible.com/collection/0x0825f050e9b021a0e9de8cb1fb10b6c9f41e834c",
                        "featured": false,
                        "featured_image_url": "https://lh3.googleusercontent.com/dQ_IEJc8dwAywDs0IUGUnstgVeNzC2uVPNrawfYEozvOredPypO3XVlJdPuWPZTUb9pJQle_3BnX-RLSszWiLu2WRcZOU3-tAq86Yw=s300",
                        "hidden": false,
                        "safelist_request_status": "verified",
                        "image_url": "https://lh3.googleusercontent.com/LS2AweBD09u_9ZJKCg0kygkkUwUEm48vdgFNYm7SoT-GvQ9iysUUCbSz5ILDKd3SfLFstKOOmKjeWdE7_SZwVhCSggHiLLwytEGN=s120",
                        "is_subject_to_whitelist": false,
                        "large_image_url": "https://lh3.googleusercontent.com/dQ_IEJc8dwAywDs0IUGUnstgVeNzC2uVPNrawfYEozvOredPypO3XVlJdPuWPZTUb9pJQle_3BnX-RLSszWiLu2WRcZOU3-tAq86Yw=s300",
                        "medium_username": null,
                        "name": "LetsWalk",
                        "only_proxied_transfers": false,
                        "opensea_buyer_fee_basis_points": "0",
                        "opensea_seller_fee_basis_points": "250",
                        "payout_address": "0x7b640407513bc16167ef3450fd6339803982e976",
                        "require_email": false,
                        "short_description": null,
                        "slug": "letswalk",
                        "telegram_url": null,
                        "twitter_username": "deekaymotion",
                        "instagram_username": "deekaymotion",
                        "wiki_url": null,
                        "is_nsfw": false
                    },
                    "decimals": null,
                    "token_metadata": "https://opensea.mypinata.cloud/ipfs/QmTs7nmoLL3k8i6Mw1sRcmpeRWWwFLg1iS8Ntfeh8RhFAA",
                    "is_nsfw": false,
                    "owner":
                    {
                        "user":
                        {
                            "username": "NullAddress"
                        },
                        "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/1.png",
                        "address": "0x0000000000000000000000000000000000000000",
                        "config": ""
                    },
                    "sell_orders": null,
                    "seaport_sell_orders": null,
                    "creator":
                    {
                        "user":
                        {
                            "username": "deekaymotion"
                        },
                        "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/30.png",
                        "address": "0x7b640407513bc16167ef3450fd6339803982e976",
                        "config": "verified"
                    },
                    "traits":
                    [],
                    "last_sale":
                    {
                        "asset":
                        {
                            "decimals": null,
                            "token_id": "1"
                        },
                        "asset_bundle": null,
                        "event_type": "successful",
                        "event_timestamp": "2022-01-01T15:38:22",
                        "auction_type": null,
                        "total_price": "14690000000000000000",
                        "payment_token":
                        {
                            "symbol": "ETH",
                            "address": "0x0000000000000000000000000000000000000000",
                            "image_url": "https://openseauserdata.com/files/6f8e2979d428180222796ff4a33ab929.svg",
                            "name": "Ether",
                            "decimals": 18,
                            "eth_price": "1.000000000000000",
                            "usd_price": "1223.859999999999900000"
                        },
                        "transaction":
                        {
                            "block_hash": "0xcd652d2c3c94b3f906b8252c42bfddb877c0f821c841217195fd8f41b8519783",
                            "block_number": "13920405",
                            "from_account":
                            {
                                "user":
                                {
                                    "username": "empongpat"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/23.png",
                                "address": "0x482b54622726e7a08500e6608a52809fcd00734a",
                                "config": ""
                            },
                            "id": 237751510,
                            "timestamp": "2022-01-01T15:38:22",
                            "to_account":
                            {
                                "user":
                                {
                                    "username": "OpenSea-Orders"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/22.png",
                                "address": "0x7be8076f4ea4a4ad08075c2508e481d6c946d12b",
                                "config": "verified"
                            },
                            "transaction_hash": "0xe072d03bee1eba87c834dd235b943e6c6ae75911aaa899c8c97410e620cd4098",
                            "transaction_index": "142"
                        },
                        "created_date": "2022-01-01T15:38:41.434369",
                        "quantity": "1"
                    },
                    "top_bid": null,
                    "listing_date": null,
                    "is_presale": false,
                    "transfer_fee_payment_token": null,
                    "transfer_fee": null,
                    "related_assets":
                    [],
                    "orders": null,
                    "auctions":
                    [],
                    "supports_wyvern": true,
                    "top_ownerships":
                    [
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "Summercat_Vault"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/18.png",
                                "address": "0x5e7472223d5d28af97832934dcb9e5408953cefb",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "empongpat"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/23.png",
                                "address": "0x482b54622726e7a08500e6608a52809fcd00734a",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "HulloFrenz"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/30.png",
                                "address": "0xc15753ae3b6099b8a3366b836433d6542645b876",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "0xd7"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/24.png",
                                "address": "0xd7510a925475cb8377bc8d2a7f1c792022b68df8",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "jackmaschka"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/15.png",
                                "address": "0x90f3146fa0e9e2056bbe383ecd2ff5864eaa78c5",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "shapiro500_vault"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/9.png",
                                "address": "0x7c3565d658a71526afd6f0e268faff2fe401623a",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "NotTodayNoNoNo"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/22.png",
                                "address": "0x57115f7d04c16f3983802b7c5a2d5089a188d76a",
                                "config": "verified"
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": null
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/15.png",
                                "address": "0x8488f78d943cf6b5e1231c5370fed186ba7a3044",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "SnaileXpress"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/6.png",
                                "address": "0x8e63e4376dde62cb82f5a91c4bce4156457dedb1",
                                "config": ""
                            },
                            "quantity": "1"
                        },
                        {
                            "owner":
                            {
                                "user":
                                {
                                    "username": "itscoldout"
                                },
                                "profile_img_url": "https://storage.googleapis.com/opensea-static/opensea-profile/12.png",
                                "address": "0x98187606980bd1f3c0e91319afb13393ac608ce4",
                                "config": ""
                            },
                            "quantity": "1"
                        }
                    ],
                    "ownership": null,
                    "highest_buyer_commitment": null,
                    "token_id": "1"
                }
                """;

        OpenseaItemDTO itemDTO = JsonUtil.fromJson(json, OpenseaItemDTO.class);
        itemDTO.setTokenId(String.valueOf(fromTokenId));
        return Lists.newArrayList(itemDTO);
    }
}
