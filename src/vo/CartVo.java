package vo;

public class CartVo {
	private Integer amount;
	private Boolean order;
	private Integer sumPrice;
	
	private Long bookNo;
	private String bookTitle;
	
	private Long memberNo;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getOrder() {
		return order;
	}

	public void setOrder(Boolean order) {
		this.order = order;
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

	public Long getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Long memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Integer sumPrice) {
		this.sumPrice = sumPrice;
	}

	@Override
	public String toString() {
		return "CartBookVo [amount=" + amount + ", order=" + order + ", sumPrice=" + sumPrice + ", bookNo=" + bookNo
				+ ", bookTitle=" + bookTitle + ", memberNo=" + memberNo + "]";
	}
}
