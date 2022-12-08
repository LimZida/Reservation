package model.LGN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class LGN1000S1Response_Body {
	/**
	* 성공 여부
	*/
	private boolean Flag = false;
	/**
	* 사용자 시퀀스
	*/
	private Integer userSeqPK = null;
	/**
	 * Jwt 토큰
	 */
	private String JwtToken = "";
	/**
	 * 사용자 이름
	 */
	private String userName = "";
	
	public LGN1000S1Response_Body() {
	}

	public LGN1000S1Response_Body(JsonNode jsonNode) {
		this.Flag = jsonNode.path("Flag").booleanValue();
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.JwtToken = jsonNode.path("JwtToken").textValue();
		this.userName= jsonNode.path("userName").textValue();
	}

	@JsonProperty("Flag")
	public boolean getFlag() {
		return this.Flag;
	}

	public void setFlag(boolean Flag) {
		this.Flag = Flag;
	}

	@JsonProperty("userSeqPK")
	public Integer getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(Integer userSeqPK) {
		this.userSeqPK = userSeqPK;
	}
	
	@JsonProperty("JwtToken")
	public String getJwtToken() {
		return this.JwtToken;
	}

	public void setJwtToken(String JwtToken) {
		this.JwtToken = JwtToken;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LGN1000S1Response_Body [");
		builder.append("Flag=").append(Flag);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("JwtToken=").append(JwtToken);
		builder.append("]");
		return builder.toString();
	}
	
	@JsonProperty("userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}