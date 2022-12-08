package adapter.demo.model.DM0003;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0003Request_Body {
	/**
	 * 문서 ID
	 */
	private String docId = "";

	public DM0003Request_Body() {
	}

	public DM0003Request_Body(JsonNode jsonNode) {
		this.docId = jsonNode.path("docId").textValue();
	}

	@JsonProperty("docId")
	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0003Request_Body [");
		builder.append("docId=").append(docId);
		builder.append("]");
		return builder.toString();
	}
}