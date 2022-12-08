package model.RES1110S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1110S1Request_Body {
	/**
	* 회원 일련번호
	*/
	private Integer userSeqPK = null;

	public RES1110S1Request_Body() {
	}

	public RES1110S1Request_Body(JsonNode jsonNode) {
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();

	}

	@JsonProperty("userSeqPK")
	public Integer getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(Integer userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1110S1Request_Body [");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append("]");
		return builder.toString();
	}
}