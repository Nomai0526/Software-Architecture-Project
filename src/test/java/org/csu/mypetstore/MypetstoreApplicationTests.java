package org.csu.mypetstore;

import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        System.out.println(orderMapper.getItemIdByOrderId(1));
    }

}
