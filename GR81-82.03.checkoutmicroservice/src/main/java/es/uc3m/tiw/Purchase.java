package es.uc3m.tiw;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the purchase database table.
 * 
 */
@Entity
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int purchase_id;

	private String code_purchase;

	private int quantity;
	
	private int price_purchase;
	
	private String name_purchase;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_purchase")
	private Product product;

	//bi-directional many-to-one association to Userebag
	@ManyToOne
	@JoinColumn(name="user_purchase")
	private Userebag userebag;

	public Purchase() {
	}

	public int getId() {
		return this.purchase_id;
	}

	public void setId(int id) {
		this.purchase_id = id;
	}

	public String getCodePurchase() {
		return this.code_purchase;
	}

	public void setCodePurchase(String codePurchase) {
		this.code_purchase = codePurchase;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPricePurchase() {
		return this.price_purchase;
	}

	public void setPricePurchase(int pricePurchase) {
		this.price_purchase = pricePurchase;
	}

	public String getNamePurchase() {
		return this.name_purchase;
	}

	public void setNamePurchase(String namePurchase) {
		this.name_purchase = namePurchase;
	}
	
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Userebag getUserebag() {
		return this.userebag;
	}

	public void setUserebag(Userebag userebag) {
		this.userebag = userebag;
	}

}