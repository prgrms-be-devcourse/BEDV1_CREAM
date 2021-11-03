package org.prgrms.cream.domain.product.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.prgrms.cream.domain.product.domain.Product;
import org.prgrms.cream.domain.product.domain.ProductOption;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

	private static final String NAME_DELETED = "isDeleted";
	private static final String NAME_OPTIONS = "options";
	private static final String NAME_BRAND = "brand";
	private static final String NAME_SIZE = "size";
	private static final String NAME_PRICE = "price";
	private static final String NAME_RELEASE_DATE = "releaseDate";
	private static final String SORT = "sort";
	private static final String SEPARATOR_COMMA = ",";

	public static Specification<Product> filterProduct(Map<String, String> filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(root.get(NAME_DELETED), 0));

			filter.forEach((key, value) -> {
				List<String> values = Arrays.asList(
					value.split(SEPARATOR_COMMA)
				);
				switch (key) {
					case NAME_BRAND:
						predicates.add(
							criteriaBuilder
								.in(root.get(key))
								.value(values));
						break;

					case NAME_SIZE:
						Join<Product, ProductOption> sizeJoin = root.join(
							NAME_OPTIONS,
							JoinType.INNER
						);
						predicates.add(
							criteriaBuilder
								.in(sizeJoin.get(key))
								.value(values));
						break;

					case NAME_PRICE:
						// TODO
						break;

					case SORT:
						if (NAME_RELEASE_DATE.equals(value)) {
							query.orderBy(criteriaBuilder.desc(root.get(value)));
						}
						break;
				}

			});
			query.distinct(true);
			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
}
