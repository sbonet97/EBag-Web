package es.uc3m.tiw;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;

	private String description;

	private String name_product;

	@Lob
	private byte[] picture; 

	private int price_product;

	private int stock;

	//bi-directional many-to-one association to Userebag
	@ManyToOne
	@JoinColumn(name="seller")
	private Userebag userebag;

	public Product() {
	}

	public int getProductId() {
		return this.product_id;
	}

	public void setProductId(int productId) {
		this.product_id = productId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNameProduct() {
		return this.name_product;
	}

	public void setNameProduct(String nameProduct) {
		this.name_product = nameProduct;
	}

	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public int getPriceProduct() {
		return this.price_product;
	}

	public void setPriceProduct(int priceProduct) {
		this.price_product = priceProduct;
	}

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public Userebag getUserebag() {
		return this.userebag;
	}

	public void setUserebag(Userebag userebag) {
		this.userebag = userebag;
	}
	
}