package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {

    void decreaseInventoryQuantity(Map<String, Object> param);

    void increaseInventoryQuantity(Map<String, Object> param);

    //将更改库存函数拆分成了两个

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);

}
