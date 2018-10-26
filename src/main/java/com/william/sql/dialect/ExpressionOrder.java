package com.william.sql.dialect;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class ExpressionOrder extends Order {

	private static final long serialVersionUID = 1L;

	private boolean ascending;
	private String propertyName;
	private String expression;

	public ExpressionOrder(String expression, String propertyName, boolean ascending) {
		super(propertyName, ascending);

		this.ascending = ascending;
		this.propertyName = propertyName;
		this.expression = expression;
	}

	/**
	 * Render the SQL fragment
	 *
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		String sql = super.toSqlString(criteria, criteriaQuery);

		sql = sql.replace("asc", "").replace("desc", "");

		return expression.replace(propertyName, sql) + (ascending ? "asc" : "desc");
	}

	/**
	 * Ascending order
	 *
	 * @param expression
	 * @param propertyName
	 * @return Order
	 */
	public static Order asc(String expression, String propertyName) {
		return new ExpressionOrder(expression, propertyName, true);
	}

	/**
	 * Descending order
	 *
	 * @param expression
	 * @param propertyName
	 * @return Order
	 */
	public static Order desc(String expression, String propertyName) {
		return new ExpressionOrder(expression, propertyName, false);
	}
}
