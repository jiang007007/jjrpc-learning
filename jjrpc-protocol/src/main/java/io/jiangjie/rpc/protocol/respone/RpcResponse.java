package io.jiangjie.rpc.protocol.respone;

import io.jiangjie.rpc.protocol.base.RpcMessage;

/**
 * RPC 响应类
 */
public class RpcResponse extends RpcMessage {
    private String error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
