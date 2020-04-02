package org.csu.mypetstore;

import org.csu.mypetstore.service.CatelogService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistance")
class MypetstoreApplicationTests {

    @Autowired
    CatelogService catelogService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCategory()
    {

        System.out.println(catelogService.getCategory("BIRDS").getName());
    }

}
