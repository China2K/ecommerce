package com.dk.core.common.utils.search;

import java.awt.image.RasterFormatException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import ognl.OgnlOps;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * 将自定义查询的值封装为SearchBean数组，并动态生成DetachedCriteria对象实例。
 * <p>
 */
@SuppressWarnings("deprecation")
public class SearchFactory {

	/**
	 * @param scarchBunch
	 *            格式：
	 *            [name,value,relation],[name,value,relation],[name,value,relation
	 *            ] name: 字段名称 value: 字段值 relation:条件关系，包含：=, <, >, <=, >=,
	 *            like, in type: 字段类型，包含：numeric, string, date, boolean
	 * @return
	 */
	public static SearchBean[] getSearchTeam(String scarchBunch) {
		String[] team = detach(scarchBunch, '[', ']');
		if (team == null)
			return null;
		SearchBean[] search = new SearchBean[team.length];
		for (int i = 0; i < team.length; i++) {
			String[] element = StringUtils.split(team[i], ',');
			if (element == null || element.length != 3)
				throw new RasterFormatException("Unable to parse the string: "
						+ scarchBunch);
			search[i] = new SearchBean(element[0], element[1], element[2]);
		}
		return search;
	}

	/**
	 * 取得查询的月份
	 * 
	 * @param searchBean
	 * @return 0～12，0表示格式错误或没有日期条件
	 */
	public static int getSearchMonth(SearchBean[] searchBean) {
		if (searchBean == null || searchBean.length == 0)
			return 0;
		Date date;
		for (int i = 0; i < searchBean.length; i++) {
			date = checkDate(StringUtils.split(searchBean[i].getValue(), ";")[0]);
			if (date == null)
				continue;
			return date.getMonth() + 1;
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static DetachedCriteria generateCriteria(Class entity,
			SearchBean[] searchBean) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entity);
		if (searchBean == null || searchBean.length == 0)
			return criteria;
		// 对于多表级联，需要进行别名
		HashMap<String, String> tempMap = generateTempMap(searchBean);
		LinkedHashMap<String, String> aliasMap = generateAliasMap(tempMap);
		ExpressionBean[] expressionBeans = generateExpressionBeans(searchBean,
				tempMap, entity);
		// 进行别名命名
		if (!aliasMap.isEmpty()) {
			for (Iterator it = aliasMap.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> e = (Entry<String, String>) it.next();
				criteria.createAlias(e.getKey(), e.getValue());
			}
		}
		for (int i = 0; i < expressionBeans.length; i++) {
			String name = expressionBeans[i].getName();
			Object value = expressionBeans[i].getValue();
			if (expressionBeans[i].getRelation().equals("="))
				criteria.add(Expression.eq(name, value));
			else if (expressionBeans[i].getRelation().equals("!="))
				criteria.add(Expression.ne(name, value));
			else if (expressionBeans[i].getRelation().equals("<"))
				criteria.add(Expression.lt(name, value));
			else if (expressionBeans[i].getRelation().equals("<="))
				criteria.add(Expression.le(name, value));
			else if (expressionBeans[i].getRelation().equals(">"))
				criteria.add(Expression.gt(name, value));
			else if (expressionBeans[i].getRelation().equals(">="))
				criteria.add(Expression.ge(name, value));
			else if (expressionBeans[i].getRelation().equals("like"))
				criteria.add(Expression.like(name, value));
			else if (expressionBeans[i].getRelation().equals("in"))
				criteria.add(Expression.in(name, (List) value));
			else if (expressionBeans[i].getRelation().equals("between"))
				criteria.add(Expression.between(name, ((List) value).get(0),
						((List) value).get(1)));
			else if (expressionBeans[i].getRelation().equals("isNull"))
				criteria.add(Expression.isNull(name));
			else if (expressionBeans[i].getRelation().equals("isNotNull"))
				criteria.add(Expression.isNotNull(name));
			else if (expressionBeans[i].getRelation().equals("or")) {
				String[] names = expressionBeans[i].getName().split("-");
				criteria.add(Expression.or(Expression.eq(names[0], value),
						Expression.eq(names[1], value)));
			}
		}
		return criteria;
	}

	/**
	 * 对需要进行别名的属性进行别名 eg: subscriber.customer.name 解析为
	 * [[subscriber,t],[subscriber.customer, t1]]
	 */
	private static HashMap<String, String> generateTempMap(
			SearchBean[] searchBean) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (searchBean != null && searchBean.length != 0) {
			for (int i = 0; i < searchBean.length; i++) {
				if (searchBean[i] != null) {
					String name = searchBean[i].getName();
					if (name.contains(".")) {
						ArrayList<String> keyList = splitName(name);
						for (String key : keyList) {
							if (!map.containsKey(key)) {
								String alias = createAlias(map);
								map.put(key, alias);
							}
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 对于AliasMap中的属性进行替换 eg: [[subscriber,t],[subscriber.customer, t1]] 替换为
	 * [[subscriber,t],[t.customer, t1]]
	 */
	private static LinkedHashMap<String, String> generateAliasMap(
			HashMap<String, String> map) {
		LinkedHashMap<String, String> rval = new LinkedHashMap<String, String>();
		// 对key进行排序
		Object[] keys = map.keySet().toArray();
		Comparator<Object> c = new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				int a1 = ((String) o1).split(".").length;
				int a2 = ((String) o2).split(".").length;
				if (a1 > a2)
					return 1;
				if (a1 == a2)
					return 0;
				else
					return -1;
			}
		};
		Arrays.sort(keys, c);
		for (Object key : keys) {
			String k = (String) key;
			if (k.contains(".")) {
				String key1 = k.substring(0, k.lastIndexOf("."));
				String key2 = k.substring(k.lastIndexOf("."));
				k = map.get(key1) + key2;
			}
			String value = map.get(key);
			rval.put(k, value);
		}
		return rval;
	}

	/**
	 * 对属性名进行解析 eg: subscriber.customer.name 解析为 [subscriber,
	 * subscriber.customer]
	 */
	private static ArrayList<String> splitName(String name) {
		ArrayList<String> rval = new ArrayList<String>();
		while (name.contains(".")) {
			name = name.substring(0, name.lastIndexOf("."));
			rval.add(name);
		}
		return rval;
	}

	/**
	 * 解析SearchBean为ExpressionBean
	 */
	private static ExpressionBean[] generateExpressionBeans(
			SearchBean[] searchBean, HashMap<String, String> aliasMap,
			Class entity) {
		ExpressionBean[] beans = new ExpressionBean[searchBean.length];
		for (int i = 0; i < searchBean.length; i++) {
			String name = searchBean[i].getName();

			Object value;

			try {
				Field field = getField(name, entity);
				Class type = field.getType();
				value = convertValue(type, searchBean[i].getValue(),
						searchBean[i].getRelation());
			} catch (Exception e) {
				value = searchBean[i].getValue();
				beans[i] = new ExpressionBean(name,
						searchBean[i].getRelation(), value);
				return beans;
			}

			if (name.contains(".")) {
				String key1 = name.substring(0, name.lastIndexOf("."));
				String key2 = name.substring(name.lastIndexOf("."));
				name = aliasMap.get(key1) + key2;
			}
			beans[i] = new ExpressionBean(name, searchBean[i].getRelation(),
					value);
		}
		return beans;
	}

	private static Field getField(String name, Class entity)
			throws NoSuchElementException {
		Field field = null;
		try {
			if (name.contains(".")) {
				String name1 = name.substring(0, name.indexOf("."));
				String name2 = name.substring(name.indexOf(".") + 1);
				Class entity1 = entity.getDeclaredField(name1).getType();
				field = getField(name2, entity1);
			} else {
				field = entity.getDeclaredField(name);
			}
		} catch (NoSuchFieldException e) {
			try {
				field = entity.getSuperclass().getDeclaredField(name);
			} catch (NoSuchFieldException e1) {
				throw new NoSuchElementException("no such this element");
			}
		}
		return field;
	}

	private static String createAlias(HashMap<String, String> aliasMap) {
		int i = 0;
		String base = "t";
		String alias = base + i;
		while (aliasMap.containsValue(alias)) {
			i++;
			alias = base + i;
		}
		return alias;
	}

	@SuppressWarnings("unchecked")
	private static Object convertValue(Class type, String value, String relation) {
		Object object;
		if (relation.equals("between")) {
			List list = new ArrayList();
			String[] betValue = StringUtils.split(value, ';');
			if (type == java.util.Date.class) {
				if (betValue[0].length() == 10)
					betValue[0] += " 00:00:00";
				else
					betValue[0] += ":00";
				if (betValue[1].length() == 10)
					betValue[1] += " 23:59:59";
				else
					betValue[1] += ":59";
				try {
					list.add(DateUtils.parseDate(betValue[0],
							new String[] { "yyyy-MM-dd hh:mm:ss" }));
					list.add(DateUtils.parseDate(betValue[1],
							new String[] { "yyyy-MM-dd hh:mm:ss" }));
				} catch (Exception e) {
					throw new RasterFormatException("Unable to parse the Date");
				}
			} else {
				list.add(OgnlOps.convertValue(betValue[0], type));
				list.add(OgnlOps.convertValue(betValue[1], type));
			}
			object = list;
		} else if (relation.equals("in")) {
			List list = new ArrayList();
			for (String s : StringUtils.split(value, ';')) {
				list.add(OgnlOps.convertValue(s, type));
			}
			object = list;
		} else if (relation.equals("like")) {
			object = OgnlOps.convertValue("%" + value + "%", type);
		} else {
			if (type == java.util.Date.class) {
				if (relation.equals("<") || relation.equals("<=")) {
					if (value.length() == 10)
						value += " 23:59:59";
					else
						value += ":59";
				} else {
					if (value.length() == 10)
						value += " 00:00:00";
					else
						value += ":00";
				}
				try {
					object = DateUtils.parseDate(value,
							new String[] { "yyyy-MM-dd hh:mm:ss" });
				} catch (ParseException e) {
					throw new RasterFormatException("Unable to parse the Date");
				}
			} else if (type == java.sql.Timestamp.class) {
				try {
					object = DateUtils.parseDate(value,
							new String[] { "yyyy-MM-dd hh:mm:ss" });
				} catch (ParseException e) {
					throw new RasterFormatException("Unable to parse the Date");
				}
			} else {
				object = OgnlOps.convertValue(value, type);
			}
		}
		return object;
	}

	private static String[] detach(String str, char left, char right) {
		String string = str;
		if (string == null || string.equals(""))
			return null;
		List<String> list = new ArrayList<String>();
		if (StringUtils.indexOf(string, left) == -1
				|| StringUtils.indexOf(string, right) == -1)
			throw new RasterFormatException("Unable to parse the string: "
					+ string);
		while (StringUtils.indexOf(string, left) >= 0
				&& StringUtils.indexOf(string, right) >= 0) {
			int il = StringUtils.indexOf(string, left);
			int ir = StringUtils.indexOf(string, right);
			if (il > ir) {
				string = StringUtils.substring(string, right + 1);
				continue;
			}
			list.add(StringUtils.substring(string, il + 1, ir));
			string = StringUtils.substring(string, ir + 1);
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 检验输入是否为正确的日期格式(不含秒的任何情况),严格要求日期正确性,格式:yyyy-MM-dd HH:mm
	 * 
	 * @param sourceDate
	 * @return
	 */
	private static Date checkDate(String sourceDate) {
		if (sourceDate == null) {
			return null;
		}
		try {
			Date date = DateUtils.parseDate(sourceDate, new String[] {
					"yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd hh:mm", "yyyy-MM-dd" });
			return date;
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) {
		String str = "[name,yubo,like],[telephone,13810770810,=],[createDate,2009-8-02 00:00,>=],[interface_,1;2;3,in]";
		SearchBean[] search = getSearchTeam(str);
		for (SearchBean s : search)
			System.out.println("search =" + s.toString());
		System.out.println(getSearchMonth(search));
	}

}
