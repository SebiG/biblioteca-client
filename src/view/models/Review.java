package view.models;

public class Review {

	private Book book;
	
	private int reviewID;
	private String review;
	private int userID;
	private int bookID;
	private String userName;
	private String reviewAsString;
	
	
	public int getReviewID() {
		return reviewID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	public Review(int reviewID, int bookID, int userID, String userName, String review) {
		super();
		this.reviewID = reviewID;
		this.bookID = bookID;
		this.userID = userID;
		this.setUserName(userName);
		this.review = review;
	}
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getBookID() {
		return bookID;
	}
	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReviewAsString() {
		return reviewAsString;
	}
	public void setReviewAsString(String reviewAsString) {
		this.reviewAsString = reviewAsString;
	}
}
