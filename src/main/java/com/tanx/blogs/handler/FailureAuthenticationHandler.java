package com.tanx.blogs.handler;


import com.alibaba.druid.support.json.JSONUtils;
import com.tanx.blogs.utils.IsEmptyUtil;
import com.tanx.blogs.utils.VariableUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 * 登录失败
 *
 * @author tan
 */
public class FailureAuthenticationHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        PrintWriter writer = httpServletResponse.getWriter();
        Map<String, Object> map = new HashMap<>(4);

        if (IsEmptyUtil.isStringEmpty(VariableUtils.loginName)) {
            map.put("state", 0);
            map.put("message", "\u7528\u6237\u540D\u4E0D\u80FD\u4E3A\u7A7A\uFF01");
        } else if (!VariableUtils.active) {
            map.put("state", 0);
            map.put("message", "\u8D26\u53F7\u672A\u6FC0\u6D3B\uFF01");
        } else {
            map.put("state", 0);
            map.put("message", "\u7528\u6237\u540D\u6216\u5BC6\u7801\u9519\u8BEF\uFF01");
        }

        writer.write(JSONUtils.toJSONString(map));
    }
}
