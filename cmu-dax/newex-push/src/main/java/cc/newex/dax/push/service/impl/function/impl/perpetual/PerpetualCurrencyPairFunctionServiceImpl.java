package cc.newex.dax.push.service.impl.function.impl.perpetual;

import com.alibaba.fastjson.JSONObject;

import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.service.impl.function.FunctionService;

public class PerpetualCurrencyPairFunctionServiceImpl implements FunctionService {

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONObject dataJson = (JSONObject) data;
    return dataJson.getBoolean("data");
  }
}
