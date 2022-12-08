package model.MAN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class MAN1000S1Request_Body {
	/**
	* 회원 일련번호
	*/
	private int userSeqPK = 0;
	/**
	* 현재 날짜(YYYY-MM-DD)
	*/
	private String Date = "";

	public MAN1000S1Request_Body() {
	}

	public MAN1000S1Request_Body(JsonNode jsonNode) {
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.Date = jsonNode.path("Date").textValue();
	}

	@JsonProperty("userSeqPK")
	public int getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(int userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	@JsonProperty("Date")
	public String getDate() {
		return this.Date;
	}

	public void setDate(String Date) {
		this.Date = Date;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MAN1000S1Request_Body [");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("Date=").append(Date);
		builder.append("]");
		return builder.toString();
	}
}