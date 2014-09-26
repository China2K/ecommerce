package com.dk.core.service.product.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dk.core.common.constant.IConstants;
import com.dk.core.common.utils.CommonUtils;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.dao.product.IHotProductDao;
import com.dk.core.dao.product.IProductDao;
import com.dk.core.model.bean.HotProduct;
import com.dk.core.model.bean.Product;
import com.dk.core.service.product.IProductService;

@Service
public class ProductServiceImpl extends GenericService<Product, String>
		implements IProductService {

	@Resource
	private IProductDao productDao;

	@Resource
	private IHotProductDao hotProductDao;

	@Override
	public IGenericDao<Product, String> getDao() {
		return productDao;
	}

	@Override
	public List<Product> findActiveHotProducts() {
		List<HotProduct> hotProducts = hotProductDao.findAll(new Sort(
				Sort.Direction.ASC, "order"), new SearchBean("product.status",
				IConstants.PRODUCT_STATUS.ACTIVE, "="));
		return getProductsFromHot(hotProducts);
	}

	private List<Product> getProductsFromHot(List<HotProduct> hotProducts) {
		if (CommonUtils.isEmpty(hotProducts)) {
			return null;
		}
		List<Product> products = new ArrayList<Product>();
		for (HotProduct hotprod : hotProducts) {
			products.add(hotprod.getProduct());
		}
		return products;
	}
}
