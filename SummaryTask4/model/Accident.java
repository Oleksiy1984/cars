package ua.nure.orlovskyi.SummaryTask4.model;

public class Accident {
	
	private Integer id;
	private Double loss;
	private String comment;
	private Order order;
	
	
	
	public Double getLoss() {
		return loss;
	}
	public void setLoss(Double loss) {
		this.loss = loss;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "Accident [id=" + id + ", loss=" + loss + ", comment=" + comment + ", order=" + order + "]";
	}
	
	
	

}
