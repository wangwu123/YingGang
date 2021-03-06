package com.pinyougou.manager.controller;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.PageResult;
import com.pinyougou.pojo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
//import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojogroup.Goods;
//import com.pinyougou.search.service.ItemSearchService;
import com.pinyougou.sellergoods.service.GoodsService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	@Reference(timeout=100000)
//	private ItemSearchService itemSearchService;

	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
//	@RequestMapping("/add")
//	public Result add(@RequestBody TbGoods goods){
//		try {
//			goodsService.add(goods);
//			return new Result(true, "增加成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(false, "增加失败");
//		}
//	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
//	@RequestMapping("/update")
//	public Result update(@RequestBody TbGoods goods){
//		try {
//			goodsService.update(goods);
//			return new Result(true, "修改成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(false, "修改失败");
//		}
//	}	
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
//	@RequestMapping("/findOne")
//	public TbGoods findOne(Long id){
//		return goodsService.findOne(id);		
//	}
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
//			itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	/**
	 * 查询+分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){


		return goodsService.findPage(goods, page, rows);		
	}


	/**
	 * 商品审核
	 */
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids,String status) {

		try {
			goodsService.updateStatus(ids, status);

			return new Result(true, "商品审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "商品审核失败");
		}
	}


	/**
	 * 更新状态
	 * @param ids
	 * @param status
	 */
/*	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status){
		try {
			goodsService.updateStatus(ids, status);
			
			if(status.equals("1")){//审核通过 查询该spu下的所有的sku
				List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
				//调用搜索接口实现数据批量导入
				if(itemList.size()>0){
					//视频中少写了，否则sku的规格信息没有导入索引库 
					//"item_spec_网络": "联通3G",
			        //"item_spec_机身内存": "16G",
					for(TbItem item:itemList){
						System.out.println(item.getId()+" "+item.getTitle());
						Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
						item.setSpecMap(specMap);//给带注解的字段赋值
					}	
					itemSearchService.importList(itemList);
				}else{
					System.out.println("没有明细数据");
				}
				//静态页生成
				for(Long goodsId:ids){
					itemPageService.genItemHtml(goodsId);
				}	
			}
			return new Result(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "失败");
		}
	}

	@Reference(timeout=40000)
	private ItemPageService itemPageService;
	*//**
	 * 生成静态页（测试）
	 * @param goodsId
	 *//*
	@RequestMapping("/genHtml")
	public void genHtml(Long goodsId){
		itemPageService.genItemHtml(goodsId);	
	}

	*/
}
