package adapter.demo.model.DM0002;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0002Request_Body {
	/**
	 * 시작 인덱스
	 */
	private Integer startIndex = null;
	/**
	 * 종료 인덱스
	 */
	private Integer endIndex = null;

	public DM0002Request_Body() {
	}

	public DM0002Request_Body(JsonNode jsonNode) {
		this.startIndex = jsonNode.path("startIndex").intValue();
		this.endIndex = jsonNode.path("endIndex").intValue();
	}

	@JsonProperty("startIndex")
	public Integer getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	@JsonProperty("endIndex")
	public Integer getEndIndex() {
		return this.endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0002Request_Body [");
		builder.append("startIndex=").append(startIndex);
		builder.append(", ");
		builder.append("endIndex=").append(endIndex);
		builder.append("]");
		return builder.toString();
	}
}