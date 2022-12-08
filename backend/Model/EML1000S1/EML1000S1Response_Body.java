package model.EML1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class EML1000S1Response_Body {
	/**
	* SelectOne으로 결과를 받기위한 변수
	*/
	private String authEmail = "";
	/**
	* 메일에 인증 코드 발송 여부
	*/
	private boolean Flag = false;

	public EML1000S1Response_Body() {
	}

	public EML1000S1Response_Body(JsonNode jsonNode) {
		this.authEmail = jsonNode.path("authEmail").textValue();
		this.Flag = jsonNode.path("Flag").booleanValue();
	}

	@JsonProperty("authEmail")
	public String getAuthEmail() {
		return this.authEmail;
	}

	public void setAuthEmail(String authEmail) {
		this.authEmail = authEmail;
	}

	@JsonProperty("Flag")
	public boolean getFlag() {
		return this.Flag;
	}

	public void setFlag(boolean Flag) {
		this.Flag = Flag;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EML1000S1Response_Body [");
		builder.append("Flag=").append(Flag);
		builder.append("]");
		return builder.toString();
	}
}