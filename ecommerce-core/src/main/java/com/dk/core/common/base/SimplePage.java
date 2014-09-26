package com.dk.core.common.base;

import java.util.List;

public class SimplePage<T>
{
	private int		totalCount;

	private List<T>	data;

	public SimplePage(List<T> data, int totalCount)
	{
		super();
		this.data = data;
		this.totalCount = totalCount;
	}

	public SimplePage()
	{
		super();
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public List<T> getData()
	{
		return data;
	}

	public void setData(List<T> data)
	{
		this.data = data;
	}

}
