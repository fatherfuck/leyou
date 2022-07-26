package com.leyou.goods.clint;

import com.leyou.item.api.GoosApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoosClint extends GoosApi{

}
