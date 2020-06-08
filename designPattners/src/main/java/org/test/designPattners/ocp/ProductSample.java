package org.test.designPattners.ocp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*
 * In this class we will learn ocp (Open closed priciple)  (Open for extnesion and closed for moidification
 * Speicificaiton pattern implmeneted via OCP
 */

//Need to filter products  based on different criteria

enum Color {
	RED, GREEN, BLUE
}

enum Size {
	SMALL, MEDIUM, LARGE, HUGE
}

class Product {
	public String name;
	public Color color;
	public Size size;

	public Product(String name, Color color, Size size) {
		this.name = name;
		this.color = color;
		this.size = size;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", color=" + color + ", size=" + size + "]";
	}

}

//For every filter we have to modify the code which is against OCP
//You are modify thing code
class ProductFilter {

	public Stream<Product> filterByColor(List<Product> products, Color color) {

		return products.stream().filter(p -> p.color == color);
	}

	public Stream<Product> filterBySize(List<Product> products, Size size) {

		return products.stream().filter(p -> p.size == size);
	}

	public Stream<Product> filterBySizeAndColor(List<Product> products, Size size, Color color) {

		return products.stream().filter(p -> (p.size == size && p.color == color));
	}
}
//the problem of having to change the code for every requirement can be acheived by specificatio npattenre

interface Specification<T> {

	boolean isSatisified(T item);

}

class AndSpecification<T> implements Specification<T> {

	Specification<T> first;
	Specification<T> second;

	public AndSpecification(Specification first, Specification second) {
		this.first = first;
		this.second = second;

	}

	@Override
	public boolean isSatisified(T item) {

		return first.isSatisified(item) && second.isSatisified(item);
	}

}

class ColorSpecicification implements Specification<Product> {

	private Color color;

	public ColorSpecicification(Color color) {
		this.color = color;
	}

	@Override
	public boolean isSatisified(Product item) {

		return item.color == color;
	}

}

interface Filter<T> {

	Stream<T> filter(List<T> items, Specification<T> spec);
}

class BetterFilter implements Filter<Product> {

	@Override
	public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
		return items.stream().filter(p -> spec.isSatisified(p));
	}

}

public class ProductSample {

	public static void main(String[] args) {

		List<Product> products = new ArrayList<Product>();
		Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
		Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
		Product house = new Product("House", Color.BLUE, Size.LARGE);
		products.add(apple);
		products.add(tree);
		products.add(house);

		// fitler object based on size
		ProductFilter productFilter = new ProductFilter();
		productFilter.filterByColor(products, Color.GREEN).forEach(product -> System.out.println(product));
		System.out.println("New filter");
		BetterFilter bf = new BetterFilter();
		bf.filter(products, new ColorSpecicification(Color.GREEN)).forEach(product -> System.out.println(product));

	}

}
