package org.apache.struts2.factory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.factory.ResultBuilder;
import org.apache.struts2.StrutsTestCase;
import com.opensymphony.xwork2.result.ParamNameAwareResult;

import java.util.HashMap;
import java.util.Map;

public class StrutsResultBuilderTest extends StrutsTestCase {

    public void testAcceptParams() throws Exception {
        // given
        initDispatcherWithConfigs("struts-default.xml");
        StrutsResultBuilder builder = (StrutsResultBuilder) container.getInstance(ResultBuilder.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("accept", "ok");
        params.put("reject", "bad");
        ResultConfig config = new ResultConfig.Builder("struts", MyResult.class.getName()).addParams(params).build();
        Map<String, Object> context = new HashMap<String, Object>();

        // when
        Result result = builder.buildResult(config, context);

        // then
        assertEquals("ok", ((MyResult)result).getAccept());
        assertEquals("ok", ((MyResult)result).getReject());
    }

    public void testUseCustomResultBuilder() throws Exception {
        // given
        initDispatcherWithConfigs("struts-default.xml,struts-object-factory-result-builder.xml");

        // when
        ResultBuilder actual = container.getInstance(ResultBuilder.class);

        // then
        assertTrue(actual instanceof MyResultBuilder);
    }

    public static class MyResult implements Result, ParamNameAwareResult {

        private String accept;
        private String reject = "ok";

        public boolean acceptParamName(String name, String value) {
            return "accept".equals(name);
        }

        public void execute(ActionInvocation invocation) throws Exception {

        }

        public String getAccept() {
            return accept;
        }

        public void setAccept(String accept) {
            this.accept = accept;
        }

        public String getReject() {
            return reject;
        }

        public void setReject(String reject) {
            this.reject = reject;
        }
    }

}
