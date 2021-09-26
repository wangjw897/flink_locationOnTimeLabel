package com.unionpay.wallet.udf;
import com.unionpay.wallet.udf.DESCryptUtil;
import org.apache.flink.table.functions.ScalarFunction;

public class TripleDESDecrypt extends ScalarFunction {
    public String eval(String content) {
        String returnVal = null;
        if (content != null && !content.equals("")) {
            try {
                returnVal = DESCryptUtil.decrypt3DES(content);
            } catch (Exception e) {
                // 异常返回null
                return null;
            }
            return returnVal;
        } else {
            return returnVal;
        }
    }
}
