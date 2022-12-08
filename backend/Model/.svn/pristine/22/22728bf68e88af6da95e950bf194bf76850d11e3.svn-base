package model.RES1330S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1330S1Response_Body {
	/**
	* 예약 취소 성공 실패 여부
	*/
	private boolean Flag = false;

	public RES1330S1Response_Body() {
	}

	public RES1330S1Response_Body(JsonNode jsonNode) {
		this.Flag = jsonNode.path("Flag").booleanValue();
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
		builder.append("RES1330S1Response_Body [");
		builder.append("Flag=").append(Flag);
		builder.append("]");
		return builder.toString();
	}
}