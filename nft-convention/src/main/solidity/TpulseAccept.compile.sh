solcjs /Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-convention/src/main/solidity/TpulseAccept.sol --bin --abi --optimize -o /Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-convention/build/solidity


export JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-8.jdk/Contents/Home
web3j generate solidity -b /Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-convention/build/solidity/TpulseAccept_sol_Tpulse.bin -a /Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-convention/build/solidity/TpulseAccept_sol_Tpulse.abi -o /Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-convention/src/main/java -p com.tenth.nft.solidity --contractName=TpulseAcceptContract