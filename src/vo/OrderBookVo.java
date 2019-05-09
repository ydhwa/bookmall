package vo;

public class OrderBookVo {
	private Integer amount;
	
	private Long bookNo;
	private String bookTitle;
	
	private Long orderNo;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getBookNo() {
		return bookNo;
	}

	public void setBookNo(Long bookNo) {
		this.bookNo = bookNo;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "OrderBookVo [amount=" + amount + ", bookNo=" + bookNo + ", bookTitle=" + bookTitle + ", orderNo="
				+ orderNo + "]";
	}
}
