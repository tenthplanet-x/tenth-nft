package com.tenth.nft.crawler.sdk.alchemy;

import com.tenth.nft.crawler.sdk.alchemy.dto.AlchemyNftDTO;
import com.tenth.nft.crawler.sdk.alchemy.dto.GetNFTsForCollectionResponse;
import com.wallan.json.JsonUtil;

import java.util.List;

/**
 * @author shijie
 * @createdAt 2022/6/14 19:59
 */
public class AlchemySdkMock extends AlchemySdk{


    @Override
    public List<AlchemyNftDTO> getItemsByConstract(String contractAddress, int fromTokenId) throws Exception {
        String json = """
                {
                    "nfts":
                    [
                        {
                            "contract":
                            {
                                "address": "0x57a204aa1042f6e66dd7730813f4024114d74f37"
                            },
                            "id":
                            {
                                "tokenId": "0x000000000000000000000000000000000000000000000000000000000000000a",
                                "tokenMetadata":
                                {
                                    "tokenType": "ERC721"
                                }
                            },
                            "title": "CyberKong #10",
                            "description": "",
                            "tokenUri":
                            {
                                "raw": "https://kongz.herokuapp.com/api/metadata/10",
                                "gateway": "https://kongz.herokuapp.com/api/metadata/10"
                            },
                            "media":
                            [
                                {
                                    "raw": "https://ipfs.io/ipfs/QmePU5uW3JzfHqzjBwWqJW8AYjpxgdLvr7eRX6sDK4nvLk",
                                    "gateway": "https://ipfs.io/ipfs/QmePU5uW3JzfHqzjBwWqJW8AYjpxgdLvr7eRX6sDK4nvLk"
                                }
                            ],
                            "metadata":
                            {
                                "name": "CyberKong #10",
                                "image": "https://ipfs.io/ipfs/QmePU5uW3JzfHqzjBwWqJW8AYjpxgdLvr7eRX6sDK4nvLk",
                                "external_url": "https://kongz.herokuapp.com/kong/10",
                                "attributes":
                                [
                                    {
                                        "value": "Shocked",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Red Tie",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Futuristic Shades",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Green Bandana",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "display_type": "date",
                                        "value": 1618671500,
                                        "trait_type": "birthday"
                                    },
                                    {
                                        "value": "Genesis",
                                        "trait_type": "Type"
                                    }
                                ]
                            },
                            "timeLastUpdated": "2022-04-05T11:50:12.362Z"
                        },
                        {
                            "contract":
                            {
                                "address": "0x57a204aa1042f6e66dd7730813f4024114d74f37"
                            },
                            "id":
                            {
                                "tokenId": "0x000000000000000000000000000000000000000000000000000000000000000b",
                                "tokenMetadata":
                                {
                                    "tokenType": "ERC721"
                                }
                            },
                            "title": "CyberKong #11",
                            "description": "",
                            "tokenUri":
                            {
                                "raw": "https://kongz.herokuapp.com/api/metadata/11",
                                "gateway": "https://kongz.herokuapp.com/api/metadata/11"
                            },
                            "media":
                            [
                                {
                                    "raw": "https://ipfs.io/ipfs/QmbVXhyh9fSHCEE5E7cnF4K867dtTKGkrYxJRXwVCQ8K9D",
                                    "gateway": "https://ipfs.io/ipfs/QmbVXhyh9fSHCEE5E7cnF4K867dtTKGkrYxJRXwVCQ8K9D"
                                }
                            ],
                            "metadata":
                            {
                                "name": "CyberKong #11",
                                "image": "https://ipfs.io/ipfs/QmbVXhyh9fSHCEE5E7cnF4K867dtTKGkrYxJRXwVCQ8K9D",
                                "external_url": "https://kongz.herokuapp.com/kong/11",
                                "attributes":
                                [
                                    {
                                        "value": "Green Tie",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Clear Glasses",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Grey Beanie",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "display_type": "date",
                                        "value": 1631762429,
                                        "trait_type": "birthday"
                                    },
                                    {
                                        "value": "Genesis",
                                        "trait_type": "Type"
                                    }
                                ]
                            },
                            "timeLastUpdated": "2022-04-05T12:09:13.941Z"
                        },
                        {
                            "contract":
                            {
                                "address": "0x57a204aa1042f6e66dd7730813f4024114d74f37"
                            },
                            "id":
                            {
                                "tokenId": "0x000000000000000000000000000000000000000000000000000000000000000c",
                                "tokenMetadata":
                                {
                                    "tokenType": "ERC721"
                                }
                            },
                            "title": "CyberKong #12",
                            "description": "",
                            "tokenUri":
                            {
                                "raw": "https://kongz.herokuapp.com/api/metadata/12",
                                "gateway": "https://kongz.herokuapp.com/api/metadata/12"
                            },
                            "media":
                            [
                                {
                                    "raw": "https://ipfs.io/ipfs/Qmc9KsJwqaWacG237NxiyM3go9DyThb8pLZwbUYztcTcaa",
                                    "gateway": "https://ipfs.io/ipfs/Qmc9KsJwqaWacG237NxiyM3go9DyThb8pLZwbUYztcTcaa"
                                }
                            ],
                            "metadata":
                            {
                                "name": "CyberKong #12",
                                "image": "https://ipfs.io/ipfs/Qmc9KsJwqaWacG237NxiyM3go9DyThb8pLZwbUYztcTcaa",
                                "external_url": "https://kongz.herokuapp.com/kong/12",
                                "attributes":
                                [
                                    {
                                        "value": "Gold Earring",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Gold Glasses",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "display_type": "date",
                                        "value": 1618612342,
                                        "trait_type": "birthday"
                                    },
                                    {
                                        "value": "Genesis",
                                        "trait_type": "Type"
                                    }
                                ]
                            },
                            "timeLastUpdated": "2022-04-05T11:52:04.245Z"
                        },
                        {
                            "contract":
                            {
                                "address": "0x57a204aa1042f6e66dd7730813f4024114d74f37"
                            },
                            "id":
                            {
                                "tokenId": "0x000000000000000000000000000000000000000000000000000000000000000d",
                                "tokenMetadata":
                                {
                                    "tokenType": "ERC721"
                                }
                            },
                            "title": "CyberKong #13",
                            "description": "",
                            "tokenUri":
                            {
                                "raw": "https://kongz.herokuapp.com/api/metadata/13",
                                "gateway": "https://kongz.herokuapp.com/api/metadata/13"
                            },
                            "media":
                            [
                                {
                                    "raw": "https://ipfs.io/ipfs/QmfTWxGXWDhwrtCJExKRPb8ByD59hGrhjxwr1RTtzS9feT",
                                    "gateway": "https://ipfs.io/ipfs/QmfTWxGXWDhwrtCJExKRPb8ByD59hGrhjxwr1RTtzS9feT"
                                }
                            ],
                            "metadata":
                            {
                                "name": "CyberKong #13",
                                "image": "https://ipfs.io/ipfs/QmfTWxGXWDhwrtCJExKRPb8ByD59hGrhjxwr1RTtzS9feT",
                                "external_url": "https://kongz.herokuapp.com/kong/13",
                                "attributes":
                                [
                                    {
                                        "value": "Fangs",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Brown Tie",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Gold Glasses",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "value": "Purple Bandana",
                                        "trait_type": "Genesis Trait"
                                    },
                                    {
                                        "display_type": "date",
                                        "value": 1618612342,
                                        "trait_type": "birthday"
                                    },
                                    {
                                        "value": "Genesis",
                                        "trait_type": "Type"
                                    }
                                ]
                            },
                            "timeLastUpdated": "2022-04-05T11:46:14.761Z"
                        }
                    ],
                    "nextToken": "0x000000000000000000000000000000000000000000000000000000000000006e"
                }
                """;

        GetNFTsForCollectionResponse getNFTsForCollectionResponse = JsonUtil.fromJson(json, GetNFTsForCollectionResponse.class);
        return getNFTsForCollectionResponse.getNfts();
    }
}
