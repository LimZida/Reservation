package model.LGO1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class LGO1000S1Response_Body {
	/**
	* 로그아웃 여부(T/F)
	*/
	private boolean Flag = false;

	public LGO1000S1Response_Body() {
	}

	public LGO1000S1Response_Body(JsonNode jsonNode) {
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
		builder.append("LGO1000S1Response_Body [");
		builder.append("Flag=").append(Flag);
		builder.append("]");
		return builder.toString();
	}
}