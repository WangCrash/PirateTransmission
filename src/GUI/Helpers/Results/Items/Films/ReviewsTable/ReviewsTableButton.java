package GUI.Helpers.Results.Items.Films.ReviewsTable;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ReviewsTableButton extends JButton {
	private String reviewsKey;
	
	public ReviewsTableButton(String buttonText, String reviewsKey){
		super(buttonText);
		this.setReviewsKey(reviewsKey);
	}

	public String getReviewsKey() {
		return reviewsKey;
	}

	public void setReviewsKey(String reviewsKey) {
		this.reviewsKey = reviewsKey;
	}
	
	
}
