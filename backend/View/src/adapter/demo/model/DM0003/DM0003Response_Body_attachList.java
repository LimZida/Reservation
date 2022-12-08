package adapter.demo.model.DM0003;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 첨부파일 목록
 */
public class DM0003Response_Body_attachList {
	/**
	 * 파일명
	 */
	private String fileName = "";
	/**
	 * 저장경로
	 */
	private String filePath = "";

	public DM0003Response_Body_attachList() {
	}

	public DM0003Response_Body_attachList(JsonNode jsonNode) {
		this.fileName = jsonNode.path("fileName").textValue();
		this.filePath = jsonNode.path("filePath").textValue();
	}

	@JsonProperty("fileName")
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@JsonProperty("filePath")
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0003Response_Body_attachList [");
		builder.append("fileName=").append(fileName);
		builder.append(", ");
		builder.append("filePath=").append(filePath);
		builder.append("]");
		return builder.toString();
	}
}