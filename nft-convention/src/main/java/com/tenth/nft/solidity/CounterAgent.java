package com.tenth.nft.solidity;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class CounterAgent extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516102f63803806102f683398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610263806100936000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063371303c01461003b5780639fa6a6e314610045575b600080fd5b61004361005f565b005b61004d610126565b60405190815260200160405180910390f35b6000805460408051600481526024810182526020810180516001600160e01b031662dc4c0f60e61b17905290516001600160a01b03909216916100a291906101f2565b6000604051808303816000865af19150503d80600081146100df576040519150601f19603f3d011682016040523d82523d6000602084013e6100e4565b606091505b50506040805133815282151560208201529192507fd43b0d5c2623ff0fbac739b710b3d071e36073c0eea8f0c4fb7f754aff6f2241910160405180910390a150565b6000805460408051600481526024810182526020810180516001600160e01b0316639fa6a6e360e01b179052905183926001600160a01b031691610169916101f2565b6000604051808303816000865af19150503d80600081146101a6576040519150601f19603f3d011682016040523d82523d6000602084013e6101ab565b606091505b50506040805133815282151560208201529192507f82f2e305663ba9095ad8ab8508ed1e526e876759e516c86fe586fcbbebf4e5db910160405180910390a1600191505090565b6000825160005b8181101561021357602081860181015185830152016101f9565b81811115610222576000828501525b50919091019291505056fea2646970667358221220e22abb0f30ab28ab72888f1420f9436aecd3292d857eccca94cf59d5f744db9d64736f6c634300080f0033";

    public static final String FUNC_CURRENT = "current";

    public static final String FUNC_INC = "inc";

    public static final Event AGENTGET_EVENT = new Event("AgentGet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event AGENTINC_EVENT = new Event("AgentInc", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected CounterAgent(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CounterAgent(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CounterAgent(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CounterAgent(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<AgentGetEventResponse> getAgentGetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AGENTGET_EVENT, transactionReceipt);
        ArrayList<AgentGetEventResponse> responses = new ArrayList<AgentGetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AgentGetEventResponse typedResponse = new AgentGetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AgentGetEventResponse> agentGetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AgentGetEventResponse>() {
            @Override
            public AgentGetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AGENTGET_EVENT, log);
                AgentGetEventResponse typedResponse = new AgentGetEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AgentGetEventResponse> agentGetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AGENTGET_EVENT));
        return agentGetEventFlowable(filter);
    }

    public List<AgentIncEventResponse> getAgentIncEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AGENTINC_EVENT, transactionReceipt);
        ArrayList<AgentIncEventResponse> responses = new ArrayList<AgentIncEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AgentIncEventResponse typedResponse = new AgentIncEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AgentIncEventResponse> agentIncEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, AgentIncEventResponse>() {
            @Override
            public AgentIncEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AGENTINC_EVENT, log);
                AgentIncEventResponse typedResponse = new AgentIncEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AgentIncEventResponse> agentIncEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AGENTINC_EVENT));
        return agentIncEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> current() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CURRENT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> inc() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INC, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CounterAgent load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CounterAgent(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CounterAgent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CounterAgent(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CounterAgent load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CounterAgent(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CounterAgent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CounterAgent(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CounterAgent> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String counterAgent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, counterAgent)));
        return deployRemoteCall(CounterAgent.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CounterAgent> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String counterAgent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, counterAgent)));
        return deployRemoteCall(CounterAgent.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CounterAgent> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String counterAgent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, counterAgent)));
        return deployRemoteCall(CounterAgent.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CounterAgent> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String counterAgent) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, counterAgent)));
        return deployRemoteCall(CounterAgent.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class AgentGetEventResponse extends BaseEventResponse {
        public String user;

        public Boolean value;
    }

    public static class AgentIncEventResponse extends BaseEventResponse {
        public String user;

        public Boolean value;
    }
}
