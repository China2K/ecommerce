package com.dk.core.service.product;

import java.util.List;

import com.dk.core.common.base.service.IGenericService;
import com.dk.core.model.bean.Product;

public interface IProductService extends IGenericService<Product, String>{
	public List<Product>findActiveHotProducts();
}
