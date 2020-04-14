package org.csu.mypetstore;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {

    @Autowired
    CatalogService catalogService;
    @Autowired
    OrderMapper orderMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testCategory()
    {
        String Code = Integer.toString((int)(Math.random()*10000));  //生成四位随机数
        JSONObject msg = new JSONObject();
        try {
            msg.put("code",Code);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FpQ41ZKdmy1bnV2BeVM", "ePSzICHyZrjO76ksnQ6OIhaSe88IA8");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18273119245");
        request.putQueryParameter("SignName", "MyPetStore");
        request.putQueryParameter("TemplateCode", "SMS_187745541");
        request.putQueryParameter("TemplateParam", msg.toString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

}
