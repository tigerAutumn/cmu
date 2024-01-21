package com.xxl.job.admin.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxl.job.admin.controller.annotation.PermessionLimit;
import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.rpc.codec.RpcRequest;
import com.xxl.job.core.rpc.codec.RpcResponse;
import com.xxl.job.core.rpc.netcom.NetComServerFactory;
import com.xxl.job.core.rpc.serialize.HessianSerializer;
import com.xxl.job.core.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xuxueli on 17/5/10.
 */
@Controller
@Slf4j
public class JobApiController {

    private RpcResponse doInvoke(HttpServletRequest request) {
        try {
            // deserialize request
            byte[] requestBytes = HttpClientUtil.readBytes(request);
            if (requestBytes == null || requestBytes.length==0) {
                RpcResponse rpcResponse = new RpcResponse();
                rpcResponse.setError("RpcRequest byte[] is null");
                return rpcResponse;
            }
            RpcRequest rpcRequest = (RpcRequest) HessianSerializer.deserialize(requestBytes, RpcRequest.class);

            // invoke
            RpcResponse rpcResponse = NetComServerFactory.invokeService(rpcRequest, null);
            return rpcResponse;
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setError("Server-error:" + e.getMessage());
            return rpcResponse;
        }
    }

    @RequestMapping(AdminBiz.MAPPING)
    @PermessionLimit(limit=false)
    public void api(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // invoke
        RpcResponse rpcResponse = doInvoke(request);

        // serialize response
        byte[] responseBytes = HessianSerializer.serialize(rpcResponse);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        //baseRequest.setHandled(true);

        OutputStream out = response.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }

}
