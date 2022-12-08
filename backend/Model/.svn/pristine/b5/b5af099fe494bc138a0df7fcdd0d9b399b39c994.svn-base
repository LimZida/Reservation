package model.SPL1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class SPL1000S1Response_Body {
	/**
	* 세션 일치 여부 확인(true : 일치 / false : 불일치)
	*/
	private boolean TokenFlag = false;
	/**
	* 회원 일련번호
	*/
	private Integer userSeqPK = null;

	public SPL1000S1Response_Body() {
	}

	public SPL1000S1Response_Body(JsonNode jsonNode) {
		this.TokenFlag = jsonNode.path("TokenFlag").booleanValue();
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
	}

	@JsonProperty("TokenFlag")
	public boolean getTokenFlag() {
		return this.TokenFlag;
	}

	public void setTokenFlag(boolean TokenFlag) {
		this.TokenFlag = TokenFlag;
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
		builder.append("SPL1000S1Response_Body [");
		builder.append("TokenFlag=").append(TokenFlag);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append("]");
		return builder.toString();
	}
}