// 这个合约会保留所有发送给它的以太币，没有办法取回。
contract Sink {
    event Received(address, uint);
    receive() external payable {
        emit Received(msg.sender, msg.value);
    }
}