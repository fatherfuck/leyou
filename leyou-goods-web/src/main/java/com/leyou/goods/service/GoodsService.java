package com.leyou.goods.service;

import com.leyou.goods.clint.BrandClient;
import com.leyou.goods.clint.CategoryClient;
import com.leyou.goods.clint.GoosClint;
import com.leyou.goods.clint.SpecificationClient;
import com.leyou.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {
    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoosClint goosClint;

    @Autowired
    private SpecificationClient specificationClient;

    public Map<String,Object> loadData(Long spuId){
        Map<String,Object> model = new HashMap<>();
        //根据spuid查询spu
        Spu spu = this.goosClint.querySpuByid(spuId);

        //查询spuDetail
        SpuDetail spuDetail = this.goosClint.querySpuDetailBySpuId(spuId);

        //查询分类：Map<String,Object>
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> names = this.categoryClient.queryNameByIds(cids);
            //初始化分类Map
        List<Map<String,Object>> categories = new ArrayList<>();
        for (int i = 0; i < cids.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",cids.get(i));
            map.put("name",names.get(i));
            categories.add(map);
        }

        //查询brand
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //查询skus
        List<Sku> skus = this.goosClint.querySkusBySpuId(spuId);

        //查询规格参数组
        List<SpecGroup> groups = this.specificationClient.queryGroupsWithParm(spu.getCid3());

        //查询特殊规格参数
        List<SpecParam> params = this.specificationClient.queryParams(null, spu.getCid3(), false, null);
            //初始化特殊规格参数的Map
        Map<Long,String> paramMap = new HashMap<>();
        params.forEach(specParam -> {
            paramMap.put(specParam.getId(),specParam.getName());
        });


        model.put("spu",spu);
        model.put("spuDetail",spuDetail);
        model.put("cateGories",categories);
        model.put("brand",brand);
        model.put("skus",skus);
        model.put("groups",groups);
        model.put("paramMap",paramMap);
        return model;
    }
}
