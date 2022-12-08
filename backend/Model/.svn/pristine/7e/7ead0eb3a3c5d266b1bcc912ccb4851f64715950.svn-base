package model.EML1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class EML1000S1Request_Body {
	/**
	* 사용자 메일
	*/
	private String inputEmail = "";

	public EML1000S1Request_Body() {
	}

	public EML1000S1Request_Body(JsonNode jsonNode) {
		this.inputEmail = jsonNode.path("inputEmail").textValue();
	}

	@JsonProperty("inputEmail")
	public String getInputEmail() {
		return this.inputEmail;
	}

	public void setInputEmail(String inputEmail) {
		this.inputEmail = inputEmail;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EML1000S1Request_Body [");
		builder.append("inputEmail=").append(inputEmail);
		builder.append("]");
		return builder.toString();
	}
}