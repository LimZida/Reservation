package model.LGO1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class LGO1000S1Request_Body {
	/**
	 * 푸쉬 키
	 */
	private String pushKey = "";

	public LGO1000S1Request_Body() {
	}

	public LGO1000S1Request_Body(JsonNode jsonNode) {
		this.pushKey = jsonNode.path("pushKey").textValue();
	}

	@JsonProperty("pushKey")
	public String getPushKey() {
		return this.pushKey;
	}

	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LGO1000S1Request_Body [");
		builder.append("pushKey=").append(pushKey);
		builder.append("]");
		return builder.toString();
	}
}